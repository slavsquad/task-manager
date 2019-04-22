package ru.stepanenko.tm.entity;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class AbstractEntity {
    @NotNull
    protected String id = UUID.randomUUID().toString();
    @NotNull
    protected String name = "";
    @NotNull
    protected String description = "";

    public AbstractEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(@NotNull final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(@NotNull final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(@NotNull final String description) {
        this.description = description;
    }
}
