package ru.stepanenko.tm.api.endpoint;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface IServerEndpoint {

    @WebMethod
    String getInfoHost();
    @WebMethod
    String getInfoPort();
}
