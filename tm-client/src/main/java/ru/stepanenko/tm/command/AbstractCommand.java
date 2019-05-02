package ru.stepanenko.tm.command;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.IEndpointServiceLocator;
import ru.stepanenko.tm.endpoint.AuthenticationSecurityException_Exception;
import ru.stepanenko.tm.endpoint.IOException_Exception;

import java.io.IOException;

public abstract class AbstractCommand {
    protected IEndpointServiceLocator endpointServiceLocator;

    public abstract String getName();

    public abstract String getDescription();

    public abstract void execute() throws AuthenticationSecurityException_Exception, IOException_Exception;

    public void setEndpointServiceLocator(@NotNull final IEndpointServiceLocator endpointServiceLocator) {
        this.endpointServiceLocator = endpointServiceLocator;
    }
}
