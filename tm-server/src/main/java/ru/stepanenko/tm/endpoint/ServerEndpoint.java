package ru.stepanenko.tm.endpoint;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.stepanenko.tm.api.endpoint.IServerEndpoint;
import ru.stepanenko.tm.api.service.IPropertyService;

import javax.jws.WebMethod;
import javax.jws.WebService;

@Controller
@WebService
public class ServerEndpoint implements IServerEndpoint {

    @NotNull
    private final IPropertyService propertyService;

    @Autowired
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
