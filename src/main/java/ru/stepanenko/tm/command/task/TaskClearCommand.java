package ru.stepanenko.tm.command.task;

import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.entity.User;

import java.util.Scanner;

public final class TaskClearCommand extends AbstractCommand {
    private final IProjectService projectService;
    private final ITaskService taskService;
    private final IUserService userService;

    public TaskClearCommand(final IProjectService projectService, final ITaskService taskService, final IUserService userService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.userService = userService;
    }

    @Override
    public String getName() {
        return "task-clear";
    }

    @Override
    public String getDescription() {
        return "Remove all tasks for selected project.";
    }

    @Override
    public void execute() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            System.out.println("This command available only login user!");
            return;
        }
        if (taskService.findAllByUserID(currentUser.getId()).isEmpty()) {
            System.out.println("List of task is empty!");
            return;
        }
        if (projectService.findAllByUserId(currentUser.getId()).isEmpty()) {
            System.out.println("List of projects is empty!");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("List of projects:");
        projectService.findAllByUserId(currentUser.getId()).forEach(e -> System.out.println("id: " + e.getId()));
        System.out.print("Please input project id or press ENTER for remove all tasks:");
        String id = scanner.nextLine();
        if ("".equals(id)) {
            taskService.removeAllByUserId(currentUser.getId());
            System.out.println("All task for user: " + currentUser.getLogin() + " has removed!");
            return;
        }
        Project project = (projectService.findOne(id));
        if (project != null) {
            if (taskService.findAllByProjectID(id).isEmpty()) {
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
