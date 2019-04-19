package ru.stepanenko.tm.repository;

import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.entity.Task;

import java.util.ArrayList;
import java.util.Collection;

public final class TaskRepository extends AbstractRepository<Task> implements ITaskRepository {

    @Override
    public Collection<Task> findAllByUserId(final String id) {
        Collection<Task> userTasks = new ArrayList<>();
        for (Task task : findAll()) {
            if (id.equals(task.getUserID())) {
                userTasks.add(task);
            }
        }
        return userTasks;
    }

    @Override
    public Collection<Task> findAllByProjectId(final String id) {
        Collection<Task> tasks = findAll();
        Collection<Task> tasksByProjectID = new ArrayList<>();
        for (Task task : tasks) {
            if (id.equals(task.getProjectID())) {
                tasksByProjectID.add(task);
            }
        }
        return tasksByProjectID;
    }

    @Override
    public void removeAllByUserId(final String id) {
        for (Task task : findAllByUserId(id)) {
            remove(task.getId());
        }
    }

    @Override
    public void removeAllByProjectId(final String id) {
        for (Task task : findAllByUserId(id)) {
            remove(task.getId());
        }
    }
}
