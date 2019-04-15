package ru.stepanenko.tm.Command;

import ru.stepanenko.tm.api.service.IProjectService;

import java.util.Scanner;

public class ProjectEditCommand extends AbstractCommand {
    private IProjectService projectService;

    public ProjectEditCommand(IProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public String getName() {
        return "project-edit";
    }

    @Override
    public String getDescription() {
        return "Edit selected project.";
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("List of projects:");
        projectService.findAll().forEach(e -> System.out.println("id: " + e.getId()));
        System.out.print("Please input project ID for edit: ");
        String projectID = scanner.nextLine();
        if (projectService.findOne(projectID) != null) {
            System.out.println("Input new project's name: ");
            String name = scanner.nextLine();
            System.out.println("Input new project's description: ");
            String description = scanner.nextLine();
            if (projectService.edit(projectID, name, description) != null) {
                System.out.println("Project " + name + " is update!");
            } else {
                System.out.println("Project name or description can't be empty!");
            }
        } else {
            System.out.println("Project id: " + projectID + " does not found!");
        }
    }
}
