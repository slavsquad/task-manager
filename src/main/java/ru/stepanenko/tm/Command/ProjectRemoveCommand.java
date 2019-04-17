package ru.stepanenko.tm.Command;

import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.entity.User;

import java.util.Scanner;

public class ProjectRemoveCommand extends AbstractCommand {
    IProjectService projectService;
    IUserService userService;

    public ProjectRemoveCommand(IProjectService projectService, IUserService userService) {
        this.projectService = projectService;
        this.userService = userService;
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
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            System.out.println("This command available only login user!");
            return;
        }
        if (projectService.findAll(currentUser.getId()).isEmpty()){
            System.out.println("List of projects is empty!");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("List of projects:");
        projectService.findAll(currentUser.getId()).forEach(e -> System.out.println("id: " + e.getId()));
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
