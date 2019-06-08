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

@WebServlet(urlPatterns = "task/delete")
public class TaskDeleteServlet extends HttpServlet {

    @NotNull
    private final ITaskService taskService = TaskService.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        @NotNull final String taskId = req.getParameter(FieldConst.TASK_ID);
        try {
            @Nullable final String projectId = req.getParameter(FieldConst.PROJECT_ID);
            taskService.remove(taskId, "1");
            if (projectId == null || "null".equals(projectId)) {
                resp.sendRedirect("list");
                return;
            }
            resp.sendRedirect("list?" + FieldConst.PROJECT_ID + "=" + projectId);
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }
}
