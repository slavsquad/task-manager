package ru.stepanenko.tm.servlet.project;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.service.ProjectService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(urlPatterns = "project/create")
public class ProjectCreateServlet extends HttpServlet {

    @NotNull
    private final IProjectService projectService = ProjectService.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        @NotNull final Project project = generateProject();
        try {
            projectService.create(project);
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
            return;
        }
        resp.sendRedirect("list");
    }

    private Project generateProject(){
        @NotNull
        final Project project = new Project();
        project.setUserId("1");
        project.setName("New Project");
        project.setDescription("New Description");
        project.setDateBegin(new Date());
        return project;
    }
}