package ru.stepanenko.tm.dao.task;

import ru.stepanenko.tm.domain.Task;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TaskDaoHashMap implements TaskDao {
    private Map<Integer, Task> tasks = new HashMap<>();
    private static int idCount;

    @Override
    public Task getById(int id) {
        return tasks.get(id);
    }


    @Override
    public Map<Integer,Task> getAll() {
        return tasks;
    }

    @Override
    public boolean clear() {
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
    public boolean create(Task task) {
        if (task != null ){
            task.setId(idCount);
            task.setDateTime(LocalDateTime.now());
            tasks.put(idCount++,task);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Map<Integer, Task> getByProjectID(int projectID) {
        Map<Integer,Task> projectTasks = new HashMap<>();
        for (Integer id:tasks.keySet()){
            if (projectID==tasks.get(id).getProjectID()){
                projectTasks.put(id,tasks.get(id));
            }
        }
        return projectTasks;
    }
}
