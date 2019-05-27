package ru.stepanenko.tm.command.user;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.command.ICommand;
import ru.stepanenko.tm.endpoint.*;

import javax.inject.Inject;

@NoArgsConstructor
public final class UserProfileViewCommand implements ICommand {

    @Inject
    @NotNull
    UserEndpoint userEndpoint;

    @Inject
    @NotNull
    SessionEndpoint sessionEndpoint;

    @Inject
    @NotNull
    ISessionService sessionService;

    @Override
    public String getName() {
        return "user-profile-view";
    }

    @Override
    public String getDescription() {
        return "View user profile.";
    }

    @Override
    public void execute(
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        @NotNull final SessionDTO currentSession = sessionService.getCurrentSession();
        sessionEndpoint.validateSession(currentSession);
        @Nullable final UserDTO currentUser = userEndpoint.getUserBySession(currentSession);
        System.out.println("Welcome to user: " + currentUser.getLogin() + " profile ");
        System.out.println("Login: " + currentUser.getLogin());
        System.out.println("Role: " + currentUser.getRole());
    }
}
