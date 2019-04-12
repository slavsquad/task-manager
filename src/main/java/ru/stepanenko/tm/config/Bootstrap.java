package ru.stepanenko.tm.config;

import ru.stepanenko.tm.App;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.repository.project.ProjectDao;
import ru.stepanenko.tm.repository.project.ProjectDaoHashMap;
import ru.stepanenko.tm.repository.task.TaskDao;
import ru.stepanenko.tm.repository.task.TaskDaoHashMap;
import ru.stepanenko.tm.services.project.ProjectCommands;
import ru.stepanenko.tm.services.project.ProjectCommandsImpl;
import ru.stepanenko.tm.services.task.TaskCommands;
import ru.stepanenko.tm.services.task.TaskCommandsImpl;

import java.util.Collection;
import java.util.Scanner;



public class Bootstrap {

    private static final String PROJECT_CLEAR = "project-clear";
    private static final String PROJECT_CREATE = "project-create";
    private static final String PROJECT_LIST = "project-list";
    private static final String PROJECT_REMOVE = "project-remove";
    private static final String PROJECT_EDIT = "project-edit";
    private static final String TASK_CLEAR = "task-clear";
    private static final String TASK_CREATE = "task-create";
    private static final String TASK_LIST = "task-list";
    private static final String TASK_REMOVE = "task-remove";
    private static final String TASK_EDIT = "task-edit";
    private static final String HELP = "help";
    private static final String EXIT = "exit";

    ProjectCommands projectCommands;
    TaskCommands taskCommands;

    public void init() {
        ProjectDao projectDao = new ProjectDaoHashMap();
        TaskDao taskDao = new TaskDaoHashMap();

        Project project1 = new Project("My_project_1", "Description for my project 1");
        Project project2 = new Project("My_project_2", "Description for my project 2");
        Project project3 = new Project("My_project_3", "Description for my project 3");
        Project project4 = new Project("My_project_4", "Description for my project 4");

        projectDao.persist(project1);
        projectDao.persist(project2);
        projectDao.persist(project3);
        projectDao.persist(project4);

        taskDao.persist(new Task("task_1", "Description for task 1", project1.getUuid()));
        taskDao.persist(new Task("task_2", "Description for task 2", project1.getUuid()));
        taskDao.persist(new Task("task_3", "Description for task 3", project1.getUuid()));
        taskDao.persist(new Task("task_4", "Description for task 4", project1.getUuid()));

        taskDao.persist(new Task("task_1", "Description for task 1", project2.getUuid()));
        taskDao.persist(new Task("task_2", "Description for task 2", project2.getUuid()));
        taskDao.persist(new Task("task_3", "Description for task 3", project2.getUuid()));

        taskDao.persist(new Task("task_1", "Description for task 1", project3.getUuid()));
        taskDao.persist(new Task("task_2", "Description for task 2", project3.getUuid()));

        taskDao.persist(new Task("task_1", "Description for task 1", project4.getUuid()));


        projectCommands = new ProjectCommandsImpl(projectDao);
        taskCommands = new TaskCommandsImpl(taskDao);


        System.out.println("==Welcome to Task manager!==\n" +
                "Input help for more information");
        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.println("\nPlease input your command:");
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
                    taskClear(scanner);
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
    private  void projectCreate(Scanner scanner) {
        System.out.println("Please input project name:");
        String name = scanner.nextLine();
        System.out.println("Please input project description:");
        String description = scanner.nextLine();
        if (projectCommands.create(name, description) != null) {
            System.out.println("Project " + name + " is create!");
        } else {
            System.out.println("Project " + name + " does not create!");
            System.out.println("Project name or description does not empty");
        }
    }

    private  void projectClear() {

        if (projectCommands.clear()) {
            System.out.println("Project is clear!");
        } else {
            System.out.println("Project does not clear!");
        }
    }

    private  void projectList(Scanner scanner) {
        System.out.print("Press ENTER for print all project or input id project: ");
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

    private  void projectRemove(Scanner scanner) {
        System.out.print("Please input project ID for remove: ");
        String projectID = scanner.nextLine();
        Project project = projectCommands.remove(projectID);
        if (project != null) {
            System.out.println("Project " + project.getName() + " is remove!");
        } else {
            System.out.println("Project id: " + projectID + " does not found!");
        }

    }

    private  void projectEdit(Scanner scanner) {
        System.out.print("Please input project ID for edit: ");
        String projectID = scanner.nextLine();

        Project oldProject = (projectCommands.findOne(projectID));
        if (oldProject != null) {
            System.out.println("Input new project's name: ");
            String newName = scanner.nextLine();

            System.out.println("Input new project's description: ");
            String newDescription = scanner.nextLine();

            Project project = projectCommands.edit(oldProject, newName, newDescription);

            if (project != null) {
                System.out.println("Project " + project.getName() + " is update!");
            } else {
                System.out.println("Project name or description can't be empty!");
            }
        } else {
            System.out.println("Project id: " + projectID + " does not found!");
        }
    }

    private  void taskClear(Scanner scanner) {
        System.out.println("Please input project id:");
        String projectID = scanner.nextLine();

        Project project = (projectCommands.findOne(projectID));
        if (project != null) {
            if (taskCommands.clear(project.getUuid())) {
                System.out.println("All tasks for project id:" + projectID + " clear.");
            } else {
                System.out.println("Tasks for project id:" + projectID + " does not found!");
            }
        } else {
            System.out.println("Project id: " + projectID + " does not found!");
        }
    }

    private  void taskCreate(Scanner scanner) {
        System.out.println("Please input project id:");
        String projectID = scanner.nextLine();

        Project project = (projectCommands.findOne(projectID));
        if (project != null) {
            System.out.println("Please input task name:");
            String name = scanner.nextLine();
            System.out.println("Please input task description:");
            String description = scanner.nextLine();
            Task task = taskCommands.create(name, description, project.getUuid());
            if (task != null) {
                System.out.println("Task " + name + " is create!");
            } else {
                System.out.println("Task " + name + " does not create!");
                System.out.println("Task name or description can't be empty!");
            }
        } else {
            System.out.println("Project id: " + projectID + " does not found!");
        }
    }

    private  void taskList(Scanner scanner) {
        System.out.println("Please input project id:");
        String projectID = scanner.nextLine();
        Project project = projectCommands.findOne(projectID);
        if (project != null) {

            System.out.print("Please input ID task or press ENTER for print all task: ");
            String taskID = scanner.nextLine();

            if ("".equals(taskID)) {
                printCollection(taskCommands.findAllByProjectUUID(project.getUuid()));
            } else {
                Task task = taskCommands.findOne(taskID);
                if (task != null) {
                    System.out.println(task);
                } else {
                    System.out.println("Task id: " + taskID + " does not found!");
                }
            }
        } else {
            System.out.println("Project id " + projectID + " does not found!");
        }
    }

    private  void taskRemove(Scanner scanner) {
        System.out.println("Please input project id:");
        String projectID = scanner.nextLine();
        Project project = projectCommands.findOne(projectID);
        if (project != null) {
            System.out.println("Please input ID task for remove:");
            String taskID = scanner.nextLine();
            Task task = taskCommands.remove(taskID);
            if (task != null) {
                System.out.println("Task id: " + task.getId() + " is remove!");
            } else {
                System.out.println("Task id: " + taskID + " is not found!");
            }

        } else {
            System.out.println("Project id " + projectID + " does not found!");
        }
    }

    private  void taskEdit(Scanner scanner) {
        System.out.println("Please input project id:");
        String projectID = scanner.nextLine();
        Project project = projectCommands.findOne(projectID);
        if (project != null) {
            System.out.println("Please input ID task for edit:");
            String taskID = scanner.nextLine();
            Task oldTask = taskCommands.findOne(taskID);
            if (oldTask != null) {
                System.out.println("Input new task's name: ");
                String newName = scanner.nextLine();

                System.out.println("Input new task's description: ");
                String newDescription = scanner.nextLine();

                Task task = taskCommands.edit(oldTask, newName, newDescription);
                if (task != null) {
                    System.out.println("Task id: " + task.getId() + "edit is complete!");
                } else {
                    System.out.println("Task name or description can't be empty!");
                }

            } else {
                System.out.println("Task id: " + taskID + " is not found!");
            }

        } else {
            System.out.println("Project id " + projectID + " does not found!");
        }
    }

    private  void help() {
        System.out.println("project-clear: Remove all projects.\n" +
                "project-create: Create new project.\n" +
                "project-list: Show all project or selected project.\n" +
                "project-remove: Remove selected project.\n" +
                "task-clear: Remove all tasks.\n" +
                "task-create: Create new task.\n" +
                "task-list: Show all tasks or selected task.\n" +
                "task-remove: Remove selected task.\n" +
                "help: Show all commands.\n" +
                "exit: quit from application.");
    }

    private  void exit() {
        System.exit(0);
    }

    private  <T> void printCollection(Collection<T> collection) {
        if (collection.size() != 0) {
            collection.forEach(System.out::println);
        } else {
            System.out.println("List is empty!");
        }
    }
}
