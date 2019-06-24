package ru.stepanenko.tm.face;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.model.dto.TaskDTO;
import ru.stepanenko.tm.model.dto.UserDTO;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class TaskViewController {

    @Nullable
    private List<TaskDTO> tasks;

    @Nullable
    private TaskDTO selectedTask;

    @Nullable
    private String projectId;

    @NotNull
    @Autowired
    private ITaskService taskService;

    @NotNull
    @Autowired
    private ISessionService sessionService;

    public Collection<TaskDTO> getTasks() throws AuthenticationSecurityException, DataValidateException {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        @NotNull final HttpSession session = (HttpSession) context
                .getExternalContext()
                .getSession(false);
        sessionService.validateSession(session);
        @NotNull final UserDTO loggedUser = sessionService.getLoggedUser(session);
        if (projectId == null)
            tasks = new ArrayList<>(taskService.findAllByUserId(loggedUser.getId()));
        if (projectId != null)
            tasks = new ArrayList<>(taskService.findAllByProjectId(projectId, loggedUser.getId()));
        return tasks;
    }

    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }

    public void taskCreate() throws AuthenticationSecurityException, DataValidateException {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        @NotNull final HttpSession session = (HttpSession) context
                .getExternalContext()
                .getSession(false);
        sessionService.validateSession(session);
        @NotNull final UserDTO loggedUser = sessionService.getLoggedUser(session);
        @NotNull final TaskDTO task = new TaskDTO(
                "New project",
                "Description for new project",
                new Date(),
                null,
                Status.PLANNED,
                projectId,
                loggedUser.getId());
        taskService.create(task);
    }

    @Nullable
    public TaskDTO getSelectedTask() throws AuthenticationSecurityException {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        @NotNull final HttpSession session = (HttpSession) context
                .getExternalContext()
                .getSession(false);
        sessionService.validateSession(session);
        return selectedTask;
    }

    public void setSelectedTask(@Nullable TaskDTO selectedTask) {
        this.selectedTask = selectedTask;
    }

    @Nullable
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(@Nullable String projectId) {
        this.projectId = projectId;
    }

    public void onRowSelect(SelectEvent event) {
        selectedTask = ((TaskDTO) event.getObject());
    }

    public void onRowUnselect(UnselectEvent event) {
        selectedTask = null;
    }

    public void taskDelete() throws AuthenticationSecurityException, DataValidateException {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        @NotNull final HttpSession session = (HttpSession) context
                .getExternalContext()
                .getSession(false);
        sessionService.validateSession(session);
        @NotNull final UserDTO loggedUser = sessionService.getLoggedUser(session);
        if (selectedTask == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Input Error:", "No project selected!"));
            return;
        }
        taskService.remove(selectedTask.getId(), loggedUser.getId());
    }

    public void taskEdit() {
        if (selectedTask == null) {
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
        PrimeFaces.current().dialog().openDynamic("taskEditOutcome", options, null);
    }

    public Status[] getStatuses() {
        return Status.values();
    }

    public void taskUpdate() throws DataValidateException {
        System.out.println(selectedTask.getName());
        taskService.edit(selectedTask);
        PrimeFaces.current().dialog().closeDynamic("taskEditOutcome");
    }

    public String redirectToListTask(@Nullable final ProjectDTO selectedProject) {

        if (selectedProject == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Input Error:", "No project selected!"));
            return "";
        }
        projectId = selectedProject.getId();
        return "taskListOutcome";
    }

    public String redirectToListTask() {
        projectId = null;
        return "taskListOutcome";
    }

}
