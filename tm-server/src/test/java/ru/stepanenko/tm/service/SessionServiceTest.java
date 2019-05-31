package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
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
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.config.AppConfiguration;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.SessionDTO;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.util.DataGenerator;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfiguration.class)
public class SessionServiceTest {

    @Autowired
    ISessionService sessionService;

    @Autowired
    IUserService userService;

    @Autowired
    DataGenerator dataGenerator;

    @Before
    public void setUp() {
        dataGenerator.generate();
    }

    @After
    public void tearDown() throws DataValidateException {
        dataGenerator.cleanUp();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void sessionCRUD(
    ) throws DataValidateException {
        @Nullable final UserDTO user = userService.findByLogin("admin");
        assertNotNull(user);
        @NotNull final SessionDTO session = sessionService.create(user);
        @NotNull final String sessionId = session.getId();
        assertEquals(sessionId, sessionService.findOne(sessionId).getId());
        @Nullable final int size = sessionService.findAll().size();
        assertNotNull(size);
        sessionService.remove(sessionId);
        assertEquals(size-1,sessionService.findAll().size());
    }

    @Test
    public void validate() throws DataValidateException, AuthenticationSecurityException {
        @Nullable UserDTO user = userService.findByLogin("admin");
        assertNotNull(user);
        @NotNull SessionDTO session = sessionService.create(user);
        sessionService.validate(session);
        sessionService.validateAdmin(session);

        user = userService.findByLogin("user");
        assertNotNull(user);
        session = sessionService.create(user);
        sessionService.validate(session);
        thrown.expect(AuthenticationSecurityException.class);
        sessionService.validateAdmin(session);
    }
}