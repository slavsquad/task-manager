package ru.stepanenko.tm.services.task;

import ru.stepanenko.tm.repository.task.TaskDao;
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.services.util.StringValidator;

import java.util.*;

public class TaskCommandsImpl implements TaskCommands {
    private TaskDao taskDao;

    public TaskCommandsImpl(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public boolean clear(UUID projectUUID) {

        Collection<Task> removalTasks = findAllByProjectUUID(projectUUID);
        if (removalTasks.size() != 0) {
            for (Task task : removalTasks) {
                taskDao.remove(task.getId());
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Task create(String name, String description, UUID projectUUID) {

        if (StringValidator.validate(name) && StringValidator.validate(description)) {
            return taskDao.persist(new Task(name, description, projectUUID));
        } else {
            return null;
        }
    }

    @Override
    public Collection<Task> findAllByProjectUUID(UUID projectUUID) {
        Map<Integer, Task> tasks = taskDao.findAll();
        List<Task> taskList = new ArrayList<>();

        for (Integer id : tasks.keySet()) {
            Task currentTask = tasks.get(id);
            if (projectUUID == currentTask.getProjectUUID()) {
                taskList.add(currentTask);
            }
        }

        return taskList;
    }

    @Override
    public Task remove(String taskID) {
        if (StringValidator.isNumeric(taskID)) {
            return taskDao.remove(Integer.parseInt(taskID));
        } else {
            return null;
        }
    }

    @Override
    public Task edit(Task oldTask, String newName, String newDescription) {

        if (StringValidator.validate(newName) && StringValidator.validate(newDescription)) {
            Task newTask = new Task(newName,newDescription, oldTask.getProjectUUID());
            newTask.setId(oldTask.getId());
            newTask.setStartDate(oldTask.getStartDate());
            newTask.setEndDate(oldTask.getEndDate());
            return taskDao.merge(newTask);
        } else {
            return null;
        }
    }

    @Override
    public Task findOne(String taskID) {
        if (StringValidator.validate(taskID) && StringValidator.isNumeric(taskID)) {
            return taskDao.findOne(Integer.parseInt(taskID));
        } else {
            return null;
        }
    }
}
