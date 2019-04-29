package ru.stepanenko.tm.command.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.util.Domain;

import java.io.File;
import java.io.IOException;

public class DataLoadFasterJSONCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "timeStamp-load-faster-json";
    }

    @Override
    public String getDescription() {
        return "Load timeStamp from json file provided by fasterxml.";
    }

    @Override
    public void execute(){
        @NotNull final IProjectService projectService = serviceLocator.getProjectService();
        @NotNull final IUserService userService = serviceLocator.getUserService();
        @NotNull final ITaskService taskService = serviceLocator.getTaskService();
        ObjectMapper mapper = new ObjectMapper();
        try {
            Domain domain = mapper.readValue(new File("timeStamp.json"), Domain.class);
            projectService.recovery(domain.getProjects());
            taskService.recovery(domain.getTasks());
            userService.recovery(domain.getUsers());
            System.out.println("Success all timeStamp load!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
