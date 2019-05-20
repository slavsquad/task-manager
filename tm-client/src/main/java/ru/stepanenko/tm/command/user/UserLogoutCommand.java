package ru.stepanenko.tm.command.user;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.*;

@NoArgsConstructor
public final class UserLogoutCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "user-logout";
    }

    @Override
    public String getDescription() {
        return "Logout user";
    }

    @Override
    public void execute(
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        @NotNull final UserEndpoint userEndpoint = endpointServiceLocator.getUserEndpoint();
        @NotNull final SessionDTO currentSession = endpointServiceLocator.getSessionDTO();
        @NotNull final SessionEndpoint sessionEndpoint = endpointServiceLocator.getSessionEndpoint();
        sessionEndpoint.validateSession(currentSession);
        @Nullable final String login = userEndpoint.getUserBySession(currentSession).getLogin();
        sessionEndpoint.closeSession(currentSession);
        System.out.println("Session id:" + currentSession.getId() + " was close!");
        System.out.println("User " + login + " logout!");
        endpointServiceLocator.setSessionDTO(null);
    }
}
