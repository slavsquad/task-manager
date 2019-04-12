package ru.stepanenko.tm.repository.project;

import ru.stepanenko.tm.entity.Project;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ProjectDao {
    Project findOne(int id);
    Collection<Project> findAll();
    boolean removeAll();
    Project remove(int id);
    Project persist(Project project);
    Project merge(Project project);


}
