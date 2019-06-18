package ru.stepanenko.tm.repository;

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
import org.springframework.transaction.annotation.Transactional;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.config.DataBaseConfig;
import ru.stepanenko.tm.config.WebMvcConfig;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.util.DataGenerator;

import java.util.ArrayList;

import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebMvcConfig.class, DataBaseConfig.class})
public class ProjectRepositoryTest {


    @Autowired
    private IProjectRepository projectRepository;

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
    @Transactional
    public void projectCRUD(
    ) throws DataValidateException {
        @Nullable final User user = new ArrayList<>(projectRepository.findAll()).get(0).getUser();
        assertNotNull(user);
        @NotNull final Project project = new Project();
        @NotNull final String projectId = project.getId();
        project.setUser(user);
        project.setName("Project");
        project.setDescription("Description");
        projectRepository.persist(project);
        assertEquals(projectId, projectRepository.findOneByUserId(projectId, user).getId());
        project.setName("Change name");
        project.setDescription("Change description");
        projectRepository.merge(project);
        assertEquals("Change name", projectRepository.findOneByUserId(projectId, user).getName());
        assertEquals("Change description", projectRepository.findOneByUserId(projectId, user).getDescription());
        @Nullable final int size = projectRepository.findAllByUserId(user).size();
        assertNotNull(size);
        projectRepository.remove(project);
        assertEquals(size - 1, projectRepository.findAllByUserId(user).size());
    }

}