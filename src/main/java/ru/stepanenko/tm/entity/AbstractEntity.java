package ru.stepanenko.tm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractEntity {

    @NotNull
    protected String id = UUID.randomUUID().toString();
    @NotNull
    protected String name = "";
    @NotNull
    protected String description = "";
}
