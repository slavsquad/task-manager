package ru.stepanenko.tm.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.enumerate.Status;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class BaseEntityDTO extends AbstractEntityDTO implements Serializable {

    @Nullable
    protected Date dateBegin = new Date();

    @Nullable
    protected Date dateEnd = null;

    @Nullable
    protected String userId = "";

    @Getter
    protected Status status = Status.PLANNED;
}
