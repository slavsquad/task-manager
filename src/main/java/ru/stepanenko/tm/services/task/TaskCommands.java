package ru.stepanenko.tm.services.task;

import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.entity.Task;

import java.util.Collection;
import java.util.UUID;

public interface TaskCommands {
    boolean clear(UUID projectUUID);
    Task create(String name,String description,UUID projectUUID);
    Collection<Task> findAllByProjectUUID(UUID projectUUID);
    Task remove(String taskID);
    Task edit(Task oldTask, String newName, String newDescription);
    Task findOne(String taskID);
}
