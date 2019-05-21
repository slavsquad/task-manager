package ru.stepanenko.tm.util;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.AppServerTest;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.model.dto.TaskDTO;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.service.*;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

public class TestDataGenerator {

    @NotNull
    private final IProjectService projectService;

    @NotNull
    private final ITaskService taskService;

    @NotNull
    private final IUserService userService;

    @NotNull
    private final ISessionService sessionService;

    @Inject
    public TestDataGenerator(
            @NotNull final EntityManagerFactory factory) {
        this.projectService = new ProjectService(factory);
        this.taskService = new TaskService(factory);
        this.userService = new UserService(factory);
        this.sessionService = new SessionService(factory, new PropertyService(AppServerTest.class, "application.properties"));
    }

    public void generate() {
        generateUsers();
        generateData();
    }

    public void generateUsers() {
        try {
            UserDTO admin = new UserDTO();
            admin.setId("1");
            admin.setLogin("admin");
            admin.setPassword(HashUtil.md5("admin"));
            admin.setRole(Role.ADMIN);

            UserDTO user = new UserDTO();
            user.setId("2");
            user.setLogin("user");
            user.setPassword(HashUtil.md5("user"));
            user.setRole(Role.USER);

            userService.clear();
            userService.create(admin);
            userService.create(user);
        } catch (DataValidateException e) {
            e.printStackTrace();
        }
    }

    public void generateData() {
        try {
            ProjectDTO projectDTO1 = new ProjectDTO("My_project_1", "Description for my project 1", userService.findByLogin("admin").getId());
            projectDTO1.setId("1");
            projectService.create(projectDTO1);

            ProjectDTO projectDTO2 = new ProjectDTO("My_project_2", "Description for my project 2", userService.findByLogin("admin").getId());
            projectDTO2.setId("2");
            projectService.create(projectDTO2);

            ProjectDTO projectDTO3 = new ProjectDTO("My_project_3", "Description for my project 3", userService.findByLogin("user").getId());
            projectDTO3.setId("3");
            projectService.create(projectDTO3);

            ProjectDTO projectDTO4 = new ProjectDTO("My_project_4", "Description for my project 4", userService.findByLogin("user").getId());
            projectDTO4.setId("4");
            projectService.create(projectDTO4);


            TaskDTO taskDTO1 = new TaskDTO("task_1", "Description for task 1", projectDTO1.getId(), userService.findByLogin("admin").getId());
            taskDTO1.setId("1");
            taskService.create(taskDTO1);

            TaskDTO taskDTO2 = new TaskDTO("task_2", "Description for task 2", projectDTO1.getId(), userService.findByLogin("admin").getId());
            taskDTO2.setId("2");
            taskService.create(taskDTO2);

            TaskDTO taskDTO3 = new TaskDTO("task_3", "Description for task 3", projectDTO1.getId(), userService.findByLogin("admin").getId());
            taskDTO3.setId("3");
            taskService.create(taskDTO3);

            TaskDTO taskDTO4 = new TaskDTO("task_4", "Description for task 4", projectDTO1.getId(), userService.findByLogin("admin").getId());
            taskDTO4.setId("4");
            taskService.create(taskDTO4);

            TaskDTO taskDTO5 = new TaskDTO("task_5", "Description for task 5", projectDTO1.getId(), userService.findByLogin("admin").getId());
            taskDTO5.setId("5");
            taskService.create(taskDTO5);

            TaskDTO taskDTO6 = new TaskDTO("task_6", "Description for task 6", projectDTO1.getId(), userService.findByLogin("admin").getId());
            taskDTO6.setId("6");
            taskService.create(taskDTO6);

            TaskDTO taskDTO7 = new TaskDTO("task_7", "Description for task 7", projectDTO1.getId(), userService.findByLogin("admin").getId());
            taskDTO7.setId("7");
            taskService.create(taskDTO7);

            TaskDTO taskDTO8 = new TaskDTO("task_8", "Description for task 8", projectDTO1.getId(), userService.findByLogin("admin").getId());
            taskDTO8.setId("8");
            taskService.create(taskDTO8);


            TaskDTO taskDTO100 = new TaskDTO("task_100", "Description for task 100", projectDTO3.getId(), userService.findByLogin("user").getId());
            taskDTO100.setId("100");
            taskService.create(taskDTO100);

            TaskDTO taskDTO200 = new TaskDTO("task_200", "Description for task 200", projectDTO3.getId(), userService.findByLogin("user").getId());
            taskDTO200.setId("200");
            taskService.create(taskDTO200);

            TaskDTO taskDTO300 = new TaskDTO("task_300", "Description for task 300", projectDTO3.getId(), userService.findByLogin("user").getId());
            taskDTO300.setId("300");
            taskService.create(taskDTO300);

            TaskDTO taskDTO400 = new TaskDTO("task_400", "Description for task 400", projectDTO3.getId(), userService.findByLogin("user").getId());
            taskDTO400.setId("400");
            taskService.create(taskDTO400);

            TaskDTO taskDTO500 = new TaskDTO("task_500", "Description for task 500", projectDTO4.getId(), userService.findByLogin("user").getId());
            taskDTO500.setId("500");
            taskService.create(taskDTO500);
        } catch (DataValidateException e) {
            e.printStackTrace();
        }
    }

    public void cleanUp() {
        try {
            userService.remove("1");
            userService.remove("2");
        } catch (DataValidateException e) {
            e.printStackTrace();
        }
    }
}
