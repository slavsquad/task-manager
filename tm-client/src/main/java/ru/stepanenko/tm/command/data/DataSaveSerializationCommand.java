package ru.stepanenko.tm.command.data;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.ForbiddenActionException_Exception;
import ru.stepanenko.tm.endpoint.InvalidSessionException_Exception;
import ru.stepanenko.tm.endpoint.Session;
import ru.stepanenko.tm.endpoint.UserEndpoint;

public class DataSaveSerializationCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "data-save";
    }

    @Override
    public String getDescription() {
        return "Save user date to file.";
    }

    @Override
    public void execute() throws InvalidSessionException_Exception, ForbiddenActionException_Exception {
        @NotNull
        final UserEndpoint userEndpoint = endpointServiceLocator.getUserEndpoint();
        @NotNull
        final Session currentSession = endpointServiceLocator.getSession();
        userEndpoint.saveUserData(currentSession);
    }
}
