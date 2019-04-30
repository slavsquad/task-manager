package ru.stepanenko.tm.exception.session;

public class InvalidSessionException extends Exception {
    public InvalidSessionException(String message) {
        super("Session is invalid. "+message+"\nPlease re-login!");
    }
}
