package ru.stepanenko.tm.command.user;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.*;

@NoArgsConstructor
public final class UserChangePasswordCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "user-change-pass";
    }

    @Override
    public String getDescription() {
        return "Change user password.";
    }

    @Override
    public void execute() throws AuthenticationSecurityException_Exception, InputDataValidateException_Exception  {
        @NotNull final UserEndpoint userEndpoint = endpointServiceLocator.getUserEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @NotNull final Session currentSession = endpointServiceLocator.getSession();
        endpointServiceLocator.getSessionEndpoint().validateAdminSession(currentSession);
        System.out.println("Please input user name:");
        @NotNull final String login = terminalService.nextLine();
        @Nullable final User user = userEndpoint.findUserByLogin(currentSession, login);
        System.out.println("Please input password:");
        @NotNull
        String password = terminalService.nextLine();
        userEndpoint.changeUserPassword(currentSession, user.getId(), user.getLogin(), password, user.getRole().toString());
        System.out.println("User " + login + " password changed!");
    }
}
