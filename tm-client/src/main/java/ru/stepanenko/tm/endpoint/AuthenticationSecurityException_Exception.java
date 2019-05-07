
package ru.stepanenko.tm.endpoint;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.2.7
 * 2019-05-07T19:30:51.421+03:00
 * Generated source version: 3.2.7
 */

@WebFault(name = "AuthenticationSecurityException", targetNamespace = "http://endpoint.tm.stepanenko.ru/")
public class AuthenticationSecurityException_Exception extends Exception {

    private ru.stepanenko.tm.endpoint.AuthenticationSecurityException authenticationSecurityException;

    public AuthenticationSecurityException_Exception() {
        super();
    }

    public AuthenticationSecurityException_Exception(String message) {
        super(message);
    }

    public AuthenticationSecurityException_Exception(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public AuthenticationSecurityException_Exception(String message, ru.stepanenko.tm.endpoint.AuthenticationSecurityException authenticationSecurityException) {
        super(message);
        this.authenticationSecurityException = authenticationSecurityException;
    }

    public AuthenticationSecurityException_Exception(String message, ru.stepanenko.tm.endpoint.AuthenticationSecurityException authenticationSecurityException, java.lang.Throwable cause) {
        super(message, cause);
        this.authenticationSecurityException = authenticationSecurityException;
    }

    public ru.stepanenko.tm.endpoint.AuthenticationSecurityException getFaultInfo() {
        return this.authenticationSecurityException;
    }
}
