package ru.stepanenko.tm.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.service.ProjectService;
import ru.stepanenko.tm.service.SessionService;
import ru.stepanenko.tm.util.FieldConst;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class ProjectController {

    @NotNull
    @Autowired
    private IProjectService projectService;

    @NotNull
    @Autowired
    private ISessionService sessionService;

    @RequestMapping(value = {"project/list"}, method = {RequestMethod.GET})
    public void projectList(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        try {
            sessionService.validateSession(session);
            @NotNull final User loggedUser = (User) session.getAttribute(FieldConst.USER);
            req.setAttribute(FieldConst.PROJECTS, projectService.findAllByUserId(loggedUser.getId()));
            req.getRequestDispatcher("/WEB-INF/jsp/project/projectList.jsp").forward(req, resp);
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (ServletException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
