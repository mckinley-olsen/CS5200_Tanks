
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
 *         &lt;element name="GetGameStatsResult" type="{http://tempuri.org/}ArrayOfGameStats" minOccurs="0"/>
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
    "getGameStatsResult"
})
@XmlRootElement(name = "GetGameStatsResponse")
public class GetGameStatsResponse {

    @XmlElement(name = "GetGameStatsResult")
    protected ArrayOfGameStats getGameStatsResult;

    /**
     * Gets the value of the getGameStatsResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfGameStats }
     *     
     */
    public ArrayOfGameStats getGetGameStatsResult() {
        return getGameStatsResult;
    }

    /**
     * Sets the value of the getGameStatsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfGameStats }
     *     
     */
    public void setGetGameStatsResult(ArrayOfGameStats value) {
        this.getGameStatsResult = value;
    }

}
