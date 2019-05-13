
package ru.stepanenko.tm.endpoint;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for status.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="status"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="PLANNED"/&gt;
 *     &lt;enumeration value="INPROCESS"/&gt;
 *     &lt;enumeration value="DONE"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "status")
@XmlEnum
public enum Status {

    PLANNED,
    INPROCESS,
    DONE;

    public String value() {
        return name();
    }

    public static Status fromValue(String v) {
        return valueOf(v);
    }

}
