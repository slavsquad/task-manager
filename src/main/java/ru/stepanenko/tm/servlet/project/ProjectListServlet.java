package ru.stepanenko.tm.servlet.project;

import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.repository.ProjectRepository;
import ru.stepanenko.tm.service.ProjectService;
import ru.stepanenko.tm.util.FieldConst;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "project/list")
public class ProjectListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.getWriter().println(new Date());
        try {
            req.setAttribute(FieldConst.PROJECTS, new ProjectService(new ProjectRepository()).findAll());
        } catch (DataValidateException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/WEB-INF/jsp/project/projectList.jsp").forward(req,resp);

    }
}
