package ru.stepanenko.tm.command.data;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.AuthenticationSecurityException_Exception;
import ru.stepanenko.tm.endpoint.Session;
import ru.stepanenko.tm.endpoint.UserEndpoint;


public class DataLoadJaxBJSONCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "data-load-jaxb-json";
    }

    @Override
    public String getDescription() {
        return "Load data from json file provided by jax-b.";
    }

    @Override
    public void execute() throws AuthenticationSecurityException_Exception {
        @NotNull final UserEndpoint userEndpoint = endpointServiceLocator.getUserEndpoint();
        @NotNull final Session currentSession = endpointServiceLocator.getSession();
        endpointServiceLocator.getSessionEndpoint().validateSession(currentSession);
        userEndpoint.loadUserDataJaxbJSON(currentSession);
        System.out.println("Success all data load!");
    }
}
