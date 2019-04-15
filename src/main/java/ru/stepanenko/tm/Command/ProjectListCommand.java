package ru.stepanenko.tm.Command;

import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.entity.Project;

import java.util.Scanner;

public class ProjectListCommand extends AbstractCommand {
    private IProjectService projectService;

    public ProjectListCommand(IProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public String getName() {
        return "project-list";
    }

    @Override
    public String getDescription() {
        return "Show all project or selected project.";
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("List of projects:");
        projectService.findAll().forEach(e -> System.out.println("id: " + e.getId()));
        System.out.print("Press ENTER for print all project or input project id: ");
        String projectID = scanner.nextLine();
        //list for all projects
        if ("".equals(projectID)) {
            projectService.findAll().forEach(System.out::println);
        } else {//list for selected project
            Project project = projectService.findOne(projectID);
            if (project == null) {
                System.out.println("Project with id: " + projectID + " does not exist!");
            } else {
                System.out.println(project);
            }
        }
    }
}
