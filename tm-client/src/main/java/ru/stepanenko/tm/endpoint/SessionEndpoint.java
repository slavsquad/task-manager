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
 * 2019-05-31T13:35:09.537+03:00
 * Generated source version: 3.2.7
 *
 */
@WebService(targetNamespace = "http://endpoint.tm.stepanenko.ru/", name = "SessionEndpoint")
@XmlSeeAlso({ObjectFactory.class})
public interface SessionEndpoint {

    @WebMethod
    @Action(input = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/validateAdminSessionRequest", output = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/validateAdminSessionResponse", fault = {@FaultAction(className = AuthenticationSecurityException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/validateAdminSession/Fault/AuthenticationSecurityException"), @FaultAction(className = DataValidateException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/validateAdminSession/Fault/DataValidateException")})
    @RequestWrapper(localName = "validateAdminSession", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.ValidateAdminSession")
    @ResponseWrapper(localName = "validateAdminSessionResponse", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.ValidateAdminSessionResponse")
    public void validateAdminSession(
        @WebParam(name = "session", targetNamespace = "")
        ru.stepanenko.tm.endpoint.SessionDTO session
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception;

    @WebMethod
    @Action(input = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/validateSessionRequest", output = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/validateSessionResponse", fault = {@FaultAction(className = AuthenticationSecurityException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/validateSession/Fault/AuthenticationSecurityException"), @FaultAction(className = DataValidateException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/validateSession/Fault/DataValidateException")})
    @RequestWrapper(localName = "validateSession", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.ValidateSession")
    @ResponseWrapper(localName = "validateSessionResponse", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.ValidateSessionResponse")
    public void validateSession(
        @WebParam(name = "session", targetNamespace = "")
        ru.stepanenko.tm.endpoint.SessionDTO session
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception;

    @WebMethod
    @Action(input = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/closeSessionRequest", output = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/closeSessionResponse", fault = {@FaultAction(className = AuthenticationSecurityException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/closeSession/Fault/AuthenticationSecurityException"), @FaultAction(className = DataValidateException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/closeSession/Fault/DataValidateException")})
    @RequestWrapper(localName = "closeSession", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.CloseSession")
    @ResponseWrapper(localName = "closeSessionResponse", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.CloseSessionResponse")
    public void closeSession(
        @WebParam(name = "session", targetNamespace = "")
        ru.stepanenko.tm.endpoint.SessionDTO session
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception;

    @WebMethod
    @Action(input = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/findOneSessionRequest", output = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/findOneSessionResponse", fault = {@FaultAction(className = AuthenticationSecurityException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/findOneSession/Fault/AuthenticationSecurityException"), @FaultAction(className = DataValidateException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/findOneSession/Fault/DataValidateException")})
    @RequestWrapper(localName = "findOneSession", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.FindOneSession")
    @ResponseWrapper(localName = "findOneSessionResponse", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.FindOneSessionResponse")
    @WebResult(name = "return", targetNamespace = "")
    public ru.stepanenko.tm.endpoint.SessionDTO findOneSession(
        @WebParam(name = "session", targetNamespace = "")
        ru.stepanenko.tm.endpoint.SessionDTO session,
        @WebParam(name = "id", targetNamespace = "")
        java.lang.String id
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception;

    @WebMethod
    @Action(input = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/openSessionRequest", output = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/openSessionResponse", fault = {@FaultAction(className = AuthenticationSecurityException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/openSession/Fault/AuthenticationSecurityException"), @FaultAction(className = DataValidateException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/SessionEndpoint/openSession/Fault/DataValidateException")})
    @RequestWrapper(localName = "openSession", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.OpenSession")
    @ResponseWrapper(localName = "openSessionResponse", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.OpenSessionResponse")
    @WebResult(name = "return", targetNamespace = "")
    public ru.stepanenko.tm.endpoint.SessionDTO openSession(
        @WebParam(name = "login", targetNamespace = "")
        java.lang.String login,
        @WebParam(name = "password", targetNamespace = "")
        java.lang.String password
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception;
}
