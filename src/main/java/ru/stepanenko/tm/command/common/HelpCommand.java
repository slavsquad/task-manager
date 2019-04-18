package ru.stepanenko.tm.command.common;

import ru.stepanenko.tm.command.AbstractCommand;

public final class HelpCommand extends AbstractCommand {

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
                "project-edit: Edit selected project.\n" +
                "project-list: Show all project or selected project.\n" +
                "project-remove: Remove selected project.\n" +
                "task-clear: Remove all tasks.\n" +
                "task-create: Create new task.\n" +
                "task-edit: Edit selected task.\n" +
                "task-list: Show all tasks or selected task.\n" +
                "task-remove: Remove selected task.\n" +
                "user-login: Login user.\n" +
                "user-logout: Login logout.\n" +
                "user-profile-edit: Edit current user profile.\n" +
                "user-profile-view: View current user profile.\n" +
                "user-register: Register new user.\n" +
                "user-change-pass: Change user's password .\n" +
                "help: Show all commands.\n" +
                "exit: quit from application.");
    }
}
