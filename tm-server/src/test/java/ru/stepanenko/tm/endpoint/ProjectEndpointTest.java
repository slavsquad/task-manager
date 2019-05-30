package ru.stepanenko.tm.endpoint;

import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.stepanenko.tm.api.endpoint.IProjectEndpoint;
import ru.stepanenko.tm.api.endpoint.ISessionEndpoint;
import ru.stepanenko.tm.config.AppConfiguration;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.model.dto.SessionDTO;
import ru.stepanenko.tm.util.DataGenerator;
import ru.stepanenko.tm.util.HashUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfiguration.class)
public class ProjectEndpointTest {

    @Autowired
    private IProjectEndpoint projectEndpoint;

    @Autowired
    private ISessionEndpoint sessionEndpoint;

    @Autowired
    private DataGenerator dataGenerator;

    @NotNull
    private SessionDTO currentSession;

    @Before
    public void setUp(
    ) throws DataValidateException, AuthenticationSecurityException {
        dataGenerator.generate();
        currentSession = sessionEndpoint.openSession("admin", HashUtil.md5("admin"));
    }

    @After
    public void tearDown(
    ) throws DataValidateException, AuthenticationSecurityException {
        sessionEndpoint.closeSession(currentSession);
        dataGenerator.cleanUp();
        currentSession = null;
    }

    @Test
    public void projectCRUID(
    ) throws DataValidateException, AuthenticationSecurityException {
        assertNotNull(currentSession);
        projectEndpoint.removeAllProjectByUserId(currentSession);
        assertEquals(0, projectEndpoint.findAllProjectByUserId(currentSession).size());
        @NotNull final ProjectDTO project = new ProjectDTO();
        project.setId(UUID.randomUUID().toString());
        project.setName("New_Project");
        project.setDescription("New_Description");
        project.setUserId(currentSession.getUserId());
        projectEndpoint.createProject(currentSession, project);//CREATE
        assertEquals(project.getId(), projectEndpoint.findOneProject(currentSession, project.getId()).getId());//READ
        project.setName("Update_name");
        project.setDescription("Update_description");
        projectEndpoint.editProject(currentSession, project);//UPDATE
        assertEquals(project.getName(), projectEndpoint.findOneProject(currentSession, project.getId()).getName());
        assertEquals(project.getDescription(), projectEndpoint.findOneProject(currentSession, project.getId()).getDescription());
        projectEndpoint.removeProject(currentSession, project.getId());//DELETE
        assertEquals(0, projectEndpoint.findAllProjectByUserId(currentSession).size());

        for (int i = 0; i < 10; i++) {
            @NotNull final ProjectDTO testProject = new ProjectDTO();
            testProject.setId(UUID.randomUUID().toString());
            testProject.setName("New_Project_" + i);
            testProject.setDescription("New_Description_" + i);
            testProject.setUserId(currentSession.getUserId());
            projectEndpoint.createProject(currentSession, testProject);
        }
        assertEquals(10, projectEndpoint.findAllProjectByUserId(currentSession).size());
        projectEndpoint.removeAllProjectByUserId(currentSession);
        assertEquals(0, projectEndpoint.findAllProjectByUserId(currentSession).size());
    }

    @Test
    public void sortProject(
    ) throws DataValidateException, AuthenticationSecurityException {
        assertNotNull(currentSession);
        @NotNull final ProjectDTO project1 = new ProjectDTO();
        project1.setUserId(currentSession.getUserId());
        project1.setId(UUID.randomUUID().toString());
        project1.setName("project1");
        project1.setDescription("Description1");
        project1.setStatus(Status.DONE);
        project1.setDateBegin(new Date(1000));
        project1.setDateEnd(new Date(1000));

        @NotNull final ProjectDTO project2 = new ProjectDTO();
        project2.setUserId(currentSession.getUserId());
        project2.setId(UUID.randomUUID().toString());
        project2.setName("project2");
        project2.setDescription("Description2");
        project2.setStatus(Status.INPROCESS);
        project2.setDateBegin(new Date(1000000));
        project2.setDateEnd(new Date(1000000));

        @NotNull final ProjectDTO project3 = new ProjectDTO();
        project3.setId(UUID.randomUUID().toString());
        project3.setUserId(currentSession.getUserId());
        project3.setName("project3");
        project3.setDescription("Description3");
        project3.setStatus(Status.PLANNED);
        project3.setDateBegin(new Date(0));
        project3.setDateEnd(new Date(0));

        projectEndpoint.removeAllProjectByUserId(currentSession);
        assertTrue(projectEndpoint.findAllProjectByUserId(currentSession).size() == 0);

        projectEndpoint.createProject(currentSession, project1);
        projectEndpoint.createProject(currentSession, project2);
        projectEndpoint.createProject(currentSession, project3);

        @NotNull final List<ProjectDTO> sortStatus = new ArrayList<>(projectEndpoint.sortAllProjectByUserId(currentSession, "status"));
        assertEquals(project3.getId(), sortStatus.get(0).getId());
        assertEquals(project2.getId(), sortStatus.get(1).getId());
        assertEquals(project1.getId(), sortStatus.get(2).getId());

        @NotNull final List<ProjectDTO> sortDateBegin = new ArrayList<>(projectEndpoint.sortAllProjectByUserId(currentSession, "dateBegin"));
        assertEquals(project2.getId(), sortDateBegin.get(0).getId());
        assertEquals(project1.getId(), sortDateBegin.get(1).getId());
        assertEquals(project3.getId(), sortDateBegin.get(2).getId());

        @NotNull final List<ProjectDTO> sortDateEnd = new ArrayList<>(projectEndpoint.sortAllProjectByUserId(currentSession, "dateEnd"));
        assertEquals(project2.getId(), sortDateEnd.get(0).getId());
        assertEquals(project1.getId(), sortDateEnd.get(1).getId());
        assertEquals(project3.getId(), sortDateEnd.get(2).getId());
        projectEndpoint.removeAllProjectByUserId(currentSession);
    }

    @Test
    public void findProject(
    ) throws DataValidateException, AuthenticationSecurityException {
        assertNotNull(currentSession);
        @NotNull final ProjectDTO project1 = new ProjectDTO();
        project1.setUserId(currentSession.getUserId());
        project1.setId(UUID.randomUUID().toString());
        project1.setName("Homework");
        project1.setDescription("Make all homework");

        @NotNull final ProjectDTO project2 = new ProjectDTO();
        project2.setUserId(currentSession.getUserId());
        project2.setId(UUID.randomUUID().toString());
        project2.setName("Cooking");
        project2.setDescription("Make apple pie!");

        projectEndpoint.removeAllProjectByUserId(currentSession);
        assertTrue(projectEndpoint.findAllProjectByUserId(currentSession).size() == 0);

        projectEndpoint.createProject(currentSession, project1);
        projectEndpoint.createProject(currentSession, project2);
        @NotNull final List<String> findProjectsId = projectEndpoint
                .findAllProjectByPartOfNameOrDescription(currentSession, "Home", "apple")
                .stream()
                .map(ProjectDTO::getId)
                .collect(Collectors.toList());
        assertTrue(findProjectsId.contains(project1.getId()));
        assertTrue(findProjectsId.contains(project2.getId()));
        projectEndpoint.removeAllProjectByUserId(currentSession);
    }
}