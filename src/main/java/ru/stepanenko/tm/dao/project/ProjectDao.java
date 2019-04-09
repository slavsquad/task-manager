package ru.stepanenko.tm.dao.project;

import ru.stepanenko.tm.domain.Project;

import java.util.Map;

public interface ProjectDao {
    Project getById(int id);
    Map<Integer,Project> getAll();
    boolean clear();
    Project remove(int id);
    boolean create(Project project);

}
