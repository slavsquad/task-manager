package ru.stepanenko.tm.command.data;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.endpoint.IUserEndpoint;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.UserEndpoint;
import ru.stepanenko.tm.entity.Session;
import ru.stepanenko.tm.exception.ForbiddenActionException;
import ru.stepanenko.tm.exception.session.InvalidSessionException;
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
    public void execute() throws InvalidSessionException, ForbiddenActionException {
        IUserEndpoint userEndpoint = new UserEndpoint(serviceLocator.getUserService(), serviceLocator.getSessionService());
        userEndpoint.loadUserDataJaxbXml(serviceLocator.getSession());
    }
}
