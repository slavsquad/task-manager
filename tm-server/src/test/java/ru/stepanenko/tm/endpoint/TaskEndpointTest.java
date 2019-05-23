package ru.stepanenko.tm.endpoint;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.*;
import ru.stepanenko.tm.AppServerTest;
import ru.stepanenko.tm.api.endpoint.ITaskEndpoint;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.config.EntityManagerFactoryProducer;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.model.dto.SessionDTO;
import ru.stepanenko.tm.model.dto.TaskDTO;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.service.*;
import ru.stepanenko.tm.util.DataGenerator;

import javax.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class TaskEndpointTest {
    @NotNull
    private static EntityManagerFactory entityManagerFactory;

    @NotNull
    private static DataGenerator testDataGenerator;

    @NotNull
    private ITaskEndpoint taskEndpoint;

    @NotNull
    private SessionDTO currentSession;

    @NotNull
    private UserDTO currentUser;

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
        @NotNull ITaskService taskService = new TaskService(entityManagerFactory);
        @NotNull IUserService userService = new UserService(entityManagerFactory);
        @NotNull ISessionService sessionService = new SessionService(entityManagerFactory, new PropertyService(AppServerTest.class, "application.properties"));
        taskEndpoint = new TaskEndpoint(taskService, sessionService);
        testDataGenerator.generate();
        currentUser = userService.findByLogin("admin");
        currentSession = sessionService.create(currentUser);
    }

    @After
    public void tearDown() throws Exception {
        taskEndpoint = null;
        currentSession = null;
        currentUser = null;
    }

    @Test
    public void createTask() throws DataValidateException, AuthenticationSecurityException {
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
        @NotNull final int size = taskEndpoint.findAllTaskByUserId(currentSession).size();
        assertTrue(size > 0);
        taskEndpoint.createTask(currentSession, task);
        assertEquals(size + 1, taskEndpoint.findAllTaskByUserId(currentSession).size());
    }

    @Test
    public void editTask() throws DataValidateException, AuthenticationSecurityException {
        @Nullable final TaskDTO task = getEntity();
        assertNotNull(task);
        task.setName("update_name");
        task.setDescription("update_Description");
        taskEndpoint.editTask(currentSession, task);
        assertEquals(task.getName(), taskEndpoint.findOneTask(currentSession, task.getId()).getName());
        assertEquals(task.getDescription(), taskEndpoint.findOneTask(currentSession, task.getId()).getDescription());
    }

    @Test
    public void findOneTask() throws DataValidateException, AuthenticationSecurityException {
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
        taskEndpoint.createTask(currentSession, task);
        assertEquals(task.getId(), taskEndpoint.findOneTask(currentSession, task.getId()).getId());
    }

    @Test
    public void removeTask() throws DataValidateException, AuthenticationSecurityException {
        @NotNull final int size = taskEndpoint.findAllTaskByUserId(currentSession).size();
        assertTrue(size > 0);
        taskEndpoint.removeTask(currentSession, getEntity().getId());
        assertEquals(size - 1, taskEndpoint.findAllTaskByUserId(currentSession).size());
    }

    @Test
    public void findAllTaskByProjectId() throws DataValidateException, AuthenticationSecurityException {
        @Nullable final TaskDTO entity = getEntity();
        assertNotNull(entity);
        @NotNull final int size = taskEndpoint.findAllTaskByProjectId(currentSession, entity.getProjectId()).size();
        assertTrue(size > 0);
        taskEndpoint.removeAllTaskByProjectId(currentSession, entity.getProjectId());
        assertEquals(0, taskEndpoint.findAllTaskByProjectId(currentSession, entity.getProjectId()).size());
    }

    @Test
    public void findAllTaskByUserId() throws DataValidateException, AuthenticationSecurityException {
        @Nullable final TaskDTO entity = getEntity();
        assertNotNull(entity);
        @NotNull final int size = taskEndpoint.findAllTaskByUserId(currentSession).size();
        assertTrue(size > 0);
        taskEndpoint.removeAllTaskByUserId(currentSession);
        assertEquals(0, taskEndpoint.findAllTaskByUserId(currentSession).size());
    }

    @Test
    public void removeAllTaskByProjectId() throws DataValidateException, AuthenticationSecurityException {
        @Nullable final TaskDTO entity = getEntity();
        assertNotNull(entity);
        @NotNull final int size = taskEndpoint.findAllTaskByProjectId(currentSession, entity.getProjectId()).size();
        assertTrue(size > 0);
        taskEndpoint.removeAllTaskByProjectId(currentSession, entity.getProjectId());
        assertEquals(0, taskEndpoint.findAllTaskByProjectId(currentSession, entity.getProjectId()).size());
    }

    @Test
    public void removeAllTaskByUserId() throws DataValidateException, AuthenticationSecurityException {
        @Nullable final TaskDTO entity = getEntity();
        assertNotNull(entity);
        @NotNull final int size = taskEndpoint.findAllTaskByUserId(currentSession).size();
        assertTrue(size > 0);
        taskEndpoint.removeAllTaskByUserId(currentSession);
        assertEquals(0, taskEndpoint.findAllTaskByUserId(currentSession).size());
    }

    @Test
    public void sortAllTaskByUserId() throws DataValidateException, AuthenticationSecurityException {
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

        taskEndpoint.removeAllTaskByUserId(currentSession);
        assertTrue(taskEndpoint.findAllTaskByUserId(currentSession).size() == 0);

        taskEndpoint.createTask(currentSession, task1);
        taskEndpoint.createTask(currentSession, task2);
        taskEndpoint.createTask(currentSession, task3);

        @NotNull final List<TaskDTO> sortStatus = new ArrayList<>(taskEndpoint.sortAllTaskByUserId(currentSession, "status"));
        assertEquals(task3.getId(), sortStatus.get(0).getId());
        assertEquals(task2.getId(), sortStatus.get(1).getId());
        assertEquals(task1.getId(), sortStatus.get(2).getId());

        @NotNull final List<TaskDTO> sortDateBegin = new ArrayList<>(taskEndpoint.sortAllTaskByUserId(currentSession, "dateBegin"));
        assertEquals(task2.getId(), sortDateBegin.get(0).getId());
        assertEquals(task1.getId(), sortDateBegin.get(1).getId());
        assertEquals(task3.getId(), sortDateBegin.get(2).getId());

        @NotNull final List<TaskDTO> sortDateEnd = new ArrayList<>(taskEndpoint.sortAllTaskByUserId(currentSession, "dateEnd"));
        assertEquals(task2.getId(), sortDateEnd.get(0).getId());
        assertEquals(task1.getId(), sortDateEnd.get(1).getId());
        assertEquals(task3.getId(), sortDateEnd.get(2).getId());
    }

    @Test
    public void findAllTaskByPartOfNameOrDescription() throws DataValidateException, AuthenticationSecurityException {
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

        taskEndpoint.removeAllTaskByUserId(currentSession);
        assertTrue(taskEndpoint.findAllTaskByUserId(currentSession).size() == 0);

        taskEndpoint.createTask(currentSession, task1);
        taskEndpoint.createTask(currentSession, task2);
        @NotNull final List<String> findTasksId = taskEndpoint
                .findAllTaskByPartOfNameOrDescription(currentSession, "Home", "apple")
                .stream()
                .map(TaskDTO::getId)
                .collect(Collectors.toList());
        assertTrue(findTasksId.contains(task1.getId()));
        assertTrue(findTasksId.contains(task2.getId()));
    }

    private TaskDTO getEntity() throws DataValidateException, AuthenticationSecurityException {
        @Nullable final List<TaskDTO> tasks = new ArrayList<>(taskEndpoint.findAllTaskByUserId(currentSession));
        if (tasks.isEmpty()) return null;
        return tasks.get(0);
    }
}