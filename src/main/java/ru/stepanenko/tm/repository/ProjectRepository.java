package ru.stepanenko.tm.repository;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.entity.Project;

import java.util.*;

public final class ProjectRepository extends AbstractRepository<Project> implements IProjectRepository {

    @Override
    public Collection<Project> findAllByUserID(@NotNull final String id) {
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
        for (Project project : findAllByUserID(id)) {
            remove(project.getId());
        }
    }

    @Override
    public Collection<Project> sortAllByUserId(@NotNull String id, Comparator<Project> comparator) {
        List<Project> projects = new ArrayList<>(findAllByUserID(id));
        Collections.sort(projects,comparator);
        return projects;
    }
}
