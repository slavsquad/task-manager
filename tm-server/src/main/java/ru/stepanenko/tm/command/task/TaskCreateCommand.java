package ru.stepanenko.tm.command.task;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.exception.UserNoLoginException;

@NoArgsConstructor
public final class TaskCreateCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "task-createProject";
    }

    @Override
    public String getDescription() {
        return "Create new task.";
    }

    @Override
    public void execute() throws UserNoLoginException {
        @NotNull final IProjectService projectService = serviceLocator.getProjectService();
        @NotNull final ITaskService taskService = serviceLocator.getTaskService();
        @NotNull final IUserService userService = serviceLocator.getUserService();
        @NotNull final ITerminalService terminalService = serviceLocator.getTerminalService();
        @Nullable
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) throw new UserNoLoginException();
        if (projectService.findAllByUserId(currentUser.getId()).isEmpty()) {
            System.out.println("List of projects is empty!");
            return;
        }
        System.out.println("List of projects:");
        projectService.findAllByUserId(currentUser.getId()).forEach(e -> System.out.println("id: " + e.getId()));
        System.out.println("Please input project id:");
        String projectID = terminalService.nextLine();
        @Nullable
        Project project = (projectService.findOne(projectID));
        if (project != null) {
            System.out.println("Please input task name:");
            String name = terminalService.nextLine();
            System.out.println("Please input task description:");
            String description = terminalService.nextLine();
            if (taskService.create(name, description, projectID, currentUser.getId()) != null) {
                System.out.println("Task " + name + " is createProject!");
            } else {
                System.out.println("Task " + name + " does not createProject!");
                System.out.println("Task name or description can't be empty!");
            }
        } else {
            System.out.println("Project id: " + projectID + " does not found!");
        }
    }
}
