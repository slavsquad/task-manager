package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.*;
import ru.stepanenko.tm.AppServerTest;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.config.EntityManagerFactoryProducer;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.util.DataGenerator;

import javax.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ProjectServiceTest {

    @NotNull
    private static EntityManagerFactory entityManagerFactory;

    @NotNull
    private static DataGenerator testDataGenerator;

    @NotNull
    private IProjectService projectService;

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
        projectService = new ProjectService(entityManagerFactory);
        testDataGenerator.generate();
    }

    @After
    public void tearDown() throws Exception {
        projectService = null;
    }

    @Test
    public void create() throws DataValidateException {
        @Nullable final String userId = getEntity().getUserId();
        assertNotNull(userId);
        @NotNull final ProjectDTO project = new ProjectDTO();
        project.setName("New_Project");
        project.setDescription("New_Description");
        project.setUserId(userId);
        @NotNull final int size = projectService.findAll().size();
        assertTrue(size > 0);
        projectService.create(project);
        assertEquals(size + 1, projectService.findAll().size());
    }

    @Test
    public void edit() throws DataValidateException {
        @Nullable final ProjectDTO project = getEntity();
        assertNotNull(project);
        project.setName("update_name");
        project.setDescription("update_Description");
        projectService.edit(project);
        assertEquals(project.getName(), projectService.findOne(project.getId()).getName());
        assertEquals(project.getDescription(), projectService.findOne(project.getId()).getDescription());
    }

    @Test
    public void findOne() throws DataValidateException {
        @Nullable final String userId = getEntity().getUserId();
        assertNotNull(userId);
        @NotNull ProjectDTO project = new ProjectDTO();
        project.setName("New_Project");
        project.setDescription("New_Description");
        project.setUserId(userId);
        projectService.create(project);
        assertEquals(project.getId(), projectService.findOne(project.getId()).getId());
    }

    @Test
    public void remove() throws DataValidateException {
        @NotNull final int size = projectService.findAll().size();
        assertTrue(size>0);
        projectService.remove(getEntity().getId());
        assertEquals(size-1, projectService.findAll().size());
    }

    @Test
    public void clear() throws DataValidateException {
        @NotNull final int size = projectService.findAll().size();
        assertTrue(size>0);
        projectService.clear();
        assertEquals(0, projectService.findAll().size());
    }

    @Test
    public void findOneByUserId() throws DataValidateException {
        @Nullable final String userId = getEntity().getUserId();
        assertNotNull(userId);
        @NotNull ProjectDTO project = new ProjectDTO();
        project.setName("New_Project");
        project.setDescription("New_Description");
        project.setUserId(userId);
        projectService.create(project);
        assertEquals(project.getId(), projectService.findOne(project.getId(),userId).getId());
    }

    @Test
    public void removeOneByUserId() throws DataValidateException {
        @Nullable final ProjectDTO entity = getEntity();
        assertNotNull(entity);
        @NotNull final int size = projectService.findAllByUserId(entity.getUserId()).size();
        assertTrue(size>0);
        projectService.remove(entity.getId(), entity.getUserId());
        assertEquals(size-1, projectService.findAllByUserId(entity.getUserId()).size());
    }

    @Test
    public void findAll() throws DataValidateException {
        @NotNull final int size = projectService.findAll().size();
        assertTrue(size > 0);
        projectService.clear();
        assertEquals(0, projectService.findAll().size());
    }

    @Test
    public void findAllByUserId() throws DataValidateException {
        @NotNull final String userId = getEntity().getUserId();
        @NotNull final int size = projectService.findAllByUserId(userId).size();
        assertTrue(size > 0);
        projectService.removeAllByUserId(userId);
        assertEquals(0, projectService.findAllByUserId(userId).size());
    }

    @Test
    public void removeAllByUserId() throws DataValidateException {
        @NotNull final String userId = getEntity().getUserId();
        @NotNull final int size = projectService.findAllByUserId(userId).size();
        assertTrue(size > 0);
        projectService.removeAllByUserId(userId);
        assertEquals(0, projectService.findAllByUserId(userId).size());
    }

    @Test
    public void sortAllByUserId() throws DataValidateException {
        @Nullable final String userId = getEntity().getUserId();
        @NotNull final ProjectDTO project1 = new ProjectDTO();
        project1.setUserId(userId);
        project1.setName("project1");
        project1.setDescription("Description1");
        project1.setStatus(Status.DONE);
        project1.setDateBegin(new Date(1000));
        project1.setDateEnd(new Date(1000));

        @NotNull final ProjectDTO project2 = new ProjectDTO();
        project2.setUserId(userId);
        project2.setName("project2");
        project2.setDescription("Description2");
        project2.setStatus(Status.INPROCESS);
        project2.setDateBegin(new Date(1000000));
        project2.setDateEnd(new Date(1000000));

        @NotNull final ProjectDTO project3 = new ProjectDTO();
        project3.setUserId(userId);
        project3.setName("project3");
        project3.setDescription("Description3");
        project3.setStatus(Status.PLANNED);
        project3.setDateBegin(new Date(0));
        project3.setDateEnd(new Date(0));

        projectService.removeAllByUserId(userId);
        assertTrue(projectService.findAllByUserId(userId).size()==0);

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
    }

    @Test
    public void findAllByPartOfNameOrDescription() throws DataValidateException {
        @Nullable final String userId = getEntity().getUserId();
        assertNotNull(userId);

        @NotNull final ProjectDTO project1 = new ProjectDTO();
        project1.setUserId(userId);
        project1.setName("Homework");
        project1.setDescription("Make all homework");

        @NotNull final ProjectDTO project2 = new ProjectDTO();
        project2.setUserId(userId);
        project2.setName("Cooking");
        project2.setDescription("Make apple pie!");

        projectService.removeAllByUserId(userId);
        assertTrue(projectService.findAllByUserId(userId).size()==0);

        projectService.create(project1);
        projectService.create(project2);
        @NotNull final List<String> findProjectsId = projectService
                .findAllByPartOfNameOrDescription("Home", "apple", userId)
                .stream()
                .map(ProjectDTO::getId)
                .collect(Collectors.toList());
        assertTrue(findProjectsId.contains(project1.getId()));
        assertTrue(findProjectsId.contains(project2.getId()));
    }

    private ProjectDTO getEntity() throws DataValidateException {
        @Nullable final List<ProjectDTO> projects = new ArrayList<>(projectService.findAll());
        if (projects.isEmpty()) return null;
        return projects.get(0);
    }
}