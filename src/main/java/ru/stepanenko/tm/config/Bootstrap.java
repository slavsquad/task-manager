package ru.stepanenko.tm.config;

import ru.stepanenko.tm.Command.*;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.repository.ProjectRepository;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.repository.TaskRepository;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.service.ProjectService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.service.TaskService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Bootstrap {
    private static final String PROJECT_CLEAR = "project-clear";
    private static final String PROJECT_CREATE = "project-create";
    private static final String PROJECT_LIST = "project-list";
    private static final String PROJECT_REMOVE = "project-remove";
    private static final String PROJECT_EDIT = "project-edit";
    private static final String TASK_CLEAR = "task-clear";
    private static final String TASK_CREATE = "task-create";
    private static final String TASK_LIST = "task-list";
    private static final String TASK_REMOVE = "task-remove";
    private static final String TASK_EDIT = "task-edit";
    private static final String HELP = "help";
    private static final String EXIT = "exit";

    public void init() {
        IProjectService projectService;
        ITaskService taskService;
        Map<String, AbstractCommand> commandMap;
        IProjectRepository IProjectRepository = new ProjectRepository();
        ITaskRepository ITaskRepository = new TaskRepository();
        projectService = new ProjectService(IProjectRepository);
        taskService = new TaskService(ITaskRepository);
        commandMap = new HashMap<>();

        ProjectClearCommand projectClearCommand = new ProjectClearCommand(projectService);
        ProjectCreateCommand projectCreateCommand = new ProjectCreateCommand(projectService);
        ProjectListCommand projectListCommand = new ProjectListCommand(projectService);
        ProjectRemoveCommand projectRemoveCommand = new ProjectRemoveCommand(projectService);
        ProjectEditCommand projectEditCommand = new ProjectEditCommand(projectService);
        TaskCleanCommand taskCleanCommand = new TaskCleanCommand(projectService, taskService);
        TaskCreateCommand taskCreateCommand = new TaskCreateCommand(projectService, taskService);
        TaskListCommand taskListCommand = new TaskListCommand(projectService, taskService);
        TaskRemoveCommand taskRemoveCommand = new TaskRemoveCommand(projectService, taskService);
        TaskEditCommand taskEditCommand = new TaskEditCommand(projectService, taskService);
        HelpCommand helpCommand = new HelpCommand();
        ExitCommand exitCommand = new ExitCommand();

        commandMap.put(projectClearCommand.getName(), projectClearCommand);
        commandMap.put(projectCreateCommand.getName(), projectCreateCommand);
        commandMap.put(projectListCommand.getName(), projectListCommand);
        commandMap.put(projectRemoveCommand.getName(), projectRemoveCommand);
        commandMap.put(projectEditCommand.getName(), projectEditCommand);
        commandMap.put(taskCleanCommand.getName(), taskCleanCommand);
        commandMap.put(taskCreateCommand.getName(), taskCreateCommand);
        commandMap.put(taskListCommand.getName(), taskListCommand);
        commandMap.put(taskRemoveCommand.getName(), taskRemoveCommand);
        commandMap.put(taskEditCommand.getName(), taskEditCommand);
        commandMap.put(helpCommand.getName(), helpCommand);
        commandMap.put(exitCommand.getName(), exitCommand);


        //----------------------------------------- test data-------------------------------------------
        projectService.create("My_project_1", "Description for my project 1");
        projectService.create("My_project_2", "Description for my project 2");
        projectService.create("My_project_3", "Description for my project 3");
        projectService.create("My_project_4", "Description for my project 4");


        for (Project project : projectService.findAll()) {
            taskService.create("task_1", "Description for task 1", project.getId());
            taskService.create("task_2", "Description for task 2", project.getId());
            taskService.create("task_3", "Description for task 3", project.getId());
            taskService.create("task_4", "Description for task 4", project.getId());
        }
        //----------------------------------------------------------------------------------------------

        System.out.println("==Welcome to Task manager!==\n" +
                "Input help for more information");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please input your command:");
            String commandName = scanner.nextLine();
            AbstractCommand command = commandMap.get(commandName);
            if (command != null) {
                command.execute();
            }
        }
    }
}
