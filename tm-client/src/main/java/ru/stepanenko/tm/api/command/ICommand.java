package ru.stepanenko.tm.api.command;

import ru.stepanenko.tm.endpoint.AuthenticationSecurityException_Exception;
import ru.stepanenko.tm.endpoint.DataValidateException_Exception;

public interface ICommand {

    public abstract String getName();

    public abstract String getDescription();

    public abstract void execute(
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception;
}
