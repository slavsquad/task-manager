package ru.stepanenko.tm.command.user;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.*;

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
    public void execute() throws AuthenticationSecurityException_Exception, IOException_Exception {
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @NotNull final SessionEndpoint sessionEndpoint = endpointServiceLocator.getSessionEndpoint();
        System.out.println("Please input user name:");
        @NotNull final String login = terminalService.nextLine();
        System.out.println("Please input password:");
        @NotNull final String password = terminalService.nextLine();
        endpointServiceLocator.setSession(sessionEndpoint.openSession(login, password));
        System.out.println("User '" + login + "' login in application!");
    }
}
