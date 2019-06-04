package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.repository.ProjectRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ProjectServiceTest {

    @NotNull
    private IProjectService projectService;

    @NotNull
    private User currentUser;

    @Before
    public void setUp() throws Exception {
        projectService = ProjectService.INSTANCE;
        currentUser = new User();
        currentUser.setLogin("admin");
        currentUser.setPassword("admin");
    }

    @After
    public void tearDown() throws Exception {
        projectService = null;
    }

    @Test
    public void projectCRUD(
    ) throws DataValidateException {
        @Nullable final String userId = currentUser.getId();
        assertNotNull(userId);
        @NotNull final Project project = new Project();
        @NotNull final String projectId = project.getId();
        project.setUserId(userId);
        project.setName("Project");
        project.setDescription("Description");
        projectService.create(project);
        assertEquals(projectId, projectService.findOne(projectId, userId).getId());
        project.setName("Change name");
        project.setDescription("Change description");
        projectService.edit(project);
        assertEquals("Change name", projectService.findOne(projectId, userId).getName());
        assertEquals("Change description", projectService.findOne(projectId, userId).getDescription());
        @Nullable final int size = projectService.findAllByUserId(userId).size();
        assertNotNull(size);
        projectService.remove(projectId, userId);
        assertEquals(size-1,projectService.findAllByUserId(userId).size());
    }

    @Test
    public void projectSort(
    ) throws DataValidateException {
        @Nullable final String userId = currentUser.getId();
        assertNotNull(userId);
        @NotNull final Project project1 = new Project();
        project1.setUserId(userId);
        project1.setName("project1");
        project1.setDescription("Description1");
        project1.setStatus(Status.DONE);
        project1.setDateBegin(new Date(1000));
        project1.setDateEnd(new Date(1000));

        @NotNull final Project project2 = new Project();
        project2.setUserId(userId);
        project2.setName("project2");
        project2.setDescription("Description2");
        project2.setStatus(Status.INPROCESS);
        project2.setDateBegin(new Date(1000000));
        project2.setDateEnd(new Date(1000000));

        @NotNull final Project project3 = new Project();
        project3.setUserId(userId);
        project3.setName("project3");
        project3.setDescription("Description3");
        project3.setStatus(Status.PLANNED);
        project3.setDateBegin(new Date(0));
        project3.setDateEnd(new Date(0));

        projectService.removeAllByUserId(userId);
        assertTrue(projectService.findAllByUserId(userId).size() == 0);

        projectService.create(project1);
        projectService.create(project2);
        projectService.create(project3);

        @NotNull final List<Project> sortStatus = new ArrayList<>(projectService.sortAllByUserId(userId, "status"));
        assertEquals(project3.getId(), sortStatus.get(0).getId());
        assertEquals(project2.getId(), sortStatus.get(1).getId());
        assertEquals(project1.getId(), sortStatus.get(2).getId());

        @NotNull final List<Project> sortDateBegin = new ArrayList<>(projectService.sortAllByUserId(userId, "dateBegin"));
        assertEquals(project3.getId(), sortDateBegin.get(0).getId());
        assertEquals(project1.getId(), sortDateBegin.get(1).getId());
        assertEquals(project2.getId(), sortDateBegin.get(2).getId());

        @NotNull final List<Project> sortDateEnd = new ArrayList<>(projectService.sortAllByUserId(userId, "dateEnd"));
        assertEquals(project3.getId(), sortDateEnd.get(0).getId());
        assertEquals(project1.getId(), sortDateEnd.get(1).getId());
        assertEquals(project2.getId(), sortDateEnd.get(2).getId());
        projectService.removeAllByUserId(userId);
    }

    @Test
    public void findProject(
    ) throws DataValidateException {
        @Nullable final String userId = currentUser.getId();
        assertNotNull(userId);
        @NotNull final Project project1 = new Project();
        project1.setUserId(userId);
        project1.setName("Homework");
        project1.setDescription("Make all homework");

        @NotNull final Project project2 = new Project();
        project2.setUserId(userId);
        project2.setName("Cooking");
        project2.setDescription("Make apple pie!");

        projectService.removeAllByUserId(userId);
        assertTrue(projectService.findAllByUserId(userId).size() == 0);

        projectService.create(project1);
        projectService.create(project2);

        @NotNull final List<String> findProjectsId = projectService
                .findAllByPartOfNameOrDescription("Home", "apple", userId)
                .stream()
                .map(Project::getId)
                .collect(Collectors.toList());
        assertTrue(findProjectsId.contains(project1.getId()));
        assertTrue(findProjectsId.contains(project2.getId()));
        projectService.removeAllByUserId(userId);
    }
}