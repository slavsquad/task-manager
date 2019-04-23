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
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.entity.User;

@NoArgsConstructor
public final class TaskEditCommand extends AbstractCommand {
    @Override
    public String getName() {
        return "task-edit";
    }

    @Override
    public String getDescription() {
        return "Edit selected task.";
    }

    @Override
    public void execute() {
        @NotNull final IProjectService projectService = serviceLocator.getProjectService();
        @NotNull final ITaskService taskService = serviceLocator.getTaskService();
        @NotNull final IUserService userService = serviceLocator.getUserService();
        @NotNull final ITerminalService terminalService = serviceLocator.getTerminalService();
        @Nullable
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            System.out.println("This command available only login user!");
            return;
        }
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
        System.out.println("Please input project id:");
        String projectID = terminalService.nextLine();
        @Nullable
        Project project = projectService.findOne(projectID);
        if (project != null) {
            if (taskService.findAllByProjectId(projectID).isEmpty()) {
                System.out.println("List task for project id:" + projectID + " is empty!");
                return;
            }
            System.out.println("List of task:");
            taskService.findAllByProjectId(projectID).forEach(e -> System.out.println("id: " + e.getId()));
            System.out.println("Please input ID task for edit:");
            @NotNull
            String id = terminalService.nextLine();
            if (taskService.findOne(id) != null) {
                System.out.println("Input new task's name: ");
                @NotNull
                String name = terminalService.nextLine();
                System.out.println("Input new task's description: ");
                @NotNull
                String description = terminalService.nextLine();
                System.out.println("Input task's status(planned, in process, done: ");
                @NotNull
                String status = terminalService.nextLine();
                @Nullable
                Task task = taskService.edit(id, name, description, status);
                if (task != null) {
                    System.out.println("Task id: " + task.getId() + "edit is complete!");
                } else {
                    System.out.println("Task name or description can't be empty!");
                }

            } else {
                System.out.println("Task id: " + id + " is not found!");
            }

        } else {
            System.out.println("Project id " + projectID + " does not found!");
        }
    }
}
