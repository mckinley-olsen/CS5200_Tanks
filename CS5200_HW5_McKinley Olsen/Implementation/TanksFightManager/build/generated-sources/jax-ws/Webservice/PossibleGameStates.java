
package Webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PossibleGameStates.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PossibleGameStates">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="InProgress"/>
 *     &lt;enumeration value="Won"/>
 *     &lt;enumeration value="Cancelled"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PossibleGameStates")
@XmlEnum
public enum PossibleGameStates {

    @XmlEnumValue("InProgress")
    IN_PROGRESS("InProgress"),
    @XmlEnumValue("Won")
    WON("Won"),
    @XmlEnumValue("Cancelled")
    CANCELLED("Cancelled");
    private final String value;

    PossibleGameStates(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PossibleGameStates fromValue(String v) {
        for (PossibleGameStates c: PossibleGameStates.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
