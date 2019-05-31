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
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.config.AppConfiguration;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.util.DataGenerator;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfiguration.class)
public class UserServiceTest {

    @Autowired
    IUserService userService;

    @Autowired
    DataGenerator dataGenerator;

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
        user.setLogin("change login");
        userService.edit(user);
        assertEquals(user.getLogin(), userService.findOne(userId).getLogin());
        @Nullable final int size = userService.findAll().size();
        assertNotNull(size);
        userService.remove(userId);
        assertEquals(size-1,userService.findAll().size());
    }
}