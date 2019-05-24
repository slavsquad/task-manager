package ru.stepanenko.tm.api.service;

import ru.stepanenko.tm.endpoint.ProjectEndpoint;
import ru.stepanenko.tm.endpoint.SessionEndpoint;
import ru.stepanenko.tm.endpoint.TaskEndpoint;
import ru.stepanenko.tm.endpoint.UserEndpoint;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface IEndpointProducerService {
    ProjectEndpoint getProjectEndpoint();

    TaskEndpoint getTaskEndpoint();

    SessionEndpoint getSessionEndpoint();

    UserEndpoint getUserEndpoint();
}
