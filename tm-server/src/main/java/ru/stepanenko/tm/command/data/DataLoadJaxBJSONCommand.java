package ru.stepanenko.tm.command.data;

import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.compiler.Property;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.util.Domain;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DataLoadJaxBJSONCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "timeStamp-load-jaxb-json";
    }

    @Override
    public String getDescription() {
        return "Load timeStamp from json file provided by jax-b.";
    }

    @Override
    public void execute() {
        @NotNull final IProjectService projectService = serviceLocator.getProjectService();
        @NotNull final IUserService userService = serviceLocator.getUserService();
        @NotNull final ITaskService taskService = serviceLocator.getTaskService();
        try {
            Map<String, Object> properties = new HashMap<String, Object>(3);
            properties.put(MarshallerProperties.MEDIA_TYPE, "application/json");
            properties.put(MarshallerProperties.JSON_INCLUDE_ROOT, Boolean.FALSE);
            properties.put(MarshallerProperties.JSON_WRAPPER_AS_ARRAY_NAME, Boolean.TRUE);
            JAXBContext context = JAXBContextFactory.createContext(new Class[]{Domain.class, Project.class, Task.class, User.class}, properties);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StreamSource json = new StreamSource("timeStamp.json");
            Domain domain = unmarshaller.unmarshal(json, Domain.class).getValue();
            projectService.recovery(domain.getProjects());
            taskService.recovery(domain.getTasks());
            userService.recovery(domain.getUsers());
            System.out.println("Success all timeStamp load!");
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
