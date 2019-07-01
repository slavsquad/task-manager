package ru.stepanenko.tm.model.dto;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SessionDTO  extends AbstractEntityDTO{

    @Nullable
    String userId;

    @Nullable
    String userLogin;


    @Nullable
    public String getUserId() {
        return userId;
    }

    public void setUserId(@Nullable String userId) {
        this.userId = userId;
    }

    @Nullable
    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(@Nullable String userLogin) {
        this.userLogin = userLogin;
    }

    public SessionDTO(
            @Nullable final String userId,
            @Nullable final String userLogin) {
        this.userId = userId;
        this.userLogin = userLogin;
    }

    public SessionDTO() {
    }
}
