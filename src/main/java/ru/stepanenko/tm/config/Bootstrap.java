package ru.stepanenko.tm.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.IServiceLocator;
import ru.stepanenko.tm.command.*;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.common.AboutCommand;
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
        @NotNull
        final IServiceLocator serviceLocator = new ServiceLocator();
        generateTestData(serviceLocator.getProjectService(), serviceLocator.getTaskService(), serviceLocator.getUserService());
        Map<String, AbstractCommand> commands = generateCommands(serviceLocator.getProjectService(), serviceLocator.getTaskService(), serviceLocator.getUserService());
        menu(commands);
    }

    private Map<String, AbstractCommand> generateCommands(@NotNull final IProjectService projectService, @NotNull final ITaskService taskService, @NotNull final IUserService userService) {
        @NotNull final Map<String, AbstractCommand> mapCommand = new HashMap<>();

        registerCommand(new ProjectClearCommand(projectService, userService), mapCommand);
        registerCommand(new ProjectCreateCommand(projectService, userService), mapCommand);
        registerCommand(new ProjectListCommand(projectService, userService), mapCommand);
        registerCommand(new ProjectRemoveCommand(projectService, userService), mapCommand);
        registerCommand(new ProjectEditCommand(projectService, userService), mapCommand);

        registerCommand(new TaskClearCommand(projectService, taskService, userService), mapCommand);
        registerCommand(new TaskCreateCommand(projectService, taskService, userService), mapCommand);
        registerCommand(new TaskListCommand(projectService, taskService, userService), mapCommand);
        registerCommand(new TaskRemoveCommand(projectService, taskService, userService), mapCommand);
        registerCommand(new TaskEditCommand(projectService, taskService, userService), mapCommand);

        registerCommand(new UserLoginCommand(userService), mapCommand);
        registerCommand(new UserLogoutCommand(userService), mapCommand);
        registerCommand(new UserRegisterCommand(userService), mapCommand);
        registerCommand(new UserProfileViewCommand(userService), mapCommand);
        registerCommand(new UserChangePasswordCommand(userService), mapCommand);
        registerCommand(new UserProfileEditCommand(userService), mapCommand);

        registerCommand(new HelpCommand(), mapCommand);
        registerCommand(new ExitCommand(), mapCommand);
        registerCommand(new AboutCommand(), mapCommand);

        return mapCommand;
    }

    private void registerCommand(@NotNull final AbstractCommand abstractCommand, @NotNull final Map<String, AbstractCommand> command) {
        command.put(abstractCommand.getName(), abstractCommand);
    }

    private void generateTestData(@NotNull final IProjectService projectService, @NotNull final ITaskService taskService, @NotNull final IUserService userService) {
        //----------------------------------------- test data-------------------------------------------
        userService.create("admin", "admin", Role.ADMINISTRATOR.toString());
        userService.create("user", "user", Role.USER.toString());
        userService.create("root", "root", Role.USER.toString());

        projectService.create("My_project_1", "Description for my project 1", userService.findByLogin("admin").getId());
        projectService.create("My_project_2", "Description for my project 2", userService.findByLogin("admin").getId());
        projectService.create("My_project_3", "Description for my project 3", userService.findByLogin("user").getId());
        projectService.create("My_project_4", "Description for my project 4", userService.findByLogin("user").getId());

        for (Project project : projectService.findAllByUserId(userService.findByLogin("admin").getId())) {
            taskService.create("task_100", "Description for task 100", project.getId(), userService.findByLogin("admin").getId());
            taskService.create("task_200", "Description for task 200", project.getId(), userService.findByLogin("admin").getId());
            taskService.create("task_300", "Description for task 300", project.getId(), userService.findByLogin("admin").getId());
            taskService.create("task_400", "Description for task 400", project.getId(), userService.findByLogin("admin").getId());
        }

        for (Project project : projectService.findAllByUserId(userService.findByLogin("user").getId())) {
            taskService.create("task_1", "Description for task 1", project.getId(), userService.findByLogin("user").getId());
            taskService.create("task_2", "Description for task 2", project.getId(), userService.findByLogin("user").getId());
            taskService.create("task_3", "Description for task 3", project.getId(), userService.findByLogin("user").getId());
            taskService.create("task_4", "Description for task 4", project.getId(), userService.findByLogin("user").getId());
        }
        //----------------------------------------------------------------------------------------------
    }

    private void menu(@NotNull final Map<String, AbstractCommand> commands) {
        System.out.println("==Welcome to Task manager!==\n" +
                "Input help for more information");
        @NotNull
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please input your command:");
            @NotNull
            String commandName = scanner.nextLine();
            @Nullable
            AbstractCommand command = commands.get(commandName);
            if (command != null) {
                command.execute();
            } else {
                System.out.println("Incorrect input, please try again!");
            }
        }
    }
}
