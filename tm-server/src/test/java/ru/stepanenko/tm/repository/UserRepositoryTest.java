package ru.stepanenko.tm.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.*;
import ru.stepanenko.tm.AppServerTest;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.config.EntityManagerFactoryProducer;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.service.*;
import ru.stepanenko.tm.util.DataGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserRepositoryTest {

    @NotNull
    private static EntityManagerFactory entityManagerFactory;

    @NotNull
    private static DataGenerator testDataGenerator;

    @NotNull
    private IUserRepository userRepository;

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
        userRepository = new UserRepository(entityManager);
        testDataGenerator.generate();
    }

    @After
    public void tearDown() throws Exception {
        userRepository = null;
        entityManager.close();
        entityManager = null;
    }

    @Test
    public void findOne() {
        entityManager.getTransaction().begin();
        @NotNull final User user = new User();
        userRepository.persist(user);
        assertEquals(user, userRepository.findOne(user.getId()));
        entityManager.getTransaction().commit();
    }

    @Test
    public void findAll() {
        entityManager.getTransaction().begin();
        assertTrue(userRepository.findAll().size() > 0);
        userRepository.removeAll();
        assertTrue(userRepository.findAll().isEmpty());
        entityManager.getTransaction().commit();
    }

    @Test
    public void removeAll() {
        entityManager.getTransaction().begin();
        assertTrue(userRepository.findAll().size() > 0);
        userRepository.removeAll();
        assertTrue(userRepository.findAll().isEmpty());
        entityManager.getTransaction().commit();
    }

    @Test
    public void remove() {
        entityManager.getTransaction().begin();
        @NotNull final int size = userRepository.findAll().size();
        userRepository.remove(userRepository.findOne(getEntity().getId()));
        assertEquals(size - 1, userRepository.findAll().size());
        entityManager.getTransaction().commit();
    }

    @Test
    public void persist() {
        entityManager.getTransaction().begin();
        @NotNull final User user = new User();
        @NotNull final int size = userRepository.findAll().size();
        entityManager.persist(user);
        assertEquals(size + 1, userRepository.findAll().size());
        entityManager.getTransaction().commit();
    }

    @Test
    public void merge() {
        entityManager.getTransaction().begin();
        @Nullable final User user = getEntity();
        assertNotNull(user);
        @NotNull final String uuid = user.getId();
        user.setLogin("update_login");
        user.setPassword("update_password");
        @NotNull final int size = userRepository.findAll().size();
        assertTrue(size>0);
        userRepository.merge(user);
        assertEquals("update_login", userRepository.findOne(uuid).getLogin());
        assertEquals("update_password", userRepository.findOne(uuid).getPassword());
        assertEquals(size, userRepository.findAll().size());
        entityManager.getTransaction().commit();
    }

    @Test
    public void findByLogin() {
        entityManager.getTransaction().begin();
        @NotNull final User user = new User();
        @NotNull final String login = "root";
        user.setLogin(login);
        assertNull(userRepository.findByLogin(login));
        userRepository.persist(user);
        assertEquals(user, userRepository.findByLogin(login));
        entityManager.getTransaction().commit();
    }

    private User getEntity() {
        @NotNull final List<User> users = new ArrayList<>(userRepository.findAll());
        if (users.isEmpty()) return null;
        return users.get(0);
    }
}