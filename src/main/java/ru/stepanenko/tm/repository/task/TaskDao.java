package ru.stepanenko.tm.repository.task;

import ru.stepanenko.tm.entity.Task;

import java.util.Map;
import java.util.UUID;

public interface TaskDao {
    Task findOne(int id);
    Map<Integer,Task> findAll();
    boolean removeAll();
    Task remove(int id);
    boolean persist(Task task);
    Map<Integer,Task> getByProjectUUID(UUID projectUUID);
    boolean merge();
}
