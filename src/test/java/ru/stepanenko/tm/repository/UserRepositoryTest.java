package ru.stepanenko.tm.repository;

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
import org.springframework.transaction.annotation.Transactional;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.config.DataBaseConfig;
import ru.stepanenko.tm.config.WebMvcConfig;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.util.DataGenerator;

import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebMvcConfig.class, DataBaseConfig.class})
public class UserRepositoryTest {

    @Autowired
    private IUserRepository userRepository;

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
    @Transactional
    public void userCRUD(
    ) throws DataValidateException {
        @NotNull final User user = new User();
        user.setLogin("root");
        user.setPassword("root");
        user.setRole(Role.ADMIN);
        @NotNull final String userId = user.getId();
        userRepository.persist(user);
        assertEquals(userId, userRepository.findByLogin("root").getId());
        user.setLogin("change login");
        userRepository.merge(user);
        assertEquals(user.getLogin(), userRepository.findOne(userId).getLogin());
        @Nullable final int size = userRepository.findAll().size();
        assertNotNull(size);
        userRepository.remove(user);
        assertEquals(size - 1, userRepository.findAll().size());
    }

}