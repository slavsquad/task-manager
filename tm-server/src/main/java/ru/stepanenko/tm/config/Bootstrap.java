package ru.stepanenko.tm.config;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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

import javax.sql.DataSource;
import javax.xml.ws.Endpoint;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;

public class Bootstrap {
    @NotNull final IPropertyService propertyService = new PropertyService();
    public void init(Class[] endpoints) {
        Connection connection = ConnectionDB.create(propertyService.getJdbcURL(), propertyService.getJdbcUser(), propertyService.getJdbcPassword());
        @NotNull final IProjectRepository projectRepository = new ProjectRepository(connection);
        @NotNull final ITaskRepository taskRepository = new TaskRepository(connection);
        @NotNull final IUserRepository userRepository = new UserRepository(connection);
        @NotNull final ISessionRepository sessionRepository = new SessionRepository(connection);
        @NotNull final IProjectService projectService = new ProjectService(projectRepository);
        @NotNull final ITaskService taskService = new TaskService(taskRepository);
        @NotNull final IUserService userService = new UserService(userRepository, projectRepository, taskRepository, sessionRepository);
        @NotNull final ISessionService sessionService = new SessionService(sessionRepository, userRepository, propertyService);
        @NotNull final IServiceLocator serviceLocator = new ServiceLocator(projectService, taskService, userService, sessionService);
        if (userRepository.findAll() == null || userRepository.findAll().isEmpty()) generateTestUsers(serviceLocator);
       // generateTestData(serviceLocator);
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

    public SqlSessionFactory getSqlSessionFactory() {
        @Nullable final String user = propertyService.getJdbcUser();
        @Nullable final String password = propertyService.getJdbcPassword();
        @Nullable final String url = propertyService.getJdbcURL();
        @Nullable final String driver = propertyService.getJdbcDriver();
        final DataSource dataSource =
                new PooledDataSource(driver, url, user, password);
        final TransactionFactory transactionFactory =
                new JdbcTransactionFactory();
        final Environment environment =
                new Environment("development", transactionFactory, dataSource);
        final Configuration configuration = new Configuration(environment);
        configuration.addMapper(IUserRepository.class);
        configuration.addMapper(IProjectRepository.class);
        configuration.addMapper(ISessionRepository.class);
        configuration.addMapper(ITaskRepository.class);
        return new SqlSessionFactoryBuilder().build(configuration);
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
