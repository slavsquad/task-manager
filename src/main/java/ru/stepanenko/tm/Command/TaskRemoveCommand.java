package ru.stepanenko.tm.Command;

import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.entity.Task;

import java.util.Scanner;

public class TaskRemoveCommand extends AbstractCommand {
    IProjectService projectService;
    ITaskService taskService;

    public TaskRemoveCommand(IProjectService projectService, ITaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("List of projects:");
        projectService.findAll().forEach(e -> System.out.println("id: " + e.getId()));
        System.out.println("Please input project id:");
        String projectID = scanner.nextLine();
        Project project = projectService.findOne(projectID);
        if (project != null) {
            taskService.findAllByProjectID(projectID).forEach(System.out::println);
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
