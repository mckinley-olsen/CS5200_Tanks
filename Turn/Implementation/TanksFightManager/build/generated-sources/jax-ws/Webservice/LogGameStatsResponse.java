
package Webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="LogGameStatsResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "logGameStatsResult"
})
@XmlRootElement(name = "LogGameStatsResponse")
public class LogGameStatsResponse {

    @XmlElement(name = "LogGameStatsResult")
    protected String logGameStatsResult;

    /**
     * Gets the value of the logGameStatsResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogGameStatsResult() {
        return logGameStatsResult;
    }

    /**
     * Sets the value of the logGameStatsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogGameStatsResult(String value) {
        this.logGameStatsResult = value;
    }

}
