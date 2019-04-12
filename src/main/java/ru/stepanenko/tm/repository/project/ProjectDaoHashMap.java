package ru.stepanenko.tm.repository.project;

import ru.stepanenko.tm.entity.Project;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class  ProjectDaoHashMap implements ProjectDao {
    private Map<Integer, Project> projects = new HashMap<>();
    private static int idCount = 0;

    @Override
    public Project findOne(int id) {
        return projects.get(id);
    }

    @Override
    public Collection<Project> findAll() {
        return projects.values();
    }

    @Override
    public boolean removeAll() {
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
    public Project persist(Project project) {

        project.setId(idCount);
        project.setStartDate(LocalDateTime.now());
        Project result = projects.put(idCount,project);
        idCount++;
        if (result==null){
            return project;
        }else {
            return null;
        }
    }

    @Override
    public Project merge(Project project) {
        return projects.put(project.getId(),project);
    }
}
