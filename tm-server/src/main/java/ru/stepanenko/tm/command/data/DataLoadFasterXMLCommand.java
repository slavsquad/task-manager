package ru.stepanenko.tm.command.data;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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

import java.io.File;
import java.io.IOException;

public class DataLoadFasterXMLCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "timeStamp-load-faster-xml";
    }

    @Override
    public String getDescription() {
        return "Load timeStamp from mxl file provided by fasterxml.";
    }

    @Override
    public void execute() throws InvalidSessionException, ForbiddenActionException {
        IUserEndpoint userEndpoint = new UserEndpoint(serviceLocator.getUserService(),serviceLocator.getSessionService());
        userEndpoint.loadUserDataFasterXml(serviceLocator.getSession());
    }
}
