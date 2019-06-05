package ru.stepanenko.tm.servlet.project;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.service.ProjectService;
import ru.stepanenko.tm.util.FieldConst;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "project/edit")
public class ProjectEditServlet extends HttpServlet {

    @NotNull
    IProjectService projectService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        projectService = ProjectService.INSTANCE;
        @Nullable final String id = req.getParameter(FieldConst.ID);
        try {
            @Nullable final Project project = projectService.findOne(id);
            req.setAttribute(FieldConst.PROJECT, project);
            req.getRequestDispatcher("/WEB-INF/jsp/project/projectEdit.jsp").forward(req,resp);
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
            return;
        }
    }
}
