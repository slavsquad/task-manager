package ru.stepanenko.tm.command.data;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.Session;
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

        @NotNull final IUserService userService = serviceLocator.getUserService();
        userService.loadJaxbXml(new Session());
    }
}
