package ru.stepanenko.tm.repository;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.entity.Task;

import java.util.ArrayList;
import java.util.Collection;

public final class TaskRepository extends AbstractRepository<Task> implements ITaskRepository {

    @Override
    public Collection<Task> findAllByUserId(@NotNull final String id) {
        @NotNull
        Collection<Task> userTasks = new ArrayList<>();
        for (Task task : findAll()) {
            if (id.equals(task.getUserID())) {
                userTasks.add(task);
            }
        }
        return userTasks;
    }

    @Override
    public Collection<Task> findAllByProjectId(@NotNull final String id) {
        @NotNull
        Collection<Task> tasksByProjectID = new ArrayList<>();
        for (Task task : findAll()) {
            if (id.equals(task.getProjectID())) {
                tasksByProjectID.add(task);
            }
        }
        return tasksByProjectID;
    }

    @Override
    public void removeAllByUserId(@NotNull final String id) {
        for (Task task : findAllByUserId(id)) {
            remove(task.getId());
        }
    }

    @Override
    public void removeAllByProjectId(@NotNull final String id) {
        for (Task task : findAllByProjectId(id)) {
            remove(task.getId());
        }
    }
}
