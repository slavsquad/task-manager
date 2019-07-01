package ru.stepanenko.tm.rest;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.stepanenko.tm.api.rest.IUserRestController;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.util.FieldConst;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("rest/user/")
public class UserRestController implements IUserRestController {

    @Autowired
    @NotNull IUserService userService;

    @Override
    @PostMapping(value = "login")
    public void login(
            @RequestParam(name = "login") @NotNull final String login,
            @RequestParam(name = "password") @NotNull final String password,
            @NotNull final HttpSession session
    ) throws DataValidateException, AuthenticationSecurityException {
        @NotNull final UserDTO loggedUser = userService.authenticationUser(login, password);
        session.setAttribute(FieldConst.USER, loggedUser);
    }

    @Override
    @PostMapping(value = "logout")
    public void logout(
            @NotNull final HttpSession session){
        session.invalidate();
    }
}
