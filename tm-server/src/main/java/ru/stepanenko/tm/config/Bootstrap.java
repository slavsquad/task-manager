package ru.stepanenko.tm.config;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.AppServer;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.api.repository.ISessionRepository;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.api.service.*;

import ru.stepanenko.tm.repository.ProjectRepository;
import ru.stepanenko.tm.repository.SessionRepository;
import ru.stepanenko.tm.repository.TaskRepository;
import ru.stepanenko.tm.repository.UserRepository;
import ru.stepanenko.tm.service.*;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.util.ConnectionDB;
import ru.stepanenko.tm.util.PropertesLoader;

import javax.xml.ws.Endpoint;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.Properties;

public class Bootstrap {
    public void init(Class[] endpoints) {

        @NotNull final Properties properties = PropertesLoader.load(AppServer.class);
        Connection connection = ConnectionDB.create(properties);

        @NotNull final IProjectRepository projectRepository = new ProjectRepository(connection);
        @NotNull final ITaskRepository taskRepository = new TaskRepository(connection);
        @NotNull final IUserRepository userRepository = new UserRepository(connection);
        @NotNull final ISessionRepository sessionRepository = new SessionRepository(connection);
        @NotNull final IProjectService projectService = new ProjectService(projectRepository);
        @NotNull final ITaskService taskService = new TaskService(taskRepository);
        @NotNull final IUserService userService = new UserService(userRepository, projectRepository, taskRepository, sessionRepository);
        @NotNull final ISessionService sessionService = new SessionService(sessionRepository, userRepository, properties);
        @NotNull final IServiceLocator serviceLocator = new ServiceLocator(projectService, taskService, userService, sessionService);

        if (userRepository.findAll() == null || userRepository.findAll().isEmpty()) generateTestUsers(serviceLocator);
        generateTestData(serviceLocator);
        registryEndpoint(endpoints, serviceLocator);
    }

    private void registryEndpoint(Class[] endpoints, IServiceLocator serviceLocator) {

        for (Class endpoint : endpoints) {
            if (endpoint == null) continue;
            Constructor endpointConstructor = null;
            try {
                endpointConstructor = endpoint.getConstructor(IServiceLocator.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            String wsdl = "http://localhost:8080/" + endpoint.getSimpleName() + "?wsdl";
            try {
                Endpoint.publish(wsdl, endpointConstructor.newInstance(serviceLocator));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            System.out.println(wsdl);
        }
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
}
