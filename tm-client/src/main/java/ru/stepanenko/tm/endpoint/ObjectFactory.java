
package ru.stepanenko.tm.endpoint;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.stepanenko.tm.endpoint package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AuthenticationSecurityException_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "AuthenticationSecurityException");
    private final static QName _InputDataValidateException_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "InputDataValidateException");
    private final static QName _ChangeUserPassword_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "changeUserPassword");
    private final static QName _ChangeUserPasswordResponse_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "changeUserPasswordResponse");
    private final static QName _CreateUser_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "createUser");
    private final static QName _CreateUserResponse_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "createUserResponse");
    private final static QName _EditUserProfile_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "editUserProfile");
    private final static QName _EditUserProfileResponse_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "editUserProfileResponse");
    private final static QName _FindAllUser_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "findAllUser");
    private final static QName _FindAllUserResponse_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "findAllUserResponse");
    private final static QName _FindUserByLogin_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "findUserByLogin");
    private final static QName _FindUserByLoginResponse_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "findUserByLoginResponse");
    private final static QName _GetUserBySession_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "getUserBySession");
    private final static QName _GetUserBySessionResponse_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "getUserBySessionResponse");
    private final static QName _LoadUserData_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "loadUserData");
    private final static QName _LoadUserDataFasterJSON_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "loadUserDataFasterJSON");
    private final static QName _LoadUserDataFasterJSONResponse_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "loadUserDataFasterJSONResponse");
    private final static QName _LoadUserDataFasterXml_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "loadUserDataFasterXml");
    private final static QName _LoadUserDataFasterXmlResponse_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "loadUserDataFasterXmlResponse");
    private final static QName _LoadUserDataJaxbJSON_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "loadUserDataJaxbJSON");
    private final static QName _LoadUserDataJaxbJSONResponse_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "loadUserDataJaxbJSONResponse");
    private final static QName _LoadUserDataJaxbXml_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "loadUserDataJaxbXml");
    private final static QName _LoadUserDataJaxbXmlResponse_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "loadUserDataJaxbXmlResponse");
    private final static QName _LoadUserDataResponse_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "loadUserDataResponse");
    private final static QName _SaveUserData_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "saveUserData");
    private final static QName _SaveUserDataFasterJSON_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "saveUserDataFasterJSON");
    private final static QName _SaveUserDataFasterJSONResponse_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "saveUserDataFasterJSONResponse");
    private final static QName _SaveUserDataFasterXml_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "saveUserDataFasterXml");
    private final static QName _SaveUserDataFasterXmlResponse_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "saveUserDataFasterXmlResponse");
    private final static QName _SaveUserDataJaxbJSON_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "saveUserDataJaxbJSON");
    private final static QName _SaveUserDataJaxbJSONResponse_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "saveUserDataJaxbJSONResponse");
    private final static QName _SaveUserDataJaxbXml_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "saveUserDataJaxbXml");
    private final static QName _SaveUserDataJaxbXmlResponse_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "saveUserDataJaxbXmlResponse");
    private final static QName _SaveUserDataResponse_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "saveUserDataResponse");
    private final static QName _User_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "user");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.stepanenko.tm.endpoint
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AuthenticationSecurityException }
     * 
     */
    public AuthenticationSecurityException createAuthenticationSecurityException() {
        return new AuthenticationSecurityException();
    }

    /**
     * Create an instance of {@link InputDataValidateException }
     * 
     */
    public InputDataValidateException createInputDataValidateException() {
        return new InputDataValidateException();
    }

    /**
     * Create an instance of {@link ChangeUserPassword }
     * 
     */
    public ChangeUserPassword createChangeUserPassword() {
        return new ChangeUserPassword();
    }

    /**
     * Create an instance of {@link ChangeUserPasswordResponse }
     * 
     */
    public ChangeUserPasswordResponse createChangeUserPasswordResponse() {
        return new ChangeUserPasswordResponse();
    }

    /**
     * Create an instance of {@link CreateUser }
     * 
     */
    public CreateUser createCreateUser() {
        return new CreateUser();
    }

    /**
     * Create an instance of {@link CreateUserResponse }
     * 
     */
    public CreateUserResponse createCreateUserResponse() {
        return new CreateUserResponse();
    }

    /**
     * Create an instance of {@link EditUserProfile }
     * 
     */
    public EditUserProfile createEditUserProfile() {
        return new EditUserProfile();
    }

    /**
     * Create an instance of {@link EditUserProfileResponse }
     * 
     */
    public EditUserProfileResponse createEditUserProfileResponse() {
        return new EditUserProfileResponse();
    }

    /**
     * Create an instance of {@link FindAllUser }
     * 
     */
    public FindAllUser createFindAllUser() {
        return new FindAllUser();
    }

    /**
     * Create an instance of {@link FindAllUserResponse }
     * 
     */
    public FindAllUserResponse createFindAllUserResponse() {
        return new FindAllUserResponse();
    }

    /**
     * Create an instance of {@link FindUserByLogin }
     * 
     */
    public FindUserByLogin createFindUserByLogin() {
        return new FindUserByLogin();
    }

    /**
     * Create an instance of {@link FindUserByLoginResponse }
     * 
     */
    public FindUserByLoginResponse createFindUserByLoginResponse() {
        return new FindUserByLoginResponse();
    }

    /**
     * Create an instance of {@link GetUserBySession }
     * 
     */
    public GetUserBySession createGetUserBySession() {
        return new GetUserBySession();
    }

    /**
     * Create an instance of {@link GetUserBySessionResponse }
     * 
     */
    public GetUserBySessionResponse createGetUserBySessionResponse() {
        return new GetUserBySessionResponse();
    }

    /**
     * Create an instance of {@link LoadUserData }
     * 
     */
    public LoadUserData createLoadUserData() {
        return new LoadUserData();
    }

    /**
     * Create an instance of {@link LoadUserDataFasterJSON }
     * 
     */
    public LoadUserDataFasterJSON createLoadUserDataFasterJSON() {
        return new LoadUserDataFasterJSON();
    }

    /**
     * Create an instance of {@link LoadUserDataFasterJSONResponse }
     * 
     */
    public LoadUserDataFasterJSONResponse createLoadUserDataFasterJSONResponse() {
        return new LoadUserDataFasterJSONResponse();
    }

    /**
     * Create an instance of {@link LoadUserDataFasterXml }
     * 
     */
    public LoadUserDataFasterXml createLoadUserDataFasterXml() {
        return new LoadUserDataFasterXml();
    }

    /**
     * Create an instance of {@link LoadUserDataFasterXmlResponse }
     * 
     */
    public LoadUserDataFasterXmlResponse createLoadUserDataFasterXmlResponse() {
        return new LoadUserDataFasterXmlResponse();
    }

    /**
     * Create an instance of {@link LoadUserDataJaxbJSON }
     * 
     */
    public LoadUserDataJaxbJSON createLoadUserDataJaxbJSON() {
        return new LoadUserDataJaxbJSON();
    }

    /**
     * Create an instance of {@link LoadUserDataJaxbJSONResponse }
     * 
     */
    public LoadUserDataJaxbJSONResponse createLoadUserDataJaxbJSONResponse() {
        return new LoadUserDataJaxbJSONResponse();
    }

    /**
     * Create an instance of {@link LoadUserDataJaxbXml }
     * 
     */
    public LoadUserDataJaxbXml createLoadUserDataJaxbXml() {
        return new LoadUserDataJaxbXml();
    }

    /**
     * Create an instance of {@link LoadUserDataJaxbXmlResponse }
     * 
     */
    public LoadUserDataJaxbXmlResponse createLoadUserDataJaxbXmlResponse() {
        return new LoadUserDataJaxbXmlResponse();
    }

    /**
     * Create an instance of {@link LoadUserDataResponse }
     * 
     */
    public LoadUserDataResponse createLoadUserDataResponse() {
        return new LoadUserDataResponse();
    }

    /**
     * Create an instance of {@link SaveUserData }
     * 
     */
    public SaveUserData createSaveUserData() {
        return new SaveUserData();
    }

    /**
     * Create an instance of {@link SaveUserDataFasterJSON }
     * 
     */
    public SaveUserDataFasterJSON createSaveUserDataFasterJSON() {
        return new SaveUserDataFasterJSON();
    }

    /**
     * Create an instance of {@link SaveUserDataFasterJSONResponse }
     * 
     */
    public SaveUserDataFasterJSONResponse createSaveUserDataFasterJSONResponse() {
        return new SaveUserDataFasterJSONResponse();
    }

    /**
     * Create an instance of {@link SaveUserDataFasterXml }
     * 
     */
    public SaveUserDataFasterXml createSaveUserDataFasterXml() {
        return new SaveUserDataFasterXml();
    }

    /**
     * Create an instance of {@link SaveUserDataFasterXmlResponse }
     * 
     */
    public SaveUserDataFasterXmlResponse createSaveUserDataFasterXmlResponse() {
        return new SaveUserDataFasterXmlResponse();
    }

    /**
     * Create an instance of {@link SaveUserDataJaxbJSON }
     * 
     */
    public SaveUserDataJaxbJSON createSaveUserDataJaxbJSON() {
        return new SaveUserDataJaxbJSON();
    }

    /**
     * Create an instance of {@link SaveUserDataJaxbJSONResponse }
     * 
     */
    public SaveUserDataJaxbJSONResponse createSaveUserDataJaxbJSONResponse() {
        return new SaveUserDataJaxbJSONResponse();
    }

    /**
     * Create an instance of {@link SaveUserDataJaxbXml }
     * 
     */
    public SaveUserDataJaxbXml createSaveUserDataJaxbXml() {
        return new SaveUserDataJaxbXml();
    }

    /**
     * Create an instance of {@link SaveUserDataJaxbXmlResponse }
     * 
     */
    public SaveUserDataJaxbXmlResponse createSaveUserDataJaxbXmlResponse() {
        return new SaveUserDataJaxbXmlResponse();
    }

    /**
     * Create an instance of {@link SaveUserDataResponse }
     * 
     */
    public SaveUserDataResponse createSaveUserDataResponse() {
        return new SaveUserDataResponse();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link Session }
     * 
     */
    public Session createSession() {
        return new Session();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthenticationSecurityException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "AuthenticationSecurityException")
    public JAXBElement<AuthenticationSecurityException> createAuthenticationSecurityException(AuthenticationSecurityException value) {
        return new JAXBElement<AuthenticationSecurityException>(_AuthenticationSecurityException_QNAME, AuthenticationSecurityException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InputDataValidateException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "InputDataValidateException")
    public JAXBElement<InputDataValidateException> createInputDataValidateException(InputDataValidateException value) {
        return new JAXBElement<InputDataValidateException>(_InputDataValidateException_QNAME, InputDataValidateException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangeUserPassword }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "changeUserPassword")
    public JAXBElement<ChangeUserPassword> createChangeUserPassword(ChangeUserPassword value) {
        return new JAXBElement<ChangeUserPassword>(_ChangeUserPassword_QNAME, ChangeUserPassword.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangeUserPasswordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "changeUserPasswordResponse")
    public JAXBElement<ChangeUserPasswordResponse> createChangeUserPasswordResponse(ChangeUserPasswordResponse value) {
        return new JAXBElement<ChangeUserPasswordResponse>(_ChangeUserPasswordResponse_QNAME, ChangeUserPasswordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "createUser")
    public JAXBElement<CreateUser> createCreateUser(CreateUser value) {
        return new JAXBElement<CreateUser>(_CreateUser_QNAME, CreateUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "createUserResponse")
    public JAXBElement<CreateUserResponse> createCreateUserResponse(CreateUserResponse value) {
        return new JAXBElement<CreateUserResponse>(_CreateUserResponse_QNAME, CreateUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EditUserProfile }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "editUserProfile")
    public JAXBElement<EditUserProfile> createEditUserProfile(EditUserProfile value) {
        return new JAXBElement<EditUserProfile>(_EditUserProfile_QNAME, EditUserProfile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EditUserProfileResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "editUserProfileResponse")
    public JAXBElement<EditUserProfileResponse> createEditUserProfileResponse(EditUserProfileResponse value) {
        return new JAXBElement<EditUserProfileResponse>(_EditUserProfileResponse_QNAME, EditUserProfileResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "findAllUser")
    public JAXBElement<FindAllUser> createFindAllUser(FindAllUser value) {
        return new JAXBElement<FindAllUser>(_FindAllUser_QNAME, FindAllUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "findAllUserResponse")
    public JAXBElement<FindAllUserResponse> createFindAllUserResponse(FindAllUserResponse value) {
        return new JAXBElement<FindAllUserResponse>(_FindAllUserResponse_QNAME, FindAllUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindUserByLogin }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "findUserByLogin")
    public JAXBElement<FindUserByLogin> createFindUserByLogin(FindUserByLogin value) {
        return new JAXBElement<FindUserByLogin>(_FindUserByLogin_QNAME, FindUserByLogin.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindUserByLoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "findUserByLoginResponse")
    public JAXBElement<FindUserByLoginResponse> createFindUserByLoginResponse(FindUserByLoginResponse value) {
        return new JAXBElement<FindUserByLoginResponse>(_FindUserByLoginResponse_QNAME, FindUserByLoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserBySession }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "getUserBySession")
    public JAXBElement<GetUserBySession> createGetUserBySession(GetUserBySession value) {
        return new JAXBElement<GetUserBySession>(_GetUserBySession_QNAME, GetUserBySession.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserBySessionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "getUserBySessionResponse")
    public JAXBElement<GetUserBySessionResponse> createGetUserBySessionResponse(GetUserBySessionResponse value) {
        return new JAXBElement<GetUserBySessionResponse>(_GetUserBySessionResponse_QNAME, GetUserBySessionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadUserData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "loadUserData")
    public JAXBElement<LoadUserData> createLoadUserData(LoadUserData value) {
        return new JAXBElement<LoadUserData>(_LoadUserData_QNAME, LoadUserData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadUserDataFasterJSON }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "loadUserDataFasterJSON")
    public JAXBElement<LoadUserDataFasterJSON> createLoadUserDataFasterJSON(LoadUserDataFasterJSON value) {
        return new JAXBElement<LoadUserDataFasterJSON>(_LoadUserDataFasterJSON_QNAME, LoadUserDataFasterJSON.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadUserDataFasterJSONResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "loadUserDataFasterJSONResponse")
    public JAXBElement<LoadUserDataFasterJSONResponse> createLoadUserDataFasterJSONResponse(LoadUserDataFasterJSONResponse value) {
        return new JAXBElement<LoadUserDataFasterJSONResponse>(_LoadUserDataFasterJSONResponse_QNAME, LoadUserDataFasterJSONResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadUserDataFasterXml }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "loadUserDataFasterXml")
    public JAXBElement<LoadUserDataFasterXml> createLoadUserDataFasterXml(LoadUserDataFasterXml value) {
        return new JAXBElement<LoadUserDataFasterXml>(_LoadUserDataFasterXml_QNAME, LoadUserDataFasterXml.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadUserDataFasterXmlResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "loadUserDataFasterXmlResponse")
    public JAXBElement<LoadUserDataFasterXmlResponse> createLoadUserDataFasterXmlResponse(LoadUserDataFasterXmlResponse value) {
        return new JAXBElement<LoadUserDataFasterXmlResponse>(_LoadUserDataFasterXmlResponse_QNAME, LoadUserDataFasterXmlResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadUserDataJaxbJSON }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "loadUserDataJaxbJSON")
    public JAXBElement<LoadUserDataJaxbJSON> createLoadUserDataJaxbJSON(LoadUserDataJaxbJSON value) {
        return new JAXBElement<LoadUserDataJaxbJSON>(_LoadUserDataJaxbJSON_QNAME, LoadUserDataJaxbJSON.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadUserDataJaxbJSONResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "loadUserDataJaxbJSONResponse")
    public JAXBElement<LoadUserDataJaxbJSONResponse> createLoadUserDataJaxbJSONResponse(LoadUserDataJaxbJSONResponse value) {
        return new JAXBElement<LoadUserDataJaxbJSONResponse>(_LoadUserDataJaxbJSONResponse_QNAME, LoadUserDataJaxbJSONResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadUserDataJaxbXml }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "loadUserDataJaxbXml")
    public JAXBElement<LoadUserDataJaxbXml> createLoadUserDataJaxbXml(LoadUserDataJaxbXml value) {
        return new JAXBElement<LoadUserDataJaxbXml>(_LoadUserDataJaxbXml_QNAME, LoadUserDataJaxbXml.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadUserDataJaxbXmlResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "loadUserDataJaxbXmlResponse")
    public JAXBElement<LoadUserDataJaxbXmlResponse> createLoadUserDataJaxbXmlResponse(LoadUserDataJaxbXmlResponse value) {
        return new JAXBElement<LoadUserDataJaxbXmlResponse>(_LoadUserDataJaxbXmlResponse_QNAME, LoadUserDataJaxbXmlResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadUserDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "loadUserDataResponse")
    public JAXBElement<LoadUserDataResponse> createLoadUserDataResponse(LoadUserDataResponse value) {
        return new JAXBElement<LoadUserDataResponse>(_LoadUserDataResponse_QNAME, LoadUserDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveUserData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "saveUserData")
    public JAXBElement<SaveUserData> createSaveUserData(SaveUserData value) {
        return new JAXBElement<SaveUserData>(_SaveUserData_QNAME, SaveUserData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveUserDataFasterJSON }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "saveUserDataFasterJSON")
    public JAXBElement<SaveUserDataFasterJSON> createSaveUserDataFasterJSON(SaveUserDataFasterJSON value) {
        return new JAXBElement<SaveUserDataFasterJSON>(_SaveUserDataFasterJSON_QNAME, SaveUserDataFasterJSON.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveUserDataFasterJSONResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "saveUserDataFasterJSONResponse")
    public JAXBElement<SaveUserDataFasterJSONResponse> createSaveUserDataFasterJSONResponse(SaveUserDataFasterJSONResponse value) {
        return new JAXBElement<SaveUserDataFasterJSONResponse>(_SaveUserDataFasterJSONResponse_QNAME, SaveUserDataFasterJSONResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveUserDataFasterXml }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "saveUserDataFasterXml")
    public JAXBElement<SaveUserDataFasterXml> createSaveUserDataFasterXml(SaveUserDataFasterXml value) {
        return new JAXBElement<SaveUserDataFasterXml>(_SaveUserDataFasterXml_QNAME, SaveUserDataFasterXml.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveUserDataFasterXmlResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "saveUserDataFasterXmlResponse")
    public JAXBElement<SaveUserDataFasterXmlResponse> createSaveUserDataFasterXmlResponse(SaveUserDataFasterXmlResponse value) {
        return new JAXBElement<SaveUserDataFasterXmlResponse>(_SaveUserDataFasterXmlResponse_QNAME, SaveUserDataFasterXmlResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveUserDataJaxbJSON }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "saveUserDataJaxbJSON")
    public JAXBElement<SaveUserDataJaxbJSON> createSaveUserDataJaxbJSON(SaveUserDataJaxbJSON value) {
        return new JAXBElement<SaveUserDataJaxbJSON>(_SaveUserDataJaxbJSON_QNAME, SaveUserDataJaxbJSON.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveUserDataJaxbJSONResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "saveUserDataJaxbJSONResponse")
    public JAXBElement<SaveUserDataJaxbJSONResponse> createSaveUserDataJaxbJSONResponse(SaveUserDataJaxbJSONResponse value) {
        return new JAXBElement<SaveUserDataJaxbJSONResponse>(_SaveUserDataJaxbJSONResponse_QNAME, SaveUserDataJaxbJSONResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveUserDataJaxbXml }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "saveUserDataJaxbXml")
    public JAXBElement<SaveUserDataJaxbXml> createSaveUserDataJaxbXml(SaveUserDataJaxbXml value) {
        return new JAXBElement<SaveUserDataJaxbXml>(_SaveUserDataJaxbXml_QNAME, SaveUserDataJaxbXml.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveUserDataJaxbXmlResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "saveUserDataJaxbXmlResponse")
    public JAXBElement<SaveUserDataJaxbXmlResponse> createSaveUserDataJaxbXmlResponse(SaveUserDataJaxbXmlResponse value) {
        return new JAXBElement<SaveUserDataJaxbXmlResponse>(_SaveUserDataJaxbXmlResponse_QNAME, SaveUserDataJaxbXmlResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveUserDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "saveUserDataResponse")
    public JAXBElement<SaveUserDataResponse> createSaveUserDataResponse(SaveUserDataResponse value) {
        return new JAXBElement<SaveUserDataResponse>(_SaveUserDataResponse_QNAME, SaveUserDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link User }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "user")
    public JAXBElement<User> createUser(User value) {
        return new JAXBElement<User>(_User_QNAME, User.class, null, value);
    }

}
