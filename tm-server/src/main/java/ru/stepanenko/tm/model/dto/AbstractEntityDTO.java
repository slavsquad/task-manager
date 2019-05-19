package ru.stepanenko.tm.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractEntityDTO implements Serializable {

    @NotNull
    protected String id =  UUID.randomUUID().toString();

    @Nullable
    protected String name = null;

    @Nullable
    protected String description = null;
}
