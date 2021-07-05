package org.akomeshi.json;

import java.util.*;

public class JSONParser {
    static class State {
        final String propertyName;
        final Object container;
        final Token type;

        State(String propertyName, Object container, Token type) {
            this.propertyName = propertyName;
            this.container = container;
            this.type = type;
        }
    }

    private static int indexOfSpecial(String str, int start) {
        do {
            start++;
        } while (str.charAt(start) != '"' && str.charAt(start) != '\\');
        return start;
    }

    private enum Token {
        ARRAY,
        OBJECT,
        HEURISTIC,
        KEY,
        STRING,
        NUMBER,
        CONSTANT
    }

    private static class ExtractedString {
        int sourceEnd;
        String str;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseAsMap(String JSONData) {
        return (Map<String, Object>) parse(JSONData);
    }

    @SuppressWarnings("unchecked")
    public static Object parse(String JSONData) {
        Stack<State> stateStack = new Stack<>();
        Token currentType;

        boolean expectingComma = false, expectingColon = false;
        int fieldStart = 0, end = JSONData.length() - 1, i = 0;
        String propertyName = null;
        Object currentContainer = null;
        Object value;
        char current;

        try {
            while (Utility.isWhitespace((current = JSONData.charAt(i)))) i++;
        } catch (IndexOutOfBoundsException e) {
            throw new JSONParseException("Provided JSON string did not contain a value");
        }

        if (current == '{') {
            currentType = Token.OBJECT;
            currentContainer = new HashMap<>();
            i++;
        } else if (current == '[') {
            currentType = Token.ARRAY;
            currentContainer = new ArrayList<>();
            propertyName = null;
            i++;
        } else if (current == '"') {
            currentType = Token.STRING;
            fieldStart = i;
        } else if (Utility.isLetter(current)) {
            currentType = Token.CONSTANT;
            fieldStart = i;
        } else if (Utility.isNumberStarter(current)) {
            currentType = Token.NUMBER;
            fieldStart = i;
        } else {
            throw new JSONParseException(stateStack, String.format("Unexpected character \"%s\" instead of root value", current));
        }

        while (i <= end) {
            current = JSONData.charAt(i);
            switch (currentType) {
                case KEY:
                    try {
                        ExtractedString extracted = extractString(JSONData, i);
                        i = extracted.sourceEnd;
                        propertyName = extracted.str;
                    } catch (StringIndexOutOfBoundsException e) {
                        throw new JSONParseException(stateStack, "String did not have ending quote.");
                    }
                    currentType = Token.HEURISTIC;
                    expectingColon = true;
                    i++;
                    break;
                case STRING:
                    try {
                        ExtractedString extracted = extractString(JSONData, i);
                        i = extracted.sourceEnd;
                        value = extracted.str;
                    } catch (StringIndexOutOfBoundsException e) {
                        throw new JSONParseException(stateStack, "String did not have ending quote.");
                    }

                    if (currentContainer == null) {
                        return value;
                    } else {
                        expectingComma = true;
                        if (currentContainer instanceof Map) {
                            ((Map<String, Object>) currentContainer).put(propertyName, value);
                            currentType = Token.OBJECT;
                        } else {
                            ((List<Object>) currentContainer).add(value);
                            currentType = Token.ARRAY;

                        }
                    }

                    i++;
                    break;
                case NUMBER: {
                    boolean withDecimal = false;
                    boolean withE = false;
                    do {
                        current = JSONData.charAt(i);
                        if (!withDecimal && current == '.') {
                            withDecimal = true;
                        } else if (!withE && (current == 'e' || current == 'E')) {
                            withE = true;
                        } else if (!Utility.isNumberStarter(current) && current != '+') {
                            break;
                        }
                    } while (i++ < end);

                    String valueString = JSONData.substring(fieldStart, i);
                    try {
                        if (withDecimal || withE) {
                            value = Double.valueOf(valueString);
                        } else {
                            value = Long.valueOf(valueString);
                        }
                    } catch (NumberFormatException e) {
                        throw new JSONParseException(stateStack, "\"" + valueString +
                                "\" expected to be a number, but wasn't");
                    }

                    if (currentContainer == null) {
                        return value;
                    } else {
                        expectingComma = true;
                        if (currentContainer instanceof Map) {
                            ((Map<String, Object>) currentContainer).put(propertyName, value);
                            currentType = Token.OBJECT;
                        } else {
                            ((List<Object>) currentContainer).add(value);
                            currentType = Token.ARRAY;
                        }
                    }
                    break;
                }
                case CONSTANT:
                    while (Utility.isLetter(current) && i++ < end) {
                        current = JSONData.charAt(i);
                    }

                    String valueString = JSONData.substring(fieldStart, i);
                    switch (valueString) {
                        case "false":
                            value = false;
                            break;
                        case "true":
                            value = true;
                            break;
                        case "null":
                            value = null;
                            break;
                        default:
                            if (currentContainer instanceof Map) {
                                stateStack.push(new State(propertyName, currentContainer, Token.OBJECT));
                            } else if (currentContainer instanceof List) {
                                stateStack.push(new State(propertyName, currentContainer, Token.ARRAY));
                            }
                            throw new JSONParseException(stateStack, String.format("\"%s\" is not a valid constant.", valueString));
                    }

                    if (currentContainer == null) {
                        return value;
                    } else {
                        expectingComma = true;
                        if (currentContainer instanceof Map) {
                            ((Map<String, Object>) currentContainer).put(propertyName, value);
                            currentType = Token.OBJECT;
                        } else {
                            ((List<Object>) currentContainer).add(value);
                            currentType = Token.ARRAY;

                        }
                    }
                    break;
                case HEURISTIC:
                    while (Utility.isWhitespace(current) && i++ < end) {
                        current = JSONData.charAt(i);
                    }

                    if (current != ':' && expectingColon) {
                        stateStack.push(new State(propertyName, currentContainer, Token.OBJECT));
                        throw new JSONParseException(stateStack, "was not followed by a colon");
                    }

                    if (current == ':') {
                        if (expectingColon) {
                            expectingColon = false;
                            i++;
                        } else {
                            stateStack.push(new State(propertyName, currentContainer, Token.OBJECT));
                            throw new JSONParseException(stateStack, "was followed by too many colons");
                        }
                    } else if (current == '"') {
                        currentType = Token.STRING;
                        fieldStart = i;
                    } else if (current == '{') {
                        stateStack.push(new State(propertyName, currentContainer, Token.OBJECT));
                        currentType = Token.OBJECT;
                        currentContainer = new HashMap<>();
                        i++;
                    } else if (current == '[') {
                        stateStack.push(new State(propertyName, currentContainer, Token.OBJECT));
                        currentType = Token.ARRAY;
                        currentContainer = new ArrayList<>();
                        i++;
                    } else if (Utility.isLetter(current)) {
                        currentType = Token.CONSTANT;
                        fieldStart = i;
                    } else if (Utility.isNumberStarter(current)) {
                        currentType = Token.NUMBER;
                        fieldStart = i;
                    } else {
                        throw new JSONParseException(stateStack, String.format("unexpected character \"%s\" instead of object value", current));
                    }
                    break;
                case OBJECT:
                    while (Utility.isWhitespace(current) && i++ < end) {
                        current = JSONData.charAt(i);
                    }

                    if (current == ',') {
                        if (expectingComma) {
                            expectingComma = false;
                            i++;
                        } else {
                            stateStack.push(new State(propertyName, currentContainer, Token.OBJECT));
                            throw new JSONParseException(stateStack, "was followed by too many commas");
                        }
                    } else if (current == '"') {
                        if (expectingComma) {
                            stateStack.push(new State(propertyName, currentContainer, Token.OBJECT));
                            throw new JSONParseException(stateStack, "was not followed by a comma");
                        }

                        currentType = Token.KEY;
                        fieldStart = i;
                    } else if (current == '}') {
                        if (!stateStack.isEmpty()) {
                            State upper = stateStack.pop();
                            Object upperContainer = upper.container;
                            String parentName = upper.propertyName;
                            currentType = upper.type;

                            if (upperContainer instanceof Map) {
                                ((Map<String, Object>) upperContainer).put(parentName, currentContainer);
                            } else {
                                ((List<Object>) upperContainer).add(currentContainer);
                            }
                            currentContainer = upperContainer;
                            expectingComma = true;
                            i++;
                        } else {
                            return currentContainer;
                        }
                    } else if (!Utility.isWhitespace(current)) {
                        throw new JSONParseException(stateStack, String.format("Unexpected character '%s' where a property name was expected.", current));
                    }
                    break;
                case ARRAY:
                    while (Utility.isWhitespace(current) && i++ < end) {
                        current = JSONData.charAt(i);
                    }

                    if (current != ',' && current != ']' && current != '}' && expectingComma) {
                        stateStack.push(new State(null, currentContainer, Token.ARRAY));
                        throw new JSONParseException(stateStack, "was not preceded by a comma");
                    }

                    if (current == ',') {
                        if (expectingComma) {
                            expectingComma = false;
                            i++;
                        } else {
                            stateStack.push(new State(null, currentContainer, Token.ARRAY));
                            throw new JSONParseException(stateStack, "was preceded by too many commas");
                        }
                    } else if (current == '"') {
                        currentType = Token.STRING;
                        fieldStart = i;
                    } else if (current == '{') {
                        stateStack.push(new State(null, currentContainer, Token.ARRAY));
                        currentType = Token.OBJECT;
                        currentContainer = new HashMap<>();
                        i++;
                    } else if (current == '[') {
                        stateStack.push(new State(null, currentContainer, Token.ARRAY));
                        currentType = Token.ARRAY;
                        currentContainer = new ArrayList<>();
                        i++;
                    } else if (current == ']') {
                        if (!stateStack.isEmpty()) {
                            State upper = stateStack.pop();
                            Object upperContainer = upper.container;
                            String parentName = upper.propertyName;
                            currentType = upper.type;

                            if (upperContainer instanceof Map) {
                                ((Map<String, Object>) upperContainer).put(parentName, currentContainer);
                            } else {
                                ((List<Object>) upperContainer).add(currentContainer);
                            }
                            currentContainer = upperContainer;
                            expectingComma = true;
                            i++;
                        } else {
                            return currentContainer;
                        }
                    } else if (Utility.isLetter(current)) {
                        currentType = Token.CONSTANT;
                        fieldStart = i;
                    } else if (Utility.isNumberStarter(current)) {
                        // Is a number
                        currentType = Token.NUMBER;
                        fieldStart = i;
                    } else {
                        stateStack.push(new State(propertyName, currentContainer, Token.ARRAY));
                        throw new JSONParseException(stateStack, String.format("Unexpected character \"%s\" instead of array value", current));
                    }
                    break;
            }
        }

        throw new JSONParseException("Root element was not executed correctly (Missing a ']' or '}')");
    }

    private static ExtractedString extractString(String JSONData, int fieldStart) {
        StringBuilder builder = new StringBuilder();
        while (true) {
            int i = indexOfSpecial(JSONData, fieldStart);
            char c = JSONData.charAt(i);
            if (c == '"') {
                builder.append(JSONData.substring(fieldStart + 1, i));
                ExtractedString val = new ExtractedString();
                val.sourceEnd = i;
                val.str = builder.toString();
                return val;
            } else if (c == '\\') {
                builder.append(JSONData.substring(fieldStart + 1, i));

                c = JSONData.charAt(i + 1);
                switch (c) {
                    case '"': {
                        builder.append('\"');
                        break;
                    }
                    case '\\': {
                        builder.append('\\');
                        break;
                    }
                    case '/': {
                        builder.append('/');
                        break;
                    }
                    case 'b': {
                        builder.append('\b');
                        break;
                    }
                    case 'f': {
                        builder.append('\f');
                        break;
                    }
                    case 'n': {
                        builder.append('\n');
                        break;
                    }
                    case 'r': {
                        builder.append('\r');
                        break;
                    }
                    case 't': {
                        builder.append('\t');
                        break;
                    }
                    case 'u': {
                        builder.append(Character.toChars(
                                Integer.parseInt(JSONData.substring(i + 2, i + 6), 16)));
                        fieldStart = i + 5;
                        continue;
                    }
                }
                fieldStart = i + 1;
            } else {
                throw new IndexOutOfBoundsException();
            }
        }
    }
}