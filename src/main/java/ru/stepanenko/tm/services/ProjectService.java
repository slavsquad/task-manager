package ru.stepanenko.tm.services;

import ru.stepanenko.tm.api.services.IProjectService;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.util.StringValidator;
import java.util.Collection;

public class ProjectService implements IProjectService {

    private IProjectRepository IProjectRepository;

    public ProjectService(IProjectRepository IProjectRepository) {
        this.IProjectRepository = IProjectRepository;
    }

    @Override
    public void clear() {
        IProjectRepository.removeAll();
    }

    @Override
    public boolean create(String name, String description) {
        if (StringValidator.validate(name,description)) {
            IProjectRepository.persist(name,description);
            return true;
        }
        return false;
    }

    @Override
    public Collection<Project> findAll() {
        return IProjectRepository.findAll();
    }

    @Override
    public Project findOne(String id) {
        if (StringValidator.validate(id)){
                return IProjectRepository.findOne(id);
        }
        return null;
    }

    @Override
    public Project remove(String id) {
        if (StringValidator.validate(id)){
            return IProjectRepository.remove(id);
        }
        return null;
    }

    @Override
    public Project edit(String projectID, String newName, String newDescription) {
        if (StringValidator.validate(projectID,newName,newDescription)) {
            return IProjectRepository.merge(projectID,newName,newDescription);
        }
        return null;
    }
}
