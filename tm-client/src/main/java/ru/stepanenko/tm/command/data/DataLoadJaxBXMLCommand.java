package ru.stepanenko.tm.command.data;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.AuthenticationSecurityException_Exception;
import ru.stepanenko.tm.endpoint.InputDataValidateException_Exception;
import ru.stepanenko.tm.endpoint.Session;
import ru.stepanenko.tm.endpoint.UserEndpoint;

public class DataLoadJaxBXMLCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "data-load-jaxb-xml";
    }

    @Override
    public String getDescription() {
        return "Load data from xml file provided by jax-b.";
    }

    @Override
    public void execute() throws AuthenticationSecurityException_Exception, InputDataValidateException_Exception {
        @NotNull final UserEndpoint userEndpoint = endpointServiceLocator.getUserEndpoint();
        @NotNull final Session currentSession = endpointServiceLocator.getSession();
        endpointServiceLocator.getSessionEndpoint().validateAdminSession(currentSession);
        userEndpoint.loadUserDataJaxbXml(currentSession);
        System.out.println("Success all data load!");
    }
}
