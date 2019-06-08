package ru.stepanenko.tm.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.model.entity.User;

import static org.junit.Assert.*;

public class UserRepositoryTest {

    @NotNull
    private IUserRepository userRepository;

    @Before
    public void setUp() {
        userRepository = UserRepository.INSTANCE;
    }

    @After
    public void tearDown() {
        userRepository = null;
    }

    @Test
    public void userCRUD() {
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
        userRepository.remove(userId);
        assertEquals(size - 1, userRepository.findAll().size());
    }
}