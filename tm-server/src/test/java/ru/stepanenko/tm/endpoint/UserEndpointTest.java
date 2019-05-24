package ru.stepanenko.tm.endpoint;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.*;
import ru.stepanenko.tm.AppServerTest;
import ru.stepanenko.tm.api.endpoint.IProjectEndpoint;
import ru.stepanenko.tm.api.endpoint.IUserEndpoint;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.config.EntityManagerFactoryProducer;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.SessionDTO;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.service.*;
import ru.stepanenko.tm.util.DataGenerator;

import javax.persistence.EntityManagerFactory;

import static org.junit.Assert.*;

public class UserEndpointTest {

    @NotNull
    private static EntityManagerFactory entityManagerFactory;

    @NotNull
    private static DataGenerator testDataGenerator;

    @NotNull
    private IUserEndpoint userEndpoint;

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
    public void setUp(
    ) throws Exception {
        @NotNull IUserService userService = new UserService(entityManagerFactory);
        @NotNull ISessionService sessionService = new SessionService(entityManagerFactory, new PropertyService(AppServerTest.class, "application.properties"));
        userEndpoint = new UserEndpoint(userService, sessionService);
        testDataGenerator.generate();
        currentUser = userService.findByLogin("admin");
        currentSession = sessionService.create(currentUser);
    }

    @After
    public void tearDown(
    ) throws Exception {
        userEndpoint = null;
        currentSession = null;
        currentUser = null;
    }

    @Test
    public void createUser(
    ) throws DataValidateException, AuthenticationSecurityException {
        @NotNull final UserDTO user = new UserDTO();
        user.setLogin("root");
        user.setPassword("root");
        @NotNull final int size = userEndpoint.findAllUser(currentSession).size();
        assertTrue(size > 0);
        userEndpoint.createUser(currentSession, user);
        assertEquals(size + 1, userEndpoint.findAllUser(currentSession).size());
    }

    @Test
    public void changeUserPassword(
    ) throws DataValidateException, AuthenticationSecurityException {
        @NotNull ISessionService sessionService =
                new SessionService(entityManagerFactory,
                        new PropertyService(AppServerTest.class, "application.properties"));
        @NotNull IUserService userService = new UserService(entityManagerFactory);
        @NotNull final UserDTO user = new UserDTO();
        user.setLogin("root");
        user.setPassword("root");
        userEndpoint.createUser(currentSession, user);
        assertNotNull(sessionService.create(userService.authenticationUser("root", "root")));
        user.setPassword("toor");
        userEndpoint.changeUserPassword(currentSession, user);
        assertNotNull(sessionService.create(userService.authenticationUser("root", "toor")));
    }

    @Test
    public void editUserProfile(
    ) throws DataValidateException, AuthenticationSecurityException {
        @Nullable final UserDTO user = currentUser;
        assertNotNull(user);
        assertNotEquals("root", user.getLogin());
        assertNotEquals("root", user.getPassword());
        user.setLogin("root");
        user.setPassword("root");
        userEndpoint.editUserProfile(currentSession, user);
        assertEquals(user.getLogin(), userEndpoint.findUserById(currentSession, currentUser.getId()).getLogin());
        assertEquals(null, userEndpoint.findUserById(currentSession, currentUser.getId()).getPassword());//Server don't give a password
    }

    @Test
    public void findUserByLogin(
    ) throws DataValidateException, AuthenticationSecurityException {
        @NotNull final UserDTO user = new UserDTO();
        user.setLogin("root");
        user.setPassword("root");
        userEndpoint.createUser(currentSession, user);
        assertEquals(user.getId(), userEndpoint.findUserByLogin(currentSession, user.getLogin()).getId());
    }

    @Test
    public void getUserBySession(
    ) throws DataValidateException, AuthenticationSecurityException {
        @NotNull ISessionService sessionService =
                new SessionService(entityManagerFactory,
                        new PropertyService(AppServerTest.class, "application.properties"));
        @NotNull IUserService userService = new UserService(entityManagerFactory);
        @NotNull final UserDTO user = new UserDTO();
        user.setLogin("root");
        user.setPassword("root");
        userEndpoint.createUser(currentSession, user);
        @Nullable final SessionDTO session = sessionService.create(userService.authenticationUser("root", "root"));
        assertNotNull(session);
        assertEquals(user.getId(), userEndpoint.getUserBySession(session).getId());
    }

    @Test
    public void findAllUser(
    ) throws DataValidateException, AuthenticationSecurityException {
        @NotNull final int size = userEndpoint.findAllUser(currentSession).size();
        assertTrue(size > 0);
        @NotNull final UserDTO user = new UserDTO();
        user.setLogin("root");
        user.setPassword("root");
        userEndpoint.createUser(currentSession, user);
        assertEquals(size + 1, userEndpoint.findAllUser(currentSession).size());
    }

    @Test
    public void removeOneUser(
    ) throws DataValidateException, AuthenticationSecurityException {
        @NotNull final UserDTO user = new UserDTO();
        user.setLogin("root");
        user.setPassword("root");
        userEndpoint.createUser(currentSession, user);
        @NotNull final int size = userEndpoint.findAllUser(currentSession).size();
        assertTrue(size > 0);
        userEndpoint.removeOneUser(currentSession, user.getId());
        assertEquals(size - 1, userEndpoint.findAllUser(currentSession).size());

    }
}