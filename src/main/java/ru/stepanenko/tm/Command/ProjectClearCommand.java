package ru.stepanenko.tm.Command;

import ru.stepanenko.tm.api.service.IProjectService;

public class ProjectClearCommand extends AbstractCommand {
    private IProjectService projectService;

    public ProjectClearCommand(IProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public String getName() {
        return "task-clear";
    }

    @Override
    public String getDescription() {
        return "Remove all tasks.";
    }

    @Override
    public void execute() {
        projectService.clear();
        System.out.println("Project is clear!");
    }
}
