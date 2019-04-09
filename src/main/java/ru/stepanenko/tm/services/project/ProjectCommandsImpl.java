package ru.stepanenko.tm.services.project;

import ru.stepanenko.tm.dao.project.ProjectDao;
import ru.stepanenko.tm.domain.Project;

import java.util.Scanner;

public class ProjectCommandsImpl implements ProjectCommands {

    private ProjectDao projectDao;

    public ProjectCommandsImpl(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public void clear() {

        if (projectDao.clear()){
            System.out.println("Project list is clear!");
        } else {
            System.out.println("Project list does not clear!");
        }
    }

    @Override
    public void create(Project project) {

        if (projectDao.create(project)){
            System.out.println("Project "+project.getName()+" is create!");
        }else {
            System.out.println("Project "+project.getName()+" does not create!");
        }
    }

    @Override
    public void list() {
        if (projectDao.getAll().size()!=0) {
            for (Integer id : projectDao.getAll().keySet()) {
                System.out.println(projectDao.getAll().get(id));
            }
        }else{
            System.out.println("Projects lists is empty!");
        }
    }

    @Override
    public void list(int id) {
        Project project = projectDao.getById(id);
        if (project==null){
            System.out.println("Project with id = "+id+" does not exist!");
        }else{
            System.out.println(project);
        }
    }

    @Override
    public void remove(int id) {
        Project project = projectDao.remove(id);
        if (project!=null){
            System.out.println("Project "+project.getName()+" is remove!");
        }else {
            System.out.println("Project id: "+id+" does not found!");
        }
    }

    @Override
    public void edit(int id) {
        Project project = projectDao.getById(id);
        if (project!=null){
            System.out.println(project);
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input project name:");
            String name = scanner.nextLine();
            System.out.println("Input project description:");
            String description = scanner.nextLine();
            project.setName(name);
            project.setDescription(description);
        }
    }
}
