package ru.stepanenko.tm.config;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.endpoint.IProjectEndpoint;
import ru.stepanenko.tm.api.endpoint.ISessionEndpoint;
import ru.stepanenko.tm.api.endpoint.ITaskEndpoint;
import ru.stepanenko.tm.api.endpoint.IUserEndpoint;
import ru.stepanenko.tm.util.DataGenerator;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@NoArgsConstructor
public class Bootstrap {

    @Inject
    @NotNull
    IProjectEndpoint projectEndpoint;

    @Inject
    @NotNull
    ITaskEndpoint taskEndpoint;

    @Inject
    @NotNull
    IUserEndpoint userEndpoint;

    @Inject
    @NotNull
    ISessionEndpoint sessionEndpoint;

    @Inject
    @NotNull
    DataGenerator dataGenerator;


    public void init() {
        dataGenerator.generateUsers();
        dataGenerator.generateData();
        registryEndpoint();
    }

    private void registryEndpoint() {

        String wsdl = "http://localhost:8080/" + projectEndpoint.getClass().getSimpleName() + "?wsdl";
        Endpoint.publish(wsdl, projectEndpoint);
        System.out.println(wsdl);

        wsdl = "http://localhost:8080/" + taskEndpoint.getClass().getSimpleName() + "?wsdl";
        Endpoint.publish(wsdl, taskEndpoint);
        System.out.println(wsdl);

        wsdl = "http://localhost:8080/" + userEndpoint.getClass().getSimpleName() + "?wsdl";
        Endpoint.publish(wsdl, userEndpoint);
        System.out.println(wsdl);

        wsdl = "http://localhost:8080/" + sessionEndpoint.getClass().getSimpleName() + "?wsdl";
        Endpoint.publish(wsdl, sessionEndpoint);
        System.out.println(wsdl);
    }
}
