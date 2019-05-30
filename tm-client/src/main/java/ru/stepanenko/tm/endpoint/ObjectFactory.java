
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
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetInfoHost_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "getInfoHost");
    private final static QName _GetInfoHostResponse_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "getInfoHostResponse");
    private final static QName _GetInfoPort_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "getInfoPort");
    private final static QName _GetInfoPortResponse_QNAME = new QName("http://endpoint.tm.stepanenko.ru/", "getInfoPortResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.stepanenko.tm.endpoint
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetInfoHost }
     */
    public GetInfoHost createGetInfoHost() {
        return new GetInfoHost();
    }

    /**
     * Create an instance of {@link GetInfoHostResponse }
     */
    public GetInfoHostResponse createGetInfoHostResponse() {
        return new GetInfoHostResponse();
    }

    /**
     * Create an instance of {@link GetInfoPort }
     */
    public GetInfoPort createGetInfoPort() {
        return new GetInfoPort();
    }

    /**
     * Create an instance of {@link GetInfoPortResponse }
     */
    public GetInfoPortResponse createGetInfoPortResponse() {
        return new GetInfoPortResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInfoHost }{@code >}}
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "getInfoHost")
    public JAXBElement<GetInfoHost> createGetInfoHost(GetInfoHost value) {
        return new JAXBElement<GetInfoHost>(_GetInfoHost_QNAME, GetInfoHost.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInfoHostResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "getInfoHostResponse")
    public JAXBElement<GetInfoHostResponse> createGetInfoHostResponse(GetInfoHostResponse value) {
        return new JAXBElement<GetInfoHostResponse>(_GetInfoHostResponse_QNAME, GetInfoHostResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInfoPort }{@code >}}
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "getInfoPort")
    public JAXBElement<GetInfoPort> createGetInfoPort(GetInfoPort value) {
        return new JAXBElement<GetInfoPort>(_GetInfoPort_QNAME, GetInfoPort.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInfoPortResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://endpoint.tm.stepanenko.ru/", name = "getInfoPortResponse")
    public JAXBElement<GetInfoPortResponse> createGetInfoPortResponse(GetInfoPortResponse value) {
        return new JAXBElement<GetInfoPortResponse>(_GetInfoPortResponse_QNAME, GetInfoPortResponse.class, null, value);
    }

}
