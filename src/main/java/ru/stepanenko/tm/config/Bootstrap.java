package ru.stepanenko.tm.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.IServiceLocator;
import ru.stepanenko.tm.command.*;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.exception.*;
import ru.stepanenko.tm.service.ServiceLocator;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.entity.Project;

import java.util.HashMap;
import java.util.Map;

public class Bootstrap {
    @NotNull
    final IServiceLocator serviceLocator = new ServiceLocator();

    public void init(Class[] arrayCommands) {
        generateTestData(serviceLocator);
        menu(initializeCommands(serviceLocator, arrayCommands));
    }

    private Map<String, AbstractCommand> initializeCommands(@NotNull final IServiceLocator serviceLocator, @NotNull final Class[] arrayCommands) {
        @NotNull final Map<String, AbstractCommand> mapCommand = new HashMap<>();

        for (Class command : arrayCommands) {
            if (command.getSuperclass().equals(AbstractCommand.class)) {
                try {
                    AbstractCommand abstractCommand = (AbstractCommand) command.newInstance();
                    abstractCommand.setServiceLocator(serviceLocator);
                    registerCommand(abstractCommand, mapCommand);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return mapCommand;
    }

    private void registerCommand(@NotNull final AbstractCommand abstractCommand, @NotNull final Map<String, AbstractCommand> command) {
        command.put(abstractCommand.getName(), abstractCommand);
    }

    private void generateTestData(IServiceLocator serviceLocator) {
        @NotNull final IProjectService projectService = serviceLocator.getProjectService();
        @NotNull final ITaskService taskService = serviceLocator.getTaskService();
        @NotNull final IUserService userService = serviceLocator.getUserService();
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

        while (true) {
            System.out.println("Please input your command:");
            @NotNull
            String commandName = serviceLocator.getTerminalService().nextLine();
            @Nullable
            AbstractCommand command = commands.get(commandName);
            if (command != null) {
                try {
                    command.execute();

                }catch (AuthenticationSecurityException e){
                    System.out.println(e);
                }
            } else {
                System.out.println("Incorrect input, please try again!");
            }
        }
    }
}
