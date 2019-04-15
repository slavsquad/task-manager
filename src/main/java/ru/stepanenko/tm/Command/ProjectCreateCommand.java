package ru.stepanenko.tm.Command;

import ru.stepanenko.tm.api.service.IProjectService;

import java.util.Scanner;

public class ProjectCreateCommand extends AbstractCommand {
    IProjectService projectService;

    public ProjectCreateCommand(IProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public String getName() {
        return "project-create";
    }

    @Override
    public String getDescription() {
        return "Create new project.";
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input project name:");
        String name = scanner.nextLine();
        System.out.println("Please input project description:");
        String description = scanner.nextLine();
        if (projectService.create(name, description) != null) {
            System.out.println("Project " + name + " is create!");
        } else {
            System.out.println("Project " + name + " does not create!");
            System.out.println("Project name or description does not empty");
        }
    }
}
