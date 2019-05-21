package ru.stepanenko.tm.command.user;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.*;

import javax.inject.Inject;

@NoArgsConstructor
public final class UserLogoutCommand implements AbstractCommand {

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
        return "user-logout";
    }

    @Override
    public String getDescription() {
        return "Logout user";
    }

    @Override
    public void execute(
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        @NotNull final SessionDTO currentSession = sessionService.getCurrentSession();
        sessionEndpoint.validateSession(currentSession);
        @Nullable final String login = userEndpoint.getUserBySession(currentSession).getLogin();
        sessionEndpoint.closeSession(currentSession);
        System.out.println("Session id:" + currentSession.getId() + " was close!");
        System.out.println("User " + login + " logout!");
        sessionService.setCurrentSession(null);
    }
}
