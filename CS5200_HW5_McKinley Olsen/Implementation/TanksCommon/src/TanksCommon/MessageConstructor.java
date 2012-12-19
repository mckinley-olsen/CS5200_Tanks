package TanksCommon;

import GeneralPackage.ByteList;
import MessagePackage.AckNak;
import MessagePackage.CreateFightProtocol.CreateFightReply;
import MessagePackage.FightListProtocol.FightListReply;
import MessagePackage.FightListProtocol.FightListRequest;
import MessagePackage.FillShellProtocol.FillShellReply;
import MessagePackage.FillShellProtocol.FillShellRequest;
import MessagePackage.FireShellProtocol.FireShellReply;
import MessagePackage.FireShellRequest;
import MessagePackage.GetShellProtocol.GetShellReply;
import MessagePackage.GetShellProtocol.GetShellRequest;
import MessagePackage.JoinFightProtocol.JoinFightRequest;
import MessagePackage.LocationListProtocol.LastLocationsRequest;
import MessagePackage.LocationListProtocol.LocationListReply;
import MessagePackage.LocationListProtocol.LocationListRequest;
import MessagePackage.Message;
import MessagePackage.MultiUse.NewConnectionReply;
import MessagePackage.MultiUse.ShellFiredReply;
import MessagePackage.MultiUse.ShellFiredRequest;
import MessagePackage.PlayerList.PlayerListReply;
import MessagePackage.PlayerList.PlayerListRequest;
import MessagePackage.RegisterProtocol.RegisterReply;
import MessagePackage.RegisterProtocol.RegisterRequest;
import MessagePackage.Reply;
import MessagePackage.Request;
import MessagePackage.UnregisterProtocol.UnregisterRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageConstructor
{
    private static Logger logger = LoggerFactory.getLogger(MessageConstructor.getLogName());
    
    public static Message constructMessage(byte[] messageByteArray) throws Exception
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
                    
                case 312:
                    resultingMessage = LocationListReply.Create(messageBytes);
                    break;
            }
        }
        else
        {
            MessageConstructor.getLogger().error("MessageConstructor constructMessage\n\t UNRECOGNIZED MESSAGE TYPE: "+type);
            throw new Exception("UNRECOGNIZED MESSAGE");
        }
        return resultingMessage;
    }
    
    public static String getLogName()
    {
        return MessageConstructor.class.getName();
    }
    
    public static Logger getLogger()
    {
        return MessageConstructor.logger;
    }
}
