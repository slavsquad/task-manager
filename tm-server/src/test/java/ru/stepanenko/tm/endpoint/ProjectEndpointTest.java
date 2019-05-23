package ru.stepanenko.tm.endpoint;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.*;
import ru.stepanenko.tm.AppServerTest;
import ru.stepanenko.tm.api.endpoint.IProjectEndpoint;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.config.EntityManagerFactoryProducer;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.model.dto.SessionDTO;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.service.*;
import ru.stepanenko.tm.util.DataGenerator;

import javax.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ProjectEndpointTest {

    @NotNull
    private static EntityManagerFactory entityManagerFactory;

    @NotNull
    private static DataGenerator testDataGenerator;

    @NotNull
    private IProjectEndpoint projectEndpoint;

    @NotNull
    private SessionDTO currentSession;

    @NotNull
    private UserDTO currentUser;

    @BeforeClass
    public static void setUpClass() {
        entityManagerFactory =
                new EntityManagerFactoryProducer(new PropertyService(AppServerTest.class, "application.properties")).getFactory();
        testDataGenerator = new DataGenerator(
                new ProjectService(entityManagerFactory),
                new TaskService(entityManagerFactory),
                new UserService(entityManagerFactory),
                new SessionService(entityManagerFactory, new PropertyService(AppServerTest.class, "application.properties")));
    }

    @AfterClass
    public static void tearDownClass() {
        entityManagerFactory.close();
        entityManagerFactory = null;
    }

    @Before
    public void setUp() throws Exception {
        @NotNull IProjectService projectService = new ProjectService(entityManagerFactory);
        @NotNull IUserService userService = new UserService(entityManagerFactory);
        @NotNull ISessionService sessionService = new SessionService(entityManagerFactory, new PropertyService(AppServerTest.class, "application.properties"));
        projectEndpoint = new ProjectEndpoint(projectService, sessionService);
        testDataGenerator.generate();
        currentUser = userService.findByLogin("admin");
        currentSession = sessionService.create(currentUser);
    }

    @After
    public void tearDown() throws Exception {
        projectEndpoint = null;
        currentSession = null;
        currentUser = null;
    }

    @Test
    public void createProject() throws DataValidateException, AuthenticationSecurityException {
        @NotNull final ProjectDTO project = new ProjectDTO();
        project.setName("New_Project");
        project.setDescription("New_Description");
        project.setUserId(currentUser.getId());
        @NotNull final int size = projectEndpoint.findAllProjectByUserId(currentSession).size();
        assertTrue(size > 0);
        projectEndpoint.createProject(currentSession, project);
        assertEquals(size + 1, projectEndpoint.findAllProjectByUserId(currentSession).size());
    }

    @Test
    public void editProject() throws DataValidateException, AuthenticationSecurityException {
        @Nullable final ProjectDTO project = getEntity();
        assertNotNull(project);
        project.setName("update_name");
        project.setDescription("update_Description");
        projectEndpoint.editProject(currentSession, project);
        assertEquals(project.getName(), projectEndpoint.findOneProject(currentSession, project.getId()).getName());
        assertEquals(project.getDescription(), projectEndpoint.findOneProject(currentSession, project.getId()).getDescription());
    }

    @Test
    public void findOneProject() throws DataValidateException, AuthenticationSecurityException {
        @NotNull ProjectDTO project = new ProjectDTO();
        project.setName("New_Project");
        project.setDescription("New_Description");
        project.setUserId(currentUser.getId());
        projectEndpoint.createProject(currentSession, project);
        assertEquals(project.getId(), projectEndpoint.findOneProject(currentSession, project.getId()).getId());
    }

    @Test
    public void removeProject() throws DataValidateException, AuthenticationSecurityException {
        @NotNull final int size = projectEndpoint.findAllProjectByUserId(currentSession).size();
        assertTrue(size > 0);
        projectEndpoint.removeProject(currentSession, getEntity().getId());
        assertEquals(size - 1, projectEndpoint.findAllProjectByUserId(currentSession).size());
    }

    @Test
    public void findAllProjectByUserId() throws DataValidateException, AuthenticationSecurityException {
        @NotNull final int size = projectEndpoint.findAllProjectByUserId(currentSession).size();
        assertTrue(size > 0);
        projectEndpoint.removeProject(currentSession, getEntity().getId());
        assertEquals(size - 1, projectEndpoint.findAllProjectByUserId(currentSession).size());
    }

    @Test
    public void removeAllProjectByUserId() throws DataValidateException, AuthenticationSecurityException {
        @NotNull final int size = projectEndpoint.findAllProjectByUserId(currentSession).size();
        assertTrue(size > 0);
        projectEndpoint.removeAllProjectByUserId(currentSession);
        assertEquals(0, projectEndpoint.findAllProjectByUserId(currentSession).size());
    }

    @Test
    public void sortAllProjectByUserId() throws DataValidateException, AuthenticationSecurityException {
        @NotNull final ProjectDTO project1 = new ProjectDTO();
        project1.setUserId(currentUser.getId());
        project1.setName("project1");
        project1.setDescription("Description1");
        project1.setStatus(Status.DONE);
        project1.setDateBegin(new Date(1000));
        project1.setDateEnd(new Date(1000));

        @NotNull final ProjectDTO project2 = new ProjectDTO();
        project2.setUserId(currentUser.getId());
        project2.setName("project2");
        project2.setDescription("Description2");
        project2.setStatus(Status.INPROCESS);
        project2.setDateBegin(new Date(1000000));
        project2.setDateEnd(new Date(1000000));

        @NotNull final ProjectDTO project3 = new ProjectDTO();
        project3.setUserId(currentUser.getId());
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
    }

    @Test
    public void findAllProjectByPartOfNameOrDescription() throws DataValidateException, AuthenticationSecurityException {

        @NotNull final ProjectDTO project1 = new ProjectDTO();
        project1.setUserId(currentUser.getId());
        project1.setName("Homework");
        project1.setDescription("Make all homework");

        @NotNull final ProjectDTO project2 = new ProjectDTO();
        project2.setUserId(currentUser.getId());
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
    }

    private ProjectDTO getEntity() throws DataValidateException, AuthenticationSecurityException {
        @Nullable final List<ProjectDTO> projects = new ArrayList<>(projectEndpoint.findAllProjectByUserId(currentSession));
        if (projects.isEmpty()) return null;
        return projects.get(0);
    }
}