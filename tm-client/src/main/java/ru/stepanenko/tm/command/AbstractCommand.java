package ru.stepanenko.tm.command;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.IEndpointServiceLocator;
import ru.stepanenko.tm.endpoint.ForbiddenActionException_Exception;
import ru.stepanenko.tm.endpoint.InvalidSessionException_Exception;

import java.io.IOException;

public abstract class AbstractCommand {
    protected IEndpointServiceLocator endpointServiceLocator;

    public abstract String getName();

    public abstract String getDescription();

    public abstract void execute() throws InvalidSessionException_Exception, IOException, ForbiddenActionException_Exception;

    public void setEndpointServiceLocator(@NotNull final IEndpointServiceLocator endpointServiceLocator) {
        this.endpointServiceLocator = endpointServiceLocator;
    }
}
