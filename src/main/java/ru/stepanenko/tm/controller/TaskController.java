package ru.stepanenko.tm.controller;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.TaskDTO;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.model.entity.Task;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.util.DataValidator;
import ru.stepanenko.tm.util.DateFormatter;
import ru.stepanenko.tm.util.FieldConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

@Controller
public class TaskController {

    @NotNull
    @Autowired
    private IProjectService projectService;

    @NotNull
    @Autowired
    private ITaskService taskService;

    @NotNull
    @Autowired
    private ISessionService sessionService;

    @RequestMapping(value = {"task/list"}, method = RequestMethod.GET)
    public ModelAndView taskList(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        @NotNull final ModelAndView model = new ModelAndView("task/taskList");
        try {
            sessionService.validateSession(session);
            @NotNull final UserDTO loggedUser = (UserDTO) session.getAttribute(FieldConst.USER);
            @Nullable final String projectId = req.getParameter(FieldConst.PROJECT_ID);
            model.addObject(FieldConst.PROJECT_ID, projectId);
            if (projectId == null || projectId.isEmpty() || "null".equals(projectId)) {
                @NotNull final Collection<TaskDTO> tasks = taskService.findAllByUserId(loggedUser.getId());
                model.addObject(FieldConst.TASKS, tasks);
                return model;
            }
            @NotNull final Collection<TaskDTO> tasks = taskService.findAllByProjectId(projectId, loggedUser.getId());
            model.addObject(FieldConst.TASKS, tasks);
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return model;
    }

    @RequestMapping(value = {"task/edit"}, method = RequestMethod.GET)
    public ModelAndView taskEdit(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        @NotNull final ModelAndView model = new ModelAndView("task/taskEdit");
        try {
            sessionService.validateSession(session);
            @NotNull final UserDTO loggedUser = (UserDTO) session.getAttribute(FieldConst.USER);
            @NotNull final String taskId = req.getParameter(FieldConst.TASK_ID);
            @NotNull final String projectId = req.getParameter(FieldConst.PROJECT_ID);
            @NotNull final TaskDTO task = taskService.findOne(taskId, loggedUser.getId());
            model.addObject(FieldConst.PROJECT_ID, projectId);
            model.addObject(FieldConst.TASK, task);
            model.addObject(FieldConst.PROJECTS, projectService.findAllByUserId(loggedUser.getId()));
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return model;
    }

    @RequestMapping(value = {"task/edit"}, method = RequestMethod.POST)
    public String taskUpdate(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        @Nullable String projectId = null;
        @Nullable String editProjectId = null;
        try {
            sessionService.validateSession(session);
            @NotNull final UserDTO loggedUser = (UserDTO) session.getAttribute(FieldConst.USER);
            projectId = req.getParameter(FieldConst.PROJECT_ID);
            editProjectId = req.getParameter(FieldConst.EDIT_PROJECT_ID);
            @NotNull final TaskDTO task = new TaskDTO(
                    req.getParameter(FieldConst.NAME),
                    req.getParameter(FieldConst.DESCRIPTION),
                    DateFormatter.stringToDate(req.getParameter(FieldConst.DATE_BEGIN)),
                    DateFormatter.stringToDate(req.getParameter(FieldConst.DATE_END)),
                    Status.valueOf(req.getParameter(FieldConst.STATUS)),
                    DataValidator.stringIsNull(editProjectId) ? null : editProjectId,
                    loggedUser.getId());
            task.setId(req.getParameter(FieldConst.TASK_ID));
            taskService.edit(task);
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException | ParseException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        if (DataValidator.stringIsNull(projectId)) {
            return "redirect:/task/list";
        }
        return "redirect:/task/list?" + FieldConst.PROJECT_ID + "=" + projectId;
    }

    @RequestMapping(value = {"task/create"}, method = RequestMethod.POST)
    public String taskCreate(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        @Nullable String projectId = null;
        try {
            sessionService.validateSession(session);
            @NotNull final UserDTO loggedUser = (UserDTO) session.getAttribute(FieldConst.USER);
            projectId = req.getParameter(FieldConst.PROJECT_ID);
            @NotNull final TaskDTO task = new TaskDTO(
                    "New task",
                    "Description for new task",
                    new Date(),
                    null,
                    Status.PLANNED,
                    DataValidator.stringIsNull(projectId) ? null : projectId,
                    loggedUser.getId());
            taskService.create(task);
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        if (DataValidator.stringIsNull(projectId)) {
            return "redirect:/task/list";
        }
        return "redirect:/task/list?" + FieldConst.PROJECT_ID + "=" + projectId;
    }

    @RequestMapping(value = {"task/delete"}, method = RequestMethod.POST)
    public String taskDelete(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        @Nullable String projectId = null;
        try {
            sessionService.validateSession(session);
            @NotNull final UserDTO loggedUser = (UserDTO) session.getAttribute(FieldConst.USER);
            projectId = req.getParameter(FieldConst.PROJECT_ID);
            @Nullable final String taskId = req.getParameter(FieldConst.TASK_ID);
            taskService.remove(taskId, loggedUser.getId());
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        if (DataValidator.stringIsNull(projectId)) {
            return "redirect:/task/list";
        }
        return "redirect:/task/list?" + FieldConst.PROJECT_ID + "=" + projectId;
    }

}
