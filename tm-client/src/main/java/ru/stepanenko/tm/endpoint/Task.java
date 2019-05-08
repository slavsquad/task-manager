
package ru.stepanenko.tm.endpoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for task complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="task"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://endpoint.tm.stepanenko.ru/}abstractEntity"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="projectId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dateBegin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dateEnd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="status" type="{http://endpoint.tm.stepanenko.ru/}status" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "task", propOrder = {
    "projectId",
    "dateBegin",
    "dateEnd",
    "userId",
    "status"
})
public class Task
    extends AbstractEntity
{

    protected String projectID;
    protected String dateBegin;
    protected String dateEnd;
    protected String userID;
    @XmlSchemaType(name = "string")
    protected Status status;

    /**
     * Gets the value of the projectId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectID() {
        return projectID;
    }

    /**
     * Sets the value of the projectId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectID(String value) {
        this.projectID = value;
    }

    /**
     * Gets the value of the dateBegin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateBegin() {
        return dateBegin;
    }

    /**
     * Sets the value of the dateBegin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateBegin(String value) {
        this.dateBegin = value;
    }

    /**
     * Gets the value of the dateEnd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateEnd() {
        return dateEnd;
    }

    /**
     * Sets the value of the dateEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateEnd(String value) {
        this.dateEnd = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserID(String value) {
        this.userID = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link Status }
     *     
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link Status }
     *     
     */
    public void setStatus(Status value) {
        this.status = value;
    }

}
