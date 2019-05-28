package ru.stepanenko.tm.endpoint;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.endpoint.IServerEndpoint;
import ru.stepanenko.tm.api.service.IPropertyService;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class ServerEndpoint implements IServerEndpoint {

    @NotNull private final IPropertyService propertyService;

    @Inject
    public ServerEndpoint(@NotNull final IPropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @Override
    @WebMethod
    public String getInfoHost() {
        return propertyService.getServerHost();
    }

    @Override
    @WebMethod
    public String getInfoPort() {
        return propertyService.getServerPort();
    }
}
