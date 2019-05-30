package ru.stepanenko.tm.endpoint;

import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.stepanenko.tm.api.endpoint.ISessionEndpoint;
import ru.stepanenko.tm.api.endpoint.IUserEndpoint;
import ru.stepanenko.tm.config.AppConfiguration;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.SessionDTO;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.util.DataGenerator;
import ru.stepanenko.tm.util.HashUtil;

import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfiguration.class)
public class UserEndpointTest {

    @Autowired
    private IUserEndpoint userEndpoint;

    @Autowired
    private ISessionEndpoint sessionEndpoint;

    @Autowired
    private DataGenerator dataGenerator;

    @NotNull
    private SessionDTO currentSession;

    @Before
    public void setUp(
    ) throws DataValidateException, AuthenticationSecurityException {
        dataGenerator.generate();
        currentSession = sessionEndpoint.openSession("admin", HashUtil.md5("admin"));
    }

    @After
    public void tearDown(
    ) throws DataValidateException, AuthenticationSecurityException {
        sessionEndpoint.closeSession(currentSession);
        dataGenerator.cleanUp();
        currentSession = null;
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void userCRUID(
    ) throws DataValidateException, AuthenticationSecurityException {
        assertNotNull(currentSession);
        @NotNull final UserDTO user = new UserDTO();
        user.setId(UUID.randomUUID().toString());
        user.setLogin("new_user");
        user.setPassword(HashUtil.md5("new_user"));
        user.setRole(Role.ADMIN);
        userEndpoint.createUser(currentSession, user);//CREATE
        assertEquals(user.getId(), userEndpoint.findUserById(currentSession, user.getId()).getId());//READ
        user.setLogin("Update_login");
        user.setDescription("Update_description");
        userEndpoint.editUserProfile(currentSession, user);//UPDATE
        assertEquals(user.getLogin(), userEndpoint.findUserByLogin(currentSession, "Update_login").getLogin());
        assertEquals(user.getDescription(), userEndpoint.findUserById(currentSession, user.getId()).getDescription());
        userEndpoint.removeOneUser(currentSession, user.getId());//DELETE
        thrown.expect(DataValidateException.class);
        userEndpoint.findUserByLogin(currentSession, "Update_login");
    }
}