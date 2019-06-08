package ru.stepanenko.tm.servlet.task;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.Task;
import ru.stepanenko.tm.service.ProjectService;
import ru.stepanenko.tm.service.TaskService;
import ru.stepanenko.tm.util.DateFormatter;
import ru.stepanenko.tm.util.FieldConst;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

@WebServlet(urlPatterns = "task/edit")
public class TaskEditServlet extends HttpServlet {

    @NotNull
    private final IProjectService projectService = ProjectService.INSTANCE;

    @NotNull
    private final ITaskService taskService = TaskService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        @NotNull final String taskId = req.getParameter(FieldConst.TASK_ID);
        try {
            @NotNull final Task task = taskService.findOne(taskId, "1");
            req.setAttribute(FieldConst.TASK, task);
            req.setAttribute(FieldConst.PROJECTS, projectService.findAllByUserId("1"));
            req.getRequestDispatcher("/WEB-INF/jsp/task/taskEdit.jsp").forward(req, resp);
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            @NotNull final Task task = taskService.findOne(req.getParameter(FieldConst.TASK_ID), "1");
            task.setName(req.getParameter(FieldConst.NAME));
            task.setDescription(req.getParameter(FieldConst.DESCRIPTION));
            task.setDateBegin(DateFormatter.stringToDate(req.getParameter(FieldConst.DATE_BEGIN)));
            task.setDateEnd(DateFormatter.stringToDate(req.getParameter(FieldConst.DATE_END)));
            task.setStatus(Status.valueOf(req.getParameter(FieldConst.STATUS)));
            task.setProjectId(req.getParameter(FieldConst.PROJECT_ID));
            taskService.edit(task);
            resp.sendRedirect("list?"+FieldConst.PROJECT_ID+"="+req.getParameter(FieldConst.PROJECT_ID));
        } catch (DataValidateException | ParseException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
            return;
        }
    }
}
