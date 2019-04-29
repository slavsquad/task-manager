package ru.stepanenko.tm.exception;

public class PasswordEmptyException extends AuthenticationSecurityException {
    public PasswordEmptyException() {
        super("User password not must be empty!");
    }
}
