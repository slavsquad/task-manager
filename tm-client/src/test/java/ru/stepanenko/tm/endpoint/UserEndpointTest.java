package ru.stepanenko.tm.endpoint;

import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import ru.stepanenko.tm.api.IntegrationTest;
import ru.stepanenko.tm.api.service.IEndpointProducerService;
import ru.stepanenko.tm.service.EndpointProducerService;
import ru.stepanenko.tm.util.HashUtil;

import java.util.UUID;

import static org.junit.Assert.*;

@Category(IntegrationTest.class)
public class UserEndpointTest {
    @NotNull
    private UserEndpoint userEndpoint;

    @NotNull
    private SessionEndpoint sessionEndpoint;

    @NotNull
    private SessionDTO currentSession;

    @Before
    public void setUp(
    ) throws DataValidateException_Exception, AuthenticationSecurityException_Exception {
        @NotNull final IEndpointProducerService endpointService = new EndpointProducerService();
        userEndpoint = endpointService.getUserEndpoint();
        sessionEndpoint = endpointService.getSessionEndpoint();
        currentSession = sessionEndpoint.openSession("testAdmin", HashUtil.md5("testAdmin"));
    }

    @After
    public void tearDown(
    ) throws DataValidateException_Exception, AuthenticationSecurityException_Exception {
        sessionEndpoint.closeSession(currentSession);
        userEndpoint = null;
        sessionEndpoint = null;
        currentSession = null;
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void userCRUID(
    ) throws DataValidateException_Exception, AuthenticationSecurityException_Exception {
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
        thrown.expect(DataValidateException_Exception.class);
        userEndpoint.findUserByLogin(currentSession, "Update_login");
    }
}