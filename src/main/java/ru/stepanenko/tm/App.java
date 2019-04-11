package ru.stepanenko.tm;

import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.repository.project.ProjectDao;
import ru.stepanenko.tm.repository.project.ProjectDaoHashMap;
import ru.stepanenko.tm.repository.task.TaskDao;
import ru.stepanenko.tm.repository.task.TaskDaoHashMap;
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.services.project.ProjectCommands;
import ru.stepanenko.tm.services.project.ProjectCommandsImpl;
import ru.stepanenko.tm.services.task.TaskCommands;
import ru.stepanenko.tm.services.task.TaskCommandsImpl;

import java.util.Collection;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    private static final String PROJECT_CLEAR = "project-clear";
    private static final String PROJECT_CREATE = "project-create";
    private static final String PROJECT_LIST = "project-list";
    private static final String PROJECT_REMOVE = "project-remove";
    private static final String PROJECT_EDIT = "project-edit";
    private static final String TASK_CLEAR = "task-removeAll";
    private static final String TASK_CREATE = "task-persist";
    private static final String TASK_LIST = "task-list";
    private static final String TASK_REMOVE = "task-remove";
    private static final String TASK_EDIT = "task-edit";
    private static final String HELP = "help";
    private static final String EXIT = "exit";


    static ProjectDao projectDao = new ProjectDaoHashMap();
    static TaskDao taskDao = new TaskDaoHashMap();

    static {
        Project project1 = new Project("My_project_1","Description for my project 1");
        Project project2 = new Project("My_project_2","Description for my project 2");
        Project project3 = new Project("My_project_3","Description for my project 3");
        Project project4 = new Project("My_project_4","Description for my project 4");

        projectDao.persist(project1);
        projectDao.persist(project2);
        projectDao.persist(project3);
        projectDao.persist(project4);

        taskDao.persist(new Task("task_1","Description for task 1",project1.getUuid()));
        taskDao.persist(new Task("task_2","Description for task 2",project1.getUuid()));
        taskDao.persist(new Task("task_3","Description for task 3",project1.getUuid()));
        taskDao.persist(new Task("task_4","Description for task 4",project1.getUuid()));

        taskDao.persist(new Task("task_1","Description for task 1",project2.getUuid()));
        taskDao.persist(new Task("task_2","Description for task 2",project2.getUuid()));
        taskDao.persist(new Task("task_3","Description for task 3",project2.getUuid()));

        taskDao.persist(new Task("task_1","Description for task 1",project3.getUuid()));
        taskDao.persist(new Task("task_2","Description for task 2",project3.getUuid()));

        taskDao.persist(new Task("task_1","Description for task 1",project4.getUuid()));

    }

    static ProjectCommands projectCommands = new ProjectCommandsImpl(projectDao);
    static TaskCommands taskCommands = new TaskCommandsImpl(taskDao);

    public static void main(String[] args) {


        System.out.println("==Welcome to Task manager!==\n" +
                "Input help for more information");
        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.println("Please input your command:");
            String command = scanner.nextLine();
            switch (command) {
                case PROJECT_CLEAR:
                    projectClear();
                    break;

                case PROJECT_CREATE:
                    projectCreate(scanner);
                    break;

                case PROJECT_LIST:
                    projectList(scanner);
                    break;

                case PROJECT_REMOVE:
                    projectRemove(scanner);
                    break;

                case PROJECT_EDIT:
                    projectEdit(scanner);
                    break;

                case TASK_CLEAR:
                    taskClear();
                    break;

                case TASK_CREATE:
                    taskCreate(scanner);

                    break;
                case TASK_LIST:
                    taskList(scanner);
                    break;

                case TASK_REMOVE:
                    taskRemove(scanner);
                    break;

                case TASK_EDIT:
                    taskEdit(scanner);
                    break;

                case HELP:
                    help();
                    break;

                case EXIT:
                    exit();
                    break;
            }
        }
    }


    private static void projectCreate(Scanner scanner) {
        System.out.println("Please input project name:");
        String name = scanner.nextLine();
        System.out.println("Please input project description:");
        String description = scanner.nextLine();
        if (projectCommands.create(name, description)) {
            System.out.println("Project " + name + " is create!");
        } else {
            System.out.println("Project " + name + " does not create!");
            System.out.println("Project name or description does not empty");
        }
    }

    private static void projectClear() {

        if (projectCommands.clear()) {
            System.out.println("Project list is clear!");
        } else {
            System.out.println("Project list does not clear!");
        }
    }

    private static void projectList(Scanner scanner) {
        System.out.print("Press Enter for print all project or input id project: ");
        String projectID = scanner.nextLine();
        //list for all projects
        if ("".equals(projectID)) {
            printCollection(projectCommands.findAll());
        } else {//list for selected project
            Project project = projectCommands.findOne(projectID);
            if (project == null) {
                System.out.println("Project with id: " + projectID + " does not exist!");
            } else {
                System.out.println(project);
            }
        }
    }

    private static void projectRemove(Scanner scanner) {
        System.out.print("Please input project ID for remove: ");
        String projectID = scanner.nextLine();
        Project project = projectCommands.remove(projectID);
        if (project != null) {
            System.out.println("Project " + project.getName() + " is remove!");
        } else {
            System.out.println("Project id: " + projectID + " does not found!");
        }

    }

    private static void projectEdit(Scanner scanner) {
        System.out.print("Please input project ID for edit: ");
        String projectID = scanner.nextLine();

        System.out.println("Input new project's name: ");
        String name = scanner.nextLine();

        System.out.println("Input new project's description: ");
        String description = scanner.nextLine();

        Project project = projectCommands.edit(projectID,name,description);
        if (project!=null){
            System.out.println("Project " + project.getName() + " is update!");
        } else {
            System.out.println("Project id: " + projectID + " does not found or name and description can't be empty!");
        }





        if (project == null) {

        } else {
            System.out.println(project);



            project.setName(name);
            project.setDescription(description);
        }


       // projectCommands.edit(id);
    }

    private static void taskClear() {
        taskCommands.clear();
    }

    private static void taskCreate(Scanner scanner) {
        System.out.println("Please input project id:");
        int projectID = scanner.nextInt();
        if (projectDao.findOne(projectID) != null) {
            scanner.nextLine();

            System.out.println("Please input task name:");
            String name = scanner.nextLine();
            System.out.println("Please input task description:");
            String description = scanner.nextLine();
            taskCommands.create(new Task(name, description, projectDao.findOne(projectID).getUuid()));
        } else {
            System.out.println("Project id " + projectID + " does not found!");
        }
    }

    private static void taskList(Scanner scanner) {
        System.out.println("Please input project id:");
        int projectID = scanner.nextInt();
        if (projectDao.findOne(projectID) != null) {
            scanner.nextLine();
            System.out.println("Please input ID task for print task or input all for print all task");
            String string = scanner.nextLine();
            if ("all".equals(string)) {
                taskCommands.list(projectDao.findOne(projectID).getUuid());
            } else {
                try {
                    int id = Integer.parseInt(string);
                    taskCommands.list(projectDao.findOne(projectID).getUuid(), id);
                } catch (IllegalArgumentException e) {
                    System.out.println("Incorrect input error:" + e);
                }
            }
        } else {
            System.out.println("Project id " + projectID + " does not found!");
        }
    }

    private static void taskRemove(Scanner scanner) {
        System.out.println("Please input project id:");
        int projectID = scanner.nextInt();
        if (projectDao.findOne(projectID) != null) {
            scanner.nextLine();

            System.out.println("Please input ID task for remove:");
            int id = scanner.nextInt();
            taskCommands.remove(id);
        } else {
            System.out.println("Project id " + projectID + " does not found!");
        }
    }

    private static void taskEdit(Scanner scanner) {
        System.out.println("Please input project id:");
        int projectID = scanner.nextInt();
        if (projectDao.findOne(projectID) != null) {
            scanner.nextLine();

            System.out.println("Please input ID task for edit:");
            int id = scanner.nextInt();
            taskCommands.edit(id);
        } else {
            System.out.println("Project id " + projectID + " does not found!");
        }
    }

    private static void help() {
        System.out.println("project-clear: Remove all projects.\n" +
                "project-create: Create new project.\n" +
                "project-list: Show all project or selected project.\n" +
                "project-remove: Remove selected project.\n" +
                "task-removeAll: Remove all tasks.\n" +
                "task-persist: Create new task.\n" +
                "task-list: Show all tasks or selected task.\n" +
                "task-remove: Remove selected task.\n" +
                "help: Show all commands.\n" +
                "exit: quit from application.");
    }

    private static void exit() {
        System.exit(0);
    }

    private static <T> void printCollection(Collection<T> collection) {
        if (collection.size() != 0) {
            collection.forEach(System.out::println);
        } else {
            System.out.println("List is empty!");
        }
    }
}
