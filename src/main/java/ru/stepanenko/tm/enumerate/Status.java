package ru.stepanenko.tm.enumerate;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public enum Status implements Serializable {
    PLANNED("planned"), INPROCESS("in process"), DONE("done");
    @NotNull
    final String displayName;

    Status(@NotNull String status) {
        this.displayName = status;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
