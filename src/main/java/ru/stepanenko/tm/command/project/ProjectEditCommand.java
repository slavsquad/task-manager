package ru.stepanenko.tm.command.project;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.User;

@NoArgsConstructor
public final class ProjectEditCommand extends AbstractCommand {
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
        @NotNull final IProjectService projectService = serviceLocator.getProjectService();
        @NotNull final IUserService userService = serviceLocator.getUserService();
        @NotNull final ITerminalService terminalService = serviceLocator.getTerminalService();
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
        System.out.println("List of projects:");
        projectService.findAllByUserId(currentUser.getId()).forEach(e -> System.out.println("id: " + e.getId()));
        System.out.print("Please input project ID for edit: ");
        String projectID = terminalService.nextLine();
        if (projectService.findOne(projectID) != null) {
            System.out.println("Input new project's name: ");
            String name = terminalService.nextLine();
            System.out.println("Input new project's description: ");
            String description = terminalService.nextLine();
            System.out.println("Input project's status(planned, in process, done): ");
            String status = terminalService.nextLine();
            if (projectService.edit(projectID, name, description, status) != null) {
                System.out.println("Project " + name + " is update!");
            } else {
                System.out.println("Project name, description or status can't be empty!");
            }
        } else {
            System.out.println("Project id: " + projectID + " does not found!");
        }
    }
}
