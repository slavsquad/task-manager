package ru.stepanenko.tm.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.util.FieldConst;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class UserController {

    @NotNull
    @Autowired
    private IUserService userService;

    @RequestMapping(value = {"user/login"}, method = {RequestMethod.POST})
    public void userLogin(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        @NotNull final String login = req.getParameter(FieldConst.LOGIN);
        @NotNull final String password = req.getParameter(FieldConst.PASSWORD);
        try {
            @NotNull final User loggedUser = userService.authenticationUser(login, password);
            session.setAttribute(FieldConst.USER, loggedUser);
            req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);
        } catch (AuthenticationSecurityException | DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (ServletException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
