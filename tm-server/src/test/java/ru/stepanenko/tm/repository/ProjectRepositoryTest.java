package ru.stepanenko.tm.repository;

import org.jetbrains.annotations.NotNull;
import org.junit.*;
import ru.stepanenko.tm.AppServerTest;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.config.EntityManagerFactoryProducer;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.service.PropertyService;
import ru.stepanenko.tm.util.TestDataGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectRepositoryTest {

    @NotNull
    private static EntityManagerFactory entityManagerFactory;

    @NotNull
    private static TestDataGenerator testDataGenerator;

    @NotNull
    private IProjectRepository projectRepository;

    @NotNull
    private EntityManager entityManager;

    @BeforeClass
    public static void setUpClass() {
        entityManagerFactory =
                new EntityManagerFactoryProducer(new PropertyService(AppServerTest.class, "application.properties")).getFactory();
        testDataGenerator = new TestDataGenerator(entityManagerFactory);
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
        Assert.assertNotNull(projectRepository.findOne("1"));
        Assert.assertNull(projectRepository.findOne("1000"));
        entityManager.getTransaction().commit();
    }

    @Test
    public void findAll() {
        entityManager.getTransaction().begin();
        Assert.assertFalse(projectRepository.findAll().isEmpty());
        projectRepository.removeAll();
        Assert.assertTrue(projectRepository.findAll().isEmpty());
        entityManager.getTransaction().commit();
    }

    @Test
    public void removeAll() {
        entityManager.getTransaction().begin();
        Assert.assertNotEquals(0, projectRepository.findAll().size());
        projectRepository.removeAll();
        Assert.assertEquals(0, projectRepository.findAll().size());
        entityManager.getTransaction().commit();
    }

    @Test
    public void remove() {
        entityManager.getTransaction().begin();
        @NotNull final int size = projectRepository.findAll().size();
        projectRepository.remove(projectRepository.findOne("1"));
        Assert.assertEquals(size - 1, projectRepository.findAll().size());
        Assert.assertNull(projectRepository.findOne("1"));
        entityManager.getTransaction().commit();
    }

    @Test
    public void persist() {
        @NotNull final User user = new User();
        user.setId("1");
        @NotNull final Project project = new Project();
        project.setUser(user);

        entityManager.getTransaction().begin();
        @NotNull final int size = projectRepository.findAll().size();
        entityManager.persist(project);
        Assert.assertEquals(size + 1, projectRepository.findAll().size());
        entityManager.getTransaction().commit();
    }

    @Test
    public void merge() {
        entityManager.getTransaction().begin();
        @NotNull final Project project1 = projectRepository.findOne("1");
        project1.setName("update_name");
        project1.setDescription("update_description");
        projectRepository.merge(project1);
        Assert.assertEquals("update_name", projectRepository.findOne("1").getName());
        Assert.assertEquals("update_description", projectRepository.findOne("1").getDescription());
        entityManager.getTransaction().commit();
    }

    @Test
    public void findAllByUserId() {
        @NotNull final User user = new User();
        user.setId("1");
        @NotNull final Project project = new Project();
        project.setUser(user);
        @NotNull final String uuid = project.getId();

        entityManager.getTransaction().begin();
        @NotNull final int size = projectRepository.findAllByUserId(user).size();
        projectRepository.persist(project);
        Assert.assertEquals(size + 1, projectRepository.findAllByUserId(user).size());
        entityManager.getTransaction().commit();

    }

    @Test
    public void findOneByUserId() {
        @NotNull final User user = new User();
        user.setId("1");
        @NotNull final Project project = new Project();
        project.setUser(user);
        @NotNull final String uuid = project.getId();

        entityManager.getTransaction().begin();
        projectRepository.persist(project);
        Assert.assertEquals(uuid, projectRepository.findOneByUserId(uuid, user).getId());
        entityManager.getTransaction().commit();
    }

    @Test
    public void removeAllByUserID() {
        @NotNull final User user = new User();
        user.setId("1");

        entityManager.getTransaction().begin();
        @NotNull final int size = projectRepository.findAll().size();
        Assert.assertTrue(size > 0);
        @NotNull final int removeSize = projectRepository.findAllByUserId(user).size();
        Assert.assertTrue(removeSize > 0);
        projectRepository.removeAllByUserID(user);
        Assert.assertEquals(size - removeSize, projectRepository.findAll().size());
        entityManager.getTransaction().commit();
    }

    @Test
    public void sortAllByUserId() {
        @NotNull final User user = new User();
        user.setId("test");

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


        entityManager.getTransaction().begin();
        projectRepository.removeAll();
        projectRepository.persist(project1);
        projectRepository.persist(project2);
        projectRepository.persist(project3);

        @NotNull final List<Project> sortStatus = new ArrayList<>(projectRepository.sortAllByUserId(user,"status"));
        sortStatus.forEach(e->System.out.println(e.getStatus()));
        Assert.assertEquals(project3, sortStatus.get(0));
        Assert.assertEquals(project2, sortStatus.get(1));
        Assert.assertEquals(project1, sortStatus.get(2));

        @NotNull final List<Project> sortDateBegin = new ArrayList<>(projectRepository.sortAllByUserId(user,"dateBegin"));
        sortDateBegin.forEach(e->System.out.println(e.getDateBegin()));
        Assert.assertEquals(project2, sortDateBegin.get(0));
        Assert.assertEquals(project1, sortDateBegin.get(1));
        Assert.assertEquals(project3, sortDateBegin.get(2));

        @NotNull final List<Project> sortDateEnd = new ArrayList<>(projectRepository.sortAllByUserId(user,"dateEnd"));
        sortDateEnd.forEach(e->System.out.println(e.getDateEnd()));
        Assert.assertEquals(project2, sortDateEnd.get(0));
        Assert.assertEquals(project1, sortDateEnd.get(1));
        Assert.assertEquals(project3, sortDateEnd.get(2));
        entityManager.getTransaction().commit();
    }

    @Test
    public void findAllByPartOfNameOrDescription() {
        @NotNull final User user = new User();
        user.setId("test");

        @NotNull final Project project1 = new Project();
        project1.setUser(user);
        project1.setName("Homework");
        project1.setDescription("Make all homework");

        @NotNull final Project project2 = new Project();
        project2.setUser(user);
        project2.setName("Cooking");
        project2.setDescription("Make apple pie!");

        entityManager.getTransaction().begin();
        projectRepository.persist(project1);
        projectRepository.persist(project2);
        @NotNull final List<Project> findProjects = new ArrayList<Project>(projectRepository.
                findAllByPartOfNameOrDescription("Home", "apple", user));
        Assert.assertEquals(findProjects.get(0).getId(), project1.getId());
        Assert.assertEquals(findProjects.get(1).getId(), project2.getId());
        entityManager.getTransaction().commit();
    }
}