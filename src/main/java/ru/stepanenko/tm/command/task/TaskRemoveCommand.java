package ru.stepanenko.tm.command.task;

import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.entity.User;

import java.util.Scanner;

public final class TaskRemoveCommand extends AbstractCommand {
    private final IProjectService projectService;
    private final ITaskService taskService;
    private final IUserService userService;

    public TaskRemoveCommand(final IProjectService projectService, final ITaskService taskService, final IUserService userService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.userService = userService;
    }

    @Override
    public String getName() {
        return "task-remove";
    }

    @Override
    public String getDescription() {
        return "Remove task from selected project.";
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
        System.out.println("Please input project id:");
        String projectID = scanner.nextLine();
        Project project = projectService.findOne(projectID);
        if (project != null) {
            if (taskService.findAllByProjectID(projectID).isEmpty()) {
                System.out.println("List task for project id:" + projectID + " is empty!");
                return;
            }
            System.out.println("List of task:");
            taskService.findAllByProjectID(projectID).forEach(e -> System.out.println("id: " + e.getId()));
            System.out.println("Please input ID task for remove:");
            String id = scanner.nextLine();
            Task task = taskService.remove(id);
            if (task != null) {
                System.out.println("Task id: " + task.getId() + " is remove!");
            } else {
                System.out.println("Task id: " + id + " is not found!");
            }
        } else {
            System.out.println("Project id " + projectID + " does not found!");
        }
    }
}
