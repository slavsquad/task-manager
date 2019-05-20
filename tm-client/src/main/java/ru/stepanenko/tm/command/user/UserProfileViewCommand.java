package ru.stepanenko.tm.command.user;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.*;

@NoArgsConstructor
public final class UserProfileViewCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "user-profile-view";
    }

    @Override
    public String getDescription() {
        return "View user profile.";
    }

    @Override
    public void execute() throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        @NotNull final UserEndpoint userEndpoint = endpointServiceLocator.getUserEndpoint();
        @NotNull final SessionDTO currentSession = endpointServiceLocator.getSessionDTO();
        endpointServiceLocator.getSessionEndpoint().validateSession(currentSession);
        @Nullable final UserDTO currentUser = userEndpoint.getUserBySession(currentSession);
        System.out.println("Welcome to user: " + currentUser.getLogin() + " profile ");
        System.out.println("Login: " + currentUser.getLogin());
        System.out.println("Role: " + currentUser.getRole());
    }
}
