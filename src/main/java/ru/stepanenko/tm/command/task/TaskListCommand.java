package ru.stepanenko.tm.command.task;

import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.entity.User;

import java.util.Scanner;

public final class TaskListCommand extends AbstractCommand {
    private final IProjectService projectService;
    private final ITaskService taskService;
    private final IUserService userService;

    public TaskListCommand(final IProjectService projectService, final ITaskService taskService, final IUserService userService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.userService = userService;
    }

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
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            System.out.println("This command available only login user!");
            return;
        }
        if (taskService.findAllByUserID(currentUser.getId()).isEmpty()) {
            System.out.println("List of tasks is empty!");
            return;
        }
        if (projectService.findAllByUserId(currentUser.getId()).isEmpty()) {
            System.out.println("List of projects is empty!");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("List of projects:");
        projectService.findAllByUserId(currentUser.getId()).forEach(e -> System.out.println("id: " + e.getId()));
        System.out.print("Please input project id or press ENTER for print all tasks:");
        String projectId = scanner.nextLine();
        if ("".equals(projectId)) {
            taskService.findAllByUserID(currentUser.getId()).forEach(System.out::println);
            System.out.println("All task for user: " + currentUser.getLogin() + " has removed!");
            return;
        }
        Project project = projectService.findOne(projectId);
        if (project != null) {
            if (taskService.findAllByProjectID(projectId).isEmpty()) {
                System.out.println("List task for project id:" + projectId + " is empty!");
                return;
            }
            System.out.println("List of task:");
            taskService.findAllByProjectID(projectId).forEach(e -> System.out.println("id: " + e.getId()));
            System.out.print("Please input ID task or press ENTER for print all task: ");
            String id = scanner.nextLine();

            if ("".equals(id)) {
                taskService.findAllByProjectID(projectId).forEach(System.out::println);
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
