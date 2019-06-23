package ru.stepanenko.tm.face;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.model.dto.UserDTO;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@URLMappings(mappings = {
        @URLMapping(
                id = "projectList",
                pattern = "/project/list",
                viewId = "/WEB-INF/jsf/project/projectList.xhtml"),
        @URLMapping(
                id = "viewProject",
                pattern = "/project/view",
                viewId = "/WEB-INF/jsf/project/viewProject.xhtml")})

public class ProjectViewController {

    @Nullable
    private List<ProjectDTO> projects;

    @Getter
    @Setter
    @Nullable
    private ProjectDTO selectedProject;

    @NotNull
    @Autowired
    private IProjectService projectService;

    @NotNull
    @Autowired
    private ISessionService sessionService;

    public Collection<ProjectDTO> getProjects() throws AuthenticationSecurityException, DataValidateException {
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

    public void projectCreate() throws AuthenticationSecurityException, DataValidateException {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        @NotNull final HttpSession session = (HttpSession) context
                .getExternalContext()
                .getSession(false);
        sessionService.validateSession(session);
        @NotNull final UserDTO loggedUser = sessionService.getLoggedUser(session);
        @NotNull final ProjectDTO project = new ProjectDTO(
                "New project",
                "Description for new project",
                new Date(),
                null,
                Status.PLANNED,
                loggedUser.getId());
        projectService.create(project);
    }

    public void onRowSelect(SelectEvent event) {
        selectedProject = ((ProjectDTO) event.getObject());
    }

    public void onRowUnselect(UnselectEvent event) {
        selectedProject = null;
    }

    public void projectDelete() throws AuthenticationSecurityException, DataValidateException {
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
    }

    public void projectView() {
        if (selectedProject == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Input Error:", "No project selected!"));
            return;
        }
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 640);
        options.put("height", 480);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        PrimeFaces.current().dialog().openDynamic("viewProject", options, null);
    }
}
