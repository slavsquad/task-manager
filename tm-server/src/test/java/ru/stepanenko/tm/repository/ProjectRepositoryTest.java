package ru.stepanenko.tm.repository;

import org.jetbrains.annotations.NotNull;
import org.junit.*;
import ru.stepanenko.tm.AppServerTest;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.config.EntityManagerFactoryProducer;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.service.PropertyService;
import ru.stepanenko.tm.util.TestDataGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
        testDataGenerator.cleanUp();
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
        Assert.assertNotNull(projectRepository.findAll());
        projectRepository.removeAll();
        Assert.assertTrue(projectRepository.findAll().isEmpty());
        entityManager.getTransaction().commit();
    }

    @Test
    public void removeAll() {
        entityManager.getTransaction().begin();
        projectRepository.removeAll();
        Assert.assertEquals(0, projectRepository.findAll().size());
        entityManager.getTransaction().commit();
    }

    @Test
    public void remove() {
        entityManager.getTransaction().begin();
        @NotNull final int size = projectRepository.findAll().size();
        projectRepository.remove(projectRepository.findOne("1"));
        Assert.assertNotEquals(size, projectRepository.findAll().size());
        Assert.assertNull(projectRepository.findOne("1"));
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
    }

    @Test
    public void findOneByUserId() {
    }

    @Test
    public void removeAllByUserID() {
    }

    @Test
    public void sortAllByUserId() {
    }

    @Test
    public void findAllByPartOfNameOrDescription() {
    }
}