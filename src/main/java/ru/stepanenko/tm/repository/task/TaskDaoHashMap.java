package ru.stepanenko.tm.repository.task;

import ru.stepanenko.tm.entity.Task;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TaskDaoHashMap implements TaskDao {
    private Map<Integer, Task> tasks = new HashMap<>();
    private static int idCount = 0;

    @Override
    public Task findOne(int id) {
        return tasks.get(id);
    }


    @Override
    public Map<Integer, Task> findAll() {
        return tasks;
    }

    @Override
    public boolean removeAll() {
        tasks.clear();
        if (tasks.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Task remove(int id) {
        return tasks.remove(id);
    }

    @Override
    public Task persist(Task task) {

        task.setId(idCount);
        task.setStartDate(LocalDateTime.now());
        Task result = tasks.put(idCount, task);
        idCount++;
        if (result == null) {
            return task;
        } else {
            return null;
        }
    }

    @Override
    public Task merge(Task task) {
        return tasks.put(task.getId(), task);
    }
}
