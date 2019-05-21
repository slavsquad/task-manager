package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.repository.UserRepository;
import ru.stepanenko.tm.util.DataValidator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;
import java.util.stream.Collectors;

@ApplicationScoped
public final class UserService implements IUserService {

    @NotNull
    private final EntityManagerFactory entityManagerFactory;

    @Inject
    public UserService(
            @NotNull final EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void create(
            @Nullable final UserDTO userDTO
    ) throws DataValidateException {
        DataValidator.validateUserDTO(userDTO, true);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final IUserRepository userRepository = new UserRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @NotNull final User user = convertDTOtoUser(userDTO);
            userRepository
                    .persist(user);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void edit(
            @Nullable final UserDTO userDTO
    ) throws DataValidateException {
        DataValidator.validateUserDTO(userDTO, true);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final IUserRepository userRepository = new UserRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @Nullable final User user = userRepository.findOne(userDTO.getId());
            if (user == null) throw new DataValidateException("User not found");
            user.setName(userDTO.getName());
            user.setDescription(userDTO.getDescription());
            user.setLogin(userDTO.getLogin());
            user.setPassword(userDTO.getPassword());
            user.setRole(userDTO.getRole());
            userRepository
                    .merge(user);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public UserDTO findByLogin(
            @Nullable final String login
    ) throws DataValidateException {
        DataValidator.validateString(login);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final IUserRepository userRepository = new UserRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @Nullable final User user = userRepository
                    .findByLogin(login);
            if (user == null) throw new DataValidateException("User not found");
            entityManager.getTransaction().commit();
            return user.getDTO();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void clear(
    ) throws DataValidateException {
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final IUserRepository userRepository = new UserRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            userRepository
                    .removeAll();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public UserDTO findOne(
            @Nullable final String id
    ) throws DataValidateException {
        DataValidator.validateString(id);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final IUserRepository userRepository = new UserRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @Nullable final User user = userRepository
                    .findOne(id);
            if (user == null) throw new DataValidateException("User not found!");
            entityManager.getTransaction().commit();
            return user.getDTO();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void remove(
            @Nullable final String id
    ) throws DataValidateException {
        DataValidator.validateString(id);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final IUserRepository userRepository = new UserRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @Nullable User user = userRepository
                    .findOne(id);
            if (user == null) throw new DataValidateException("User not found!");
            userRepository
                    .remove(user);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<UserDTO> findAll(
    ) throws DataValidateException {
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final IUserRepository userRepository = new UserRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @Nullable final Collection<User> users = userRepository
                    .findAll();
            if (users == null) throw new DataValidateException("Users not found!");
            entityManager.getTransaction().commit();
            return users
                    .stream()
                    .map(User::getDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public UserDTO authenticationUser(
            @Nullable final String login,
            @Nullable final String password
    ) throws AuthenticationSecurityException, DataValidateException {
        DataValidator.validateString(login, password);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final IUserRepository userRepository = new UserRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @Nullable final User user = userRepository
                    .findByLogin(login);
            if (user == null) throw new AuthenticationSecurityException("Wrong user name!");
            if (!user.getPassword().equals(password)) throw new AuthenticationSecurityException("Wrong password!");
            entityManager.getTransaction().commit();
            return user.getDTO();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.println("Transaction rollback!");
            throw new AuthenticationSecurityException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    private User convertDTOtoUser(
            @Nullable final UserDTO userDTO) {
        @NotNull final User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setDescription(userDTO.getDescription());
        user.setLogin(userDTO.getLogin());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        return user;
    }
}
