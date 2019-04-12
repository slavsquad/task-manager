package ru.stepanenko.tm.services.project;

import ru.stepanenko.tm.repository.project.ProjectDao;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.services.util.StringValidator;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class ProjectCommandsImpl implements ProjectCommands {

    private ProjectDao projectDao;

    public ProjectCommandsImpl(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public boolean clear() {
        return projectDao.removeAll();
    }

    @Override
    public Project create(String name, String description) {
        if (StringValidator.validate(name) && StringValidator.validate(description)) {
            return projectDao.persist(new Project(name, description));
        } else {
            return null;
        }
    }

    @Override
    public Collection<Project> findAll() {
        return projectDao.findAll();
    }

    @Override
    public Project findOne(String id) {
        if (StringValidator.isNumeric(id) && StringValidator.validate(id)) {
            return projectDao.findOne(Integer.parseInt(id));
        } else {
            return null;
        }
    }

    @Override
    public Project remove(String id) {
        if (StringValidator.isNumeric(id) && StringValidator.validate(id)) {
            return projectDao.remove(Integer.parseInt(id));
        } else {
            return null;
        }
    }

    @Override
    public Project edit(Project oldProject, String newName, String newDescription) {
        if (StringValidator.validate(newName) && StringValidator.validate(newDescription)) {
            Project newProject = new Project(newName, newDescription);
            newProject.setUuid(oldProject.getUuid());
            newProject.setId(oldProject.getId());
            newProject.setStartDate(oldProject.getStartDate());
            newProject.setEndDate(oldProject.getEndDate());
            projectDao.merge(newProject);
            return newProject;
        } else {
            return null;
        }
    }
}
