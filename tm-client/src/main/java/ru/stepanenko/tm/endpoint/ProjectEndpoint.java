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
 * 2019-05-27T16:17:46.353+03:00
 * Generated source version: 3.2.7
 *
 */
@WebService(targetNamespace = "http://endpoint.tm.stepanenko.ru/", name = "ProjectEndpoint")
@XmlSeeAlso({ObjectFactory.class})
public interface ProjectEndpoint {

    @WebMethod
    @Action(input = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/findAllProjectByUserIdRequest", output = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/findAllProjectByUserIdResponse", fault = {@FaultAction(className = AuthenticationSecurityException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/findAllProjectByUserId/Fault/AuthenticationSecurityException"), @FaultAction(className = DataValidateException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/findAllProjectByUserId/Fault/DataValidateException")})
    @RequestWrapper(localName = "findAllProjectByUserId", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.FindAllProjectByUserId")
    @ResponseWrapper(localName = "findAllProjectByUserIdResponse", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.FindAllProjectByUserIdResponse")
    @WebResult(name = "return", targetNamespace = "")
    public java.util.List<ru.stepanenko.tm.endpoint.ProjectDTO> findAllProjectByUserId(
        @WebParam(name = "session", targetNamespace = "")
        ru.stepanenko.tm.endpoint.SessionDTO session
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception;

    @WebMethod
    @Action(input = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/findAllProjectByPartOfNameOrDescriptionRequest", output = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/findAllProjectByPartOfNameOrDescriptionResponse", fault = {@FaultAction(className = AuthenticationSecurityException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/findAllProjectByPartOfNameOrDescription/Fault/AuthenticationSecurityException"), @FaultAction(className = DataValidateException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/findAllProjectByPartOfNameOrDescription/Fault/DataValidateException")})
    @RequestWrapper(localName = "findAllProjectByPartOfNameOrDescription", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.FindAllProjectByPartOfNameOrDescription")
    @ResponseWrapper(localName = "findAllProjectByPartOfNameOrDescriptionResponse", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.FindAllProjectByPartOfNameOrDescriptionResponse")
    @WebResult(name = "return", targetNamespace = "")
    public java.util.List<ru.stepanenko.tm.endpoint.ProjectDTO> findAllProjectByPartOfNameOrDescription(
        @WebParam(name = "session", targetNamespace = "")
        ru.stepanenko.tm.endpoint.SessionDTO session,
        @WebParam(name = "name", targetNamespace = "")
        java.lang.String name,
        @WebParam(name = "description", targetNamespace = "")
        java.lang.String description
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception;

    @WebMethod
    @Action(input = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/sortAllProjectByUserIdRequest", output = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/sortAllProjectByUserIdResponse", fault = {@FaultAction(className = AuthenticationSecurityException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/sortAllProjectByUserId/Fault/AuthenticationSecurityException"), @FaultAction(className = DataValidateException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/sortAllProjectByUserId/Fault/DataValidateException")})
    @RequestWrapper(localName = "sortAllProjectByUserId", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.SortAllProjectByUserId")
    @ResponseWrapper(localName = "sortAllProjectByUserIdResponse", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.SortAllProjectByUserIdResponse")
    @WebResult(name = "return", targetNamespace = "")
    public java.util.List<ru.stepanenko.tm.endpoint.ProjectDTO> sortAllProjectByUserId(
        @WebParam(name = "session", targetNamespace = "")
        ru.stepanenko.tm.endpoint.SessionDTO session,
        @WebParam(name = "parameter", targetNamespace = "")
        java.lang.String parameter
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception;

    @WebMethod
    @Action(input = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/createProjectRequest", output = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/createProjectResponse", fault = {@FaultAction(className = AuthenticationSecurityException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/createProject/Fault/AuthenticationSecurityException"), @FaultAction(className = DataValidateException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/createProject/Fault/DataValidateException")})
    @RequestWrapper(localName = "createProject", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.CreateProject")
    @ResponseWrapper(localName = "createProjectResponse", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.CreateProjectResponse")
    public void createProject(
        @WebParam(name = "session", targetNamespace = "")
        ru.stepanenko.tm.endpoint.SessionDTO session,
        @WebParam(name = "project", targetNamespace = "")
        ru.stepanenko.tm.endpoint.ProjectDTO project
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception;

    @WebMethod
    @Action(input = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/removeProjectRequest", output = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/removeProjectResponse", fault = {@FaultAction(className = AuthenticationSecurityException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/removeProject/Fault/AuthenticationSecurityException"), @FaultAction(className = DataValidateException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/removeProject/Fault/DataValidateException")})
    @RequestWrapper(localName = "removeProject", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.RemoveProject")
    @ResponseWrapper(localName = "removeProjectResponse", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.RemoveProjectResponse")
    public void removeProject(
        @WebParam(name = "session", targetNamespace = "")
        ru.stepanenko.tm.endpoint.SessionDTO session,
        @WebParam(name = "id", targetNamespace = "")
        java.lang.String id
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception;

    @WebMethod
    @Action(input = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/findOneProjectRequest", output = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/findOneProjectResponse", fault = {@FaultAction(className = AuthenticationSecurityException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/findOneProject/Fault/AuthenticationSecurityException"), @FaultAction(className = DataValidateException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/findOneProject/Fault/DataValidateException")})
    @RequestWrapper(localName = "findOneProject", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.FindOneProject")
    @ResponseWrapper(localName = "findOneProjectResponse", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.FindOneProjectResponse")
    @WebResult(name = "return", targetNamespace = "")
    public ru.stepanenko.tm.endpoint.ProjectDTO findOneProject(
        @WebParam(name = "session", targetNamespace = "")
        ru.stepanenko.tm.endpoint.SessionDTO session,
        @WebParam(name = "id", targetNamespace = "")
        java.lang.String id
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception;

    @WebMethod
    @Action(input = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/removeAllProjectByUserIdRequest", output = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/removeAllProjectByUserIdResponse", fault = {@FaultAction(className = AuthenticationSecurityException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/removeAllProjectByUserId/Fault/AuthenticationSecurityException"), @FaultAction(className = DataValidateException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/removeAllProjectByUserId/Fault/DataValidateException")})
    @RequestWrapper(localName = "removeAllProjectByUserId", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.RemoveAllProjectByUserId")
    @ResponseWrapper(localName = "removeAllProjectByUserIdResponse", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.RemoveAllProjectByUserIdResponse")
    public void removeAllProjectByUserId(
        @WebParam(name = "session", targetNamespace = "")
        ru.stepanenko.tm.endpoint.SessionDTO session
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception;

    @WebMethod
    @Action(input = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/editProjectRequest", output = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/editProjectResponse", fault = {@FaultAction(className = AuthenticationSecurityException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/editProject/Fault/AuthenticationSecurityException"), @FaultAction(className = DataValidateException_Exception.class, value = "http://endpoint.tm.stepanenko.ru/ProjectEndpoint/editProject/Fault/DataValidateException")})
    @RequestWrapper(localName = "editProject", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.EditProject")
    @ResponseWrapper(localName = "editProjectResponse", targetNamespace = "http://endpoint.tm.stepanenko.ru/", className = "ru.stepanenko.tm.endpoint.EditProjectResponse")
    public void editProject(
        @WebParam(name = "session", targetNamespace = "")
        ru.stepanenko.tm.endpoint.SessionDTO session,
        @WebParam(name = "project", targetNamespace = "")
        ru.stepanenko.tm.endpoint.ProjectDTO project
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception;
}
