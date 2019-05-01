package ru.stepanenko.tm.repository;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.entity.Task;

import java.util.*;

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
    public Collection<Task> findAllByProjectId(@NotNull String id, @NotNull String userId) {
        @NotNull
        Collection<Task> tasksByProjectID = new ArrayList<>();
        for (Task task : findAll()) {
            if (id.equals(task.getProjectID()) && userId.equals(task.getUserID())) {
                tasksByProjectID.add(task);
            }
        }
        return tasksByProjectID;
    }

    @Override
    public Task findOne(@NotNull final String id, @NotNull final String userId) {
        @NotNull final Task task = findOne(id);
        @NotNull Collection<Task> tasks = findAllByUserId(userId);
        if (task == null) return null;
        if (tasks.isEmpty()) return null;
        if (!tasks.contains(task)) return null;
        return task;
    }

    @Override
    public Task remove(@NotNull String id, @NotNull String userId) {
        if (findOne(id, userId)==null) return null;
        return remove(id);
    }

    @Override
    public void removeAllByUserId(@NotNull final String id) {
        for (Task task : findAllByUserId(id)) {
            remove(task.getId());
        }
    }

    @Override
    public void removeAllByProjectId(@NotNull final String id, @NotNull final String userId) {
        for (Task task : findAllByProjectId(id, userId)) {
            remove(task.getId());
        }
    }

    @Override
    public Collection<Task> sortAllByUserId(@NotNull String id, Comparator<Task> comparator) {
        List<Task> tasks = new ArrayList<>(findAllByUserId(id));
        Collections.sort(tasks, comparator);
        return tasks;
    }

    @Override
    public Collection<Task> findAllByPartOfNameOrDescription(@NotNull String name, @NotNull String description, @NotNull String userId) {
        List<Task> findTasks = new ArrayList<>();
        for (Task task : findAllByUserId(userId)) {
            if (task.getName().contains(name) || task.getDescription().contains(description)) {
                findTasks.add(task);
            }
        }
        return findTasks;
    }
}
