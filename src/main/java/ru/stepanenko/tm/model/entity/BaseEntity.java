package ru.stepanenko.tm.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.enumerate.Status;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class BaseEntity extends AbstractEntity implements Serializable {

    @Nullable
    protected Date dateBegin = new Date();

    @Nullable
    protected Date dateEnd = null;

    @Nullable
    protected String userId = "";

    @Getter
    protected Status status = Status.PLANNED;
}
