package ru.stepanenko.tm.exception;

public class WrongLoginOrPasswordException extends AuthenticationSecurityException {
    public WrongLoginOrPasswordException() {
        super("Wrong login or password!");
    }
}
