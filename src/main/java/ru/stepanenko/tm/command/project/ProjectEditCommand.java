package ru.stepanenko.tm.command.project;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.User;

import java.util.Scanner;

public final class ProjectEditCommand extends AbstractCommand {
    @NotNull
    private final IProjectService projectService;
    @NotNull
    private final IUserService userService;

    public ProjectEditCommand(@NotNull final IProjectService projectService, @NotNull final IUserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @Override
    public String getName() {
        return "project-edit";
    }

    @Override
    public String getDescription() {
        return "Edit selected project.";
    }

    @Override
    public void execute() {
        @Nullable
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            System.out.println("This command available only login user!");
            return;
        }
        if (projectService.findAllByUserId(currentUser.getId()).isEmpty()) {
            System.out.println("List of projects is empty!");
            return;
        }
        @NotNull
        Scanner scanner = new Scanner(System.in);
        System.out.println("List of projects:");
        projectService.findAllByUserId(currentUser.getId()).forEach(e -> System.out.println("id: " + e.getId()));
        System.out.print("Please input project ID for edit: ");
        String projectID = scanner.nextLine();
        if (projectService.findOne(projectID) != null) {
            System.out.println("Input new project's name: ");
            String name = scanner.nextLine();
            System.out.println("Input new project's description: ");
            String description = scanner.nextLine();
            if (projectService.edit(projectID, name, description) != null) {
                System.out.println("Project " + name + " is update!");
            } else {
                System.out.println("Project name or description can't be empty!");
            }
        } else {
            System.out.println("Project id: " + projectID + " does not found!");
        }
    }
}
