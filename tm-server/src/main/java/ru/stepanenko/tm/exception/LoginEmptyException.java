package ru.stepanenko.tm.exception;

public class LoginEmptyException extends AuthenticationSecurityException {
    public LoginEmptyException() {
        super("User login not must be empty!");
    }
}
