package ru.stepanenko.tm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor

public class Session extends AbstractEntity {

    @Nullable
    private String userId=null;

    @Nullable
    private String signature=null;

    @Nullable
    private Date timestamp=null;

}
