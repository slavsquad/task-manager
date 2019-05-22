package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.*;
import ru.stepanenko.tm.AppServerTest;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.config.EntityManagerFactoryProducer;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.TaskDTO;
import ru.stepanenko.tm.util.DataGenerator;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class TaskServiceTest {
    @NotNull
    private static EntityManagerFactory entityManagerFactory;

    @NotNull
    private static DataGenerator testDataGenerator;

    @NotNull
    private ITaskService taskService;

    @BeforeClass
    public static void setUpClass() {
        entityManagerFactory =
                new EntityManagerFactoryProducer(new PropertyService(AppServerTest.class, "application.properties")).getFactory();
        testDataGenerator = new DataGenerator(
                new ProjectService(entityManagerFactory),
                new TaskService(entityManagerFactory),
                new UserService(entityManagerFactory),
                new SessionService(entityManagerFactory, new PropertyService(AppServerTest.class, "application.properties")));
    }

    @AfterClass
    public static void tearDownClass() {
        entityManagerFactory.close();
        entityManagerFactory = null;
    }

    @Before
    public void setUp() throws Exception {
        taskService = new TaskService(entityManagerFactory);
        testDataGenerator.generate();
    }

    @After
    public void tearDown() throws Exception {
        taskService = null;
    }

    @Test
    public void create() throws DataValidateException {
        @Nullable final TaskDTO entity = getEntity();
        assertNotNull(entity);
        @Nullable final String userId = entity.getUserId();
        assertNotNull(userId);
        @Nullable final String projectId = entity.getProjectId();
        assertNotNull(projectId);
        @NotNull final TaskDTO task = new TaskDTO();
        task.setName("New_Task");
        task.setDescription("New_Description");
        task.setUserId(userId);
        task.setProjectId(projectId);
        @NotNull final int size = taskService.findAll().size();
        assertTrue(size > 0);
        taskService.create(task);
        assertEquals(size + 1, taskService.findAll().size());
    }

    @Test
    public void edit() throws DataValidateException {
        @Nullable final TaskDTO task = getEntity();
        assertNotNull(task);
        task.setName("update_name");
        task.setDescription("update_Description");
        taskService.edit(task);
        assertEquals(task.getName(), taskService.findOne(task.getId()).getName());
        assertEquals(task.getDescription(), taskService.findOne(task.getId()).getDescription());
    }

    @Test
    public void findOne() throws DataValidateException {
        @Nullable final TaskDTO entity = getEntity();
        assertNotNull(entity);
        @Nullable final String userId = entity.getUserId();
        assertNotNull(userId);
        @Nullable final String projectId = entity.getProjectId();
        assertNotNull(projectId);
        @NotNull final TaskDTO task = new TaskDTO();
        task.setName("New_Task");
        task.setDescription("New_Description");
        task.setUserId(userId);
        task.setProjectId(projectId);
        taskService.create(task);
        assertEquals(task.getId(), taskService.findOne(task.getId()).getId());
    }

    @Test
    public void remove() throws DataValidateException {
        @NotNull final int size = taskService.findAll().size();
        assertTrue(size > 0);
        taskService.remove(getEntity().getId());
        assertEquals(size - 1, taskService.findAll().size());
    }

    @Test
    public void clear() throws DataValidateException {
        @NotNull final int size = taskService.findAll().size();
        assertTrue(size > 0);
        taskService.clear();
        assertEquals(0, taskService.findAll().size());
    }

    @Test
    public void findOneByUserId() throws DataValidateException {
        @Nullable final TaskDTO entity = getEntity();
        assertNotNull(entity);
        @Nullable final String userId = entity.getUserId();
        assertNotNull(userId);
        @Nullable final String projectId = entity.getProjectId();
        assertNotNull(projectId);
        @NotNull final TaskDTO task = new TaskDTO();
        task.setName("New_Task");
        task.setDescription("New_Description");
        task.setUserId(userId);
        task.setProjectId(projectId);
        taskService.create(task);
        assertEquals(task.getId(), taskService.findOne(task.getId(), userId).getId());
    }

    @Test
    public void removeOneByUserId() throws DataValidateException {
        @Nullable final TaskDTO entity = getEntity();
        assertNotNull(entity);
        @NotNull final int size = taskService.findAllByUserId(entity.getUserId()).size();
        assertTrue(size > 0);
        taskService.remove(entity.getId(), entity.getUserId());
        assertEquals(size - 1, taskService.findAllByUserId(entity.getUserId()).size());
    }

    @Test
    public void findAll() throws DataValidateException {
        @NotNull final int size = taskService.findAll().size();
        assertTrue(size > 0);
        taskService.clear();
        assertEquals(0, taskService.findAll().size());
    }

    @Test
    public void removeAllByProjectId() throws DataValidateException {
        @Nullable final TaskDTO entity = getEntity();
        assertNotNull(entity);
        @NotNull final int size = taskService.findAllByProjectId(entity.getProjectId(), entity.getUserId()).size();
        assertTrue(size > 0);
        taskService.removeAllByProjectId(entity.getProjectId(), entity.getUserId());
        assertEquals(0, taskService.findAllByProjectId(entity.getProjectId(), entity.getUserId()).size());
    }

    @Test
    public void removeAllByUserId() throws DataValidateException {
        @Nullable final TaskDTO entity = getEntity();
        assertNotNull(entity);
        @NotNull final int size = taskService.findAllByUserId(entity.getUserId()).size();
        assertTrue(size > 0);
        taskService.removeAllByUserId(entity.getUserId());
        assertEquals(0, taskService.findAllByUserId(entity.getUserId()).size());
    }
    @Test
    public void findAllByProjectId() throws DataValidateException {
        @Nullable final TaskDTO entity = getEntity();
        assertNotNull(entity);
        @NotNull final int size = taskService.findAllByProjectId(entity.getProjectId(), entity.getUserId()).size();
        assertTrue(size > 0);
        taskService.removeAllByProjectId(entity.getProjectId(), entity.getUserId());
        assertEquals(0, taskService.findAllByProjectId(entity.getProjectId(), entity.getUserId()).size());
    }

    @Test
    public void findAllByUserId() throws DataValidateException {
        @Nullable final TaskDTO entity = getEntity();
        assertNotNull(entity);
        @NotNull final int size = taskService.findAllByUserId(entity.getUserId()).size();
        assertTrue(size > 0);
        taskService.removeAllByUserId(entity.getUserId());
        assertEquals(0, taskService.findAllByUserId(entity.getUserId()).size());
    }

    @Test
    public void sortAllByUserId() throws DataValidateException {
        @Nullable final TaskDTO entity = getEntity();
        assertNotNull(entity);
        @NotNull final TaskDTO task1 = new TaskDTO();
        task1.setProjectId(entity.getProjectId());
        task1.setUserId(entity.getUserId());
        task1.setName("task1");
        task1.setDescription("Description1");
        task1.setStatus(Status.DONE);
        task1.setDateBegin(new Date(1000));
        task1.setDateEnd(new Date(1000));

        @NotNull final TaskDTO task2 = new TaskDTO();
        task2.setProjectId(entity.getProjectId());
        task2.setUserId(entity.getUserId());
        task2.setName("task2");
        task2.setDescription("Description2");
        task2.setStatus(Status.INPROCESS);
        task2.setDateBegin(new Date(1000000));
        task2.setDateEnd(new Date(1000000));

        @NotNull final TaskDTO task3 = new TaskDTO();
        task3.setProjectId(entity.getProjectId());
        task3.setUserId(entity.getUserId());
        task3.setName("task3");
        task3.setDescription("Description3");
        task3.setStatus(Status.PLANNED);
        task3.setDateBegin(new Date(0));
        task3.setDateEnd(new Date(0));

        taskService.removeAllByUserId(entity.getUserId());
        assertTrue(taskService.findAllByUserId(entity.getUserId()).size()==0);

        taskService.create(task1);
        taskService.create(task2);
        taskService.create(task3);

        @NotNull final List<TaskDTO> sortStatus = new ArrayList<>(taskService.sortAllByUserId(entity.getUserId(), "status"));
        assertEquals(task3.getId(), sortStatus.get(0).getId());
        assertEquals(task2.getId(), sortStatus.get(1).getId());
        assertEquals(task1.getId(), sortStatus.get(2).getId());

        @NotNull final List<TaskDTO> sortDateBegin = new ArrayList<>(taskService.sortAllByUserId(entity.getUserId(), "dateBegin"));
        assertEquals(task2.getId(), sortDateBegin.get(0).getId());
        assertEquals(task1.getId(), sortDateBegin.get(1).getId());
        assertEquals(task3.getId(), sortDateBegin.get(2).getId());

        @NotNull final List<TaskDTO> sortDateEnd = new ArrayList<>(taskService.sortAllByUserId(entity.getUserId(), "dateEnd"));
        assertEquals(task2.getId(), sortDateEnd.get(0).getId());
        assertEquals(task1.getId(), sortDateEnd.get(1).getId());
        assertEquals(task3.getId(), sortDateEnd.get(2).getId());

    }

    @Test
    public void findAllByPartOfNameOrDescription() throws DataValidateException {
        @Nullable final TaskDTO entity = getEntity();
        assertNotNull(entity);

        @NotNull final TaskDTO task1 = new TaskDTO();
        task1.setProjectId(entity.getProjectId());
        task1.setUserId(entity.getUserId());
        task1.setName("Homework");
        task1.setDescription("Make all homework");

        @NotNull final TaskDTO task2 = new TaskDTO();
        task2.setProjectId(entity.getProjectId());
        task2.setUserId(entity.getUserId());
        task2.setName("Cooking");
        task2.setDescription("Make apple pie!");

        taskService.removeAllByUserId(entity.getUserId());
        assertTrue(taskService.findAllByUserId(entity.getUserId()).size()==0);

        taskService.create(task1);
        taskService.create(task2);
        @NotNull final List<TaskDTO> findTasks = new ArrayList<TaskDTO>(taskService.
                findAllByPartOfNameOrDescription("Home", "apple", entity.getUserId()));
        assertEquals(findTasks.get(0).getId(), task1.getId());
        assertEquals(findTasks.get(1).getId(), task2.getId());
    }

    private TaskDTO getEntity() throws DataValidateException {
        @Nullable final List<TaskDTO> tasks = new ArrayList<>(taskService.findAll());
        if (tasks.isEmpty()) return null;
        return tasks.get(0);
    }
}