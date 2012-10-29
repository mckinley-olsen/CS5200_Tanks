
package Webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfGameStats complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfGameStats">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GameStats" type="{http://tempuri.org/}GameStats" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfGameStats", propOrder = {
    "gameStats"
})
public class ArrayOfGameStats {

    @XmlElement(name = "GameStats", nillable = true)
    protected List<GameStats> gameStats;

    /**
     * Gets the value of the gameStats property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the gameStats property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGameStats().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GameStats }
     * 
     * 
     */
    public List<GameStats> getGameStats() {
        if (gameStats == null) {
            gameStats = new ArrayList<GameStats>();
        }
        return this.gameStats;
    }

}
