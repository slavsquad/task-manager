package ru.stepanenko.tm.endpoint;

import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import ru.stepanenko.tm.api.IntegrationTest;
import ru.stepanenko.tm.api.service.IEndpointProducerService;
import ru.stepanenko.tm.service.EndpointProducerService;
import ru.stepanenko.tm.util.DateFormatter;
import ru.stepanenko.tm.util.HashUtil;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@Category(IntegrationTest.class)
public class TaskEndpointTest {

    @NotNull
    private ProjectEndpoint projectEndpoint;

    @NotNull
    private TaskEndpoint taskEndpoint;

    @NotNull
    private SessionEndpoint sessionEndpoint;

    @NotNull
    private SessionDTO currentSession;


    @Before
    public void setUp(
    ) throws DataValidateException_Exception, AuthenticationSecurityException_Exception {
        @NotNull final IEndpointProducerService endpointService = new EndpointProducerService();
        taskEndpoint = endpointService.getTaskEndpoint();
        projectEndpoint = endpointService.getProjectEndpoint();
        sessionEndpoint = endpointService.getSessionEndpoint();
        currentSession = sessionEndpoint.openSession("testAdmin", HashUtil.md5("testAdmin"));
    }

    @After
    public void tearDown(
    ) throws DataValidateException_Exception, AuthenticationSecurityException_Exception {
        sessionEndpoint.closeSession(currentSession);
        taskEndpoint = null;
        sessionEndpoint = null;
        currentSession = null;
    }

    @Test
    public void taskCRUID(
    ) throws DataValidateException_Exception, AuthenticationSecurityException_Exception {
        assertNotNull(currentSession);
        taskEndpoint.removeAllTaskByUserId(currentSession);
        assertEquals(0, taskEndpoint.findAllTaskByUserId(currentSession).size());

        @NotNull final ProjectDTO project = new ProjectDTO();
        project.setId(UUID.randomUUID().toString());
        project.setName("New_Project");
        project.setDescription("New_Description");
        project.setUserId(currentSession.userId);
        projectEndpoint.createProject(currentSession, project);

        @NotNull final TaskDTO task = new TaskDTO();
        task.setId(UUID.randomUUID().toString());
        task.setName("New_Task");
        task.setDescription("New_Description");
        task.setUserId(currentSession.userId);
        task.setProjectId(project.getId());

        taskEndpoint.createTask(currentSession, task);//CREATE
        assertEquals(task.getId(), taskEndpoint.findOneTask(currentSession, task.getId()).getId());//READ
        task.setName("Update_name");
        task.setDescription("Update_description");
        taskEndpoint.editTask(currentSession, task);//UPDATE
        assertEquals(task.getName(), taskEndpoint.findOneTask(currentSession, task.getId()).getName());
        assertEquals(task.getDescription(), taskEndpoint.findOneTask(currentSession, task.getId()).getDescription());
        taskEndpoint.removeTask(currentSession, task.getId());//DELETE
        assertEquals(0, taskEndpoint.findAllTaskByUserId(currentSession).size());

        for (int i = 0; i < 10; i++) {
            @NotNull final TaskDTO testTask = new TaskDTO();
            testTask.setId(UUID.randomUUID().toString());
            testTask.setName("New_Task_" + i);
            testTask.setDescription("New_Description_" + i);
            testTask.setUserId(currentSession.userId);
            testTask.setProjectId(project.getId());
            taskEndpoint.createTask(currentSession, testTask);
        }
        assertEquals(10, taskEndpoint.findAllTaskByProjectId(currentSession, project.getId()).size());
        taskEndpoint.removeAllTaskByProjectId(currentSession, project.getId());
        assertEquals(0, taskEndpoint.findAllTaskByProjectId(currentSession, project.getId()).size());
        projectEndpoint.removeAllProjectByUserId(currentSession);
    }

    @Test
    public void sortProject(
    ) throws DatatypeConfigurationException, DataValidateException_Exception, AuthenticationSecurityException_Exception {
        assertNotNull(currentSession);

        @NotNull final ProjectDTO project = new ProjectDTO();
        project.setId(UUID.randomUUID().toString());
        project.setName("New_Project");
        project.setDescription("New_Description");
        project.setUserId(currentSession.userId);
        projectEndpoint.createProject(currentSession, project);

        @NotNull final TaskDTO task1 = new TaskDTO();
        task1.setUserId(currentSession.getUserId());
        task1.setId(UUID.randomUUID().toString());
        task1.setProjectId(project.getId());
        task1.setName("task1");
        task1.setDescription("Description1");
        task1.setStatus(Status.DONE);
        task1.setDateBegin(DateFormatter.dateToXMLGregorianCalendar(new Date(1000)));
        task1.setDateEnd(DateFormatter.dateToXMLGregorianCalendar(new Date(1000)));

        @NotNull final TaskDTO task2 = new TaskDTO();
        task2.setUserId(currentSession.getUserId());
        task2.setId(UUID.randomUUID().toString());
        task2.setProjectId(project.getId());
        task2.setName("task2");
        task2.setDescription("Description2");
        task2.setStatus(Status.INPROCESS);
        task2.setDateBegin(DateFormatter.dateToXMLGregorianCalendar(new Date(1000000)));
        task2.setDateEnd(DateFormatter.dateToXMLGregorianCalendar(new Date(1000000)));

        @NotNull final TaskDTO task3 = new TaskDTO();
        task3.setId(UUID.randomUUID().toString());
        task3.setUserId(currentSession.getUserId());
        task3.setProjectId(project.getId());
        task3.setName("task3");
        task3.setDescription("Description3");
        task3.setStatus(Status.PLANNED);
        task3.setDateBegin(DateFormatter.dateToXMLGregorianCalendar(new Date(0)));
        task3.setDateEnd(DateFormatter.dateToXMLGregorianCalendar(new Date(0)));

        taskEndpoint.removeAllTaskByUserId(currentSession);
        assertTrue(taskEndpoint.findAllTaskByUserId(currentSession).size() == 0);

        taskEndpoint.createTask(currentSession, task1);
        taskEndpoint.createTask(currentSession, task2);
        taskEndpoint.createTask(currentSession, task3);

        @NotNull final List<TaskDTO> sortStatus = new ArrayList<>(taskEndpoint.sortAllTaskByUserId(currentSession, "status"));
        assertEquals(task3.getId(), sortStatus.get(0).getId());
        assertEquals(task2.getId(), sortStatus.get(1).getId());
        assertEquals(task1.getId(), sortStatus.get(2).getId());

        @NotNull final List<TaskDTO> sortDateBegin = new ArrayList<>(taskEndpoint.sortAllTaskByUserId(currentSession, "dateBegin"));
        assertEquals(task2.getId(), sortDateBegin.get(0).getId());
        assertEquals(task1.getId(), sortDateBegin.get(1).getId());
        assertEquals(task3.getId(), sortDateBegin.get(2).getId());

        @NotNull final List<TaskDTO> sortDateEnd = new ArrayList<>(taskEndpoint.sortAllTaskByUserId(currentSession, "dateEnd"));
        assertEquals(task2.getId(), sortDateEnd.get(0).getId());
        assertEquals(task1.getId(), sortDateEnd.get(1).getId());
        assertEquals(task3.getId(), sortDateEnd.get(2).getId());

        taskEndpoint.removeAllTaskByUserId(currentSession);
        projectEndpoint.removeAllProjectByUserId(currentSession);
    }

    @Test
    public void findProject(
    ) throws DataValidateException_Exception, AuthenticationSecurityException_Exception {
        assertNotNull(currentSession);
        @NotNull final ProjectDTO project = new ProjectDTO();
        project.setId(UUID.randomUUID().toString());
        project.setName("New_Project");
        project.setDescription("New_Description");
        project.setUserId(currentSession.userId);
        projectEndpoint.createProject(currentSession, project);

        @NotNull final TaskDTO task1 = new TaskDTO();
        task1.setUserId(currentSession.getUserId());
        task1.setProjectId(project.getId());
        task1.setId(UUID.randomUUID().toString());
        task1.setName("Homework");
        task1.setDescription("Make all homework");

        @NotNull final TaskDTO task2 = new TaskDTO();
        task2.setUserId(currentSession.getUserId());
        task2.setProjectId(project.getId());
        task2.setId(UUID.randomUUID().toString());
        task2.setName("Cooking");
        task2.setDescription("Make apple pie!");

        taskEndpoint.removeAllTaskByUserId(currentSession);
        assertTrue(taskEndpoint.findAllTaskByUserId(currentSession).size() == 0);

        taskEndpoint.createTask(currentSession, task1);
        taskEndpoint.createTask(currentSession, task2);
        @NotNull final List<String> findTasksId = taskEndpoint
                .findAllTaskByPartOfNameOrDescription(currentSession, "Home", "apple")
                .stream()
                .map(TaskDTO::getId)
                .collect(Collectors.toList());
        assertTrue(findTasksId.contains(task1.getId()));
        assertTrue(findTasksId.contains(task2.getId()));

        taskEndpoint.removeAllTaskByUserId(currentSession);
        projectEndpoint.removeAllProjectByUserId(currentSession);
    }
}