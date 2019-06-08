package ru.stepanenko.tm.servlet.project;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.service.ProjectService;
import ru.stepanenko.tm.util.DateFormatter;
import ru.stepanenko.tm.util.FieldConst;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

@WebServlet(urlPatterns = "project/edit")
public class ProjectEditServlet extends HttpServlet {

    @NotNull
    private final IProjectService projectService = ProjectService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        @Nullable final String id = req.getParameter(FieldConst.PROJECT_ID);
        try {
            @Nullable final Project project = projectService.findOne(id);
            req.setAttribute(FieldConst.PROJECT, project);
            req.getRequestDispatcher("/WEB-INF/jsp/project/projectEdit.jsp").forward(req, resp);
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            @NotNull final Project project = projectService.findOne(req.getParameter(FieldConst.PROJECT_ID));
            project.setName(req.getParameter(FieldConst.NAME));
            project.setDescription(req.getParameter(FieldConst.DESCRIPTION));
            project.setDateBegin(DateFormatter.stringToDate(req.getParameter(FieldConst.DATE_BEGIN)));
            project.setDateEnd(DateFormatter.stringToDate(req.getParameter(FieldConst.DATE_END)));
            project.setStatus(Status.valueOf(req.getParameter(FieldConst.STATUS)));
            projectService.edit(project);
            resp.sendRedirect("list");
        } catch (DataValidateException | ParseException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
            return;
        }
    }
}
