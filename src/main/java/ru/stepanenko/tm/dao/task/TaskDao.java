package ru.stepanenko.tm.dao.task;

import ru.stepanenko.tm.domain.Project;
import ru.stepanenko.tm.domain.Task;

import java.util.Map;
import java.util.UUID;

public interface TaskDao {
    Task getById(int id);
    Map<Integer,Task> getAll();
    boolean clear();
    Task remove(int id);
    boolean create(Task task);
    Map<Integer,Task> getByProjectUUID(UUID projectUUID);
}
