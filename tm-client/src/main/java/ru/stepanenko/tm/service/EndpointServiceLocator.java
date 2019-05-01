package ru.stepanenko.tm.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.IEndpointServiceLocator;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.endpoint.*;

@Getter
@NoArgsConstructor
public class EndpointServiceLocator implements IEndpointServiceLocator {

    @NotNull
    private final ProjectEndpoint projectEndpoint = new ProjectEndpointService().getProjectEndpointPort();

    @NotNull
    private final TaskEndpoint taskEndpoint = new TaskEndpointService().getTaskEndpointPort();

    @NotNull
    private final UserEndpoint userEndpoint = new UserEndpointService().getUserEndpointPort();

    @NotNull
    private final SessionEndpoint sessionEndpoint = new SessionEndpointService().getSessionEndpointPort();

    @NotNull
    private final ITerminalService terminalService = new TerminalService();

    @Setter
    @Nullable
    private Session session;
}
