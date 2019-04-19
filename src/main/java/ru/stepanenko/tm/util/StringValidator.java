package ru.stepanenko.tm.util;

public class StringValidator {

    public static boolean isNumeric(final String string) {
        return string.chars().allMatch(Character::isDigit);
    }

    public static boolean validate(String... strings) {
        for (String string : strings) {
            if (string == null || string.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
