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
    public void execute() throws AuthenticationSecurityException_Exception, InputDataValidateException_Exception  {
        @NotNull final UserEndpoint userEndpoint = endpointServiceLocator.getUserEndpoint();
        @NotNull final Session currentSession = endpointServiceLocator.getSession();
        endpointServiceLocator.getSessionEndpoint().validateSession(currentSession);
        @Nullable final User currentUser = userEndpoint.getUserBySession(currentSession);
        System.out.println("Welcome to user: " + userEndpoint.getUserBySession(currentSession) + " profile ");
        System.out.println(currentUser);
    }
}
