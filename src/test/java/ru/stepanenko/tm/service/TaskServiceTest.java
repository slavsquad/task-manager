package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.config.DataBaseConfig;
import ru.stepanenko.tm.config.WebMvcConfig;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.TaskDTO;
import ru.stepanenko.tm.util.DataGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebMvcConfig.class, DataBaseConfig.class})
public class TaskServiceTest {

    @Autowired
    private ITaskService taskService;

    @Autowired
    private DataGenerator dataGenerator;

    @Before
    public void setUp() {
        dataGenerator.generate();
    }

    @After
    public void tearDown() throws DataValidateException {
        dataGenerator.cleanUp();
    }

    @Test
    public void taskCRUD(
    ) throws DataValidateException {
        @Nullable final String userId = new ArrayList<>(taskService.findAll()).get(0).getUserId();
        assertNotNull(userId);
        @Nullable final String projectId = new ArrayList<>(taskService.findAll()).get(0).getProjectId();
        assertNotNull(projectId);

        @NotNull final TaskDTO task = new TaskDTO();
        @NotNull final String taskId = task.getId();
        task.setUserId(userId);
        task.setProjectId(projectId);
        task.setName("Task");
        task.setDescription("Description");
        taskService.create(task);
        assertEquals(taskId, taskService.findOne(taskId, userId).getId());
        task.setName("Change name");
        task.setDescription("Change description");
        taskService.edit(task);
        assertEquals("Change name", taskService.findOne(taskId, userId).getName());
        assertEquals("Change description", taskService.findOne(taskId, userId).getDescription());
        @Nullable final int size = taskService.findAllByUserId(userId).size();
        assertNotNull(size);
        taskService.remove(taskId, userId);
        assertEquals(size - 1, taskService.findAllByUserId(userId).size());
    }

    @Test
    public void projectSort(
    ) throws DataValidateException {
        @Nullable final String userId = new ArrayList<>(taskService.findAll()).get(0).getUserId();
        assertNotNull(userId);
        @Nullable final String projectId = new ArrayList<>(taskService.findAll()).get(0).getProjectId();
        assertNotNull(projectId);
        @NotNull final TaskDTO task1 = new TaskDTO();
        task1.setUserId(userId);
        task1.setProjectId(projectId);
        task1.setId(UUID.randomUUID().toString());
        task1.setName("project1");
        task1.setDescription("Description1");
        task1.setStatus(Status.DONE);
        task1.setDateBegin(new Date(1000));
        task1.setDateEnd(new Date(1000));

        @NotNull final TaskDTO task2 = new TaskDTO();
        task2.setUserId(userId);
        task2.setProjectId(projectId);
        task2.setId(UUID.randomUUID().toString());
        task2.setName("project2");
        task2.setDescription("Description2");
        task2.setStatus(Status.INPROCESS);
        task2.setDateBegin(new Date(1000000));
        task2.setDateEnd(new Date(1000000));

        @NotNull final TaskDTO task3 = new TaskDTO();
        task3.setId(UUID.randomUUID().toString());
        task3.setProjectId(projectId);
        task3.setUserId(userId);
        task3.setName("project3");
        task3.setDescription("Description3");
        task3.setStatus(Status.PLANNED);
        task3.setDateBegin(new Date(0));
        task3.setDateEnd(new Date(0));

        taskService.removeAllByUserId(userId);
        assertTrue(taskService.findAllByUserId(userId).size() == 0);

        taskService.create(task1);
        taskService.create(task2);
        taskService.create(task3);

        @NotNull final List<TaskDTO> sortStatus = new ArrayList<>(taskService.sortAllByUserId(userId, "status"));
        assertEquals(task3.getId(), sortStatus.get(0).getId());
        assertEquals(task2.getId(), sortStatus.get(1).getId());
        assertEquals(task1.getId(), sortStatus.get(2).getId());

        @NotNull final List<TaskDTO> sortDateBegin = new ArrayList<>(taskService.sortAllByUserId(userId, "dateBegin"));
        assertEquals(task2.getId(), sortDateBegin.get(0).getId());
        assertEquals(task1.getId(), sortDateBegin.get(1).getId());
        assertEquals(task3.getId(), sortDateBegin.get(2).getId());

        @NotNull final List<TaskDTO> sortDateEnd = new ArrayList<>(taskService.sortAllByUserId(userId, "dateEnd"));
        assertEquals(task2.getId(), sortDateEnd.get(0).getId());
        assertEquals(task1.getId(), sortDateEnd.get(1).getId());
        assertEquals(task3.getId(), sortDateEnd.get(2).getId());
        taskService.removeAllByUserId(userId);
    }

    @Test
    public void findProject(
    ) throws DataValidateException {
        @Nullable final String userId = new ArrayList<>(taskService.findAll()).get(0).getUserId();
        assertNotNull(userId);
        @Nullable final String projectId = new ArrayList<>(taskService.findAll()).get(0).getProjectId();
        assertNotNull(projectId);
        @NotNull final TaskDTO task1 = new TaskDTO();
        task1.setUserId(userId);
        task1.setProjectId(projectId);
        task1.setId(UUID.randomUUID().toString());
        task1.setName("Homework");
        task1.setDescription("Make all homework");

        @NotNull final TaskDTO task2 = new TaskDTO();
        task2.setUserId(userId);
        task2.setProjectId(projectId);
        task2.setId(UUID.randomUUID().toString());
        task2.setName("Cooking");
        task2.setDescription("Make apple pie!");

        taskService.removeAllByUserId(userId);
        assertTrue(taskService.findAllByUserId(userId).size() == 0);

        taskService.create(task1);
        taskService.create(task2);

        @NotNull final List<String> findProjectsId = taskService
                .findAllByPartOfNameOrDescription("Home", "apple", userId)
                .stream()
                .map(TaskDTO::getId)
                .collect(Collectors.toList());
        assertTrue(findProjectsId.contains(task1.getId()));
        assertTrue(findProjectsId.contains(task2.getId()));
        taskService.removeAllByUserId(userId);
    }

}