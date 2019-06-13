package ru.stepanenko.tm.controller;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.enumerate.Role;
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

    @NotNull
    @Autowired
    private ISessionService sessionService;

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
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            System.out.println("sfsdfsdfsdfsdf");
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (ServletException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @RequestMapping(value = {"user/edit"}, method = {RequestMethod.GET})
    public void userEdit(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        try {
            sessionService.validateSession(session);
            @NotNull final User loggedUser = (User) session.getAttribute(FieldConst.USER);
            @Nullable final String userId = req.getParameter(FieldConst.USER_ID);
            if (!loggedUser.getId().equals(userId)) sessionService.validateAdminSession(session);
            @NotNull final User user = userService.findOne(userId);
            req.setAttribute(FieldConst.USER, user);
            req.getRequestDispatcher("/WEB-INF/jsp/user/userEdit.jsp").forward(req, resp);
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (ServletException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @RequestMapping(value = {"user/edit"}, method = {RequestMethod.POST})
    public void userUpdate(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        try {
            sessionService.validateSession(session);
            @NotNull final User loggedUser = (User) session.getAttribute(FieldConst.USER);
            @Nullable final String userId = req.getParameter(FieldConst.USER_ID);
            if (!loggedUser.getId().equals(userId)) sessionService.validateAdminSession(session);
            @NotNull final User user = new User(
                    req.getParameter(FieldConst.LOGIN),
                    req.getParameter(FieldConst.PASSWORD),
                    req.getParameter(FieldConst.NAME),
                    req.getParameter(FieldConst.DESCRIPTION),
                    Role.valueOf(req.getParameter(FieldConst.ROLE)));
            user.setId(userId);
            userService.edit(user);
            if (!loggedUser.getId().equals(userId)) {
                resp.sendRedirect(req.getContextPath() + "/user/list");
                return;
            }
            resp.sendRedirect(req.getContextPath());
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    @RequestMapping(value = {"user/logout"}, method = {RequestMethod.POST})
    public void userLogout(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        session.invalidate();
        resp.sendRedirect(req.getContextPath());
    }

    @RequestMapping(value = {"user/list"}, method = RequestMethod.GET)
    public void userList(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        try {
            sessionService.validateAdminSession(session);
            req.setAttribute(FieldConst.USERS, userService.findAll());
            req.getRequestDispatcher("/WEB-INF/jsp/user/userList.jsp").forward(req, resp);
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (ServletException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @RequestMapping(value = {"user/create"}, method = RequestMethod.POST)
    public void userCreate(@NotNull final HttpSession session,
                           @NotNull final HttpServletRequest req,
                           @NotNull final HttpServletResponse resp
    ) throws IOException {
        try {
            sessionService.validateAdminSession(session);
            @NotNull final User user = new User(
                    "New User:",
                    "password",
                    "New user name",
                    "Description for new user",
                    Role.USER);
            user.setLogin("New User:" + user.getId());
            userService.create(user);
            resp.sendRedirect(req.getContextPath() + "/user/list");
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    @RequestMapping(value = {"user/delete"}, method = RequestMethod.POST)
    public void userDelete(@NotNull final HttpSession session,
                           @NotNull final HttpServletRequest req,
                           @NotNull final HttpServletResponse resp
    ) throws IOException {
        try {
            sessionService.validateAdminSession(session);
            userService.remove(req.getParameter(FieldConst.USER_ID));
            resp.sendRedirect(req.getContextPath() + "/user/list");
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

}