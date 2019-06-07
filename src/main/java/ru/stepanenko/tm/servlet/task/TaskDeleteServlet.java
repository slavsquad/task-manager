package ru.stepanenko.tm.servlet.task;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "task/delete")
public class TaskDeleteServlet extends HttpServlet {
    @NotNull private ITaskService taskService = TaskService.INSTANCE;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
