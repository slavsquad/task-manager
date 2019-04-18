package ru.stepanenko.tm.enumerate;

public enum Role {
    USER("user"), ADMINISTRATOR("admin");

    private final String displayName;

    Role(final String role){
        displayName = role;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
