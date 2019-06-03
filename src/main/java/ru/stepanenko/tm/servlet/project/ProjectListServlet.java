package ru.stepanenko.tm.servlet.project;

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
        req.getRequestDispatcher("/WEB-INF/jsp/project/projectList.jsp").forward(req,resp);
    }
}
