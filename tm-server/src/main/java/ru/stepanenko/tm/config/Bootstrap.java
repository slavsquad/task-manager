package ru.stepanenko.tm.config;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.*;
import ru.stepanenko.tm.endpoint.ProjectEndpoint;
import ru.stepanenko.tm.endpoint.SessionEndpoint;
import ru.stepanenko.tm.endpoint.TaskEndpoint;
import ru.stepanenko.tm.endpoint.UserEndpoint;

import ru.stepanenko.tm.service.ServiceLocator;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.entity.Project;

import javax.xml.ws.Endpoint;

public class Bootstrap {
    @NotNull
    final IServiceLocator serviceLocator = new ServiceLocator();

    public void init() {
        generateTestUsers(serviceLocator);

        //generateTestData(serviceLocator);
        initEndpoint(serviceLocator);
    }

    private void initEndpoint(IServiceLocator serviceLocator){
        registryEndpoint(new ProjectEndpoint(serviceLocator));
        registryEndpoint(new TaskEndpoint(serviceLocator));
        registryEndpoint(new UserEndpoint(serviceLocator));
        registryEndpoint(new SessionEndpoint(serviceLocator));
    }

    private void registryEndpoint(Object endpoint){
        if (endpoint==null) return;
        String wsdl = "http://localhost:8080/"+endpoint.getClass().getSimpleName()+"?wsdl";
        Endpoint.publish(wsdl,endpoint);
        System.out.println(wsdl);
    }

    private void generateTestUsers(IServiceLocator serviceLocator) {
        @NotNull final IProjectService projectService = serviceLocator.getProjectService();
        @NotNull final ITaskService taskService = serviceLocator.getTaskService();
        @NotNull final IUserService userService = serviceLocator.getUserService();
        @NotNull final ISessionService sessionService = serviceLocator.getSessionService();

        //----------------------------------------- test users-------------------------------------------
        userService.create("ecc9066a-8d60-4988-b00f-5dac3e95a250","admin", "admin", Role.ADMINISTRATOR.toString());
        userService.create("71242a19-1b98-4953-b3b6-fa4e2182c3a3", "user", "user", Role.USER.toString());
        userService.create("218ef653-2c56-4f88-866b-f98b4d3e5441","root", "root", Role.USER.toString());
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
