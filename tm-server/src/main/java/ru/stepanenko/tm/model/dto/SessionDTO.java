package ru.stepanenko.tm.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class SessionDTO extends AbstractEntityDTO {

    @Nullable
    private Date timestamp = null;

    @Nullable
    private String signature = null;

    @Nullable
    private String userId = null;

}
