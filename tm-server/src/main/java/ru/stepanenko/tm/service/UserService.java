package ru.stepanenko.tm.service;

import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.util.DataValidator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService implements IUserService {

    @NotNull
    private final IUserRepository userRepository;

    @Inject
    public UserService(
            @NotNull final IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void create(
            @Nullable final UserDTO userDTO
    ) throws DataValidateException {
        DataValidator.validateUserDTO(userDTO, true);
        @NotNull final User user = convertDTOtoUser(userDTO);
        userRepository
                .save(user);
    }

    @Override
    @Transactional
    public void edit(
            @Nullable final UserDTO userDTO
    ) throws DataValidateException {
        DataValidator.validateUserDTO(userDTO, true);
        @Nullable final User user = userRepository
                .findBy(userDTO.getId());
        if (user == null) throw new DataValidateException("User not found");
        user.setName(userDTO.getName());
        user.setDescription(userDTO.getDescription());
        user.setLogin(userDTO.getLogin());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        userRepository
                .save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findByLogin(
            @Nullable final String login
    ) throws DataValidateException {
        DataValidator.validateString(login);
        @Nullable final User user = userRepository
                .findAnyByLogin(login);
        if (user == null) throw new DataValidateException("User not found");
        return user.getDTO();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findOne(
            @Nullable final String id
    ) throws DataValidateException {
        DataValidator.validateString(id);
        @Nullable final User user = userRepository.findBy(id);
        if (user == null) throw new DataValidateException("User not found!");
        return user.getDTO();
    }

    @Override
    @Transactional
    public void remove(
            @Nullable final String id
    ) throws DataValidateException {
        DataValidator.validateString(id);
        @Nullable User user = userRepository
                .findBy(id);
        if (user == null) throw new DataValidateException("User not found!");
        userRepository
                .remove(user);
    }

    @Transactional
    public void clear(
    ) throws DataValidateException{
        @Nullable final Collection<User> users = userRepository
                .findAll();
        if (users == null) throw new DataValidateException("Users not found!");
        users.forEach(userRepository::remove);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<UserDTO> findAll(
    ) throws DataValidateException {
        @Nullable final Collection<User> users = userRepository
                .findAll();
        if (users == null) throw new DataValidateException("Users not found!");
        return users
                .stream()
                .map(User::getDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO authenticationUser(
            @Nullable final String login,
            @Nullable final String password
    ) throws AuthenticationSecurityException, DataValidateException {
        DataValidator.validateString(login, password);
            @Nullable final User user = userRepository
                    .findAnyByLogin(login);
            if (user == null) throw new AuthenticationSecurityException("Wrong user name!");
            if (!user.getPassword().equals(password)) throw new AuthenticationSecurityException("Wrong password!");
            return user.getDTO();
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
