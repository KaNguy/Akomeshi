package org.akomeshi.json;

/*
 * Created by KaNguy - 07/04/2021
 * File org.akomeshi.json/JSONParseException.java
 */

// Utilities
import java.util.List;
import java.util.Stack;

public class JSONParseException extends RuntimeException {

    private final String message;

    public JSONParseException(String message) {
        this.message = message;
    }

    @SuppressWarnings("unchecked")
    public JSONParseException(Stack<JSONParser.State> stateStack, String message) {
        StringBuilder jsonTrace = new StringBuilder();
        for (int i = 0; i < stateStack.size(); i++) {
            String name = stateStack.get(i).propertyName;
            if (name == null) {
                List<Object> list = (List<Object>) stateStack.get(i).container;
                name = String.format("[%d]", list.size());
            }
            jsonTrace.append(name).append(i != stateStack.size() - 1 ? "." : "");
        }

        jsonTrace = new StringBuilder(jsonTrace.toString().equals("") ? "<root>" : "<root>." + jsonTrace);

        this.message = jsonTrace + ": " + message;
    }

    @Override
    public String getMessage() { return message; }
}
