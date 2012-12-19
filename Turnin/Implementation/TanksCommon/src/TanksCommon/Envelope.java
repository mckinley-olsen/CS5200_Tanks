package TanksCommon;

import MessagePackage.Message;
import java.net.InetSocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Envelope
{
    private static Logger logger = LoggerFactory.getLogger(Envelope.getLogName());
    private Message message;
    private InetSocketAddress senderEndPoint=null;
    private InetSocketAddress receiverEndPoint=null;

    protected Envelope() {}

    public static Envelope createOutgoingEnvelope(Message message, InetSocketAddress receiverEndPoint)
    {
        Envelope newEnvelope = new Envelope();
        newEnvelope.setMessage(message);
        newEnvelope.setReceiverEndPoint(receiverEndPoint) ;
        return newEnvelope;
    }
    public static Envelope createIncomingEnvelope(Message message, InetSocketAddress senderEndPoint )
    {
        Envelope newEnvelope = new Envelope();
        newEnvelope.setMessage(message);
        newEnvelope.setSenderEndPoint(senderEndPoint);
        return newEnvelope;
    }
    public boolean isValidCommunication()
    {
        return true;
    }

    /*
    public static Envelope createIncomingEnvelope(byte[] bytes, InetSocketAddress senderEndPoint, int communicatorNumber) throws Exception
    {
        Envelope newEnvelope = new Envelope();
        Message message = newEnvelope.constructMessage(bytes);
        newEnvelope.setMessage(message);
        newEnvelope.setSenderEndPoint(senderEndPoint);
        return newEnvelope;
    }

    
    private Message constructMessage(byte[] messageByteArray) throws Exception
    {
        ByteList messageBytes = new ByteList();
        messageBytes.fromBytes(messageByteArray);
        
        int type = messageBytes.peekInt();
        Message resultingMessage = null;
        if(type>200 && type<300)
        {
            switch(type)
            {
                case 201:
                    resultingMessage = Request.Create(messageBytes);
                    break;
                    
                case 202:
                    resultingMessage = RegisterRequest.Create(messageBytes);
                    break;
                    
                case 203:
                    resultingMessage = UnregisterRequest.Create(messageBytes);
                    break;
                    
                case 204:
                    resultingMessage = FireShellRequest.Create(messageBytes);
                    break;
                    
                case 205:
                    resultingMessage = ShellFiredRequest.Create(messageBytes);
                    break;
                    
                case 206:
                    resultingMessage = LocationListRequest.Create(messageBytes);
                    break;
                    
                case 207:
                    resultingMessage = LastLocationsRequest.Create(messageBytes);
                    break;
                    
                case 208:
                    resultingMessage = PlayerListRequest.Create(messageBytes);
                    break;
                    
                case 209:
                    resultingMessage = JoinFightRequest.Create(messageBytes);
                    break;
                    
                case 210:
                    resultingMessage = FightListRequest.Create(messageBytes);
                    break;
                    
                case 211:
                    resultingMessage = GetShellRequest.Create(messageBytes);
                    break;
                    
                case 212:
                    resultingMessage = FillShellRequest.Create(messageBytes);
                    break;
            }
        }
        else if(type>300 && type<400)
        {
            switch(type)
            {
                case 301:
                    resultingMessage = Reply.Create(messageBytes);
                    break;
                    
                case 302:
                    resultingMessage = RegisterReply.Create(messageBytes);
                    break;
                    
                case 303:
                    resultingMessage = AckNak.Create(messageBytes);
                    break;
                    
                case 304:
                    resultingMessage = ShellFiredReply.Create(messageBytes);
                    break;
                    
                case 305:
                    resultingMessage = FireShellReply.Create(messageBytes);
                    break;
                    
                case 306:
                    resultingMessage = NewConnectionReply.Create(messageBytes);
                    break;
                    
                case 307:
                    resultingMessage = PlayerListReply.Create(messageBytes);
                    break;
                    
                case 308:
                    resultingMessage = CreateFightReply.Create(messageBytes);
                    break;
                    
                case 309:
                    resultingMessage = FightListReply.Create(messageBytes);
                    break;
                    
                case 310:
                    resultingMessage = GetShellReply.Create(messageBytes);
                    break;
                    
                case 311:
                    resultingMessage = FillShellReply.Create(messageBytes);
                    break;
            }
        }
        else
        {
            Envelope.getLogger().error("Envelope constructMessage\n\t UNRECOGNIZED MESSAGE TYPE: "+type);
            throw new Exception("UNRECOGNIZED MESSAGE");
        }
        return resultingMessage;
    }
    */
    
// <editor-fold defaultstate="collapsed" desc=" Getters ">
    public Message getMessage()
    {
        return this.message;
    }
    public InetSocketAddress getReceieverEndPoint()
    {
        return this.receiverEndPoint;
    }
    public InetSocketAddress getSenderEndPoint()
    {
        return this.senderEndPoint;
    }
    
    public static String getLogName()
    {
        return Envelope.class.getName();
    }
    public static Logger getLogger()
    {
        return Envelope.logger;
    }

// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc=" Setters ">
    public void setMessage(Message message)
    {
        this.message = message;
    }
    public void setReceiverEndPoint(InetSocketAddress receiverEndPoint)
    {
        this.receiverEndPoint = receiverEndPoint;
    }
    public void setSenderEndPoint(InetSocketAddress senderEndPoint)
    {
        this.senderEndPoint = senderEndPoint;
    }
// </editor-fold>
}
