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
        generateUsers();
        generateData();
    }

    public void generateUsers() {
        try {
            UserDTO admin = new UserDTO();
            admin.setLogin("admin");
            admin.setPassword(HashUtil.md5("admin"));
            admin.setRole(Role.ADMIN);

            UserDTO user = new UserDTO();
            user.setLogin("user");
            user.setPassword(HashUtil.md5("user"));
            user.setRole(Role.USER);

            UserDTO testAdmin = new UserDTO();
            testAdmin.setLogin("testAdmin");
            testAdmin.setPassword(HashUtil.md5("testAdmin"));
            testAdmin.setRole(Role.ADMIN);

            UserDTO testUser = new UserDTO();
            testUser.setLogin("testUser");
            testUser.setPassword(HashUtil.md5("testUser"));
            testUser.setRole(Role.USER);

            UserDTO test = new UserDTO();
            test.setId("1");
            test.setLogin("test");
            test.setPassword(HashUtil.md5("test"));
            test.setRole(Role.USER);

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
    ) throws DataValidateException {
        taskService.clear();
        projectService.clear();
        userService.clear();
    }
}
