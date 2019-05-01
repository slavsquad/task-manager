package ru.stepanenko.tm.command.data;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.ForbiddenActionException_Exception;
import ru.stepanenko.tm.endpoint.InvalidSessionException_Exception;
import ru.stepanenko.tm.endpoint.Session;
import ru.stepanenko.tm.endpoint.UserEndpoint;

public class DataSaveJaxBXMLCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "data-save-jaxb-xml";
    }

    @Override
    public String getDescription() {
        return "Data save to xml file provided by jax-b.";
    }

    @Override
    public void execute() throws InvalidSessionException_Exception, ForbiddenActionException_Exception {
        @NotNull
        final UserEndpoint userEndpoint = endpointServiceLocator.getUserEndpoint();
        @NotNull
        final Session currentSession = endpointServiceLocator.getSession();
        userEndpoint.saveUserDataJaxbXml(currentSession);
    }
}
