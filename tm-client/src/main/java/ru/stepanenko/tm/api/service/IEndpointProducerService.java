package ru.stepanenko.tm.api.service;

import ru.stepanenko.tm.endpoint.*;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface IEndpointProducerService {

    ProjectEndpoint getProjectEndpoint();

    TaskEndpoint getTaskEndpoint();

    SessionEndpoint getSessionEndpoint();

    UserEndpoint getUserEndpoint();

    ServerEndpoint getServerEndpoint();
}
