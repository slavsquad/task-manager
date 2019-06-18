package ru.stepanenko.tm.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.config.DataBaseConfig;
import ru.stepanenko.tm.config.WebMvcConfig;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.Task;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.util.DataGenerator;

import java.util.ArrayList;

import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebMvcConfig.class, DataBaseConfig.class})
public class TaskRepositoryTest {

    @Autowired
    private ITaskRepository taskRepository;

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
    @Transactional
    public void taskCRUD(
    ) throws DataValidateException {
        @Nullable final User user = new ArrayList<>(taskRepository.findAll()).get(0).getUser();
        assertNotNull(user);
        @Nullable final Project project = new ArrayList<>(taskRepository.findAll()).get(0).getProject();
        assertNotNull(project);

        @NotNull final Task task = new Task();
        @NotNull final String taskId = task.getId();
        task.setUser(user);
        task.setProject(project);
        task.setName("Task");
        task.setDescription("Description");
        taskRepository.persist(task);
        assertEquals(taskId, taskRepository.findOneByUserId(taskId, user).getId());
        task.setName("Change name");
        task.setDescription("Change description");
        taskRepository.merge(task);
        assertEquals("Change name", taskRepository.findOneByUserId(taskId, user).getName());
        assertEquals("Change description", taskRepository.findOneByUserId(taskId, user).getDescription());
        @Nullable final int size = taskRepository.findAllByUserId(user).size();
        assertNotNull(size);
        taskRepository.remove(task);
        assertEquals(size - 1, taskRepository.findAllByUserId(user).size());
    }

}