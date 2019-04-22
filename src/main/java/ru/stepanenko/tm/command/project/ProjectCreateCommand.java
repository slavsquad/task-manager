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
public final class ProjectCreateCommand extends AbstractCommand {
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
        @NotNull final IProjectService projectService = serviceLocator.getProjectService();
        @NotNull final IUserService userService = serviceLocator.getUserService();
        @NotNull final ITerminalService terminalService = serviceLocator.getTerminalService();
        @Nullable
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            System.out.println("This command available only login user!");
            return;
        }
        System.out.println("Please input project name:");
        @NotNull
        String name = terminalService.nextLine();
        System.out.println("Please input project description:");
        @NotNull
        String description = terminalService.nextLine();
        if (projectService.create(name, description, currentUser.getId()) != null) {
            System.out.println("Project " + name + " is create!");
        } else {
            System.out.println("Project " + name + " does not create!");
            System.out.println("Project name or description does not empty");
        }
    }
}
