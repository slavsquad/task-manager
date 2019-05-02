package ru.stepanenko.tm.command.user;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.AuthenticationSecurityException_Exception;
import ru.stepanenko.tm.endpoint.Session;
import ru.stepanenko.tm.endpoint.UserEndpoint;

@NoArgsConstructor
public final class UserRegisterCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "user-register";
    }

    @Override
    public String getDescription() {
        return "Registration new user.";
    }

    @Override
    public void execute() throws AuthenticationSecurityException_Exception {
        @NotNull final UserEndpoint userEndpoint = endpointServiceLocator.getUserEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @NotNull final Session currentSession = endpointServiceLocator.getSession();
        endpointServiceLocator.getSessionEndpoint().validateSession(currentSession);
        System.out.println("Please input user name:");
        @NotNull
        String login = terminalService.nextLine();
        if (userEndpoint.findUserByLogin(currentSession, login) == null) {
            System.out.println("Please input password:");
            @NotNull
            String password = terminalService.nextLine();
            System.out.println("Please input user role(admin or user):");
            @NotNull
            String role = terminalService.nextLine();
            if (userEndpoint.createUser(currentSession, login, password, role) != null) {
                System.out.println("User " + login + " created!");
            } else {
                System.out.println("User " + login + " is not created!");
            }
        } else {
            System.out.println("User name already exist!");
        }
    }
}
