
package Webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for GameStats complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GameStats">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="GameStatus" type="{http://tempuri.org/}PossibleGameStates"/>
 *         &lt;element name="CurrentNumberOfPlayers" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="LargestNumberOfPlayers" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="NumberOfBalloonsThrown" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="NumberOfBalloonsThatHit" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="AmountOfWaterThrown" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="AmountOfWaterThatHit" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Winner" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GameStats", propOrder = {
    "timestamp",
    "gameStatus",
    "currentNumberOfPlayers",
    "largestNumberOfPlayers",
    "numberOfBalloonsThrown",
    "numberOfBalloonsThatHit",
    "amountOfWaterThrown",
    "amountOfWaterThatHit",
    "winner"
})
public class GameStats {

    @XmlElement(name = "Timestamp", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;
    @XmlElement(name = "GameStatus", required = true)
    protected PossibleGameStates gameStatus;
    @XmlElement(name = "CurrentNumberOfPlayers")
    protected int currentNumberOfPlayers;
    @XmlElement(name = "LargestNumberOfPlayers")
    protected int largestNumberOfPlayers;
    @XmlElement(name = "NumberOfBalloonsThrown")
    protected int numberOfBalloonsThrown;
    @XmlElement(name = "NumberOfBalloonsThatHit")
    protected int numberOfBalloonsThatHit;
    @XmlElement(name = "AmountOfWaterThrown")
    protected int amountOfWaterThrown;
    @XmlElement(name = "AmountOfWaterThatHit")
    protected int amountOfWaterThatHit;
    @XmlElement(name = "Winner")
    protected String winner;

    /**
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimestamp(XMLGregorianCalendar value) {
        this.timestamp = value;
    }

    /**
     * Gets the value of the gameStatus property.
     * 
     * @return
     *     possible object is
     *     {@link PossibleGameStates }
     *     
     */
    public PossibleGameStates getGameStatus() {
        return gameStatus;
    }

    /**
     * Sets the value of the gameStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link PossibleGameStates }
     *     
     */
    public void setGameStatus(PossibleGameStates value) {
        this.gameStatus = value;
    }

    /**
     * Gets the value of the currentNumberOfPlayers property.
     * 
     */
    public int getCurrentNumberOfPlayers() {
        return currentNumberOfPlayers;
    }

    /**
     * Sets the value of the currentNumberOfPlayers property.
     * 
     */
    public void setCurrentNumberOfPlayers(int value) {
        this.currentNumberOfPlayers = value;
    }

    /**
     * Gets the value of the largestNumberOfPlayers property.
     * 
     */
    public int getLargestNumberOfPlayers() {
        return largestNumberOfPlayers;
    }

    /**
     * Sets the value of the largestNumberOfPlayers property.
     * 
     */
    public void setLargestNumberOfPlayers(int value) {
        this.largestNumberOfPlayers = value;
    }

    /**
     * Gets the value of the numberOfBalloonsThrown property.
     * 
     */
    public int getNumberOfBalloonsThrown() {
        return numberOfBalloonsThrown;
    }

    /**
     * Sets the value of the numberOfBalloonsThrown property.
     * 
     */
    public void setNumberOfBalloonsThrown(int value) {
        this.numberOfBalloonsThrown = value;
    }

    /**
     * Gets the value of the numberOfBalloonsThatHit property.
     * 
     */
    public int getNumberOfBalloonsThatHit() {
        return numberOfBalloonsThatHit;
    }

    /**
     * Sets the value of the numberOfBalloonsThatHit property.
     * 
     */
    public void setNumberOfBalloonsThatHit(int value) {
        this.numberOfBalloonsThatHit = value;
    }

    /**
     * Gets the value of the amountOfWaterThrown property.
     * 
     */
    public int getAmountOfWaterThrown() {
        return amountOfWaterThrown;
    }

    /**
     * Sets the value of the amountOfWaterThrown property.
     * 
     */
    public void setAmountOfWaterThrown(int value) {
        this.amountOfWaterThrown = value;
    }

    /**
     * Gets the value of the amountOfWaterThatHit property.
     * 
     */
    public int getAmountOfWaterThatHit() {
        return amountOfWaterThatHit;
    }

    /**
     * Sets the value of the amountOfWaterThatHit property.
     * 
     */
    public void setAmountOfWaterThatHit(int value) {
        this.amountOfWaterThatHit = value;
    }

    /**
     * Gets the value of the winner property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWinner() {
        return winner;
    }

    /**
     * Sets the value of the winner property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWinner(String value) {
        this.winner = value;
    }

}
