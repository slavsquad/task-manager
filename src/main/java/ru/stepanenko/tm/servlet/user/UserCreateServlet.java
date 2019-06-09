package ru.stepanenko.tm.servlet.user;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.service.SessionService;
import ru.stepanenko.tm.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "user/create")
public class UserCreateServlet extends HttpServlet {

    @NotNull
    private final IUserService userService = UserService.INSTANCE;

    @NotNull
    private final ISessionService sessionService = SessionService.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        @NotNull final HttpSession session = req.getSession();
        try {
            sessionService.validateAdminSession(session);
            @NotNull final User user = new User(
                    "New User:",
                    "password",
                    "New user name",
                    "Description for new user",
                    Role.USER);
            user.setLogin("New User:" + user.getId());
            userService.create(user);
            resp.sendRedirect(req.getContextPath() + "/user/list");
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }
}
