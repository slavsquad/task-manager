package ru.stepanenko.tm.command.project;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.exception.UserNoLoginException;

import java.util.*;

public class ProjectListSortCommand extends AbstractCommand {
    @Override
    public String getName() {
        return "project-sort-list";
    }

    @Override
    public String getDescription() {
        return "Sorted list project by: order, dateStart, dateEnd or status";
    }

    @Override
    public void execute() throws UserNoLoginException {
        @NotNull final IProjectService projectService = serviceLocator.getProjectService();
        @NotNull final IUserService userService = serviceLocator.getUserService();
        @NotNull final ITerminalService terminalService = serviceLocator.getTerminalService();
        @Nullable
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) throw new UserNoLoginException();
        if (projectService.findAllByUserId(currentUser.getId()).isEmpty()) {
            System.out.println("List of projects is empty!");
            return;
        }
        System.out.println("Please input how to sort list project(order, dateStart, dateEnd, status)");
        @NotNull
        String comparator = terminalService.nextLine();
        Collection<Project> sortedProjects = projectService.sortAllByUserId(currentUser.getId(), comparator);
        if (sortedProjects != null) {
            System.out.println("Project list sorted by " + comparator + " :");
            sortedProjects.forEach(System.out::println);
        } else {
            System.out.println("Incorrect input parameter!");
        }
    }
}
