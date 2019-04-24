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

import java.util.Collection;

public class ProjectFindCommand extends AbstractCommand {
    @Override
    public String getName() {
        return "project-find";
    }

    @Override
    public String getDescription() {
        return "Find project by part of name or description.";
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
        System.out.println("Please input part of project's name for search:");
        @NotNull
        String name = terminalService.nextLine();
        System.out.println("Please input part of project's description for search:");
        @NotNull
        String description = terminalService.nextLine();

        Collection<Project> findProjects = projectService.findAllByPartOfNameOrDescription(name, description, currentUser.getId());
        if (findProjects != null) {
            System.out.println("Find projects by part of name '" + name + "' or part of description '" + description + "' :");
            findProjects.forEach(System.out::println);
        } else {
            System.out.println("Projects does not found or parameters were empty!");
        }
    }
}
