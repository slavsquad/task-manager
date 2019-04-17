package ru.stepanenko.tm.config;

import ru.stepanenko.tm.command.*;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.common.ExitCommand;
import ru.stepanenko.tm.command.common.HelpCommand;
import ru.stepanenko.tm.command.project.*;
import ru.stepanenko.tm.command.task.*;
import ru.stepanenko.tm.command.user.*;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.repository.ProjectRepository;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.repository.TaskRepository;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.repository.UserRepository;
import ru.stepanenko.tm.service.ProjectService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.service.TaskService;
import ru.stepanenko.tm.service.UserService;
import ru.stepanenko.tm.enumerate.Role;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Bootstrap {

    public void init() {
        IProjectService projectService;
        ITaskService taskService;
        IUserService userService;
        IProjectRepository projectRepository = new ProjectRepository();
        ITaskRepository taskRepository = new TaskRepository();
        IUserRepository userRepository = new UserRepository();
        projectService = new ProjectService(projectRepository);
        taskService = new TaskService(taskRepository);
        userService = new UserService(userRepository);

        generateTestData(projectService, taskService, userService);
        Map<String, AbstractCommand> commands = generateCommands(projectService, taskService, userService);
        menu(commands);
    }

    private Map<String, AbstractCommand> generateCommands(IProjectService projectService, ITaskService taskService, IUserService userService) {
        Map<String, AbstractCommand> commandMap = new HashMap<>();

        ProjectClearCommand projectClearCommand = new ProjectClearCommand(projectService, userService);
        ProjectCreateCommand projectCreateCommand = new ProjectCreateCommand(projectService, userService);
        ProjectListCommand projectListCommand = new ProjectListCommand(projectService, userService);
        ProjectRemoveCommand projectRemoveCommand = new ProjectRemoveCommand(projectService, userService);
        ProjectEditCommand projectEditCommand = new ProjectEditCommand(projectService, userService);

        TaskClearCommand taskCleanCommand = new TaskClearCommand(projectService, taskService, userService);
        TaskCreateCommand taskCreateCommand = new TaskCreateCommand(projectService, taskService, userService);
        TaskListCommand taskListCommand = new TaskListCommand(projectService, taskService, userService);
        TaskRemoveCommand taskRemoveCommand = new TaskRemoveCommand(projectService, taskService, userService);
        TaskEditCommand taskEditCommand = new TaskEditCommand(projectService, taskService, userService);

        UserLoginCommand userLoginCommand = new UserLoginCommand(userService);
        UserLogoutCommand userLogoutCommand = new UserLogoutCommand(userService);
        UserRegisterCommand userRegisterCommand = new UserRegisterCommand(userService);
        UserProfileViewCommand userProfileViewCommand = new UserProfileViewCommand(userService);
        UserChangePasswordCommand userChangePasswordCommand = new UserChangePasswordCommand(userService);
        UserProfileEditCommand userProfileEditCommand = new UserProfileEditCommand(userService);

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

        commandMap.put(userLoginCommand.getName(), userLoginCommand);
        commandMap.put(userLogoutCommand.getName(), userLogoutCommand);
        commandMap.put(userRegisterCommand.getName(), userRegisterCommand);
        commandMap.put(userProfileViewCommand.getName(), userProfileViewCommand);
        commandMap.put(userChangePasswordCommand.getName(), userChangePasswordCommand);
        commandMap.put(userProfileEditCommand.getName(), userProfileEditCommand);

        return commandMap;
    }

    private void generateTestData(IProjectService projectService, ITaskService taskService, IUserService userService) {
        //----------------------------------------- test data-------------------------------------------
        userService.create("admin", "admin", Role.ADMINISTRATOR.toString());
        userService.create("user", "user", Role.USER.toString());
        userService.create("root", "root", Role.USER.toString());

        projectService.create("My_project_1", "Description for my project 1", userService.findByLogin("admin").getId());
        projectService.create("My_project_2", "Description for my project 2", userService.findByLogin("admin").getId());
        projectService.create("My_project_3", "Description for my project 3", userService.findByLogin("user").getId());
        projectService.create("My_project_4", "Description for my project 4", userService.findByLogin("user").getId());

        for (Project project : projectService.findAll(userService.findByLogin("admin").getId())) {
            taskService.create("task_100", "Description for task 100", project.getId(), userService.findByLogin("admin").getId());
            taskService.create("task_200", "Description for task 200", project.getId(), userService.findByLogin("admin").getId());
            taskService.create("task_300", "Description for task 300", project.getId(), userService.findByLogin("admin").getId());
            taskService.create("task_400", "Description for task 400", project.getId(), userService.findByLogin("admin").getId());
        }

        for (Project project : projectService.findAll(userService.findByLogin("user").getId())) {
            taskService.create("task_1", "Description for task 1", project.getId(), userService.findByLogin("user").getId());
            taskService.create("task_2", "Description for task 2", project.getId(), userService.findByLogin("user").getId());
            taskService.create("task_3", "Description for task 3", project.getId(), userService.findByLogin("user").getId());
            taskService.create("task_4", "Description for task 4", project.getId(), userService.findByLogin("user").getId());
        }
        //----------------------------------------------------------------------------------------------
    }

    private void menu(Map<String, AbstractCommand> commands) {
        System.out.println("==Welcome to Task manager!==\n" +
                "Input help for more information");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please input your command:");
            String commandName = scanner.nextLine();
            AbstractCommand command = commands.get(commandName);
            if (command != null) {
                command.execute();
            } else {
                System.out.println("Incorrect input, please try again!");
            }
        }
    }
}
