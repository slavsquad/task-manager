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
public final class TaskClearCommand extends AbstractCommand {
    @Override
    public String getName() {
        return "task-clear";
    }

    @Override
    public String getDescription() {
        return "Remove all tasks for selected project.";
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
        if (taskService.findAllByUserId(currentUser.getId()).isEmpty()) {
            System.out.println("List of task is empty!");
            return;
        }
        if (projectService.findAllByUserId(currentUser.getId()).isEmpty()) {
            System.out.println("List of projects is empty!");
            return;
        }

        System.out.println("List of projects:");
        projectService.findAllByUserId(currentUser.getId()).forEach(e -> System.out.println("id: " + e.getId()));
        System.out.print("Please input project id or press ENTER for remove all tasks:");
        @NotNull
        String id = terminalService.nextLine();
        if ("".equals(id)) {
            taskService.removeAllByUserId(currentUser.getId());
            System.out.println("All task for user: " + currentUser.getLogin() + " has removed!");
            return;
        }
        @Nullable
        Project project = (projectService.findOne(id));
        if (project != null) {
            if (taskService.findAllByProjectId(id).isEmpty()) {
                System.out.println("List task for project id:" + id + " is empty!");
                return;
            }
            taskService.removeAllByProjectId(id);
            System.out.println("All tasks for project id:" + id + " remove.");
        } else {
            System.out.println("Project id: " + id + " does not found!");
        }
    }
}
