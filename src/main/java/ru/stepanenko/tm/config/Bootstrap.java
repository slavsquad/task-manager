package ru.stepanenko.tm.config;

import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.repository.ProjectRepository;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.repository.TaskRepository;
import ru.stepanenko.tm.api.services.IProjectService;
import ru.stepanenko.tm.services.ProjectService;
import ru.stepanenko.tm.api.services.ITaskService;
import ru.stepanenko.tm.services.TaskService;

import java.util.Collection;
import java.util.Map;
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

    IProjectService IProjectService;
    ITaskService ITaskService;

    public void init() {


        IProjectRepository IProjectRepository = new ProjectRepository();
        ITaskRepository ITaskRepository = new TaskRepository();
        IProjectService = new ProjectService(IProjectRepository);
        ITaskService = new TaskService(ITaskRepository);



        //----------------------------------------- test data-------------------------------------------
        IProjectService.create("My_project_1", "Description for my project 1");
        IProjectService.create("My_project_2", "Description for my project 2");
        IProjectService.create("My_project_3", "Description for my project 3");
        IProjectService.create("My_project_4", "Description for my project 4");


        for (Project project:IProjectService.findAll()){
            ITaskService.create("task_1", "Description for task 1", project.getId());
            ITaskService.create("task_2", "Description for task 2", project.getId());
            ITaskService.create("task_3", "Description for task 3", project.getId());
            ITaskService.create("task_4", "Description for task 4", project.getId());
        }
        //----------------------------------------------------------------------------------------------



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

    private void projectCreate(Scanner scanner) {
        System.out.println("Please input project name:");
        String name = scanner.nextLine();
        System.out.println("Please input project description:");
        String description = scanner.nextLine();
        if (IProjectService.create(name, description)) {
            System.out.println("Project " + name + " is create!");
        } else {
            System.out.println("Project " + name + " does not create!");
            System.out.println("Project name or description does not empty");
        }
    }

    private void projectClear() {
        IProjectService.clear();
        System.out.println("Project is clear!");
    }

    private void projectList(Scanner scanner) {
        System.out.print("Press ENTER for print all project or input project name: ");
        String projectID = scanner.nextLine();
        //list for all projects
        if ("".equals(projectID)) {
            printCollection(IProjectService.findAll());
        } else {//list for selected project
            Project project = IProjectService.findOne(projectID);
            if (project == null) {
                System.out.println("Project with id: " + projectID + " does not exist!");
            } else {
                System.out.println(project);
            }
        }
    }

    private void projectRemove(Scanner scanner) {
        printCollection(IProjectService.findAll());
        System.out.print("Please input project ID for remove: ");
        String projectID = scanner.nextLine();
        Project project = IProjectService.remove(projectID);
        if (project != null) {
            System.out.println("Project " + project.getName() + " is remove!");
        } else {
            System.out.println("Project id: " + projectID + " does not found!");
        }

    }

    private void projectEdit(Scanner scanner) {
        printCollection(IProjectService.findAll());
        System.out.print("Please input project ID for edit: ");
        String projectID = scanner.nextLine();

        Project oldProject = (IProjectService.findOne(projectID));
        if (oldProject != null) {
            System.out.println("Input new project's name: ");
            String newName = scanner.nextLine();

            System.out.println("Input new project's description: ");
            String newDescription = scanner.nextLine();

            Project project = IProjectService.edit(projectID, newName, newDescription);

            if (project != null) {
                System.out.println("Project " + project.getName() + " is update!");
            } else {
                System.out.println("Project name or description can't be empty!");
            }
        } else {
            System.out.println("Project id: " + projectID + " does not found!");
        }
    }

    private void taskClear(Scanner scanner) {
        printCollection(IProjectService.findAll());
        System.out.println("Please input project id:");
        String projectID = scanner.nextLine();

        Project project = (IProjectService.findOne(projectID));
        if (project != null) {
            ITaskService.clear(projectID);
            System.out.println("All tasks for project id:" + projectID + " clear.");
        } else {
            System.out.println("Project id: " + projectID + " does not found!");
        }
    }

    private void taskCreate(Scanner scanner) {
        printCollection(IProjectService.findAll());
        System.out.println("Please input project id:");
        String projectID = scanner.nextLine();

        Project project = (IProjectService.findOne(projectID));
        if (project != null) {
            System.out.println("Please input task name:");
            String name = scanner.nextLine();
            System.out.println("Please input task description:");
            String description = scanner.nextLine();
            if (ITaskService.create(name, description, projectID)) {
                System.out.println("Task " + name + " is create!");
            } else {
                System.out.println("Task " + name + " does not create!");
                System.out.println("Task name or description can't be empty!");
            }
        } else {
            System.out.println("Project id: " + projectID + " does not found!");
        }
    }

    private void taskList(Scanner scanner) {
        printCollection(IProjectService.findAll());
        System.out.println("Please input project id:");
        String projectID = scanner.nextLine();
        Project project = IProjectService.findOne(projectID);
        if (project != null) {

            printCollection(ITaskService.findAllByProjectID(projectID));
            System.out.print("Please input ID task or press ENTER for print all task: ");
            String taskID = scanner.nextLine();

            if ("".equals(taskID)) {
                printCollection(ITaskService.findAllByProjectID(projectID));
            } else {
                if (ITaskService.findOne(taskID) != null) {
                    System.out.println(ITaskService.findOne(taskID));
                } else {
                    System.out.println("Task id: " + taskID + " does not found!");
                }
            }
        } else {
            System.out.println("Project id " + projectID + " does not found!");
        }
    }

    private void taskRemove(Scanner scanner) {
        printCollection(IProjectService.findAll());
        System.out.println("Please input project id:");
        String projectID = scanner.nextLine();
        Project project = IProjectService.findOne(projectID);
        if (project != null) {
            System.out.println("Please input ID task for remove:");
            String taskID = scanner.nextLine();
            Task task = ITaskService.remove(taskID);
            if (task != null) {
                System.out.println("Task id: " + task.getId() + " is remove!");
            } else {
                System.out.println("Task id: " + taskID + " is not found!");
            }
        } else {
            System.out.println("Project id " + projectID + " does not found!");
        }
    }

    private void taskEdit(Scanner scanner) {
        printCollection(IProjectService.findAll());
        System.out.println("Please input project id:");
        String projectID = scanner.nextLine();
        Project project = IProjectService.findOne(projectID);
        if (project != null) {
            System.out.println("Please input ID task for edit:");
            String taskID = scanner.nextLine();
            if (ITaskService.findOne(taskID) != null) {
                System.out.println("Input new task's name: ");
                String newName = scanner.nextLine();

                System.out.println("Input new task's description: ");
                String newDescription = scanner.nextLine();

                Task task = ITaskService.edit(taskID, newName, newDescription);
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

    private void help() {
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

    private void exit() {
        System.exit(0);
    }

    private <T> void printCollection(Collection<T> collection) {
        if (collection.size() != 0) {
            collection.forEach(System.out::println);
        } else {
            System.out.println("List is empty!");
        }
    }
}
