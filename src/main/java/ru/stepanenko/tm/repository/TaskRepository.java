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
