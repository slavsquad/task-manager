package ru.stepanenko.tm.exception;

import org.jetbrains.annotations.NotNull;
public class InputDataValidateException extends Exception {
    public InputDataValidateException(
            @NotNull final String message) {
        super(message);
    }
}
