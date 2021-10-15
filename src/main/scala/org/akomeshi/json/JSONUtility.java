package org.akomeshi.json;

/**
 * Created by KaNguy - 07/04/2021
 * File org.akomeshi.json/JSONParser.java
 */

public class JSONUtility {
    /**
     * Checks if a character is a whitespace character.
     * @param character Character.
     * @return Boolean.
     */
    public static boolean isWhitespace(char character) {
        return character == ' ' || character == '\n' || character == '\t';
    }

    /**
     * Checks if a character is a letter.
     * @param character Character.
     * @return Boolean.
     */
    public static boolean isLetter(char character) {
        return character >= 'a' && character <= 'z';
    }

    /**
     * Checks if a character is a number.
     * @param character Character.
     * @return Boolean.
     */
    public static boolean isNumberStarter(char character) {
        return (character >= '0' && character <= '9') || character == '-';
    }
}
