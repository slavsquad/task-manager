package ru.stepanenko.tm.endpoint;

import org.jetbrains.annotations.Nullable;
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
import ru.stepanenko.tm.config.AppConfiguration;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.SessionDTO;
import ru.stepanenko.tm.util.DataGenerator;
import ru.stepanenko.tm.util.HashUtil;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfiguration.class)
public class SessionEndpointTest {

    @Autowired
    private ISessionEndpoint sessionEndpoint;

    @Autowired
    private DataGenerator dataGenerator;

    @Before
    public void setUp() {
        dataGenerator.generate();
    }

    @After
    public void tearDown(
    ) throws DataValidateException {
        dataGenerator.cleanUp();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void sessionCRUID(
    ) throws DataValidateException, AuthenticationSecurityException {
        @Nullable SessionDTO currentSession = sessionEndpoint.openSession("admin", HashUtil.md5("admin"));//CREATE
        assertNotNull(currentSession);
        assertEquals(currentSession.getId(), sessionEndpoint.findOneSession(currentSession, currentSession.getId()).getId());//READ
        sessionEndpoint.closeSession(currentSession);//DELETE
        thrown.expect(Exception.class);
        sessionEndpoint.findOneSession(currentSession, currentSession.getId());
    }

    @Test
    public void validate(
    ) throws DataValidateException, AuthenticationSecurityException {
        @Nullable SessionDTO currentSession = sessionEndpoint.openSession("user", HashUtil.md5("user"));//CREATE
        assertNotNull(currentSession);
        thrown.expect(DataValidateException.class);
        sessionEndpoint.validateSession(null);
        sessionEndpoint.validateSession(currentSession);
        thrown.expect(AuthenticationSecurityException.class);
        sessionEndpoint.validateAdminSession(currentSession);
        currentSession = sessionEndpoint.openSession("admin", HashUtil.md5("admin"));//CREATE
        assertNotNull(currentSession);
        sessionEndpoint.validateAdminSession(currentSession);
    }

}