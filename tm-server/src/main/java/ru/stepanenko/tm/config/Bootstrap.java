package ru.stepanenko.tm.config;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.endpoint.*;
import ru.stepanenko.tm.api.service.IPropertyService;
import ru.stepanenko.tm.service.PropertyService;
import ru.stepanenko.tm.util.DataGenerator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@ApplicationScoped
@NoArgsConstructor
public class Bootstrap {

    @Inject
    @NotNull
    private IProjectEndpoint projectEndpoint;

    @Inject
    @NotNull
    private ITaskEndpoint taskEndpoint;

    @Inject
    @NotNull
    private IUserEndpoint userEndpoint;

    @Inject
    @NotNull
    private ISessionEndpoint sessionEndpoint;

    @Inject
    @NotNull
    private IServerEndpoint serverEndpoint;

    @Inject
    @NotNull
    private DataGenerator dataGenerator;

    @Inject
    @NotNull
    private IPropertyService propertyService;


    public void init() {
        dataGenerator.generateUsers();
        dataGenerator.generateData();
        registryEndpoint();
        //HazelcastInstance hz = Hazelcast.newHazelcastInstance();
    }

    private void registryEndpoint() {

        @NotNull final String URL = propertyService.getServerHost();
        @NotNull final String PORT = propertyService.getServerPort();
        System.out.println(System.getProperty("port")+"asdffffffffffffffffffffffffffffffffffffffffffffff");

        String wsdl = URL + ":" + PORT +"/"+ projectEndpoint.getClass().getSimpleName() + "?wsdl";
        Endpoint.publish(wsdl, projectEndpoint);
        System.out.println(wsdl);

        wsdl = URL + ":" + PORT +"/"+ taskEndpoint.getClass().getSimpleName() + "?wsdl";
        Endpoint.publish(wsdl, taskEndpoint);
        System.out.println(wsdl);

        wsdl = URL + ":" + PORT +"/"+ userEndpoint.getClass().getSimpleName() + "?wsdl";
        Endpoint.publish(wsdl, userEndpoint);
        System.out.println(wsdl);

        wsdl = URL + ":" + PORT +"/"+ sessionEndpoint.getClass().getSimpleName() + "?wsdl";
        Endpoint.publish(wsdl, sessionEndpoint);
        System.out.println(wsdl);

        wsdl = URL + ":" + PORT +"/"+ serverEndpoint.getClass().getSimpleName() + "?wsdl";
        Endpoint.publish(wsdl, serverEndpoint);
        System.out.println(wsdl);
    }
}
