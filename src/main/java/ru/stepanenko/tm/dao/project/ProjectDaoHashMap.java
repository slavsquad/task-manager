package ru.stepanenko.tm.dao.project;

import ru.stepanenko.tm.domain.Project;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectDaoHashMap implements ProjectDao {
    private Map<Integer, Project> projects = new HashMap<>();
    private static int idCount;

    @Override
    public Project getById(int id) {
        return projects.get(id);
    }

    @Override
    public Map<Integer,Project> getAll() {
        return projects;
    }

    @Override
    public boolean clear() {
        projects.clear();
        if (projects.size()==0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Project remove(int id) {
        return projects.remove(id);
    }

    @Override
    public boolean create(Project project) {
        if (project != null ){
            project.setId(idCount);
            project.setDateTime(LocalDateTime.now());
            projects.put(idCount++,project);
            return true;
        }else {
            return false;
        }
    }
}
