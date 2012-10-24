
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
 *         &lt;element name="gameId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="stats" type="{http://tempuri.org/}GameStats" minOccurs="0"/>
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
    "gameId",
    "stats"
})
@XmlRootElement(name = "LogGameStats")
public class LogGameStats {

    protected String fightManagerId;
    protected int gameId;
    protected GameStats stats;

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
     * Gets the value of the gameId property.
     * 
     */
    public int getGameId() {
        return gameId;
    }

    /**
     * Sets the value of the gameId property.
     * 
     */
    public void setGameId(int value) {
        this.gameId = value;
    }

    /**
     * Gets the value of the stats property.
     * 
     * @return
     *     possible object is
     *     {@link GameStats }
     *     
     */
    public GameStats getStats() {
        return stats;
    }

    /**
     * Sets the value of the stats property.
     * 
     * @param value
     *     allowed object is
     *     {@link GameStats }
     *     
     */
    public void setStats(GameStats value) {
        this.stats = value;
    }

}
