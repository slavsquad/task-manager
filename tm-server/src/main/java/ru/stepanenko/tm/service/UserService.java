package ru.stepanenko.tm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.model.dto.TaskDTO;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.Task;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.repository.ProjectRepository;
import ru.stepanenko.tm.repository.UserRepository;
import ru.stepanenko.tm.util.HashUtil;
import ru.stepanenko.tm.util.DataValidator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
public final class UserService implements IUserService {

    @NotNull
    private final EntityManagerFactory entityManagerFactory;

    @Override
    public void create(
            @NotNull final UserDTO userDTO)
            throws DataValidateException {
        DataValidator.validateUserDTO(userDTO, true);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final IUserRepository userRepository = new UserRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @NotNull User user = convertDTOtoUser(userDTO);
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
            @NotNull final UserDTO userDTO)
            throws DataValidateException {
        DataValidator.validateUserDTO(userDTO, true);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final IUserRepository userRepository = new UserRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @NotNull User user = convertDTOtoUser(userDTO);
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
            @NotNull final String login)
            throws DataValidateException {
        DataValidator.validateString(login);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final IUserRepository userRepository = new UserRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @Nullable User user = userRepository
                    .findByLogin(login);
            entityManager.getTransaction().commit();
            if (user == null) throw new DataValidateException("User not found");
            return user.getDTO();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void clear()
            throws DataValidateException {
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
            @NotNull final String id)
            throws DataValidateException {
        DataValidator.validateString(id);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final IUserRepository userRepository = new UserRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @Nullable User user = userRepository
                    .findOne(id);
            entityManager.getTransaction().commit();
            if (user == null) throw new DataValidateException("User not found!");
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
            @NotNull final String id)
            throws DataValidateException {
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
    public Collection<UserDTO> findAll()
            throws DataValidateException {
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final IUserRepository userRepository = new UserRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @Nullable Collection<User> users = userRepository
                    .findAll();
            entityManager.getTransaction().commit();
            if (users == null) throw new DataValidateException("Users not found!");
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
            @NotNull final String login,
            @NotNull final String password)
            throws AuthenticationSecurityException, DataValidateException {
        DataValidator.validateString(login, password);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final IUserRepository userRepository = new UserRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @Nullable User user = userRepository
                    .findByLogin(login);
            entityManager.getTransaction().commit();
            if (user == null) throw new DataValidateException("User not found!");
            if (!user.getPassword().equals(password)) throw new AuthenticationSecurityException("Wrong password!");
            return user.getDTO();
        } catch (AuthenticationSecurityException e) {
            throw new AuthenticationSecurityException(e.getMessage());
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    private User convertDTOtoUser(
            @NotNull final UserDTO userDTO){
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
