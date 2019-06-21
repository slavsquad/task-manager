package ru.stepanenko.tm.face;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.RequestScope;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.model.dto.TaskDTO;
import ru.stepanenko.tm.service.ProjectService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller("projectList")
@RequestScope
@URLMapping(
        id = "projectList",
        pattern = "/project/list",
        viewId = "/WEB-INF/jsf/project/projectList.xhtml"
)
public class ProjectListController {
    private List<ProjectDTO> projects;

    @Autowired
    private IProjectService projectService;

    @PostConstruct
    public void init() {
        try {
            projects = new ArrayList<>(projectService.findAll());
        } catch (DataValidateException e) {
            e.printStackTrace();
        }
    }

    public Collection<ProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDTO> projects) {
        this.projects = projects;
    }
}
