package ru.stepanenko.tm.command;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.IServiceLocator;

public abstract class AbstractCommand {
    protected IServiceLocator serviceLocator;

    public abstract String getName();

    public abstract String getDescription();

    public abstract void execute();

    public void setServiceLocator(@NotNull final IServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }
}
