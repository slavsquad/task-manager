package ru.stepanenko.tm.api.rest;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.model.entity.Project;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;

public interface IProjectRestController {

    List<ProjectDTO> projectList(
            @NotNull final HttpSession session) throws AuthenticationSecurityException, DataValidateException;
}
