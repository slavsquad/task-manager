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
    public static void main(String[] args) {
        System.out.println("==Welcome to Task manager!==\n" +
                "input help for information");
        Scanner scanner = new Scanner(System.in);
        String command;

        ProjectDao projectDao = new ProjectDaoHashMap();
        ProjectCommands projectCommands = new ProjectCommandsImpl(projectDao);

        TaskDao taskDao = new TaskDaoHashMap();
        TaskCommands taskCommands = new TaskCommandsImpl(taskDao);

        while (!("quit".equals(command = scanner.nextLine()))) {
            int id;
            String name;
            String description;
            String string;
            int projectID;
            switch (command) {
                case "project-clear":
                    projectCommands.clear();
                    break;
                case "project-create":
                    System.out.println("Please input project name:");
                    name = scanner.nextLine();
                    System.out.println("Please input project description:");
                    description = scanner.nextLine();
                    projectCommands.create(new Project(name, description));
                    break;
                case "project-list":
                    System.out.println("Please input ID for print project or press Enter for print all project");
                    string = scanner.nextLine();
                    if ("all".equals(string)) {
                        projectCommands.list();
                    } else {
                        try {
                            id = Integer.parseInt(string);
                            projectCommands.list(id);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Incorrect input error:" + e);
                        }
                    }
                    break;

                case "project-remove":
                    System.out.println("Please input project ID for remove:");
                    id = scanner.nextInt();
                    projectCommands.remove(id);
                    break;

                case "project-edit":
                    System.out.println("Please input project ID for edit:");
                    id = scanner.nextInt();
                    projectCommands.edit(id);
                    break;

                case "task-clear":
                    taskCommands.clear();
                    break;
                case "task-create":
                    System.out.println("Please input project id:");
                    projectID = scanner.nextInt();
                    if (projectDao.getById(projectID) != null) {
                        scanner.nextLine();

                        System.out.println("Please input task name:");
                        name = scanner.nextLine();
                        System.out.println("Please input task description:");
                        description = scanner.nextLine();
                        taskCommands.create(new Task(name, description, projectID));

                    } else {
                        System.out.println("Project id " + projectID + " does not found!");
                    }

                    break;
                case "task-list":
                    System.out.println("Please input project id:");
                    projectID = scanner.nextInt();
                    if (projectDao.getById(projectID) != null) {
                        scanner.nextLine();
                        System.out.println("Please input ID task for print task or input all for print all task");
                        string = scanner.nextLine();
                        if ("all".equals(string)) {
                            taskCommands.list(projectID);
                        } else {
                            try {
                                id = Integer.parseInt(string);
                                taskCommands.list(projectID, id);
                            } catch (IllegalArgumentException e) {
                                System.out.println("Incorrect input error:" + e);
                            }
                        }
                    } else {
                        System.out.println("Project id " + projectID + " does not found!");
                    }
                    break;

                case "task-remove":
                    System.out.println("Please input project id:");
                    projectID = scanner.nextInt();
                    if (projectDao.getById(projectID) != null) {
                        scanner.nextLine();

                        System.out.println("Please input ID task for remove:");
                        id = scanner.nextInt();
                        taskCommands.remove(id);
                    } else {
                        System.out.println("Project id " + projectID + " does not found!");
                    }
                    break;

                case "task-edit":
                    System.out.println("Please input project id:");
                    projectID = scanner.nextInt();
                    if (projectDao.getById(projectID) != null) {
                        scanner.nextLine();

                        System.out.println("Please input ID task for edit:");
                        id = scanner.nextInt();
                        taskCommands.edit(id);
                    } else {
                        System.out.println("Project id " + projectID + " does not found!");
                    }
                    break;
                case "help":
                    System.out.println("project-clear: Remove all projects.\n" +
                            "project-create: Create new project.\n" +
                            "project-list: Show all project or selected project.\n" +
                            "project-remove: Remove selected project.\n" +
                            "task-clear: Remove all tasks.\n" +
                            "task-create: Create new task.\n" +
                            "task-list: Show all tasks or selected task.\n" +
                            "task-remove: Remove selected task.\n" +
                            "help: Show all commands.\n" +
                            "quit: quit from application.");
                    break;
            }

            System.out.println("Please input your command:");
        }
    }
}
