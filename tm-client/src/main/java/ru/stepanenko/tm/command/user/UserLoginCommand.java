package ru.stepanenko.tm.command.user;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.SessionEndpoint;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;

import java.io.IOException;

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
    public void execute() throws AuthenticationSecurityException, IOException {
        SessionEndpoint sessionEndpoint = new SessionEndpoint(endpointServiceLocator);
        System.out.println("Please input user name:");
        @NotNull
        String login = endpointServiceLocator.getTerminalService().nextLine();
        System.out.println("Please input password:");
        @NotNull
        String password = endpointServiceLocator.getTerminalService().nextLine();
        endpointServiceLocator.setSession(sessionEndpoint.openSession(login,password));
        System.out.println(endpointServiceLocator.getSession());
        System.out.println("User '" + login + "' login in application!");
    }
}
