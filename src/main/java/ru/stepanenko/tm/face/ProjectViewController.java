package ru.stepanenko.tm.face;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.util.OptionsUtil;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@SessionScope
public class ProjectViewController {

    @Nullable
    private List<ProjectDTO> projects;

    @Nullable
    private ProjectDTO selectedProject;

    @NotNull
    private ProjectDTO editProject;

    @NotNull
    @Autowired
    private IProjectService projectService;

    @NotNull
    @Autowired
    private ISessionService sessionService;

    public Collection<ProjectDTO> getProjects(
    ) throws AuthenticationSecurityException, DataValidateException {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        @NotNull final HttpSession session = (HttpSession) context
                .getExternalContext()
                .getSession(false);
        sessionService.validateSession(session);
        @NotNull final UserDTO loggedUser = sessionService.getLoggedUser(session);
        projects = new ArrayList<>(projectService.findAllByUserId(loggedUser.getId()));
        return projects;
    }

    public void setProjects(List<ProjectDTO> projects) {
        this.projects = projects;
    }

    public void projectCreate() {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        @NotNull final HttpSession session = (HttpSession) context
                .getExternalContext()
                .getSession(false);
        try {
            sessionService.validateSession(session);
            @NotNull final UserDTO loggedUser = sessionService.getLoggedUser(session);
            editProject = new ProjectDTO(
                    "New project",
                    "Description for new project",
                    new Date(),
                    null,
                    Status.PLANNED,
                    loggedUser.getId());
            PrimeFaces.current().dialog().openDynamic("projectEditOutcome", OptionsUtil.getWindowOptions(), null);
        } catch (AuthenticationSecurityException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Authentication Error:", e.getMessage()));
        }

    }

    @NotNull
    public ProjectDTO getEditProject(
    ) throws AuthenticationSecurityException {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        @NotNull final HttpSession session = (HttpSession) context
                .getExternalContext()
                .getSession(false);
        sessionService.validateSession(session);
        return editProject;
    }

    public void setEditProject(
            @NotNull final ProjectDTO editProject
    ) {
        this.editProject = editProject;
    }

    @Nullable
    public ProjectDTO getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(
            @Nullable ProjectDTO selectedProject
    ) {
        this.selectedProject = selectedProject;
    }

    public void onRowSelect(SelectEvent event) {
        selectedProject = ((ProjectDTO) event.getObject());
    }

    public void onRowUnselect(UnselectEvent event) {
        selectedProject = null;
    }

    public void projectDelete() {
        try {
            @NotNull final FacesContext context = FacesContext.getCurrentInstance();
            @NotNull final HttpSession session = (HttpSession) context
                    .getExternalContext()
                    .getSession(false);
            sessionService.validateSession(session);
            @NotNull final UserDTO loggedUser = sessionService.getLoggedUser(session);
            if (selectedProject == null) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Input Error:", "No project selected!"));
                return;
            }
            projectService.remove(selectedProject.getId(), loggedUser.getId());
        } catch (DataValidateException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Input Error:", e.getMessage()));
        } catch (AuthenticationSecurityException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Authentication Error:", e.getMessage()));
        }
    }

    public void projectEdit() {
        if (selectedProject == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Input Error:", "No project selected!"));
            return;
        }
        editProject = selectedProject;
        PrimeFaces.current().dialog().openDynamic("projectEditOutcome", OptionsUtil.getWindowOptions(), null);
    }

    public Status[] getStatuses() {
        return Status.values();
    }

    public void projectSave() {
        try {
            if (selectedProject == editProject) {//equality to reference
                projectService.edit(editProject);
            } else {
                projectService.create(editProject);
            }
            PrimeFaces.current().dialog().closeDynamic("projectEditOutcome");
        } catch (DataValidateException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Input Error:", e.getMessage()));
        }
    }

}
