package ru.stepanenko.tm.controller;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.util.DateFormatter;
import ru.stepanenko.tm.util.FieldConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

@Controller
public class ProjectController {

    @NotNull
    @Autowired
    private IProjectService projectService;

    @NotNull
    @Autowired
    private ISessionService sessionService;

    @RequestMapping(value = {"project/list"}, method = {RequestMethod.GET})
    public ModelAndView projectList(
            @NotNull final HttpSession session,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        @NotNull final ModelAndView model = new ModelAndView("project/projectList");
        try {
            sessionService.validateSession(session);
            @NotNull final UserDTO loggedUser = (UserDTO) session.getAttribute(FieldConst.USER);
            model.addObject(FieldConst.PROJECTS, projectService.findAllByUserId(loggedUser.getId()));
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return model;
    }

    @RequestMapping(value = {"project/edit"}, method = RequestMethod.GET)
    public ModelAndView projectEdit(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        @NotNull final ModelAndView model = new ModelAndView("project/projectEdit");
        try {
            sessionService.validateSession(session);
            @NotNull final UserDTO loggedUser = (UserDTO) session.getAttribute(FieldConst.USER);
            @Nullable final String projectId = req.getParameter(FieldConst.PROJECT_ID);
            @Nullable final ProjectDTO project = projectService.findOne(projectId, loggedUser.getId());
            model.addObject(FieldConst.PROJECT, project);
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return model;
    }

    @RequestMapping(value = {"project/edit"}, method = RequestMethod.POST)
    public String projectUpdate(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        try {
            sessionService.validateSession(session);
            @NotNull final UserDTO loggedUser = (UserDTO) session.getAttribute(FieldConst.USER);
            @NotNull final ProjectDTO project = new ProjectDTO(
                    req.getParameter(FieldConst.NAME),
                    req.getParameter(FieldConst.DESCRIPTION),
                    DateFormatter.stringToDate(req.getParameter(FieldConst.DATE_BEGIN)),
                    DateFormatter.stringToDate(req.getParameter(FieldConst.DATE_END)),
                    Status.valueOf(req.getParameter(FieldConst.STATUS)),
                    loggedUser.getId());
            project.setId(req.getParameter(FieldConst.PROJECT_ID));
            projectService.edit(project);
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException | ParseException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return "redirect:/project/list";
    }

    @RequestMapping(value = "project/create", method = RequestMethod.POST)
    public String projectCreate(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        try {
            sessionService.validateSession(session);
            @NotNull final UserDTO loggedUser = (UserDTO) session.getAttribute(FieldConst.USER);
            @NotNull final ProjectDTO project = new ProjectDTO(
                    "New project",
                    "Description for new project",
                    new Date(),
                    null,
                    Status.PLANNED,
                    loggedUser.getId());
            projectService.create(project);
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return "redirect:/project/list";
    }

    @RequestMapping(value = "project/delete", method = RequestMethod.POST)
    public String projectDelete(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        try {
            sessionService.validateSession(session);
            @Nullable final String id = req.getParameter(FieldConst.PROJECT_ID);
            @NotNull final UserDTO loggedUser = (UserDTO) session.getAttribute(FieldConst.USER);
            projectService.remove(id, loggedUser.getId());
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return "redirect:/project/list";
    }

}
