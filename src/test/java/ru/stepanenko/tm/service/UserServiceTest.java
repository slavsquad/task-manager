package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.config.DataBaseConfig;
import ru.stepanenko.tm.config.WebMvcConfig;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.util.DataGenerator;

import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebMvcConfig.class, DataBaseConfig.class})
public class UserServiceTest {

    @Autowired
    private IUserService userService;

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

    @Test
    public void userCRUD(
    ) throws DataValidateException {
        @NotNull final UserDTO user = new UserDTO();
        user.setLogin("root");
        user.setPassword("root");
        user.setRole(Role.ADMIN);
        @NotNull final String userId = user.getId();
        userService.create(user);
        assertEquals(userId, userService.findByLogin("root").getId());
        user.setLogin("change userLogin");
        userService.edit(user);
        assertEquals(user.getLogin(), userService.findOne(userId).getLogin());
        @Nullable final int size = userService.findAll().size();
        assertNotNull(size);
        userService.remove(userId);
        assertEquals(size - 1, userService.findAll().size());
    }

    @Test
    public void userLogin(
    ) throws DataValidateException, AuthenticationSecurityException {
        @NotNull final UserDTO user = new UserDTO();
        user.setLogin("root");
        user.setPassword("root");
        user.setRole(Role.ADMIN);
        userService.create(user);
        @NotNull final String userId = user.getId();
        @NotNull final UserDTO loggedUser = userService.authenticationUser("root", "root");
        assertEquals(user.getId(), loggedUser.getId());
    }
}