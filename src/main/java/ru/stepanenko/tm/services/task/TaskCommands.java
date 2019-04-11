package ru.stepanenko.tm.services.task;

import ru.stepanenko.tm.domain.Task;

import java.util.UUID;

public interface TaskCommands {
    void clear();
    void create(Task task);
    void list(UUID projectUUID);
    void list(UUID projectUUID, int id);
    void remove(int id);
    void edit(int id);
}
