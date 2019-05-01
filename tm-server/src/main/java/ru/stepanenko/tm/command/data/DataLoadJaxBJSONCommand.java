package ru.stepanenko.tm.command.data;

import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.compiler.Property;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.endpoint.IUserEndpoint;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.UserEndpoint;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.entity.Session;
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.exception.ForbiddenActionException;
import ru.stepanenko.tm.exception.session.InvalidSessionException;
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
    public void execute() throws InvalidSessionException, ForbiddenActionException {
        IUserEndpoint userEndpoint = new UserEndpoint(serviceLocator.getUserService(),serviceLocator.getSessionService());
        userEndpoint.loadUserDataJaxbJSON(serviceLocator.getSession());
    }
}
