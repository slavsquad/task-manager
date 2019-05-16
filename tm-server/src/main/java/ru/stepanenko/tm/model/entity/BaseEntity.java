package ru.stepanenko.tm.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
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
    @Column
    protected Date dateBegin = null;

    @Nullable
    @Column
    protected Date dateEnd = null;

    @NotNull
    @Column
    @Enumerated(EnumType.STRING)
    protected Status status = Status.PLANNED;

    @Nullable
    @JoinColumn(name = "user_id")
    @ManyToOne(cascade = CascadeType.ALL)
    protected User user;
}
