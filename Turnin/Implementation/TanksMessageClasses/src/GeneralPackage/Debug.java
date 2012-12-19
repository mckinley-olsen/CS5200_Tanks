/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralPackage;

import MessagePackage.GetShellProtocol.GetShellReply;
import MessagePackage.GetShellProtocol.GetShellRequest;
import MessagePackage.RegisterProtocol.RegisterRequest;
import MessagePackage.Request;

/**
 *
 * @author Mik
 */
public class Debug 
{
    public static void main(String[] args) throws Exception 
    {
        //use this area to debug classes
        RegisterRequest request1 = new RegisterRequest("Alec");
        ByteList messageBytes = new ByteList();
        try
        {
            Serializer.Serialize(request1, messageBytes);
            
            RegisterRequest a = RegisterRequest.Create(messageBytes);
            
            System.out.println("TESTING");
            System.out.println(request1.getConversationID().getSequenceNumber());
            System.out.println(a.getConversationID().getSequenceNumber());
        }
        catch(Exception e)
        {
            System.err.println(e.getCause().getMessage());
        }
    }
}
