package ru.stepanenko.tm.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Getter
@Setter
public abstract class AbstractEntityDTO {

    @NotNull
    protected String id = UUID.randomUUID().toString();

    @Nullable
    protected String name = null;

    @Nullable
    protected String description = null;
}
