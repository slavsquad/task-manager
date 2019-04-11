package ru.stepanenko.tm.repository.task;

import ru.stepanenko.tm.entity.Task;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TaskDaoHashMap implements TaskDao {
    private Map<Integer, Task> tasks = new HashMap<>();
    private static int idCount;

    @Override
    public Task findOne(int id) {
        return tasks.get(id);
    }


    @Override
    public Map<Integer,Task> findAll() {
        return tasks;
    }

    @Override
    public boolean removeAll() {
        tasks.clear();
        if (tasks.size()==0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Task remove(int id) {
        return tasks.remove(id);
    }

    @Override
    public boolean persist(Task task) {
        if (task != null ){
            task.setId(idCount);
            task.setStartDate(LocalDateTime.now());
            tasks.put(idCount++,task);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Map<Integer, Task> getByProjectUUID(UUID projectUUID) {
        Map<Integer,Task> projectTasks = new HashMap<>();
        for (Integer id:tasks.keySet()){
            if (projectUUID==tasks.get(id).getProjectUUID()){
                projectTasks.put(id,tasks.get(id));
            }
        }
        return projectTasks;
    }

    @Override
    public boolean merge() {
        return false;
    }
}
