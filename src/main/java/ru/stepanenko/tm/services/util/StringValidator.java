package ru.stepanenko.tm.services.util;

public class StringValidator {
    public static boolean validate(String string) {
        if ("".equals(string) || string == null){
            return false;
        } else {
            return true;
        }
    }

    public static boolean isNumeric(String string){
        return string.chars().allMatch( Character::isDigit );
    }
}
