package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import ru.stepanenko.tm.api.UnitTest;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.endpoint.SessionDTO;

import static org.junit.Assert.*;

@Category(UnitTest.class)
public class SessionServiceTest {

    @NotNull
    private ISessionService sessionService;

    @Before
    public void setUp(
    ) throws Exception {
        sessionService = new SessionService();
    }

    @After
    public void tearDown(
    ) throws Exception {
        sessionService = null;

    }

    @Test
    public void getCurrentSession() {
        @NotNull final SessionDTO session = new SessionDTO();
        @NotNull final String uuid = session.getId();
        sessionService.setCurrentSession(session);
        assertEquals(uuid, sessionService.getCurrentSession().getId());
    }

    @Test
    public void setCurrentSession() {
        @NotNull final SessionDTO session = new SessionDTO();
        @NotNull final String uuid = session.getId();
        sessionService.setCurrentSession(session);
        assertEquals(uuid, sessionService.getCurrentSession().getId());
    }
}