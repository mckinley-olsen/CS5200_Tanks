/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conversation;

import MessagePackage.AckNak;
import MessagePackage.UnregisterProtocol.UnregisterRequest;

/**
 *
 * @author McKinley
 */
public abstract class UnregisterConversation extends Conversation
{
    private UnregisterRequest request;
    private AckNak reply;
    
    //<editor-fold defaultstate="collapsed" desc="setters">
    public void setRequest(UnregisterRequest request)
    {
        this.request = request;
    }
    public void setReply(AckNak reply)
    {
        this.reply = reply;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getters">
    public UnregisterRequest getRequest()
    {
        return this.request;
    }
    public AckNak getReply()
    {
        return this.reply;
    }
    //</editor-fold>
}
