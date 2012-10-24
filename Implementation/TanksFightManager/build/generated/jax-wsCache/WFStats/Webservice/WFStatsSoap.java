
package Webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "WFStatsSoap", targetNamespace = "http://tempuri.org/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface WFStatsSoap {


    /**
     * 
     * @param operatorEmail
     * @param operatorName
     * @param fightManagerId
     * @param fightManagerName
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "Register", action = "http://tempuri.org/Register")
    @WebResult(name = "RegisterResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "Register", targetNamespace = "http://tempuri.org/", className = "Webservice.Register")
    @ResponseWrapper(localName = "RegisterResponse", targetNamespace = "http://tempuri.org/", className = "Webservice.RegisterResponse")
    public String register(
        @WebParam(name = "fightManagerId", targetNamespace = "http://tempuri.org/")
        String fightManagerId,
        @WebParam(name = "fightManagerName", targetNamespace = "http://tempuri.org/")
        String fightManagerName,
        @WebParam(name = "operatorName", targetNamespace = "http://tempuri.org/")
        String operatorName,
        @WebParam(name = "operatorEmail", targetNamespace = "http://tempuri.org/")
        String operatorEmail);

    /**
     * 
     * @param start
     * @param gameId
     * @param fightManagerId
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "LogNewGame", action = "http://tempuri.org/LogNewGame")
    @WebResult(name = "LogNewGameResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "LogNewGame", targetNamespace = "http://tempuri.org/", className = "Webservice.LogNewGame")
    @ResponseWrapper(localName = "LogNewGameResponse", targetNamespace = "http://tempuri.org/", className = "Webservice.LogNewGameResponse")
    public String logNewGame(
        @WebParam(name = "fightManagerId", targetNamespace = "http://tempuri.org/")
        String fightManagerId,
        @WebParam(name = "gameId", targetNamespace = "http://tempuri.org/")
        int gameId,
        @WebParam(name = "start", targetNamespace = "http://tempuri.org/")
        XMLGregorianCalendar start);

    /**
     * 
     * @param stats
     * @param gameId
     * @param fightManagerId
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "LogGameStats", action = "http://tempuri.org/LogGameStats")
    @WebResult(name = "LogGameStatsResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "LogGameStats", targetNamespace = "http://tempuri.org/", className = "Webservice.LogGameStats")
    @ResponseWrapper(localName = "LogGameStatsResponse", targetNamespace = "http://tempuri.org/", className = "Webservice.LogGameStatsResponse")
    public String logGameStats(
        @WebParam(name = "fightManagerId", targetNamespace = "http://tempuri.org/")
        String fightManagerId,
        @WebParam(name = "gameId", targetNamespace = "http://tempuri.org/")
        int gameId,
        @WebParam(name = "stats", targetNamespace = "http://tempuri.org/")
        GameStats stats);

}
