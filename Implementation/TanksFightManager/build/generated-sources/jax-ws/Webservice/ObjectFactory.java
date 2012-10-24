
package Webservice;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the Webservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: Webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LogGameStats }
     * 
     */
    public LogGameStats createLogGameStats() {
        return new LogGameStats();
    }

    /**
     * Create an instance of {@link GameStats }
     * 
     */
    public GameStats createGameStats() {
        return new GameStats();
    }

    /**
     * Create an instance of {@link LogGameStatsResponse }
     * 
     */
    public LogGameStatsResponse createLogGameStatsResponse() {
        return new LogGameStatsResponse();
    }

    /**
     * Create an instance of {@link LogNewGame }
     * 
     */
    public LogNewGame createLogNewGame() {
        return new LogNewGame();
    }

    /**
     * Create an instance of {@link Register }
     * 
     */
    public Register createRegister() {
        return new Register();
    }

    /**
     * Create an instance of {@link RegisterResponse }
     * 
     */
    public RegisterResponse createRegisterResponse() {
        return new RegisterResponse();
    }

    /**
     * Create an instance of {@link LogNewGameResponse }
     * 
     */
    public LogNewGameResponse createLogNewGameResponse() {
        return new LogNewGameResponse();
    }

}
