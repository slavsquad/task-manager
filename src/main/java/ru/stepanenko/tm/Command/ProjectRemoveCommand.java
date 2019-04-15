package ru.stepanenko.tm.Command;

import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.entity.Project;

import java.util.Scanner;

public class ProjectRemoveCommand extends AbstractCommand {
    IProjectService projectService;

    public ProjectRemoveCommand(IProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public String getName() {
        return "project-remove";
    }

    @Override
    public String getDescription() {
        return "Remove selected project.";
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("List of projects:");
        projectService.findAll().forEach(e -> System.out.println("id: " + e.getId()));
        System.out.print("Please input project ID for remove: ");
        String projectID = scanner.nextLine();
        Project project = projectService.remove(projectID);
        if (project != null) {
            System.out.println("Project " + project.getName() + " is remove!");
        } else {
            System.out.println("Project id: " + projectID + " does not found!");
        }
    }
}
