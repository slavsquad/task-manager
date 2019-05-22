package ru.stepanenko.tm.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.*;
import ru.stepanenko.tm.AppServerTest;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.config.EntityManagerFactoryProducer;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.Task;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.service.*;
import ru.stepanenko.tm.util.DataGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class TaskRepositoryTest {

    @NotNull
    private static EntityManagerFactory entityManagerFactory;

    @NotNull
    private static DataGenerator testDataGenerator;

    @NotNull
    private ITaskRepository taskRepository;

    @NotNull
    private EntityManager entityManager;

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
        entityManager = entityManagerFactory.createEntityManager();
        taskRepository = new TaskRepository(entityManager);
        testDataGenerator.generate();
    }

    @After
    public void tearDown() throws Exception {
        taskRepository = null;
        entityManager.close();
        entityManager = null;
    }

    @Test
    public void findOne() {
        entityManager.getTransaction().begin();
        @Nullable final User user = getEntity().getUser();
        assertNotNull(user);
        @Nullable final Project project = getEntity().getProject();
        assertNotNull(project);
        @NotNull final Task task = new Task();
        task.setProject(project);
        task.setUser(user);
        taskRepository.persist(task);
        assertEquals(task, taskRepository.findOne(task.getId()));
        entityManager.getTransaction().commit();
    }

    @Test
    public void findAll() {
        entityManager.getTransaction().begin();
        assertTrue(taskRepository.findAll().size() > 0);
        taskRepository.removeAll();
        assertTrue(taskRepository.findAll().isEmpty());
        entityManager.getTransaction().commit();
    }

    @Test
    public void removeAll() {
        entityManager.getTransaction().begin();
        assertTrue(taskRepository.findAll().size() > 0);
        taskRepository.removeAll();
        assertTrue(taskRepository.findAll().isEmpty());
        entityManager.getTransaction().commit();
    }

    @Test
    public void remove() {
        entityManager.getTransaction().begin();
        @NotNull final int size = taskRepository.findAll().size();
        taskRepository.remove(taskRepository.findOne(getEntity().getId()));
        assertEquals(size - 1, taskRepository.findAll().size());
        entityManager.getTransaction().commit();
    }

    @Test
    public void persist() {
        entityManager.getTransaction().begin();
        @Nullable final Task entity = getEntity();
        assertNotNull(entity);
        @NotNull final Task task = new Task();
        task.setProject(entity.getProject());
        task.setUser(entity.getUser());
        @NotNull final int size = taskRepository.findAll().size();
        entityManager.persist(task);
        assertEquals(size + 1, taskRepository.findAll().size());
        entityManager.getTransaction().commit();
    }

    @Test
    public void merge() {
        entityManager.getTransaction().begin();
        @Nullable final Task task = getEntity();
        assertNotNull(task);
        @NotNull final String uuid = task.getId();
        task.setName("update_name");
        task.setDescription("update_description");
        taskRepository.merge(task);
        assertEquals("update_name", taskRepository.findOne(uuid).getName());
        assertEquals("update_description", taskRepository.findOne(uuid).getDescription());
        entityManager.getTransaction().commit();
    }

    @Test
    public void findAllByUserId() {
        entityManager.getTransaction().begin();
        @Nullable final Task entity = getEntity();
        assertNotNull(entity);
        @Nullable final User user = entity.getUser();
        assertNotNull(user);
        @Nullable final Project project = entity.getProject();
        assertNotNull(project);
        @NotNull final Task task = new Task();
        task.setUser(user);
        task.setProject(project);
        @NotNull final int size = taskRepository.findAllByUserId(user).size();
        taskRepository.persist(task);
        assertEquals(size + 1, taskRepository.findAllByUserId(user).size());
        entityManager.getTransaction().commit();
    }

    @Test
    public void findAllByProjectAndUserId() {
        entityManager.getTransaction().begin();
        @Nullable final Task entity = getEntity();
        assertNotNull(entity);
        @Nullable final User user = entity.getUser();
        assertNotNull(user);
        @Nullable final Project project = entity.getProject();
        assertNotNull(project);
        @NotNull final Task task = new Task();
        task.setUser(user);
        task.setProject(project);
        @NotNull final int size = taskRepository.findAllByProjectAndUserId(project, user).size();
        taskRepository.persist(task);
        assertEquals(size + 1, taskRepository.findAllByProjectAndUserId(project, user).size());
        entityManager.getTransaction().commit();
    }

    @Test
    public void findOneByUserId() {
        entityManager.getTransaction().begin();
        @Nullable final Task entity = getEntity();
        assertNotNull(entity);
        @Nullable final User user = entity.getUser();
        assertNotNull(user);
        @Nullable final Project project = entity.getProject();
        assertNotNull(project);

        @NotNull final int size = taskRepository.findAllByUserId(user).size();
        Assert.assertTrue(size > 0);
        @NotNull final Task task = new Task();
        task.setUser(user);
        task.setProject(project);
        taskRepository.persist(task);
        Assert.assertEquals(task, taskRepository.findOneByUserId(task.getId(), user));
        Assert.assertEquals(size + 1, taskRepository.findAllByUserId(user).size());
        entityManager.getTransaction().commit();
    }

    @Test
    public void removeAllByUserId() {
        entityManager.getTransaction().begin();
        @Nullable final User user = getEntity().getUser();
        assertNotNull(user);
        @NotNull final int size = taskRepository.findAllByUserId(user).size();
        assertTrue(size > 0);
        taskRepository.removeAllByUserId(user);
        assertEquals(0, taskRepository.findAllByUserId(user).size());
        entityManager.getTransaction().commit();
    }

    @Test
    public void removeAllByProjectAndUserId() {
        entityManager.getTransaction().begin();
        @Nullable final Task entity = getEntity();
        assertNotNull(entity);
        @Nullable final User user = entity.getUser();
        assertNotNull(user);
        @Nullable final Project project = entity.getProject();
        assertNotNull(project);
        @Nullable final int size = taskRepository.findAllByProjectAndUserId(project, user).size();
        assertTrue(size>0);
        taskRepository.removeAllByProjectAndUserId(project, user);
        assertEquals(0, taskRepository.findAllByProjectAndUserId(project, user).size());
        entityManager.getTransaction().commit();
    }

    @Test
    public void sortAllByUserId() {
        entityManager.getTransaction().begin();
        @Nullable final Task entity = getEntity();
        assertNotNull(entity);
        @Nullable final User user = entity.getUser();
        assertNotNull(user);
        @Nullable final Project project = entity.getProject();
        assertNotNull(project);

        @NotNull final Task task1 = new Task();
        task1.setUser(user);
        task1.setProject(project);
        task1.setStatus(Status.DONE);
        task1.setDateBegin(new Date(1000));
        task1.setDateEnd(new Date(1000));

        @NotNull final Task task2 = new Task();
        task2.setUser(user);
        task2.setProject(project);
        task2.setStatus(Status.INPROCESS);
        task2.setDateBegin(new Date(1000000));
        task2.setDateEnd(new Date(1000000));

        @NotNull final Task task3 = new Task();
        task3.setUser(user);
        task3.setProject(project);
        task3.setStatus(Status.PLANNED);
        task3.setDateBegin(new Date(0));
        task3.setDateEnd(new Date(0));

        taskRepository.removeAllByUserId(user);
        assertTrue(taskRepository.findAllByUserId(user).size()==0);

        taskRepository.persist(task1);
        taskRepository.persist(task2);
        taskRepository.persist(task3);

        @NotNull final List<Task> sortStatus = new ArrayList<>(taskRepository.sortAllByUserId(user, "status"));
        sortStatus.forEach(e -> System.out.println(e.getStatus()));
        assertEquals(task3, sortStatus.get(0));
        assertEquals(task2, sortStatus.get(1));
        assertEquals(task1, sortStatus.get(2));

        @NotNull final List<Task> sortDateBegin = new ArrayList<>(taskRepository.sortAllByUserId(user, "dateBegin"));
        sortDateBegin.forEach(e -> System.out.println(e.getDateBegin()));
        assertEquals(task2, sortDateBegin.get(0));
        assertEquals(task1, sortDateBegin.get(1));
        assertEquals(task3, sortDateBegin.get(2));

        @NotNull final List<Task> sortDateEnd = new ArrayList<>(taskRepository.sortAllByUserId(user, "dateEnd"));
        sortDateEnd.forEach(e -> System.out.println(e.getDateEnd()));
        assertEquals(task2, sortDateEnd.get(0));
        assertEquals(task1, sortDateEnd.get(1));
        assertEquals(task3, sortDateEnd.get(2));
    }

    @Test
    public void findAllByPartOfNameOrDescription() {
        entityManager.getTransaction().begin();
        @Nullable final Task entity = getEntity();
        assertNotNull(entity);
        @Nullable final User user = entity.getUser();
        assertNotNull(user);
        @Nullable final Project project = entity.getProject();
        assertNotNull(project);

        @NotNull final Task task1 = new Task();
        task1.setUser(user);
        task1.setProject(project);
        task1.setName("Homework");
        task1.setDescription("Make all homework");

        @NotNull final Task task2 = new Task();
        task2.setUser(user);
        task2.setProject(project);
        task2.setName("Cooking");
        task2.setDescription("Make apple pie!");

        taskRepository.removeAllByUserId(user);
        assertTrue(taskRepository.findAllByUserId(user).size()==0);

        taskRepository.persist(task1);
        taskRepository.persist(task2);
        @NotNull final List<Task> findTasks = new ArrayList<Task>(taskRepository.
                findAllByPartOfNameOrDescription("Home", "apple", user));
        assertEquals(findTasks.get(0).getId(), task1.getId());
        assertEquals(findTasks.get(1).getId(), task2.getId());
        entityManager.getTransaction().commit();
    }

    private Task getEntity() {
        @NotNull final List<Task> tasks = new ArrayList<>(taskRepository.findAll());
        if (tasks.isEmpty()) return null;
        return tasks.get(0);
    }
}