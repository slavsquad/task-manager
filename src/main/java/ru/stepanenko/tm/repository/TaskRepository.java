package ru.stepanenko.tm.repository;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.Task;

import java.util.*;

public enum TaskRepository implements ITaskRepository {

    INSTANCE;

    private final Map<String, Task> tasks;

    TaskRepository() {
        this.tasks = new HashMap<>();
        generate();
    }

    private void generate() {
        for (int i = 1; i<=4;i++){
            @NotNull final Task task1 = new Task("New Task 1 for project: "+i, "New Description", String.valueOf(i),"1");
            @NotNull final Task task2 = new Task("New Task 2 for project: "+i, "New Description", String.valueOf(i),"1");
            @NotNull final Task task3 = new Task("New Task 3 for project: "+i, "New Description", String.valueOf(i),"1");
            @NotNull final Task task4 = new Task("New Task 4 for project: "+i, "New Description", String.valueOf(i),"1");

            tasks.put(task1.getId(), task1);
            tasks.put(task2.getId(), task2);
            tasks.put(task3.getId(), task3);
            tasks.put(task4.getId(), task4);
        }
    }

    @Override
    public Task findOne(
            @NotNull final String id) {
        return tasks.get(id);
    }

    @Override
    public Collection<Task> findAll() {
        return tasks.values();
    }

    @Override
    public void removeAll() {
        tasks.clear();
    }

    @Override
    public void remove(
            @NotNull final String id) {
        tasks.remove(id);
    }

    @Override
    public void persist(
            @NotNull final Task task) {
        merge(task);
    }

    @Override
    public void merge(
            @NotNull final Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public Collection<Task> findAllByUserId(
            @NotNull final String id) {
        @NotNull final List<Task> findTasks = new ArrayList<>();
        for (@NotNull final Task task : findAll()) {
            if (task.getUserId().equals(id)) {
                findTasks.add(task);
            }
        }
        return findTasks;
    }

    @Override
    public Collection<Task> findAllByProjectAndUserId(
            @NotNull final String projectId,
            @NotNull final String userId) {
        @NotNull final List<Task> findTasks = new ArrayList<>();
        for (@NotNull final Task task : findAllByUserId(userId)) {
            if (task.getProjectId().equals(projectId)) {
                findTasks.add(task);
            }
        }
        return findTasks;
    }

    @Override
    public Task findOneByUserId(
            @NotNull final String id,
            @NotNull final String userId) {
        for (@NotNull final Task task : findAllByUserId(userId)) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }

    @Override
    public void removeAllByUserId(
            @NotNull final String id) {
        for (@NotNull final Task task : findAllByUserId(id)) {
            remove(task.getId());
        }

    }

    @Override
    public void removeAllByProjectAndUserId(
            @NotNull final String projectId,
            @NotNull final String userId) {
        for (@NotNull final Task task : findAllByProjectAndUserId(projectId, userId)) {
            remove(task.getId());
        }

    }

    @Override
    public Collection<Task> sortAllByUserId(
            @NotNull String id,
            @NotNull Comparator<Task> comparator) {
        @NotNull final List<Task> findTasks = new ArrayList<>(findAllByUserId(id));
        Collections.sort(findTasks, comparator);
        return findTasks;
    }

    @Override
    public Collection<Task> findAllByPartOfNameOrDescription(
            @NotNull String name,
            @NotNull String description,
            @NotNull String userId) {
        @NotNull final List<Task> findTasks = new ArrayList<>();
        for (Task task : findAllByUserId(userId)) {
            if (task.getName().contains(name) || task.getDescription().contains(description)) {
                findTasks.add(task);
            }
        }
        return findTasks;
    }
}
