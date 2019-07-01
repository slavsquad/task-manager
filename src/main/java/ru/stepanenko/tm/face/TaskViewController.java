package ru.stepanenko.tm.face;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.model.dto.TaskDTO;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.util.OptionsUtil;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@SessionScope
public class TaskViewController {

    @Nullable
    private List<TaskDTO> tasks;

    @Nullable
    private TaskDTO selectedTask;

    @Nullable
    private ProjectDTO selectedProject;

    @NotNull
    private TaskDTO editTask;

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
        sessionService.validate(session);
        @NotNull final UserDTO loggedUser = sessionService.getLoggedUser(session);
        if (selectedProject == null) {
            tasks = new ArrayList<>(taskService.findAllByUserId(loggedUser.getId()));
        } else {
            tasks = new ArrayList<>(taskService.findAllByProjectId(selectedProject.getId(), loggedUser.getId()));
        }
        return tasks;
    }

    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }

    public void taskCreate() {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        @NotNull final HttpSession session = (HttpSession) context
                .getExternalContext()
                .getSession(false);
        try {
            sessionService.validate(session);
            @NotNull final UserDTO loggedUser = sessionService.getLoggedUser(session);
            editTask = new TaskDTO(
                    "New Task",
                    "Description for new Task",
                    new Date(),
                    null,
                    Status.PLANNED,
                    selectedProject == null ? null : selectedProject.getId(),
                    loggedUser.getId());
            PrimeFaces.current().dialog().openDynamic("taskEditOutcome", OptionsUtil.getWindowOptions(), null);
        } catch (AuthenticationSecurityException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Authentication Error:", e.getMessage()));
        }
    }

    @Nullable
    public TaskDTO getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(@Nullable TaskDTO selectedTask) {
        this.selectedTask = selectedTask;
    }

    @Nullable
    public ProjectDTO getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(@Nullable final ProjectDTO selectedProject) {
        this.selectedProject = selectedProject;
    }

    @NotNull
    public TaskDTO getEditTask() throws AuthenticationSecurityException {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        @NotNull final HttpSession session = (HttpSession) context
                .getExternalContext()
                .getSession(false);
        sessionService.validate(session);
        return editTask;
    }

    public void setEditTask(@NotNull TaskDTO editTask) {
        this.editTask = editTask;
    }

    public void onRowSelect(SelectEvent event) {
        selectedTask = ((TaskDTO) event.getObject());
    }

    public void onRowUnselect(UnselectEvent event) {
        selectedTask = null;
    }

    public void taskDelete() {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        @NotNull final HttpSession session = (HttpSession) context
                .getExternalContext()
                .getSession(false);
        try {
            sessionService.validate(session);
            @NotNull final UserDTO loggedUser = sessionService.getLoggedUser(session);
            if (selectedTask == null) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Input Error:", "No task selected!"));
                return;
            }
            taskService.remove(selectedTask.getId(), loggedUser.getId());
        } catch (DataValidateException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Input Error:", e.getMessage()));
        } catch (AuthenticationSecurityException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Authentication Error:", e.getMessage()));
        }
    }

    public void taskEdit() {
        if (selectedTask == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Input Error:", "No task selected!"));
            return;
        }
        editTask = selectedTask;
        PrimeFaces.current().dialog().openDynamic("taskEditOutcome", OptionsUtil.getWindowOptions(), null);
    }

    public Status[] getStatuses() {
        return Status.values();
    }

    public void taskSave() {
        try {
            if (editTask == selectedTask) {//equality to reference
                taskService.edit(editTask);
            } else {
                taskService.create(editTask);
            }
            PrimeFaces.current().dialog().closeDynamic("taskEditOutcome");
        } catch (DataValidateException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Input Error:", e.getMessage()));
        }
    }

    public String redirectToListTask(
            @Nullable final ProjectDTO selectedProject
    ) {
        if (selectedProject == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Input Error:", "No task selected!"));
            return "";
        }
        this.selectedProject = selectedProject;
        return "taskListOutcome";
    }

    public String redirectToListTask() {
        selectedProject = null;
        return "taskListOutcome";
    }
}
