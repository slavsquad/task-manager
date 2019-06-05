package ru.stepanenko.tm.servlet.project;

import ru.stepanenko.tm.util.FieldConst;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(urlPatterns = "project/update")
public class ProjectUpdateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.getWriter().println(req.getParameter(FieldConst.ID));
        Enumeration atributes = req.getAttributeNames();
        while (atributes.hasMoreElements()) {
            resp.getWriter().println((String )atributes.nextElement()+ ".");
        }
    }
}
