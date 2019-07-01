package ru.stepanenko.tm.rest;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.stepanenko.tm.api.rest.IProjectRestController;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.util.FieldConst;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("rest/project")
public class ProjectRestController implements IProjectRestController {

    @NotNull
    @Autowired
    private IProjectService projectService;

    @NotNull
    @Autowired
    private ISessionService sessionService;

    @Override
    @GetMapping(value = "list")
    public List<ProjectDTO> projectList(
            @NotNull final HttpSession session
    ) throws AuthenticationSecurityException, DataValidateException {
        return new ArrayList<>(projectService.findAll());
    }
}
