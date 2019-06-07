package ru.stepanenko.tm.servlet.project;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.service.ProjectService;
import ru.stepanenko.tm.util.FieldConst;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "project/delete")
public class ProjectDeleteServlet extends HttpServlet {

    @NotNull
    private IProjectService projectService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        @Nullable final String id = req.getParameter(FieldConst.ID);
        projectService = ProjectService.INSTANCE;
        try {
            projectService.remove(id);
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        resp.sendRedirect("list");
    }
}
