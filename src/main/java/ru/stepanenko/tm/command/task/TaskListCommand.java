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

@NoArgsConstructor
public final class TaskListCommand extends AbstractCommand {
    @Override
    public String getName() {
        return "task-list";
    }

    @Override
    public String getDescription() {
        return "Show all tasks or selected task.";
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
            System.out.println("List of tasks is empty!");
            return;
        }
        if (projectService.findAllByUserId(currentUser.getId()).isEmpty()) {
            System.out.println("List of projects is empty!");
            return;
        }
        System.out.println("List of projects:");
        projectService.findAllByUserId(currentUser.getId()).forEach(e -> System.out.println("id: " + e.getId()));
        System.out.print("Please input project id or press ENTER for print all tasks:");
        @NotNull
        String projectId = terminalService.nextLine();
        if ("".equals(projectId)) {
            taskService.findAllByUserId(currentUser.getId()).forEach(System.out::println);
            System.out.println("All task for user: " + currentUser.getLogin() + " has removed!");
            return;
        }
        @Nullable
        Project project = projectService.findOne(projectId);
        if (project != null) {
            if (taskService.findAllByProjectId(projectId).isEmpty()) {
                System.out.println("List task for project id:" + projectId + " is empty!");
                return;
            }
            System.out.println("List of task:");
            taskService.findAllByProjectId(projectId).forEach(e -> System.out.println("id: " + e.getId()));
            System.out.print("Please input ID task or press ENTER for print all task: ");
            @NotNull
            String id = terminalService.nextLine();

            if ("".equals(id)) {
                taskService.findAllByProjectId(projectId).forEach(System.out::println);
            } else {
                if (taskService.findOne(id) != null) {
                    System.out.println(taskService.findOne(id));
                } else {
                    System.out.println("Task id: " + id + " does not found!");
                }
            }
        } else {
            System.out.println("Project id " + projectId + " does not found!");
        }
    }
}
