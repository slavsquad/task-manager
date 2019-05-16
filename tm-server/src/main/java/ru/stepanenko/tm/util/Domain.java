package ru.stepanenko.tm.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.Task;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "Domain")
@XmlAccessorType(XmlAccessType.FIELD)
public class Domain implements Serializable {

    @NotNull
    @XmlElementWrapper(name = "projects")
    @XmlElement(name = "projects")
    private Collection<Project> projects;
    @NotNull
    @XmlElementWrapper(name = "tasks")
    @XmlElement(name = "tasks")
    private Collection<Task> tasks;
}
