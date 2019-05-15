package ru.stepanenko.tm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.enumerate.Status;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "User")
public class Project extends AbstractEntity implements Serializable {


    @Nullable
    @Column(name = "dataBegin")
    private Date dateBegin = null;

    @Nullable
    @Column(name = "dataEnd")
    private Date dateEnd = null;

    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status = Status.PLANNED;

    @Nullable
    private Collection<Task> tasks;

    @NotNull
    public String getStatus() {
        return status.toString();
    }
}
