package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.*;
import ru.stepanenko.tm.AppServerTest;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.config.EntityManagerFactoryProducer;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.util.DataGenerator;

import javax.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest {

    @NotNull
    private static EntityManagerFactory entityManagerFactory;

    @NotNull
    private static DataGenerator testDataGenerator;

    @NotNull
    private IUserService userService;

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
        userService = new UserService(entityManagerFactory);
        testDataGenerator.generate();
    }

    @After
    public void tearDown() throws Exception {
        userService = null;
    }

    @Test
    public void create() throws DataValidateException {
        @NotNull final UserDTO user = new UserDTO();
        user.setLogin("root");
        user.setPassword("root");
        @NotNull final int size = userService.findAll().size();
        assertTrue(size > 0);
        userService.create(user);
        assertEquals(size + 1, userService.findAll().size());
    }

    @Test
    public void edit() throws DataValidateException {
        @Nullable final UserDTO user = getEntity();
        assertNotNull(user);
        assertNotEquals("root", user.getLogin());
        assertNotEquals("root", user.getPassword());
        user.setLogin("root");
        user.setPassword("root");
        userService.edit(user);
        assertEquals(user.getLogin(), userService.findOne(user.getId()).getLogin());
        assertEquals(null, userService.findOne(user.getId()).getPassword());//Server don't give a password
    }

    @Test
    public void findByLogin(
    ) throws DataValidateException {
        @NotNull final UserDTO user = new UserDTO();
        user.setLogin("root");
        user.setPassword("root");
        userService.create(user);
        assertEquals(user.getId(), userService.findByLogin(user.getLogin()).getId());

    }

    @Test
    public void clear() throws DataValidateException {
        @NotNull final int size = userService.findAll().size();
        assertTrue(size>0);
        userService.clear();
        assertEquals(0, userService.findAll().size());
    }

    @Test
    public void findOne() throws DataValidateException {
        @NotNull final UserDTO user = new UserDTO();
        user.setLogin("root");
        user.setPassword("root");
        userService.create(user);
        assertEquals(user.getId(), userService.findOne(user.getId()).getId());
    }

    @Test
    public void remove() throws DataValidateException {
        @NotNull final int size = userService.findAll().size();
        assertTrue(size>0);
        userService.remove(getEntity().getId());
        assertEquals(size-1, userService.findAll().size());
    }

    @Test
    public void findAll() throws DataValidateException {
        @NotNull final int size = userService.findAll().size();
        assertTrue(size>0);
        userService.clear();
        assertEquals(0, userService.findAll().size());
    }

    @Test
    public void authenticationUserPositive(
    ) throws DataValidateException, AuthenticationSecurityException {
        @NotNull final String login = "root";
        @NotNull final String password = "root";
        @NotNull final UserDTO user = new UserDTO();
        user.setLogin(login);
        user.setPassword(password);
        userService.create(user);
        assertEquals(user.getId(), userService.authenticationUser(login, password).getId());

    }

    @Test(expected=AuthenticationSecurityException.class)
    public void authenticationUserNegative(
    ) throws DataValidateException, AuthenticationSecurityException {
        @NotNull final String login = "root";
        @NotNull final String password = "root";
        @NotNull final UserDTO user = new UserDTO();
        user.setLogin(login);
        user.setPassword(password);
        userService.create(user);
        userService.authenticationUser(login, "wrong password").getId();

    }

    private UserDTO getEntity() throws DataValidateException {
        @Nullable final List<UserDTO> users = new ArrayList<>(userService.findAll());
        if (users.isEmpty()) return null;
        return users.get(0);
    }
}