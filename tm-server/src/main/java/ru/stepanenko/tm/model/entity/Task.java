package ru.stepanenko.tm.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
public class Task extends BaseEntity implements Serializable {

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;
}
