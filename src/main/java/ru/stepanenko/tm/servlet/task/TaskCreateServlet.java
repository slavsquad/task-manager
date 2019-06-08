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

@WebServlet(urlPatterns = "task/create")
public class TaskCreateServlet extends HttpServlet {

    @NotNull
    private final ITaskService taskService = TaskService.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        @Nullable final String projectId = req.getParameter(FieldConst.PROJECT_ID);
        try {
            if (projectId == null || "null".equals(projectId)) {
                @NotNull final Task task = new Task("New task", "New Description", null, "1");
                taskService.create(task);
                resp.sendRedirect("list");
                return;
            }
            @NotNull final Task task = new Task("New task", "New Description", projectId, "1");
            taskService.create(task);
            resp.sendRedirect("list?" + FieldConst.PROJECT_ID + "=" + projectId);
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }
}
