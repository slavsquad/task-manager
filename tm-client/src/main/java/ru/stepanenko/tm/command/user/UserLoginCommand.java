package ru.stepanenko.tm.command.user;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.ICommand;
import ru.stepanenko.tm.endpoint.*;
import ru.stepanenko.tm.util.HashUtil;

import javax.inject.Inject;

@NoArgsConstructor
public final class UserLoginCommand implements ICommand {

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
        return "user-login";
    }

    @Override
    public String getDescription() {
        return "User login";
    }

    @Override
    public void execute(
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        System.out.println("Please input user name:");
        @Nullable final String login = terminalService.nextLine();
        System.out.println("Please input password:");
        @Nullable final String password = terminalService.nextLine();
        sessionService.setCurrentSession(sessionEndpoint.openSession(login, HashUtil.md5(password)));
        System.out.println("User '" + login + "' login in application!");
    }
}
