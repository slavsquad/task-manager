package ru.stepanenko.tm.command.common;

import ru.stepanenko.tm.command.AbstractCommand;

public class ExitCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "Quit from application.";
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
