package ru.stepanenko.tm.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.enumerate.Status;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public class BaseEntity extends AbstractEntity implements Serializable {

    @Nullable
    protected Date dateBegin = null;

    @Nullable
    protected Date dateEnd = null;

    @Nullable
    @Enumerated(EnumType.STRING)
    protected Status status = Status.PLANNED;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "user_id")
    protected User user;
}
