package ru.stepanenko.tm.controller;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.util.DataValidator;
import ru.stepanenko.tm.util.FieldConst;
import ru.stepanenko.tm.util.HashUtil;

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
    public ModelAndView userLogin(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        @NotNull final ModelAndView model = new ModelAndView("index");
        @NotNull final String login = req.getParameter(FieldConst.LOGIN);
        @NotNull final String password = req.getParameter(FieldConst.PASSWORD);
        try {
            @NotNull final UserDTO loggedUser = userService.authenticationUser(login, HashUtil.md5(password));
            session.setAttribute(FieldConst.USER, loggedUser);
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return model;
    }

    @RequestMapping(value = {"user/edit"}, method = {RequestMethod.GET})
    public ModelAndView userEdit(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        @NotNull final ModelAndView model = new ModelAndView("user/userEdit");
        try {
            sessionService.validateSession(session);
            @NotNull final UserDTO loggedUser = (UserDTO) session.getAttribute(FieldConst.USER);
            @Nullable final String userId = req.getParameter(FieldConst.USER_ID);
            if (!loggedUser.getId().equals(userId)) sessionService.validateAdminSession(session);
            @NotNull final UserDTO user = userService.findOne(userId);
            model.addObject(FieldConst.USER, user);
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return model;
    }

    @RequestMapping(value = {"user/edit"}, method = {RequestMethod.POST})
    public String userUpdate(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        try {
            sessionService.validateSession(session);
            @NotNull final UserDTO loggedUser = (UserDTO) session.getAttribute(FieldConst.USER);
            @Nullable final String userId = req.getParameter(FieldConst.USER_ID);
            if (!loggedUser.getId().equals(userId)) sessionService.validateAdminSession(session);
            @NotNull final UserDTO user = new UserDTO(
                    req.getParameter(FieldConst.LOGIN),
                    HashUtil.md5(req.getParameter(FieldConst.PASSWORD)),
                    req.getParameter(FieldConst.NAME),
                    req.getParameter(FieldConst.DESCRIPTION),
                    Role.valueOf(req.getParameter(FieldConst.ROLE)));
            user.setId(userId);
            userService.edit(user);
            if (!loggedUser.getId().equals(userId)) {
                return "redirect:/user/list";
            }
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return "redirect:/";
    }

    @RequestMapping(value = {"user/logout"}, method = {RequestMethod.POST})
    public String userLogout(
            @NotNull final HttpSession session
    ) {
        session.setAttribute(FieldConst.USER, null);
        return "redirect:/";
    }

    @RequestMapping(value = {"user/list"}, method = RequestMethod.GET)
    public ModelAndView userList(
            @NotNull final HttpSession session,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        @NotNull final ModelAndView model = new ModelAndView("user/userList");
        try {
            sessionService.validateAdminSession(session);
            model.addObject(FieldConst.USERS, userService.findAll());
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return model;
    }

    @RequestMapping(value = {"user/create"}, method = RequestMethod.POST)
    public String userCreate(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        try {
            sessionService.validateAdminSession(session);
            @NotNull final UserDTO user = new UserDTO(
                    "New User:",
                    "password",
                    "New user name",
                    "Description for new user",
                    Role.USER);
            user.setLogin("New User:" + user.getId());
            userService.create(user);
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return "redirect:/user/list";
    }

    @RequestMapping(value = {"user/delete"}, method = RequestMethod.POST)
    public String userDelete(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        try {
            sessionService.validateAdminSession(session);
            userService.remove(req.getParameter(FieldConst.USER_ID));
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return "redirect:/user/list";
    }

    @RequestMapping(value = {"user/register"}, method = RequestMethod.GET)
    public ModelAndView userRegister(
    ) {
        @NotNull final ModelAndView model = new ModelAndView("user/userRegister");
        return model;
    }

    @RequestMapping(value = {"user/register"}, method = RequestMethod.POST)
    public ModelAndView userRegister(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        @NotNull final ModelAndView model = new ModelAndView("user/userSuccessRegister");
        try {
            @NotNull final UserDTO user = new UserDTO(
                    req.getParameter(FieldConst.LOGIN),
                    HashUtil.md5(req.getParameter(FieldConst.PASSWORD)),
                    req.getParameter(FieldConst.NAME),
                    req.getParameter(FieldConst.DESCRIPTION),
                    Role.USER);
            userService.create(user);
            model.addObject(FieldConst.USER, user);
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return model;
    }

}