package ru.stepanenko.tm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.util.DateAdapter;
import ru.stepanenko.tm.util.DateFormatter;
import ru.stepanenko.tm.util.EnumUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class Task extends AbstractEntity implements Serializable {

    @NotNull
    private String projectId = "";

    @Nullable
    @JsonFormat(pattern = "yyyy-MM-dd")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date dateBegin = null;

    @Nullable
    @JsonFormat(pattern = "yyyy-MM-dd")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date dateEnd = null;

    @NotNull
    private String userId = "";


    @Getter
    @NotNull
    private Status status = Status.PLANNED;

    public Task(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final String projectId,
            @NotNull final String userId) {
        this.name = name;
        this.description = description;
        this.dateBegin = new Date();
        this.projectId = projectId;
        this.userId = userId;
    }

    public void setStatus(
            @NotNull final String status) {
        this.status = EnumUtil.stringToStatus(status);
    }

    @NotNull
    public String getStatus() {
        return status.toString();
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dateBegin=" + DateFormatter.format(dateBegin) +
                ", dateEnd=" + DateFormatter.format(dateEnd) +
                ", status=" + status +
                ", projectId='" + projectId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
