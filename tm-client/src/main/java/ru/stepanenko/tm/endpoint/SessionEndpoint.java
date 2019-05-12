package ru.stepanenko.tm.endpoint;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.2.7
 * 2019-05-12T16:49:37.033+03:00
 * Generated source version: 3.2.7
 *
 */
@WebService(targetNamespace = "http://endpoint.tm.stepanenko.ru/", name = "SessionEndpoint")
@XmlSeeAlso({ObjectFactory.class})
public interface SessionEndpoint {

    @WebMethod
    @Action(input = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/validateAdminSessionRequest", output = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/validateAdminSessionResponse", fault = {@FaultAction(className = AuthenticationSecurityException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/validateAdminSession/Fault/AuthenticationSecurityException")})
    @RequestWrapper(localName = "validateAdminSession", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.ValidateAdminSession")
    @ResponseWrapper(localName = "validateAdminSessionResponse", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.ValidateAdminSessionResponse")
    public void validateAdminSession(
        @WebParam(name = "session", targetNamespace = "")
        ru.stepanenko.tm.endpoint.Session session
    ) throws AuthenticationSecurityException_Exception;

    @WebMethod
    @Action(input = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/validateSessionRequest", output = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/validateSessionResponse", fault = {@FaultAction(className = AuthenticationSecurityException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/validateSession/Fault/AuthenticationSecurityException")})
    @RequestWrapper(localName = "validateSession", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.ValidateSession")
    @ResponseWrapper(localName = "validateSessionResponse", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.ValidateSessionResponse")
    public void validateSession(
        @WebParam(name = "session", targetNamespace = "")
        ru.stepanenko.tm.endpoint.Session session
    ) throws AuthenticationSecurityException_Exception;

    @WebMethod
    @Action(input = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/closeSessionRequest", output = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/closeSessionResponse", fault = {@FaultAction(className = AuthenticationSecurityException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/closeSession/Fault/AuthenticationSecurityException")})
    @RequestWrapper(localName = "closeSession", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.CloseSession")
    @ResponseWrapper(localName = "closeSessionResponse", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.CloseSessionResponse")
    @WebResult(name = "return", targetNamespace = "")
    public ru.stepanenko.tm.endpoint.Session closeSession(
        @WebParam(name = "session", targetNamespace = "")
        ru.stepanenko.tm.endpoint.Session session
    ) throws AuthenticationSecurityException_Exception;

    @WebMethod
    @Action(input = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/openSessionRequest", output = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/openSessionResponse", fault = {@FaultAction(className = IOException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/openSession/Fault/IOException"), @FaultAction(className = AuthenticationSecurityException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/openSession/Fault/AuthenticationSecurityException")})
    @RequestWrapper(localName = "openSession", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.OpenSession")
    @ResponseWrapper(localName = "openSessionResponse", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.OpenSessionResponse")
    @WebResult(name = "return", targetNamespace = "")
    public ru.stepanenko.tm.endpoint.Session openSession(
        @WebParam(name = "login", targetNamespace = "")
        java.lang.String login,
        @WebParam(name = "password", targetNamespace = "")
        java.lang.String password
    ) throws IOException_Exception, AuthenticationSecurityException_Exception;
}
