package ru.stepanenko.tm.exception;

public class LoginOrPasswordEmpty extends AuthenticationSecurityException {
    public LoginOrPasswordEmpty() {
        super("User login or password must not be empty!");
    }
}
