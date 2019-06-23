package ru.stepanenko.tm.face;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.util.FieldConst;
import ru.stepanenko.tm.util.HashUtil;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@Controller
public class UserViewController {

    @NotNull
    @Autowired
    private IUserService userService;

    @Getter
    @Setter
    @Nullable
    private String login;

    @Getter
    @Setter
    @Nullable
    private String password;


    public void userLogin(
    ) {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        try {
            @NotNull final UserDTO loggedUser = userService.authenticationUser(login, HashUtil.md5(password));
            @NotNull final HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful:", "Welcome " + login));
            session.setAttribute(FieldConst.USER, loggedUser);
        } catch (AuthenticationSecurityException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Authentication Error:", e.getMessage()));
        } catch (DataValidateException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Input Error:", e.getMessage()));
        }
    }

    public String userLogout() {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        @NotNull final HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        login = null;
        password = null;
        session.invalidate();
        System.out.println();
        return "main";
    }
}
