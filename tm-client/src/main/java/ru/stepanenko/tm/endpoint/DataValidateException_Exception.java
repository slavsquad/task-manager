
package ru.stepanenko.tm.endpoint;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.2.7
 * 2019-05-24T00:37:06.257+03:00
 * Generated source version: 3.2.7
 */

@WebFault(name = "DataValidateException", targetNamespace = "http://endpoint.tm.stepanenko.ru/")
public class DataValidateException_Exception extends Exception {

    private ru.stepanenko.tm.endpoint.DataValidateException dataValidateException;

    public DataValidateException_Exception() {
        super();
    }

    public DataValidateException_Exception(String message) {
        super(message);
    }

    public DataValidateException_Exception(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public DataValidateException_Exception(String message, ru.stepanenko.tm.endpoint.DataValidateException dataValidateException) {
        super(message);
        this.dataValidateException = dataValidateException;
    }

    public DataValidateException_Exception(String message, ru.stepanenko.tm.endpoint.DataValidateException dataValidateException, java.lang.Throwable cause) {
        super(message, cause);
        this.dataValidateException = dataValidateException;
    }

    public ru.stepanenko.tm.endpoint.DataValidateException getFaultInfo() {
        return this.dataValidateException;
    }
}
