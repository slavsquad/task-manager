package ru.stepanenko.tm.command.common;

import lombok.NoArgsConstructor;
import ru.stepanenko.tm.api.command.AbstractCommand;

@NoArgsConstructor
public final class HelpCommand implements AbstractCommand {

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
                "project-print: Print project by id.\n" +
                "project-remove: Remove selected project.\n" +
                "project-list-sort: Sorted list project by: order, dateBegin, dateEnd or status.\n" +
                "project-find: Find project by part of name or description.\n" +
                "task-clear: Remove all tasks.\n" +
                "task-createProject: Create new task.\n" +
                "task-edit: Edit selected task.\n" +
                "task-list: Show all tasks or selected task.\n" +
                "task-print: Print task by id.\n" +
                "task-remove: Remove selected task.\n" +
                "task-list-sort: Sorted list task by: order, dateBegin, dateEnd or status.\n" +
                "task-find: Find task by part of name or description.\n" +
                "user-login: Login user.\n" +
                "user-logout: Login logout.\n" +
                "user-profile-edit: Edit current user profile.\n" +
                "user-profile-view: View current user profile.\n" +
                "user-register: Register new user.\n" +
                "user-change-pass: Change user's password .\n" +
                "user-list: Print list of users.\n" +
                "help: Show all commands.\n" +
                "exit: quit from application.");
    }
}
