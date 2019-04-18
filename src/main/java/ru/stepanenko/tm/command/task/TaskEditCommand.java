package ru.stepanenko.tm.command.task;

import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.entity.User;

import java.util.Scanner;

public final class TaskEditCommand extends AbstractCommand {
    private final IProjectService projectService;
    private final  ITaskService taskService;
    private final IUserService userService;

    public TaskEditCommand(final IProjectService projectService, final ITaskService taskService, final IUserService userService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.userService = userService;
    }

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
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            System.out.println("This command available only login user!");
            return;
        }
        if (projectService.findAllByUserId(currentUser.getId()).isEmpty()){
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
            System.out.println("Please input ID task for edit:");
            String id = scanner.nextLine();
            if (taskService.findOne(id) != null) {
                System.out.println("Input new task's name: ");
                String name = scanner.nextLine();
                System.out.println("Input new task's description: ");
                String description = scanner.nextLine();
                Task task = taskService.edit(id, name, description);
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
