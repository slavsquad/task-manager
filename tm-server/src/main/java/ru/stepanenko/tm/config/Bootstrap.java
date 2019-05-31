package ru.stepanenko.tm.config;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.stepanenko.tm.api.endpoint.*;
import ru.stepanenko.tm.api.service.IPropertyService;
import ru.stepanenko.tm.util.DataGenerator;

import javax.xml.ws.Endpoint;

@Component
@NoArgsConstructor
public class Bootstrap {

    @Autowired
    @NotNull
    private IProjectEndpoint projectEndpoint;

    @Autowired
    @NotNull
    private ITaskEndpoint taskEndpoint;

    @Autowired
    @NotNull
    private IUserEndpoint userEndpoint;

    @Autowired
    @NotNull
    private ISessionEndpoint sessionEndpoint;

    @Autowired
    @NotNull
    private IServerEndpoint serverEndpoint;

    @Autowired
    @NotNull
    private DataGenerator dataGenerator;

    @Autowired
    @NotNull
    private IPropertyService propertyService;

    public void init() {
        dataGenerator.generate();
        registryEndpoint();
    }

    private void registryEndpoint() {

        @NotNull final String URL = "http://"+propertyService.getServerHost();
        @NotNull final String PORT = propertyService.getServerPort();

        String wsdl = URL + ":" + PORT + "/" + projectEndpoint.getClass().getSimpleName() + "?wsdl";
        Endpoint.publish(wsdl, projectEndpoint);
        System.out.println(wsdl);

        wsdl = URL + ":" + PORT + "/" + taskEndpoint.getClass().getSimpleName() + "?wsdl";
        Endpoint.publish(wsdl, taskEndpoint);
        System.out.println(wsdl);

        wsdl = URL + ":" + PORT + "/" + userEndpoint.getClass().getSimpleName() + "?wsdl";
        Endpoint.publish(wsdl, userEndpoint);
        System.out.println(wsdl);

        wsdl = URL + ":" + PORT + "/" + sessionEndpoint.getClass().getSimpleName() + "?wsdl";
        Endpoint.publish(wsdl, sessionEndpoint);
        System.out.println(wsdl);

        wsdl = URL + ":" + PORT + "/" + serverEndpoint.getClass().getSimpleName() + "?wsdl";
        Endpoint.publish(wsdl, serverEndpoint);
        System.out.println(wsdl);
    }
}
