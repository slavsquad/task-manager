package ru.stepanenko.tm.Command;

import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.entity.User;

import java.util.Scanner;

public class TaskClearCommand extends AbstractCommand {
    private IProjectService projectService;
    private ITaskService taskService;
    private IUserService userService;

    public TaskClearCommand(IProjectService projectService, ITaskService taskService, IUserService userService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.userService = userService;
    }

    @Override
    public String getName() {
        return "task-clear";
    }

    @Override
    public String getDescription() {
        return "Remove all tasks for selected project.";
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
        System.out.println("Please input project id:");
        String projectID = scanner.nextLine();
        Project project = (projectService.findOne(projectID));
        if (project != null) {
            if (taskService.findAllByProjectID(projectID).isEmpty()) {
                System.out.println("List task for project id:" + projectID + " is empty!");
                return;
            }
            taskService.clear(projectID);
            System.out.println("All tasks for project id:" + projectID + " clear.");
        } else {
            System.out.println("Project id: " + projectID + " does not found!");
        }
    }
}
