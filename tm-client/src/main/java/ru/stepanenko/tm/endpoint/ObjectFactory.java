
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
    private final static QName _DataValidateException_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "DataValidateException");
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
    private final static QName _RemoveOneUser_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "removeOneUser");
    private final static QName _RemoveOneUserResponse_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "removeOneUserResponse");

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
     * Create an instance of {@link DataValidateException }
     * 
     */
    public DataValidateException createDataValidateException() {
        return new DataValidateException();
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
     * Create an instance of {@link RemoveOneUser }
     * 
     */
    public RemoveOneUser createRemoveOneUser() {
        return new RemoveOneUser();
    }

    /**
     * Create an instance of {@link RemoveOneUserResponse }
     * 
     */
    public RemoveOneUserResponse createRemoveOneUserResponse() {
        return new RemoveOneUserResponse();
    }

    /**
     * Create an instance of {@link SessionDTO }
     * 
     */
    public SessionDTO createSessionDTO() {
        return new SessionDTO();
    }

    /**
     * Create an instance of {@link UserDTO }
     * 
     */
    public UserDTO createUserDTO() {
        return new UserDTO();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link DataValidateException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "DataValidateException")
    public JAXBElement<DataValidateException> createDataValidateException(DataValidateException value) {
        return new JAXBElement<DataValidateException>(_DataValidateException_QNAME, DataValidateException.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveOneUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "removeOneUser")
    public JAXBElement<RemoveOneUser> createRemoveOneUser(RemoveOneUser value) {
        return new JAXBElement<RemoveOneUser>(_RemoveOneUser_QNAME, RemoveOneUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveOneUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "removeOneUserResponse")
    public JAXBElement<RemoveOneUserResponse> createRemoveOneUserResponse(RemoveOneUserResponse value) {
        return new JAXBElement<RemoveOneUserResponse>(_RemoveOneUserResponse_QNAME, RemoveOneUserResponse.class, null, value);
    }

}
