package ru.stepanenko.tm.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.util.ComparatorUtil;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ProjectRepositoryTest {

    @NotNull
    private IProjectRepository projectRepository;

    @NotNull
    private User currentUser;

    @Before
    public void setUp() throws Exception {
        projectRepository = ProjectRepository.INSTANCE;
        currentUser = new User();
        currentUser.setLogin("admin");
        currentUser.setPassword("admin");
    }

    @After
    public void tearDown() throws Exception {
        projectRepository = null;
    }

    @Test
    public void projectCRUD() {
        @Nullable final String userId = currentUser.getId();
        assertNotNull(userId);
        @NotNull final Project project = new Project();
        @NotNull final String projectId = project.getId();
        project.setUserId(userId);
        project.setName("Project");
        project.setDescription("Description");
        projectRepository.persist(project);
        assertEquals(projectId, projectRepository.findOneByUserId(projectId, userId).getId());
        project.setName("Change name");
        project.setDescription("Change description");
        projectRepository.merge(project);
        assertEquals("Change name", projectRepository.findOneByUserId(projectId, userId).getName());
        assertEquals("Change description", projectRepository.findOneByUserId(projectId, userId).getDescription());
        @Nullable final int size = projectRepository.findAllByUserId(userId).size();
        assertNotNull(size);
        projectRepository.remove(projectId);
        assertEquals(size - 1, projectRepository.findAllByUserId(userId).size());
    }

    @Test
    public void projectSort() {
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

        projectRepository.removeAllByUserId(userId);
        assertTrue(projectRepository.findAllByUserId(userId).size() == 0);

        projectRepository.persist(project1);
        projectRepository.persist(project2);
        projectRepository.persist(project3);

        @NotNull final List<Project> sortStatus = new ArrayList<>(projectRepository.sortAllByUserId(userId, ComparatorUtil.getComparator("status")));
        assertEquals(project3.getId(), sortStatus.get(0).getId());
        assertEquals(project2.getId(), sortStatus.get(1).getId());
        assertEquals(project1.getId(), sortStatus.get(2).getId());

        @NotNull final List<Project> sortDateBegin = new ArrayList<>(projectRepository.sortAllByUserId(userId, ComparatorUtil.getComparator("dateBegin")));
        assertEquals(project3.getId(), sortDateBegin.get(0).getId());
        assertEquals(project1.getId(), sortDateBegin.get(1).getId());
        assertEquals(project2.getId(), sortDateBegin.get(2).getId());

        @NotNull final List<Project> sortDateEnd = new ArrayList<>(projectRepository.sortAllByUserId(userId, ComparatorUtil.getComparator("dateEnd")));
        assertEquals(project3.getId(), sortDateEnd.get(0).getId());
        assertEquals(project1.getId(), sortDateEnd.get(1).getId());
        assertEquals(project2.getId(), sortDateEnd.get(2).getId());
        projectRepository.removeAllByUserId(userId);
    }

    @Test
    public void findProject() {
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

        projectRepository.removeAllByUserId(userId);
        assertTrue(projectRepository.findAllByUserId(userId).size() == 0);

        projectRepository.persist(project1);
        projectRepository.persist(project2);

        @NotNull final List<String> findProjectsId = projectRepository
                .findAllByPartOfNameOrDescription("Home", "apple", userId)
                .stream()
                .map(Project::getId)
                .collect(Collectors.toList());
        assertTrue(findProjectsId.contains(project1.getId()));
        assertTrue(findProjectsId.contains(project2.getId()));
        projectRepository.removeAllByUserId(userId);
    }
}