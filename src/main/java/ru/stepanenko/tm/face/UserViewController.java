package ru.stepanenko.tm.face;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.util.FieldConst;
import ru.stepanenko.tm.util.HashUtil;
import ru.stepanenko.tm.util.OptionsUtil;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class UserViewController {

    @NotNull
    @Autowired
    private IUserService userService;

    @NotNull
    @Autowired
    private ISessionService sessionService;

    @Nullable
    private Collection<UserDTO> users;

    @Nullable
    private UserDTO selectedUser;

    @NotNull
    private UserDTO editUser;

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
        session.invalidate();
        return "mainOutcome";
    }

    public Collection<UserDTO> getUsers() throws AuthenticationSecurityException, DataValidateException {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        @NotNull final HttpSession session = (HttpSession) context
                .getExternalContext()
                .getSession(false);
        sessionService.validateAdminSession(session);
        users = userService.findAll();
        return users;
    }

    public void setUsers(Collection<UserDTO> users) {
        this.users = users;
    }

    public void userCreate() throws AuthenticationSecurityException, DataValidateException {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        @NotNull final HttpSession session = (HttpSession) context
                .getExternalContext()
                .getSession(false);
        sessionService.validateAdminSession(session);
        editUser = new UserDTO(
                "New User:",
                "password",
                "New user name",
                "Description for new user",
                Role.USER);
        editUser.setLogin("New User:" + editUser.getId());
        PrimeFaces.current().dialog().openDynamic("userEditOutcome", OptionsUtil.getWindowOptions(), null);
    }

    @Nullable
    public UserDTO getSelectedUser() throws AuthenticationSecurityException, DataValidateException {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        @NotNull final HttpSession session = (HttpSession) context
                .getExternalContext()
                .getSession(false);
        if (sessionService.isAdmin(session))
            sessionService.validateAdminSession(session);
        sessionService.validateSession(session);
        return selectedUser;
    }

    public void setSelectedUser(@Nullable UserDTO selectedUser) {
        this.selectedUser = selectedUser;
    }

    @NotNull
    public UserDTO getEditUser() {
        return editUser;
    }

    public void setEditUser(@NotNull UserDTO editUser) {
        this.editUser = editUser;
    }

    public void onRowSelect(SelectEvent event) {
        selectedUser = ((UserDTO) event.getObject());
    }

    public void onRowUnselect(UnselectEvent event) {
        selectedUser = null;
    }

    public void userDelete() throws AuthenticationSecurityException, DataValidateException {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        @NotNull final HttpSession session = (HttpSession) context
                .getExternalContext()
                .getSession(false);
        sessionService.validateSession(session);
        @NotNull final UserDTO loggedUser = sessionService.getLoggedUser(session);
        if (selectedUser == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Input Error:", "No project selected!"));
            return;
        }
        userService.remove(selectedUser.getId());
    }

    public void userEdit() {
        if (selectedUser == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Input Error:", "No project selected!"));
            return;
        }
        editUser=selectedUser;
        PrimeFaces.current().dialog().openDynamic("userEditOutcome", OptionsUtil.getWindowOptions(), null);
    }

    public Role[] getRoles() {
        return Role.values();
    }

    public void userSave() throws DataValidateException {
        if (editUser==selectedUser){//equality to reference
            userService.edit(editUser);
        } else {
            userService.create(editUser);
        }
        PrimeFaces.current().dialog().closeDynamic("userEditOutcome");
    }
}
