package ru.stepanenko.tm.services.task;

import ru.stepanenko.tm.dao.task.TaskDao;
import ru.stepanenko.tm.domain.Task;

import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class TaskCommandsImpl implements TaskCommands {
    private TaskDao taskDao;

    public TaskCommandsImpl(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public void clear() {
        if (taskDao.clear()){
            System.out.println("Task list is clear!");
        } else {
            System.out.println("Task list does not clear!");
        }
    }
    @Override
    public void create(Task task) {

        if (taskDao.create(task)){
            System.out.println("Task "+task.getName()+" is create!");
        }else {
            System.out.println("Task "+task.getName()+" does not create!");
        }
    }

    @Override
    public void list(UUID projectUUID) {
        Map<Integer,Task> projectTasks = taskDao.getByProjectUUID(projectUUID);
        for (Integer id:projectTasks.keySet()){
            System.out.println(projectTasks.get(id));
        }
    }

    @Override
    public void list(UUID projectUUID, int id) {
        Task task = taskDao.getByProjectUUID(projectUUID).get(id);
        if (task!=null){
            System.out.println(task);
        }else{
            System.out.println("Task id "+ id +"does not fond!");
        }
    }


    @Override
    public void remove(int id) {
        Task task = taskDao.remove(id);
        if (task!=null){
            System.out.println("Task "+task.getName()+" is remove!");
        }else {
            System.out.println("Task id: "+id+" does not found!");
        }
    }

    @Override
    public void edit(int id) {
        Task task = taskDao.getById(id);
        if (task!=null){
            System.out.println(task);
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input task name:");
            String name = scanner.nextLine();
            System.out.println("Input task description:");
            String description = scanner.nextLine();
            task.setName(name);
            task.setDescription(description);
        }else {
            System.out.println("Task id: "+id+" does not found!");
        }
    }
}
