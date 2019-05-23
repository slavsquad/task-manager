package ru.stepanenko.tm.endpoint;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.*;
import org.junit.experimental.categories.Category;
import ru.stepanenko.tm.api.IntegrationTest;
import ru.stepanenko.tm.api.service.IEndpointProducerService;
import ru.stepanenko.tm.service.EndpointProducerService;
import ru.stepanenko.tm.util.DateFormatter;
import ru.stepanenko.tm.util.HashUtil;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@Category(IntegrationTest.class)
public class ProjectEndpointTest {

    @NotNull
    private ProjectEndpoint projectEndpoint;

    @NotNull
    private SessionEndpoint sessionEndpoint;

    @NotNull
    private SessionDTO currentSession;

    @Before
    public void setUp() throws Exception {
        @NotNull final IEndpointProducerService endpointService = new EndpointProducerService();
        projectEndpoint = endpointService.getProjectEndpoint();
        sessionEndpoint = endpointService.getSessionEndpoint();
        currentSession = sessionEndpoint.openSession("testAdmin", HashUtil.md5("testAdmin"));
    }

    @After
    public void tearDown() throws Exception {
        sessionEndpoint.closeSession(currentSession);
        projectEndpoint = null;
        sessionEndpoint = null;
        currentSession = null;
    }

    @Test
    public void projectCRUID(
    ) throws DataValidateException_Exception, AuthenticationSecurityException_Exception {
        assertNotNull(currentSession);
        projectEndpoint.removeAllProjectByUserId(currentSession);
        assertEquals(0, projectEndpoint.findAllProjectByUserId(currentSession).size());
        @NotNull final ProjectDTO project = new ProjectDTO();
        project.setId(UUID.randomUUID().toString());
        project.setName("New_Project");
        project.setDescription("New_Description");
        project.setUserId(currentSession.userId);
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
            testProject.setUserId(currentSession.userId);
            projectEndpoint.createProject(currentSession, testProject);
        }
        assertEquals(10, projectEndpoint.findAllProjectByUserId(currentSession).size());
        projectEndpoint.removeAllProjectByUserId(currentSession);
        assertEquals(0, projectEndpoint.findAllProjectByUserId(currentSession).size());
    }

    @Test
    public void sortProject(
    ) throws DatatypeConfigurationException, DataValidateException_Exception, AuthenticationSecurityException_Exception {
        assertNotNull(currentSession);
        @NotNull final ProjectDTO project1 = new ProjectDTO();
        project1.setUserId(currentSession.getUserId());
        project1.setId(UUID.randomUUID().toString());
        project1.setName("project1");
        project1.setDescription("Description1");
        project1.setStatus(Status.DONE);
        project1.setDateBegin(DateFormatter.dateToXMLGregorianCalendar(new Date(1000)));
        project1.setDateEnd(DateFormatter.dateToXMLGregorianCalendar(new Date(1000)));

        @NotNull final ProjectDTO project2 = new ProjectDTO();
        project2.setUserId(currentSession.getUserId());
        project2.setId(UUID.randomUUID().toString());
        project2.setName("project2");
        project2.setDescription("Description2");
        project2.setStatus(Status.INPROCESS);
        project2.setDateBegin(DateFormatter.dateToXMLGregorianCalendar(new Date(1000000)));
        project2.setDateEnd(DateFormatter.dateToXMLGregorianCalendar(new Date(1000000)));

        @NotNull final ProjectDTO project3 = new ProjectDTO();
        project3.setId(UUID.randomUUID().toString());
        project3.setUserId(currentSession.getUserId());
        project3.setName("project3");
        project3.setDescription("Description3");
        project3.setStatus(Status.PLANNED);
        project3.setDateBegin(DateFormatter.dateToXMLGregorianCalendar(new Date(0)));
        project3.setDateEnd(DateFormatter.dateToXMLGregorianCalendar(new Date(0)));

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
    public void findProject() throws DataValidateException_Exception, AuthenticationSecurityException_Exception {
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