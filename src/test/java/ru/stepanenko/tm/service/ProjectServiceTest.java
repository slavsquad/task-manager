package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.config.DataBaseConfig;
import ru.stepanenko.tm.config.WebMvcConfig;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.util.DataGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebMvcConfig.class, DataBaseConfig.class})
public class ProjectServiceTest {

    @Autowired
    private IProjectService projectService;

    @Autowired
    private DataGenerator dataGenerator;

    @Before
    public void setUp() {
        dataGenerator.generate();
    }

    @After
    public void tearDown() throws DataValidateException {
        dataGenerator.cleanUp();
    }

    @Test
    public void projectCRUD(
    ) throws DataValidateException {
        @Nullable final String userId = new ArrayList<>(projectService.findAll()).get(0).getUserId();
        assertNotNull(userId);
        @NotNull final ProjectDTO project = new ProjectDTO(
                "Project",
                "Description",
                new Date(1000000),
                new Date(1000000),
                Status.DONE,
                userId);
        @NotNull final String projectId = project.getId();
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
        assertEquals(size - 1, projectService.findAllByUserId(userId).size());
    }

    @Test
    public void projectSort(
    ) throws DataValidateException {
        @Nullable final String userId = new ArrayList<>(projectService.findAll()).get(0).getUserId();
        assertNotNull(userId);
        @NotNull final ProjectDTO project1 = new ProjectDTO(
                "project1",
                "Description1",
                new Date(1000),
                new Date(1000),
                Status.DONE,
                userId);

        @NotNull final ProjectDTO project2 = new ProjectDTO(
                "project2",
                "Description2",
                new Date(1000000),
                new Date(1000000),
                Status.INPROCESS,
                userId);

        @NotNull final ProjectDTO project3 = new ProjectDTO(
                "project3",
                "Description3",
                new Date(0),
                new Date(0),
                Status.PLANNED,
                userId);

        projectService.removeAllByUserId(userId);
        assertTrue(projectService.findAllByUserId(userId).size() == 0);

        projectService.create(project1);
        projectService.create(project2);
        projectService.create(project3);

        @NotNull final List<ProjectDTO> sortStatus = new ArrayList<>(projectService.sortAllByUserId(userId, "status"));
        assertEquals(project3.getId(), sortStatus.get(0).getId());
        assertEquals(project2.getId(), sortStatus.get(1).getId());
        assertEquals(project1.getId(), sortStatus.get(2).getId());

        @NotNull final List<ProjectDTO> sortDateBegin = new ArrayList<>(projectService.sortAllByUserId(userId, "dateBegin"));
        assertEquals(project2.getId(), sortDateBegin.get(0).getId());
        assertEquals(project1.getId(), sortDateBegin.get(1).getId());
        assertEquals(project3.getId(), sortDateBegin.get(2).getId());

        @NotNull final List<ProjectDTO> sortDateEnd = new ArrayList<>(projectService.sortAllByUserId(userId, "dateEnd"));
        assertEquals(project2.getId(), sortDateEnd.get(0).getId());
        assertEquals(project1.getId(), sortDateEnd.get(1).getId());
        assertEquals(project3.getId(), sortDateEnd.get(2).getId());
        projectService.removeAllByUserId(userId);
    }

    @Test
    public void findProject(
    ) throws DataValidateException {
        @Nullable final String userId = new ArrayList<>(projectService.findAll()).get(0).getUserId();
        assertNotNull(userId);
        @NotNull final ProjectDTO project1 = new ProjectDTO(
                "Homework",
                "Make all homework",
                new Date(1000000),
                new Date(1000000),
                Status.DONE,
                userId);

        @NotNull final ProjectDTO project2 = new ProjectDTO(
                "Cooking",
                "Make apple pie!",
                new Date(1000000),
                new Date(1000000),
                Status.DONE,
                userId);

        projectService.removeAllByUserId(userId);
        assertTrue(projectService.findAllByUserId(userId).size() == 0);

        projectService.create(project1);
        projectService.create(project2);

        @NotNull final List<String> findProjectsId = projectService
                .findAllByPartOfNameOrDescription("Home", "apple", userId)
                .stream()
                .map(ProjectDTO::getId)
                .collect(Collectors.toList());
        assertTrue(findProjectsId.contains(project1.getId()));
        assertTrue(findProjectsId.contains(project2.getId()));
        projectService.removeAllByUserId(userId);
    }
}