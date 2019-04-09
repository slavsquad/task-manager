package ru.stepanenko.tm.services.project;

import ru.stepanenko.tm.domain.Project;

public interface ProjectCommands {
    void clear();
    void create(Project project);
    void list();
    void list(int id);
    void remove(int id);
    void edit(int id);
}
