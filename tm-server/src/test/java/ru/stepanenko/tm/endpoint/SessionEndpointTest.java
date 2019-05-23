package ru.stepanenko.tm.endpoint;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.*;
import ru.stepanenko.tm.AppServerTest;
import ru.stepanenko.tm.api.endpoint.ISessionEndpoint;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.config.EntityManagerFactoryProducer;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.SessionDTO;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.service.*;
import ru.stepanenko.tm.util.DataGenerator;

import javax.persistence.EntityManagerFactory;

import static org.junit.Assert.*;

public class SessionEndpointTest {

    @NotNull
    private static EntityManagerFactory entityManagerFactory;

    @NotNull
    private static DataGenerator testDataGenerator;

    @NotNull
    private ISessionEndpoint sessionEndpoint;

    @NotNull
    private UserDTO addedAdmin;

    @NotNull
    private UserDTO addedUser;

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
        @NotNull IUserService userService = new UserService(entityManagerFactory);
        @NotNull ISessionService sessionService = new SessionService(entityManagerFactory, new PropertyService(AppServerTest.class, "application.properties"));
        sessionEndpoint = new SessionEndpoint(userService, sessionService);
        testDataGenerator.generate();
        addedAdmin = new UserDTO();
        addedAdmin.setLogin("root");
        addedAdmin.setPassword("root");
        addedAdmin.setRole(Role.ADMIN);
        userService.create(addedAdmin);

        addedUser = new UserDTO();
        addedUser.setLogin("test");
        addedUser.setPassword("test");
        addedUser.setRole(Role.USER);
        userService.create(addedUser);

    }

    @After
    public void tearDown() throws Exception {
        sessionEndpoint = null;
        addedAdmin = null;
        addedUser = null;
    }

    @Test
    public void openSession() throws DataValidateException, AuthenticationSecurityException {
        @Nullable final SessionDTO session = sessionEndpoint.openSession(addedAdmin.getLogin(), addedAdmin.getPassword());
        assertNotNull(session);
        assertEquals(addedAdmin.getId(), session.getUserId());
    }

    @Test(expected = AuthenticationSecurityException.class)
    public void validateSession() throws DataValidateException, AuthenticationSecurityException {
        @Nullable final SessionDTO session = sessionEndpoint.openSession(addedAdmin.getLogin(), addedAdmin.getPassword());
        assertNotNull(session);
        session.setSignature("wrong signature");
        sessionEndpoint.validateSession(session);
    }

    @Test(expected = AuthenticationSecurityException.class)
    public void validateAdminSession() throws DataValidateException, AuthenticationSecurityException {
        @Nullable final SessionDTO session = sessionEndpoint.openSession(addedUser.getLogin(), addedUser.getPassword());
        assertNotNull(session);
        sessionEndpoint.validateAdminSession(session);
    }

    @Test(expected = DataValidateException.class)
    public void closeSession() throws DataValidateException, AuthenticationSecurityException {
        @Nullable final SessionDTO session = sessionEndpoint.openSession(addedAdmin.getLogin(), addedAdmin.getPassword());
        assertNotNull(session);
        assertEquals(session.getId(), sessionEndpoint.findOneSession(session, session.getId()).getId());
        sessionEndpoint.closeSession(session);
        //Exception session not found.
        assertEquals(session.getId(), sessionEndpoint.findOneSession(session, session.getId()).getId());
    }

    @Test
    public void findOneSession() throws DataValidateException, AuthenticationSecurityException {
        @Nullable final SessionDTO session = sessionEndpoint.openSession(addedAdmin.getLogin(), addedAdmin.getPassword());
        assertNotNull(session);
        assertEquals(session.getId(), sessionEndpoint.findOneSession(session, session.getId()).getId());
    }
}