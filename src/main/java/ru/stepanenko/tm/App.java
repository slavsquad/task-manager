package ru.stepanenko.tm;

import ru.stepanenko.tm.dao.project.ProjectDao;
import ru.stepanenko.tm.dao.project.ProjectDaoHashMap;
import ru.stepanenko.tm.dao.task.TaskDao;
import ru.stepanenko.tm.dao.task.TaskDaoHashMap;
import ru.stepanenko.tm.domain.Project;
import ru.stepanenko.tm.domain.Task;
import ru.stepanenko.tm.services.project.ProjectCommands;
import ru.stepanenko.tm.services.project.ProjectCommandsImpl;
import ru.stepanenko.tm.services.task.TaskCommands;
import ru.stepanenko.tm.services.task.TaskCommandsImpl;

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
    private static final String TASK_CLEAR = "task-clear";
    private static final String TASK_CREATE = "task-create";
    private static final String TASK_LIST = "task-list";
    private static final String TASK_REMOVE = "task-remove";
    private static final String TASK_EDIT = "task-edit";
    private static final String HELP = "help";
    private static final String EXIT = "exit";

    static ProjectDao projectDao = new ProjectDaoHashMap();
    static ProjectCommands projectCommands = new ProjectCommandsImpl(projectDao);

    static TaskDao taskDao = new TaskDaoHashMap();
    static TaskCommands taskCommands = new TaskCommandsImpl(taskDao);

    public static void main(String[] args) {
        System.out.println("==Welcome to Task manager!==\n" +
                "Input help for more information");
        Scanner scanner = new Scanner(System.in);


        while (true) {
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

            System.out.println("Please input your command:");
        }
    }


    private static void projectCreate(Scanner scanner) {
        System.out.println("Please input project name:");
        String name = scanner.nextLine();
        System.out.println("Please input project description:");
        String description = scanner.nextLine();
        projectCommands.create(new Project(name, description));
    }

    private static void projectClear() {
        projectCommands.clear();
    }

    private static void projectList(Scanner scanner) {
        System.out.println("Please input ID for print project or press Enter for print all project");
        String string = scanner.nextLine();
        if ("all".equals(string)) {
            projectCommands.list();
        } else {
            try {
                int id = Integer.parseInt(string);
                projectCommands.list(id);
            } catch (IllegalArgumentException e) {
                System.out.println("Incorrect input error:" + e);
            }
        }
    }

    private static void projectRemove(Scanner scanner) {
        System.out.println("Please input project ID for remove:");
        int id = scanner.nextInt();
        projectCommands.remove(id);
    }

    private static void projectEdit(Scanner scanner) {
        System.out.println("Please input project ID for edit:");
        int id = scanner.nextInt();
        projectCommands.edit(id);
    }

    private static void taskClear() {
        taskCommands.clear();
    }

    private static void taskCreate(Scanner scanner) {
        System.out.println("Please input project id:");
        int projectID = scanner.nextInt();
        if (projectDao.getById(projectID) != null) {
            scanner.nextLine();

            System.out.println("Please input task name:");
            String name = scanner.nextLine();
            System.out.println("Please input task description:");
            String description = scanner.nextLine();
            taskCommands.create(new Task(name, description, projectID));

        } else {
            System.out.println("Project id " + projectID + " does not found!");
        }
    }

    private static void taskList(Scanner scanner) {
        System.out.println("Please input project id:");
        int projectID = scanner.nextInt();
        if (projectDao.getById(projectID) != null) {
            scanner.nextLine();
            System.out.println("Please input ID task for print task or input all for print all task");
            String string = scanner.nextLine();
            if ("all".equals(string)) {
                taskCommands.list(projectID);
            } else {
                try {
                    int id = Integer.parseInt(string);
                    taskCommands.list(projectID, id);
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
        if (projectDao.getById(projectID) != null) {
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
        if (projectDao.getById(projectID) != null) {
            scanner.nextLine();

            System.out.println("Please input ID task for edit:");
            int id = scanner.nextInt();
            taskCommands.edit(id);
        } else {
            System.out.println("Project id " + projectID + " does not found!");
        }
    }

    private static void exit() {
        System.exit(0);
    }

    private static void help() {
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
}
