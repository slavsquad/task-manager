package ru.stepanenko.tm.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.model.entity.Project;

import java.util.*;

@Repository
public class ProjectRepository implements IProjectRepository {

    @NotNull
    private final Map<String, Project> projects;

    public ProjectRepository() {
        this.projects = new LinkedHashMap<>();
        generate();
    }

    @Override
    public Project findOne(
            @NotNull final String id) {
        return projects.get(id);
    }

    private void generate() {
        for (int i = 1; i <= 4; i++) {
            @NotNull final Project project = new Project("Project #"+i, "Description for project #"+i, new Date(), null, Status.PLANNED, "1");
            project.setId(String.valueOf(i));
            projects.put(project.getId(), project);
        }
    }

    @Override
    public Collection<Project> findAll() {
        return projects.values();
    }

    @Override
    public void removeAll() {
        projects.clear();
    }

    @Override
    public void remove(
            @NotNull final String id) {
        projects.remove(id);
    }

    @Override
    public void persist(
            @NotNull final Project project) {
        merge(project);
    }

    @Override
    public void merge(
            @NotNull final Project project) {
        projects.put(project.getId(), project);
    }

    @Override
    public Collection<Project> findAllByUserId(
            @NotNull final String id) {
        @NotNull final Collection<Project> findProjects = new ArrayList<>();
        for (@NotNull final Project project : findAll()) {
            if (project.getUserId().equals(id)) {
                findProjects.add(project);
            }
        }
        return findProjects;
    }

    @Override
    public Project findOneByUserId(
            @NotNull final String id,
            @NotNull final String userId) {
        for (@NotNull final Project project : findAllByUserId(userId)) {
            if (project.getId().equals(id)) {
                return project;
            }
        }
        return null;
    }

    @Override
    public void removeAllByUserId(
            @NotNull String id) {
        for (@NotNull final Project project : findAllByUserId(id)) {
            projects.remove(project.getId());
        }
    }

    @Override
    public Collection<Project> sortAllByUserId(
            @NotNull final String id,
            @NotNull final Comparator<Project> comparator) {
        @NotNull final List<Project> findProjects = new ArrayList<>(findAllByUserId(id));
        Collections.sort(findProjects, comparator);
        return findProjects;
    }

    @Override
    public Collection<Project> findAllByPartOfNameOrDescription(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final String userId) {
        @NotNull final List<Project> findProjects = new ArrayList<>();
        for (Project project : findAllByUserId(userId)) {
            if (project.getName().contains(name) || project.getDescription().contains(description)) {
                findProjects.add(project);
            }
        }
        return findProjects;
    }

}
