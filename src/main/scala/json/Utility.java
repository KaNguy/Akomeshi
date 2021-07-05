package org.akomeshi.json;

public class Utility {
    public static boolean isWhitespace(char character) {
        return character == ' ' || character == '\n' || character == '\t';
    }

    public static boolean isLetter(char character) {
        return character >= 'a' && character <= 'z';
    }

    public static boolean isNumberStarter(char character) {
        return (character >= '0' && character <= '9') || character == '-';
    }
}
