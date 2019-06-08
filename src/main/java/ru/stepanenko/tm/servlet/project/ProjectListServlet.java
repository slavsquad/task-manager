package ru.stepanenko.tm.servlet.project;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.repository.ProjectRepository;
import ru.stepanenko.tm.service.ProjectService;
import ru.stepanenko.tm.service.SessionService;
import ru.stepanenko.tm.util.FieldConst;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "project/list")
public class ProjectListServlet extends HttpServlet {

    @NotNull
    private final IProjectService projectService = ProjectService.INSTANCE;

    @NotNull
    private final ISessionService sessionService = SessionService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        @NotNull final HttpSession session = req.getSession();
        try {
            sessionService.validateSession(session);
            @NotNull final User loggedUser = (User)session.getAttribute(FieldConst.USER);
            req.setAttribute(FieldConst.PROJECTS, projectService.findAllByUserId(loggedUser.getId()));
            req.getRequestDispatcher("/WEB-INF/jsp/project/projectList.jsp").forward(req,resp);
        } catch (DataValidateException | AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
            return;
        }
    }
}
