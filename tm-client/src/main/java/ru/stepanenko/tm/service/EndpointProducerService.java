package ru.stepanenko.tm.service;

import lombok.NoArgsConstructor;
import ru.stepanenko.tm.api.service.IEndpointProducerService;
import ru.stepanenko.tm.endpoint.*;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
@NoArgsConstructor
public class EndpointProducerService implements IEndpointProducerService {

    @Override
    @Produces
    public ProjectEndpoint getProjectEndpoint() {
        return new ProjectEndpointService().getProjectEndpointPort();
    }

    @Override
    @Produces
    public TaskEndpoint getTaskEndpoint() {
        return new TaskEndpointService().getTaskEndpointPort();
    }

    @Override
    @Produces
    public SessionEndpoint getSessionEndpoint() {
        return new SessionEndpointService().getSessionEndpointPort();
    }

    @Override
    @Produces
    public UserEndpoint getUserEndpoint() {
        return new UserEndpointService().getUserEndpointPort();
    }
}
