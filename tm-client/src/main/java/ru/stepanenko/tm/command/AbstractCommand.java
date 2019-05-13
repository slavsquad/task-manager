package ru.stepanenko.tm.command;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.IEndpointServiceLocator;
import ru.stepanenko.tm.endpoint.AuthenticationSecurityException_Exception;
import ru.stepanenko.tm.endpoint.InputDataValidateException_Exception;

public abstract class AbstractCommand {
    protected IEndpointServiceLocator endpointServiceLocator;

    public abstract String getName();

    public abstract String getDescription();

    public abstract void execute() throws AuthenticationSecurityException_Exception, InputDataValidateException_Exception;

    public void setEndpointServiceLocator(@NotNull final IEndpointServiceLocator endpointServiceLocator) {
        this.endpointServiceLocator = endpointServiceLocator;
    }
}
