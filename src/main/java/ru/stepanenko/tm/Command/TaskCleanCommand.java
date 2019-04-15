package ru.stepanenko.tm.Command;

import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.entity.Project;

import java.util.Scanner;

public class TaskCleanCommand extends AbstractCommand {
    private ITaskService taskService;
    private IProjectService projectService;

    public TaskCleanCommand(IProjectService projectService, ITaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @Override
    public String getName() {
        return "task-clean";
    }

    @Override
    public String getDescription() {
        return "Remove all tasks for selected project.";
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("List of projects:");
        projectService.findAll().forEach(e -> System.out.println("id: " + e.getId()));
        System.out.println("Please input project id:");
        String projectID = scanner.nextLine();
        Project project = (projectService.findOne(projectID));
        if (project != null) {
            taskService.clear(projectID);
            System.out.println("All tasks for project id:" + projectID + " clear.");
        } else {
            System.out.println("Project id: " + projectID + " does not found!");
        }
    }
}
