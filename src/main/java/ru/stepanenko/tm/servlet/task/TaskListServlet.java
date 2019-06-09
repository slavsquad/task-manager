package ru.stepanenko.tm.servlet.task;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.entity.Task;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.service.SessionService;
import ru.stepanenko.tm.service.TaskService;
import ru.stepanenko.tm.util.FieldConst;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@WebServlet(urlPatterns = "task/list")
public class TaskListServlet extends HttpServlet {

    @NotNull
    final private ITaskService taskService = TaskService.INSTANCE;

    @NotNull
    final private ISessionService sessionService = SessionService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        @NotNull final HttpSession session = req.getSession();
        try {
            sessionService.validateSession(session);
            @NotNull final User loggedUser = (User) session.getAttribute(FieldConst.USER);
            @Nullable final String projectId = req.getParameter(FieldConst.PROJECT_ID);
            req.setAttribute(FieldConst.PROJECT_ID, projectId);
            if (projectId == null) {
                @NotNull final Collection<Task> tasks = taskService.findAllByUserId(loggedUser.getId());
                req.setAttribute(FieldConst.TASKS, tasks);
                req.getRequestDispatcher("/WEB-INF/jsp/task/taskList.jsp").forward(req, resp);
                return;
            }
            @NotNull final Collection<Task> tasks = taskService.findAllByProjectId(projectId, loggedUser.getId());
            req.setAttribute(FieldConst.TASKS, tasks);
            req.getRequestDispatcher("/WEB-INF/jsp/task/taskList.jsp").forward(req, resp);
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }
}
