package ru.stepanenko.tm.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.model.dto.TaskDTO;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.model.entity.Project;

import java.util.ArrayList;
import java.util.Date;

@Component
public class DataGenerator {

    @NotNull
    private final IProjectService projectService;

    @NotNull
    private final ITaskService taskService;

    @NotNull
    private final IUserService userService;

    @NotNull
    private final ISessionService sessionService;

    @Autowired
    public DataGenerator(
            @NotNull final IProjectService projectService,
            @NotNull final ITaskService taskService,
            @NotNull final IUserService userService,
            @NotNull final ISessionService sessionService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.userService = userService;
        this.sessionService = sessionService;
        generate();
    }

    public void generate() {
        cleanUp();
        generateUsers();
        generateData();
    }

    public void generateUsers() {
        try {
            UserDTO admin = new UserDTO(
                    "admin",
                    HashUtil.md5("admin"),
                    "Administrator",
                    "Application's administrator",
                    Role.ADMIN);

            UserDTO user = new UserDTO("user",
                    HashUtil.md5("user"),
                    "User",
                    "Application's user",
                    Role.USER);

            UserDTO testAdmin = new UserDTO("testAdmin",
                    HashUtil.md5("testAdmin"),
                    "Test admin",
                    "Test application's administrator",
                    Role.ADMIN);

            UserDTO testUser = new UserDTO("testUser",
                    HashUtil.md5("testUser"),
                    "Test user",
                    "Test application's user",
                    Role.USER);

            UserDTO test = new UserDTO("test",
                    HashUtil.md5("test"),
                    "Test user",
                    "Test application's user with id equals 1",
                    Role.USER);
            test.setId("1");

            userService.create(admin);
            userService.create(user);
            userService.create(testAdmin);
            userService.create(testUser);
            userService.create(test);
        } catch (DataValidateException e) {
            e.printStackTrace();
        }
    }

    public void generateData() {
        try {
            for (@NotNull UserDTO user : userService.findAll()) {
                for (int i = 1; i <= 4; i++) {
                    @NotNull final ProjectDTO project = new ProjectDTO("Project for " + user.getLogin() + " #" + i, "Description for project #" + i, new Date(), null, Status.PLANNED, user.getId());
                    if (i==1 && "1".equals(user.getId())) project.setId("1");
                    projectService.create(project);
                    for (int j = 1; j <= 4; j++) {
                        @NotNull final TaskDTO task = new TaskDTO("Task  for " + user.getLogin() + " #" + j, "Description task #" + j + " for project #" + i, new Date(), null, Status.PLANNED, project.getId(), user.getId());
                        taskService.create(task);
                    }
                }
            }
        } catch (DataValidateException e) {
            e.printStackTrace();
        }
    }

    public void cleanUp(
    ) {
        try {
            taskService.clear();
            projectService.clear();
            userService.clear();
        } catch (DataValidateException e) {
            e.printStackTrace();
        }
    }
}
