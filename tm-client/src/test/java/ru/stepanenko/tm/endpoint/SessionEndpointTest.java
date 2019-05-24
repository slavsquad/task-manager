package ru.stepanenko.tm.endpoint;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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

import static org.junit.Assert.*;

@Category(IntegrationTest.class)
public class SessionEndpointTest {

    @NotNull
    private SessionEndpoint sessionEndpoint;

    @Before
    public void setUp(
    ) throws Exception {
        @NotNull final IEndpointProducerService endpointService = new EndpointProducerService();
        sessionEndpoint = endpointService.getSessionEndpoint();
    }

    @After
    public void tearDown(
    ) throws Exception {
        sessionEndpoint = null;
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void sessionCRUID(
    ) throws DataValidateException_Exception, AuthenticationSecurityException_Exception {
        @Nullable SessionDTO currentSession = sessionEndpoint.openSession("testAdmin", HashUtil.md5("testAdmin"));//CREATE
        assertNotNull(currentSession);
        assertEquals(currentSession.getId(), sessionEndpoint.findOneSession(currentSession, currentSession.getId()).getId());//READ
        sessionEndpoint.closeSession(currentSession);//DELETE
        thrown.expect(Exception.class);
        sessionEndpoint.findOneSession(currentSession, currentSession.getId());
    }

    @Test
    public void validate(
    ) throws DataValidateException_Exception, AuthenticationSecurityException_Exception {
        @Nullable SessionDTO currentSession = sessionEndpoint.openSession("testUser", HashUtil.md5("testUser"));//CREATE
        assertNotNull(currentSession);
        thrown.expect(DataValidateException_Exception.class);
        sessionEndpoint.validateSession(null);
        sessionEndpoint.validateSession(currentSession);
        thrown.expect(AuthenticationSecurityException_Exception.class);
        sessionEndpoint.validateAdminSession(currentSession);
        currentSession = sessionEndpoint.openSession("testAdmin", HashUtil.md5("testAdmin"));//CREATE
        assertNotNull(currentSession);
        sessionEndpoint.validateAdminSession(currentSession);
    }
}