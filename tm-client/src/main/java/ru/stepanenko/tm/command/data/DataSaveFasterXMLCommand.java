package ru.stepanenko.tm.command.data;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.AuthenticationSecurityException_Exception;
import ru.stepanenko.tm.endpoint.Session;
import ru.stepanenko.tm.endpoint.UserEndpoint;

public class DataSaveFasterXMLCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "data-save-faster-xml";
    }

    @Override
    public String getDescription() {
        return "Save data to xml file provided by fasterXML.";
    }

    @Override
    public void execute() throws AuthenticationSecurityException_Exception {
        @NotNull final UserEndpoint userEndpoint = endpointServiceLocator.getUserEndpoint();
        @NotNull final Session currentSession = endpointServiceLocator.getSession();
        endpointServiceLocator.getSessionEndpoint().validateAdminSession(currentSession);
        userEndpoint.saveUserDataFasterXml(currentSession);
        System.out.println("Success all data save!");
    }
}
