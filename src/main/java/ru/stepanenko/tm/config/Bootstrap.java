package ru.stepanenko.tm.config;

import ru.stepanenko.tm.api.service.IServiceLocator;
import ru.stepanenko.tm.command.*;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.common.ExitCommand;
import ru.stepanenko.tm.command.common.HelpCommand;
import ru.stepanenko.tm.command.project.*;
import ru.stepanenko.tm.command.task.*;
import ru.stepanenko.tm.command.user.*;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.service.ServiceLocator;
import ru.stepanenko.tm.enumerate.Role;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Bootstrap {

    public void init() {
        final IServiceLocator serviceLocator = new ServiceLocator();
        generateTestData(serviceLocator.getProjectService(), serviceLocator.getTaskService(), serviceLocator.getUserService());
        Map<String, AbstractCommand> commands = generateCommands(serviceLocator.getProjectService(), serviceLocator.getTaskService(), serviceLocator.getUserService());
        menu(commands);
    }

    private Map<String, AbstractCommand> generateCommands(final IProjectService projectService, final ITaskService taskService, final IUserService userService) {
        final Map<String, AbstractCommand> commandMap = new HashMap<>();

        final ProjectClearCommand projectClearCommand = new ProjectClearCommand(projectService, userService);
        final ProjectCreateCommand projectCreateCommand = new ProjectCreateCommand(projectService, userService);
        final ProjectListCommand projectListCommand = new ProjectListCommand(projectService, userService);
        final ProjectRemoveCommand projectRemoveCommand = new ProjectRemoveCommand(projectService, userService);
        final ProjectEditCommand projectEditCommand = new ProjectEditCommand(projectService, userService);

        final TaskClearCommand taskCleanCommand = new TaskClearCommand(projectService, taskService, userService);
        final TaskCreateCommand taskCreateCommand = new TaskCreateCommand(projectService, taskService, userService);
        final TaskListCommand taskListCommand = new TaskListCommand(projectService, taskService, userService);
        final TaskRemoveCommand taskRemoveCommand = new TaskRemoveCommand(projectService, taskService, userService);
        final TaskEditCommand taskEditCommand = new TaskEditCommand(projectService, taskService, userService);

        final UserLoginCommand userLoginCommand = new UserLoginCommand(userService);
        final UserLogoutCommand userLogoutCommand = new UserLogoutCommand(userService);
        final UserRegisterCommand userRegisterCommand = new UserRegisterCommand(userService);
        final UserProfileViewCommand userProfileViewCommand = new UserProfileViewCommand(userService);
        final UserChangePasswordCommand userChangePasswordCommand = new UserChangePasswordCommand(userService);
        final UserProfileEditCommand userProfileEditCommand = new UserProfileEditCommand(userService);

        final HelpCommand helpCommand = new HelpCommand();
        final ExitCommand exitCommand = new ExitCommand();

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

    private void generateTestData(final IProjectService projectService, final ITaskService taskService, final IUserService userService) {
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

    private void menu(final Map<String, AbstractCommand> commands) {
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
