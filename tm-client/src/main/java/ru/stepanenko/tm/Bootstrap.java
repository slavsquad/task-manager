package ru.stepanenko.tm;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.IEndpointServiceLocator;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.*;
import ru.stepanenko.tm.service.EndpointServiceLocator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Bootstrap {
    @NotNull
    final IEndpointServiceLocator endpointServiceLocator = new EndpointServiceLocator();

    public void init(Class[] arrayCommands) {
        menu(initializeCommands(serviceLocator, arrayCommands));
    }

    private Map<String, AbstractCommand> initializeCommands(@NotNull final IServiceLocator serviceLocator, @NotNull final Class[] arrayCommands) {
        @NotNull final Map<String, AbstractCommand> mapCommand = new HashMap<>();

        for (Class command : arrayCommands) {
            if (command.getSuperclass().equals(AbstractCommand.class)) {
                try {
                    AbstractCommand abstractCommand = (AbstractCommand) command.newInstance();
                    abstractCommand.setEndpointServiceLocator(serviceLocator);
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

    private void generateTestUsers(IServiceLocator serviceLocator) {
        @NotNull final IProjectService projectService = serviceLocator.getProjectService();
        @NotNull final ITaskService taskService = serviceLocator.getTaskService();
        @NotNull final IUserService userService = serviceLocator.getUserService();
        @NotNull final ISessionService sessionService = serviceLocator.getSessionService();

        //----------------------------------------- test users-------------------------------------------
        userService.create("ecc9066a-8d60-4988-b00f-5dac3e95a250", "admin", "admin", Role.ADMINISTRATOR.toString());
        userService.create("71242a19-1b98-4953-b3b6-fa4e2182c3a3", "user", "user", Role.USER.toString());
        userService.create("218ef653-2c56-4f88-866b-f98b4d3e5441", "root", "root", Role.USER.toString());
    }

    private void generateTestData(IServiceLocator serviceLocator) {
        @NotNull final IProjectService projectService = serviceLocator.getProjectService();
        @NotNull final ITaskService taskService = serviceLocator.getTaskService();
        @NotNull final IUserService userService = serviceLocator.getUserService();
        @NotNull final ISessionService sessionService = serviceLocator.getSessionService();
        //----------------------------------------- test data-------------------------------------------

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

                } catch (AuthenticationSecurityException | InvalidSessionException e) {
                    System.out.println(e);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Incorrect input, please try again!");
            }
        }
    }
}
