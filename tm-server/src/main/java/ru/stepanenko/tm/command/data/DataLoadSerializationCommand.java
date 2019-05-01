package ru.stepanenko.tm.command.data;

import ru.stepanenko.tm.api.endpoint.IUserEndpoint;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.UserEndpoint;
import ru.stepanenko.tm.exception.ForbiddenActionException;
import ru.stepanenko.tm.exception.session.InvalidSessionException;

public class DataLoadSerializationCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "data-load";
    }

    @Override
    public String getDescription() {
        return "Load user date from file.";
    }

    @Override
    public void execute() throws InvalidSessionException, ForbiddenActionException {
        IUserEndpoint userEndpoint = new UserEndpoint(serviceLocator.getUserService(),serviceLocator.getSessionService());
        userEndpoint.loadUserData(serviceLocator.getSession());
    }
}
