package ru.stepanenko.tm.servlet.task;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.entity.Task;
import ru.stepanenko.tm.service.TaskService;
import ru.stepanenko.tm.util.FieldConst;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebServlet(urlPatterns = "task/list")
public class TaskListServlet extends HttpServlet {

    @NotNull final ITaskService taskService = TaskService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            @Nullable final String id = req.getParameter(FieldConst.ID);
            if (id==null){
                @NotNull final Collection<Task> tasks = taskService.findAllByUserId("1");
                req.setAttribute(FieldConst.TASKS, tasks);
            } else {
                @NotNull final Collection<Task> tasks = taskService.findAllByProjectId(id,"1");
                req.setAttribute(FieldConst.TASKS, tasks);
            }
            req.getRequestDispatcher("/WEB-INF/jsp/task/taskList.jsp").forward(req, resp);
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
            return;
        }
    }
}
