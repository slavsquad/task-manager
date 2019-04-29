package ru.stepanenko.tm.command.data;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.util.Domain;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class DataLoadJaxBXMLCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "timeStamp-load-jaxb-xml";
    }

    @Override
    public String getDescription() {
        return "Load timeStamp from xml file provided by jax-b.";
    }

    @Override
    public void execute(){
        @NotNull final IProjectService projectService = serviceLocator.getProjectService();
        @NotNull final IUserService userService = serviceLocator.getUserService();
        @NotNull final ITaskService taskService = serviceLocator.getTaskService();
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(Domain.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Domain domain = (Domain)unmarshaller.unmarshal( new File("timeStamp.xml") );
            projectService.recovery(domain.getProjects());
            taskService.recovery(domain.getTasks());
            userService.recovery(domain.getUsers());
            System.out.println("Success all timeStamp load!");
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
