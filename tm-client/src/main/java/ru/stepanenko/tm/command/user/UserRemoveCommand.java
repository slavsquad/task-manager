package ru.stepanenko.tm.command.user;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.AuthenticationSecurityException_Exception;
import ru.stepanenko.tm.endpoint.DataValidateException_Exception;
import ru.stepanenko.tm.endpoint.SessionDTO;
import ru.stepanenko.tm.endpoint.UserEndpoint;

public class UserRemoveCommand extends AbstractCommand {
    @Override
    public String getName() {
        return "user-remove";
    }

    @Override
    public String getDescription() {
        return "Remove selected user.";
    }

    @Override
    public void execute(
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        @NotNull final UserEndpoint userEndpoint = endpointServiceLocator.getUserEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @Nullable final SessionDTO currentSession = endpointServiceLocator.getSessionDTO();
        endpointServiceLocator.getSessionEndpoint().validateAdminSession(currentSession);
        System.out.println("Please input user ID for remove: ");
        @Nullable final String id = terminalService.nextLine();
        userEndpoint.removeOneUser(currentSession, id);
        System.out.println("Project with id:" + id + " is remove!");
    }
}
