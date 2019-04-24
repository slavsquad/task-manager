package ru.stepanenko.tm.exception;

public class ForbiddenCommandException extends AuthenticationSecurityException {
    public ForbiddenCommandException() {
        super("Forbidden command for your role!");
    }
}
