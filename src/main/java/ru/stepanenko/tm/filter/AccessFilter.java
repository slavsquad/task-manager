package ru.stepanenko.tm.filter;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"rest/task/*", "rest/project/*"})
public class AccessFilter implements Filter {


    @Autowired
    ISessionService sessionService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(
            @NotNull final ServletRequest servletRequest,
            @NotNull final ServletResponse servletResponse,
            @NotNull final FilterChain filterChain
    ) throws IOException, ServletException {
        @NotNull final HttpSession session = (HttpSession) ((HttpServletRequest)servletRequest).getSession();
        try {
            sessionService.validate(session);
        } catch (AuthenticationSecurityException e) {
            ((HttpServletResponse)servletResponse).sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}