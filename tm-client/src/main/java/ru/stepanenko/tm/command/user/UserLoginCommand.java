package ru.stepanenko.tm.command.user;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.*;
import ru.stepanenko.tm.util.HashUtil;

@NoArgsConstructor
public final class UserLoginCommand extends AbstractCommand {

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
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @NotNull final SessionEndpoint sessionEndpoint = endpointServiceLocator.getSessionEndpoint();
        System.out.println("Please input user name:");
        @Nullable final String login = terminalService.nextLine();
        System.out.println("Please input password:");
        @Nullable final String password = terminalService.nextLine();
        endpointServiceLocator.setSessionDTO(sessionEndpoint.openSession(login, HashUtil.md5(password)));
        System.out.println("User '" + login + "' login in application!");
    }
}
