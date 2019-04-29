package ru.stepanenko.tm.command.data;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.util.Domain;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataSaveSerializationCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "data-save";
    }

    @Override
    public String getDescription() {
        return "Save user date to file.";
    }

    @Override
    public void execute(){
        @NotNull final IProjectService projectService = serviceLocator.getProjectService();
        @NotNull final IUserService userService = serviceLocator.getUserService();
        @NotNull final ITaskService taskService = serviceLocator.getTaskService();

        Domain domain = new Domain(new ArrayList<>(projectService.findAll()), new ArrayList<>(taskService.findAll()), new ArrayList<>(userService.findAll()));
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.out"))){
            oos.writeObject(domain);
            oos.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
