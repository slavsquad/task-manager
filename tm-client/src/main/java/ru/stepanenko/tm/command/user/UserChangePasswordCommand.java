package ru.stepanenko.tm.command.user;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.api.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.*;
import ru.stepanenko.tm.util.HashUtil;

import javax.inject.Inject;

@NoArgsConstructor
public final class UserChangePasswordCommand implements AbstractCommand {

    @Inject
    @NotNull
    UserEndpoint userEndpoint;

    @Inject
    @NotNull
    SessionEndpoint sessionEndpoint;

    @Inject
    @NotNull
    ISessionService sessionService;

    @Inject
    @NotNull
    ITerminalService terminalService;

    @Override
    public String getName() {
        return "user-change-pass";
    }

    @Override
    public String getDescription() {
        return "Change user password.";
    }

    @Override
    public void execute(
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        @Nullable final SessionDTO currentSession = sessionService.getCurrentSession();
        sessionEndpoint.validateAdminSession(currentSession);
        System.out.println("Please input user name:");
        @Nullable final String login = terminalService.nextLine();
        @Nullable final UserDTO user = userEndpoint.findUserByLogin(currentSession, login);
        System.out.println("Please input password:");
        @Nullable
        String password = terminalService.nextLine();
        user.setPassword(HashUtil.md5(password));
        userEndpoint.changeUserPassword(currentSession, user);
        System.out.println("User " + login + " password changed!");
    }
}
