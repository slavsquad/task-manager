package ru.stepanenko.tm.command.project;

import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.User;

import java.util.Scanner;

public final class ProjectCreateCommand extends AbstractCommand {
    private final IProjectService projectService;
    private final IUserService userService;

    public ProjectCreateCommand(final IProjectService projectService, final IUserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @Override
    public String getName() {
        return "project-create";
    }

    @Override
    public String getDescription() {
        return "Create new project.";
    }

    @Override
    public void execute() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            System.out.println("This command available only login user!");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input project name:");
        String name = scanner.nextLine();
        System.out.println("Please input project description:");
        String description = scanner.nextLine();
        if (projectService.create(name, description, currentUser.getId()) != null) {
            System.out.println("Project " + name + " is create!");
        } else {
            System.out.println("Project " + name + " does not create!");
            System.out.println("Project name or description does not empty");
        }
    }
}
