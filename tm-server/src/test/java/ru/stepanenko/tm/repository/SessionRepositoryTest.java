package ru.stepanenko.tm.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.*;
import ru.stepanenko.tm.AppServerTest;
import ru.stepanenko.tm.api.repository.ISessionRepository;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.config.EntityManagerFactoryProducer;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.Session;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.service.*;
import ru.stepanenko.tm.util.DataGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SessionRepositoryTest {

    @NotNull
    private static EntityManagerFactory entityManagerFactory;

    @NotNull
    private static DataGenerator testDataGenerator;

    @NotNull
    private ISessionRepository sessionRepository;

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
        sessionRepository = new SessionRepository(entityManager);
        testDataGenerator.generate();
    }

    @After
    public void tearDown() throws Exception {
        sessionRepository = null;
        entityManager.close();
        entityManager = null;
    }

    @Test
    public void findOne() {
        entityManager.getTransaction().begin();
        @Nullable final User user = getEntity().getUser();
        assertNotNull(user);
        @NotNull final Session session = new Session();
        session.setUser(user);
        sessionRepository.persist(session);
        assertEquals(session, sessionRepository.findOne(session.getId()));
        entityManager.getTransaction().commit();
    }

    @Test
    public void findAll() {
        entityManager.getTransaction().begin();
        assertTrue(sessionRepository.findAll().size() > 0);
        sessionRepository.removeAll();
        assertTrue(sessionRepository.findAll().isEmpty());
        entityManager.getTransaction().commit();
    }

    @Test
    public void removeAll() {
        entityManager.getTransaction().begin();
        assertTrue(sessionRepository.findAll().size() > 0);
        sessionRepository.removeAll();
        assertTrue(sessionRepository.findAll().isEmpty());
        entityManager.getTransaction().commit();
    }

    @Test
    public void remove() {
        entityManager.getTransaction().begin();
        @NotNull final int size = sessionRepository.findAll().size();
        sessionRepository.remove(sessionRepository.findOne(getEntity().getId()));
        assertEquals(size - 1, sessionRepository.findAll().size());
        entityManager.getTransaction().commit();
    }

    @Test
    public void persist() {
        entityManager.getTransaction().begin();
        @NotNull final Session session = new Session();
        session.setUser(getEntity().getUser());
        @NotNull final int size = sessionRepository.findAll().size();
        entityManager.persist(session);
        assertEquals(size + 1, sessionRepository.findAll().size());
        entityManager.getTransaction().commit();
    }

    @Test
    public void merge() {
        entityManager.getTransaction().begin();
        @Nullable final Session session = getEntity();
        assertNotNull(session);
        @NotNull final String uuid = session.getId();
        session.setName("update_name");
        session.setDescription("update_description");
        sessionRepository.merge(session);
        assertEquals("update_name", sessionRepository.findOne(uuid).getName());
        assertEquals("update_description", sessionRepository.findOne(uuid).getDescription());
        entityManager.getTransaction().commit();
    }

    @Test
    public void findAllByUserId() {
        entityManager.getTransaction().begin();
        @Nullable final User user = getEntity().getUser();
        assertNotNull(user);
        @NotNull final Session session = new Session();
        session.setUser(user);
        @NotNull final int size = sessionRepository.findAllByUserId(user).size();
        sessionRepository.persist(session);
        assertEquals(size + 1, sessionRepository.findAllByUserId(user).size());
        entityManager.getTransaction().commit();
    }

    private Session getEntity() {
        @NotNull final List<Session> sessions = new ArrayList<>(sessionRepository.findAll());
        if (sessions.isEmpty()) return null;
        return sessions.get(0);
    }
}