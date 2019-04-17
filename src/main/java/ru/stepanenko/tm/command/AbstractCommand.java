package ru.stepanenko.tm.command;

public abstract class AbstractCommand {

    public abstract String getName();
    public abstract String getDescription();
    public abstract void execute();
}
