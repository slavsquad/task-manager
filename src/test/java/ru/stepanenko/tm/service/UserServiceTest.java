package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.repository.ProjectRepository;
import ru.stepanenko.tm.repository.UserRepository;

import static org.junit.Assert.*;

public class UserServiceTest {

    IUserService userService;

    @Before
    public void setUp() {
        userService =  UserService.INSTANCE;
    }

    @After
    public void tearDown() {
        userService = null;
    }

    @Test
    public void userCRUD(
    ) throws DataValidateException {
        @NotNull final User user = new User();
        user.setLogin("root");
        user.setPassword("root");
        user.setRole(Role.ADMIN);
        @NotNull final String userId = user.getId();
        userService.create(user);
        assertEquals(userId, userService.findByLogin("root").getId());
        user.setLogin("change login");
        userService.edit(user);
        assertEquals(user.getLogin(), userService.findOne(userId).getLogin());
        @Nullable final int size = userService.findAll().size();
        assertNotNull(size);
        userService.remove(userId);
        assertEquals(size - 1, userService.findAll().size());
    }

    @Test
    public void authentication() throws DataValidateException, AuthenticationSecurityException {
        @NotNull final User user = new User();
        user.setLogin("root");
        user.setPassword("root");
        user.setRole(Role.ADMIN);
        userService.create(user);
        @Nullable final User loggedUser = userService.authenticationUser("root", "root");
        assertNotNull(loggedUser);
        assertEquals(user.getId(), loggedUser.getId());
    }
}