package ru.stepanenko.tm.Command;

public class HelpCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Show information for all commands.";
    }

    @Override
    public void execute() {
        System.out.println("project-clear: Remove all projects.\n" +
                "project-create: Create new project.\n" +
                "project-list: Show all project or selected project.\n" +
                "project-remove: Remove selected project.\n" +
                "task-clear: Remove all tasks.\n" +
                "task-create: Create new task.\n" +
                "task-list: Show all tasks or selected task.\n" +
                "task-remove: Remove selected task.\n" +
                "help: Show all commands.\n" +
                "exit: quit from application.");
    }
}
