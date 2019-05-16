package ru.stepanenko.tm.model.dto;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.enumerate.Status;

import java.util.Date;

public class BaseEntityDTO extends AbstractEntityDTO {

    @Nullable
    protected Date dateBegin = null;

    @Nullable
    protected Date dateEnd = null;

    @NotNull
    protected String userId = "";

    @Getter
    protected Status status = Status.PLANNED;
}
