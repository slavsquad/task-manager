package ru.stepanenko.tm.command.project;

import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.entity.User;

import java.util.Scanner;

public final class ProjectListCommand extends AbstractCommand {
    private final IProjectService projectService;
    private final IUserService userService;

    public ProjectListCommand(final IProjectService projectService, final IUserService userService) {
        this.projectService = projectService;
        this.userService = userService;
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
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            System.out.println("This command available only login user!");
            return;
        }
        if (projectService.findAllByUserId(currentUser.getId()).isEmpty()){
            System.out.println("List of projects is empty!");
            return;
        }
        System.out.println("List of projects:");
        projectService.findAllByUserId(currentUser.getId()).forEach(e -> System.out.println("id: " + e.getId()));
        System.out.print("Press ENTER for print all project or input project id: ");
        Scanner scanner = new Scanner(System.in);
        String projectID = scanner.nextLine();
        //list for all projects
        if ("".equals(projectID)) {
            projectService.findAllByUserId(currentUser.getId()).forEach(System.out::println);
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
