package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.*;
import ru.stepanenko.tm.AppServerTest;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.config.EntityManagerFactoryProducer;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.SessionDTO;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.util.DataGenerator;

import javax.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SessionServiceTest {

    @NotNull
    private static EntityManagerFactory entityManagerFactory;

    @NotNull
    private static DataGenerator testDataGenerator;

    @NotNull
    private ISessionService sessionService;

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
        sessionService = new SessionService(entityManagerFactory, new PropertyService(AppServerTest.class, "application.properties"));
        testDataGenerator.generate();
    }

    @After
    public void tearDown() throws Exception {
        sessionService = null;
    }

    @Test
    public void findOne() throws DataValidateException {
        @Nullable final String userId = getEntity().getUserId();
        assertNotNull(userId);
        @NotNull final UserDTO loggedUser = new UserDTO();
        loggedUser.setId(userId);
        loggedUser.setLogin("dummy");
        loggedUser.setPassword("dummy");
        @Nullable final SessionDTO session = sessionService.create(loggedUser);
        assertEquals(loggedUser.getId(), session.getUserId());
        assertEquals(session.getId(), sessionService.findOne(session.getId()).getId());
    }

    @Test
    public void remove() throws DataValidateException {
        @NotNull final int size = sessionService.findAll().size();
        assertTrue(size>0);
        sessionService.remove(getEntity().getId());
        assertEquals(size-1, sessionService.findAll().size());
    }

    @Test
    public void clear() throws DataValidateException {
        @NotNull final int size = sessionService.findAll().size();
        assertTrue(size>0);
        sessionService.clear();
        assertEquals(0, sessionService.findAll().size());
    }

    @Test
    public void findAll() throws DataValidateException {
        @NotNull final int size = sessionService.findAll().size();
        assertTrue(size>0);
        sessionService.clear();
        assertEquals(0, sessionService.findAll().size());
    }

    @Test
    public void create() throws DataValidateException {
        @Nullable final String userId = getEntity().getUserId();
        assertNotNull(userId);
        @NotNull final UserDTO loggedUser = new UserDTO();
        loggedUser.setId(userId);
        loggedUser.setLogin("dummy");
        loggedUser.setPassword("dummy");
        @NotNull final int size = sessionService.findAll().size();
        assertTrue(size>0);
        sessionService.create(loggedUser);
        assertEquals(size+1, sessionService.findAll().size());

    }

    @Test
    public void validatePositive() throws DataValidateException, AuthenticationSecurityException {
        @Nullable final String userId = getEntity().getUserId();
        assertNotNull(userId);
        @NotNull final UserDTO loggedUser = new UserDTO();
        loggedUser.setId(userId);
        loggedUser.setLogin("dummy");
        loggedUser.setPassword("dummy");
        @Nullable final SessionDTO session = sessionService.create(loggedUser);
        sessionService.validate(session);
    }

    @Test(expected = DataValidateException.class)
    public void validateNegative() throws DataValidateException, AuthenticationSecurityException {
        @NotNull final SessionDTO session = new SessionDTO();
        sessionService.validate(session);
    }

    @Test
    public void validateAdminPositive() throws DataValidateException, AuthenticationSecurityException {
        @Nullable final UserDTO admin = new UserService(entityManagerFactory).findByLogin("admin");
        assertNotNull(admin);
        @Nullable final SessionDTO session = sessionService.create(admin);
        assertNotNull(session);
        sessionService.validateAdmin(session);
    }

    @Test(expected = AuthenticationSecurityException.class)
    public void validateAdminNegative() throws DataValidateException, AuthenticationSecurityException {
        @Nullable final UserDTO user = new UserService(entityManagerFactory).findByLogin("user");
        assertNotNull(user);
        @Nullable final SessionDTO session = sessionService.create(user);
        assertNotNull(session);
        sessionService.validateAdmin(session);
    }

    private SessionDTO getEntity() throws DataValidateException {
        @Nullable final List<SessionDTO> sessions = new ArrayList<>(sessionService.findAll());
        if (sessions.isEmpty()) return null;
        return sessions.get(0);
    }
}