
package ru.stepanenko.tm.endpoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getUserBySession complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="getUserBySession"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="session" type="{http://endpoint.tm.stepanenko.ru/}sessionDTO" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getUserBySession", propOrder = {
        "session"
})
public class GetUserBySession {

    protected SessionDTO session;

    /**
     * Gets the value of the session property.
     *
     * @return possible object is
     * {@link SessionDTO }
     */
    public SessionDTO getSession() {
        return session;
    }

    /**
     * Sets the value of the session property.
     *
     * @param value allowed object is
     *              {@link SessionDTO }
     */
    public void setSession(SessionDTO value) {
        this.session = value;
    }

}
