package ru.stepanenko.tm.util;

import org.jetbrains.annotations.Nullable;

public class StringValidator {

    public static boolean isNumeric(@Nullable final String string) {
        return string.chars().allMatch(Character::isDigit);
    }

    public static boolean validate(@Nullable String... strings) {
        for (String string : strings) {
            if (string == null || string.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
