package ru.stepanenko.tm.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.model.dto.SessionDTO;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "app_session")
public class Session extends AbstractEntity {

    @Nullable
    private String signature;

    @Nullable
    private Date timestamp;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public SessionDTO getDTO() {
        @NotNull final SessionDTO dto = new SessionDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setDescription(description);
        dto.setSignature(signature);
        dto.setTimestamp(timestamp);
        dto.setUserId(user.getId());
        return dto;
    }
}
