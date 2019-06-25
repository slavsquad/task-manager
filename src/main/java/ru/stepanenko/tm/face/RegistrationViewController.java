package ru.stepanenko.tm.face;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.util.DataValidator;
import ru.stepanenko.tm.util.HashUtil;
import ru.stepanenko.tm.util.OptionsUtil;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Controller
public class RegistrationViewController {

    @NotNull
    private UserDTO registeredUser;

    @Autowired
    IUserService userService;

    @NotNull
    public UserDTO getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(@NotNull UserDTO registeredUser) {
        this.registeredUser = registeredUser;
    }

    public void registrationUser() {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        registeredUser = new UserDTO(
                "New User",
                "password",
                "New user name",
                "Description for new user",
                Role.USER);
        PrimeFaces.current().dialog().openDynamic("registrationOutcome", OptionsUtil.getWindowOptions(), null);
    }

    public void saveUser() {
        @Nullable final String password = registeredUser.getPassword();
        if (!DataValidator.stringIsNull(password))
            registeredUser.setPassword(HashUtil.md5(password));
        try {
            userService.create(registeredUser);
            PrimeFaces.current().dialog().closeDynamic("registrationOutcome");
        } catch (DataValidateException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Input Error:", e.getMessage()));
        }
    }
}
