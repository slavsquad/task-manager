package ru.stepanenko.tm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Session extends AbstractEntity{
    @Nullable
    private String userId;
    @Nullable
    private String signature;
    @Nullable
    private Date timeStamp;
}