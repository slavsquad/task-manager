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
public final class UserRegisterCommand implements AbstractCommand {

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
        return "user-register";
    }

    @Override
    public String getDescription() {
        return "Registration new user.";
    }

    @Override
    public void execute(
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        @Nullable final SessionDTO currentSession = sessionService.getCurrentSession();
        sessionEndpoint.validateAdminSession(currentSession);
        System.out.println("Please input user name:");
        @Nullable final String login = terminalService.nextLine();
        System.out.println("Please input password:");
        @Nullable final String password = terminalService.nextLine();
        System.out.println("Please input user role(admin or user):");
        @Nullable final String role = terminalService.nextLine();
        @Nullable final UserDTO user = new UserDTO();
        user.setLogin(login);
        user.setPassword(HashUtil.md5(password));
        try {
            user.setRole(Role.valueOf(role.toUpperCase()));
            userEndpoint.createUser(currentSession, user);
            System.out.println("User " + login + " created!");
        } catch (Exception e) {
            throw new DataValidateException_Exception(e.getMessage());
        }
    }
}
