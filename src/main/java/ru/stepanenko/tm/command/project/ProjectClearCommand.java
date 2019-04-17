package ru.stepanenko.tm.command.project;

import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.User;

public class ProjectClearCommand extends AbstractCommand {
    private IProjectService projectService;
    private IUserService userService;

    public ProjectClearCommand(IProjectService projectService, IUserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @Override
    public String getName() {
        return "project-clear";
    }

    @Override
    public String getDescription() {
        return "Remove all project.";
    }

    @Override
    public void execute() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            System.out.println("This command available only login user!");
            return;
        }

        projectService.clear();
        System.out.println("Project is clear!");
    }
}
