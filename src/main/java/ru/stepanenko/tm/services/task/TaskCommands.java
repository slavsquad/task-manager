package ru.stepanenko.tm.services.task;

import ru.stepanenko.tm.domain.Task;

public interface TaskCommands {
    void clear();
    void create(Task task);
    void list(int projectID);
    void list(int projectID, int id);
    void remove(int id);
    void edit(int id);
}
