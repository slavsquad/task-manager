package ru.stepanenko.tm.Command;

public abstract class AbstractCommand {

    public abstract String getName();
    public abstract String getDescription();
    public abstract void execute();
}
