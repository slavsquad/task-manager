package ru.stepanenko.tm.exception;

public class ForbiddenActionException extends AuthenticationSecurityException {
    public ForbiddenActionException() {
        super("Forbidden action for your role!");
    }
}
