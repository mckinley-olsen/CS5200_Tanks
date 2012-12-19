
package Webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fightManagerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fightManagerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operatorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operatorEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "fightManagerId",
    "fightManagerName",
    "operatorName",
    "operatorEmail"
})
@XmlRootElement(name = "Register")
public class Register {

    protected String fightManagerId;
    protected String fightManagerName;
    protected String operatorName;
    protected String operatorEmail;

    /**
     * Gets the value of the fightManagerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFightManagerId() {
        return fightManagerId;
    }

    /**
     * Sets the value of the fightManagerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFightManagerId(String value) {
        this.fightManagerId = value;
    }

    /**
     * Gets the value of the fightManagerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFightManagerName() {
        return fightManagerName;
    }

    /**
     * Sets the value of the fightManagerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFightManagerName(String value) {
        this.fightManagerName = value;
    }

    /**
     * Gets the value of the operatorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * Sets the value of the operatorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperatorName(String value) {
        this.operatorName = value;
    }

    /**
     * Gets the value of the operatorEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperatorEmail() {
        return operatorEmail;
    }

    /**
     * Sets the value of the operatorEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperatorEmail(String value) {
        this.operatorEmail = value;
    }

}
