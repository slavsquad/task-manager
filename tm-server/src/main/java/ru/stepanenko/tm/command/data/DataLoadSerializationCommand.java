package ru.stepanenko.tm.command.data;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.util.Domain;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class DataLoadSerializationCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "data-load";
    }

    @Override
    public String getDescription() {
        return "Load user date from file.";
    }

    @Override
    public void execute(){
        @NotNull final IProjectService projectService = serviceLocator.getProjectService();
        @NotNull final IUserService userService = serviceLocator.getUserService();
        @NotNull final ITaskService taskService = serviceLocator.getTaskService();
        @NotNull final ITerminalService terminalService = serviceLocator.getTerminalService();

        try(ObjectInputStream oin = new ObjectInputStream(new FileInputStream("data.out"))){
            Domain domain = (Domain) oin.readObject();
            projectService.recovery(domain.getProjects());
            taskService.recovery(domain.getTasks());
            userService.recovery(domain.getUsers());
            System.out.println("Success all data load!");
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
