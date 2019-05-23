package ru.stepanenko.tm.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.*;
import ru.stepanenko.tm.AppServerTest;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.config.EntityManagerFactoryProducer;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.service.*;
import ru.stepanenko.tm.util.DataGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ProjectRepositoryTest {

    @NotNull
    private static EntityManagerFactory entityManagerFactory;

    @NotNull
    private static DataGenerator testDataGenerator;

    @NotNull
    private IProjectRepository projectRepository;

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
        projectRepository = new ProjectRepository(entityManager);
        testDataGenerator.generate();
    }

    @After
    public void tearDown() throws Exception {
        projectRepository = null;
        entityManager.close();
        entityManager = null;
    }

    @Test
    public void findOne() {
        entityManager.getTransaction().begin();
        @Nullable final User user = getEntity().getUser();
        assertNotNull(user);
        @NotNull final Project project = new Project();
        project.setUser(user);
        projectRepository.persist(project);
        assertEquals(project, projectRepository.findOne(project.getId()));
        entityManager.getTransaction().commit();
    }

    @Test
    public void findAll() {
        entityManager.getTransaction().begin();
        assertTrue(projectRepository.findAll().size() > 0);
        projectRepository.removeAll();
        assertTrue(projectRepository.findAll().isEmpty());
        entityManager.getTransaction().commit();
    }

    @Test
    public void removeAll() {
        entityManager.getTransaction().begin();
        assertTrue(projectRepository.findAll().size() > 0);
        projectRepository.removeAll();
        assertTrue(projectRepository.findAll().isEmpty());
        entityManager.getTransaction().commit();
    }

    @Test
    public void remove() {
        entityManager.getTransaction().begin();
        @NotNull final int size = projectRepository.findAll().size();
        projectRepository.remove(projectRepository.findOne(getEntity().getId()));
        assertEquals(size - 1, projectRepository.findAll().size());
        entityManager.getTransaction().commit();
    }

    @Test
    public void persist() {
        entityManager.getTransaction().begin();
        @NotNull final Project project = new Project();
        project.setUser(getEntity().getUser());
        @NotNull final int size = projectRepository.findAll().size();
        entityManager.persist(project);
        assertEquals(size + 1, projectRepository.findAll().size());
        entityManager.getTransaction().commit();
    }

    @Test
    public void merge() {
        entityManager.getTransaction().begin();
        @Nullable final Project project = getEntity();
        assertNotNull(project);
        @NotNull final String uuid = project.getId();
        project.setName("update_name");
        project.setDescription("update_description");
        projectRepository.merge(project);
        assertEquals("update_name", projectRepository.findOne(uuid).getName());
        assertEquals("update_description", projectRepository.findOne(uuid).getDescription());
        entityManager.getTransaction().commit();
    }

    @Test
    public void findAllByUserId() {
        entityManager.getTransaction().begin();
        @Nullable final User user = getEntity().getUser();
        assertNotNull(user);
        @NotNull final Project project = new Project();
        project.setUser(user);
        @NotNull final int size = projectRepository.findAllByUserId(user).size();
        projectRepository.persist(project);
        assertEquals(size + 1, projectRepository.findAllByUserId(user).size());
        entityManager.getTransaction().commit();
    }

    @Test
    public void findOneByUserId() {
        entityManager.getTransaction().begin();
        @Nullable final User user = getEntity().getUser();
        assertNotNull(user);
        @NotNull final Project project = new Project();
        @NotNull final String uuid = project.getId();
        project.setUser(user);
        projectRepository.persist(project);
        assertEquals(project, projectRepository.findOneByUserId(uuid, user));
        entityManager.getTransaction().commit();
    }

    @Test
    public void removeAllByUserID() {
        entityManager.getTransaction().begin();
        @Nullable final User user = getEntity().getUser();
        assertNotNull(user);
        @NotNull final int size = projectRepository.findAllByUserId(user).size();
        assertTrue(size > 0);
        projectRepository.removeAllByUserID(user);
        assertEquals(0, projectRepository.findAllByUserId(user).size());
        entityManager.getTransaction().commit();
    }

    @Test
    public void sortAllByUserId() {
        entityManager.getTransaction().begin();
        @Nullable final User user = getEntity().getUser();

        @NotNull final Project project1 = new Project();
        project1.setUser(user);
        project1.setStatus(Status.DONE);
        project1.setDateBegin(new Date(1000));
        project1.setDateEnd(new Date(1000));

        @NotNull final Project project2 = new Project();
        project2.setUser(user);
        project2.setStatus(Status.INPROCESS);
        project2.setDateBegin(new Date(1000000));
        project2.setDateEnd(new Date(1000000));

        @NotNull final Project project3 = new Project();
        project3.setUser(user);
        project3.setStatus(Status.PLANNED);
        project3.setDateBegin(new Date(0));
        project3.setDateEnd(new Date(0));

        projectRepository.removeAllByUserID(user);
        assertTrue(projectRepository.findAllByUserId(user).size() == 0);

        projectRepository.persist(project1);
        projectRepository.persist(project2);
        projectRepository.persist(project3);

        @NotNull final List<Project> sortStatus = new ArrayList<>(projectRepository.sortAllByUserId(user, "status"));
        assertEquals(project3, sortStatus.get(0));
        assertEquals(project2, sortStatus.get(1));
        assertEquals(project1, sortStatus.get(2));

        @NotNull final List<Project> sortDateBegin = new ArrayList<>(projectRepository.sortAllByUserId(user, "dateBegin"));
        assertEquals(project2, sortDateBegin.get(0));
        assertEquals(project1, sortDateBegin.get(1));
        assertEquals(project3, sortDateBegin.get(2));

        @NotNull final List<Project> sortDateEnd = new ArrayList<>(projectRepository.sortAllByUserId(user, "dateEnd"));
        assertEquals(project2, sortDateEnd.get(0));
        assertEquals(project1, sortDateEnd.get(1));
        assertEquals(project3, sortDateEnd.get(2));
        entityManager.getTransaction().commit();
    }

    @Test
    public void findAllByPartOfNameOrDescription() {
        entityManager.getTransaction().begin();
        @Nullable final User user = getEntity().getUser();
        assertNotNull(user);

        @NotNull final Project project1 = new Project();
        project1.setUser(user);
        project1.setName("Homework");
        project1.setDescription("Make all homework");

        @NotNull final Project project2 = new Project();
        project2.setUser(user);
        project2.setName("Cooking");
        project2.setDescription("Make apple pie!");

        projectRepository.removeAllByUserID(user);
        assertTrue(projectRepository.findAllByUserId(user).size() == 0);

        projectRepository.persist(project1);
        projectRepository.persist(project2);
        @NotNull final List<String> findProjectsId = new ArrayList<>(projectRepository
                .findAllByPartOfNameOrDescription("Home", "apple", user))
                .stream()
                .map(Project::getId)
                .collect(Collectors.toList());
        assertTrue(findProjectsId.contains(project1.getId()));
        assertTrue(findProjectsId.contains(project2.getId()));
        entityManager.getTransaction().commit();
    }

    private Project getEntity() {
        @Nullable final List<Project> projects = new ArrayList<>(projectRepository.findAll());
        if (projects.isEmpty()) return null;
        return projects.get(0);
    }
}