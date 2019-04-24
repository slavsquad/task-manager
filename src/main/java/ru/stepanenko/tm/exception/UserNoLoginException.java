package ru.stepanenko.tm.exception;

public class UserNoLoginException extends AuthenticationSecurityException {
    public UserNoLoginException() {
        super("User does not login in application!\n" +
                "Run command: user-login");
    }
}
