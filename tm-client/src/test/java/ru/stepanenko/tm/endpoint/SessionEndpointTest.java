package ru.stepanenko.tm.endpoint;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.stepanenko.tm.api.IntegrationTest;
import ru.stepanenko.tm.config.AppConfiguration;
import ru.stepanenko.tm.util.HashUtil;

import static org.junit.Assert.*;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfiguration.class)
public class SessionEndpointTest {

    @NotNull
    @Autowired
    private SessionEndpoint sessionEndpoint;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
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