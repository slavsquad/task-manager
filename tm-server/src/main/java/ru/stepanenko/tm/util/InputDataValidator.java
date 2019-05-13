package ru.stepanenko.tm.util;

import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.exception.InputDataValidateException;

import java.util.HashMap;
import java.util.Map;

public class InputDataValidator {
    public static void validateString(
            @Nullable final String... strings)
            throws InputDataValidateException {
        for (String string : strings) {
            if (string == null || string.isEmpty())
                throw new InputDataValidateException("Input parameter not must be null or empty!");
        }
    }

    public static void validateRole(
            @Nullable final String role)
            throws InputDataValidateException {
        if (EnumUtil.stringToRole(role) == null) throw new InputDataValidateException("Incorrect input role!");
    }

    public static void validateStatus(
            @Nullable final String status)
            throws InputDataValidateException {
        if (EnumUtil.stringToStatus(status)== null) throw new InputDataValidateException("Incorrect input status!");
    }

    public static void validateParameter(
            @Nullable final String parameter)
            throws InputDataValidateException {
        Map<String, String> parameters = new HashMap<>(4);
        parameters.put("order", "order");
        parameters.put("dateBegin", "dateBegin");
        parameters.put("dateEnd", "dateEnd");
        parameters.put("status", "status");
        if (parameters.get(parameter) == null) throw new InputDataValidateException("Incorrect input parameter!");
    }
}
