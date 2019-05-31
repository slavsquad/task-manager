package ru.stepanenko.tm.repository;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.repository.IProjectRepository;
import ru.stepanenko.tm.entity.Project;

import java.util.*;

public final class ProjectRepository extends AbstractRepository<Project> implements IProjectRepository {

    @Override
    public Collection<Project> findAllByUserId(@NotNull final String id) {
        @NotNull
        Collection<Project> userProjects = new ArrayList<>();
        for (Project project : findAll()) {
            if (id.equals(project.getUserID())) {
                userProjects.add(project);
            }
        }
        return userProjects;
    }

    @Override
    public void removeAllByUserID(@NotNull final String id) {
        for (Project project : findAllByUserId(id)) {
            remove(project.getId());
        }
    }

    @Override
    public Collection<Project> sortAllByUserId(@NotNull final String id, Comparator<Project> comparator) {
        List<Project> projects = new ArrayList<>(findAllByUserId(id));
        Collections.sort(projects, comparator);
        return projects;
    }

    @Override
    public Collection<Project> findAllByPartOfNameOrDescription(@NotNull String name, @NotNull String description, @NotNull String userId) {
        List<Project> findProjects = new ArrayList<>();
        for (Project project : findAllByUserId(userId)) {
            if (project.getName().contains(name) || project.getDescription().contains(description)) {
                findProjects.add(project);
            }
        }
        return findProjects;
    }
}
