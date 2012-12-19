/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagePackage.GetShellProtocol;

import GeneralPackage.ByteList;
import GeneralPackage.Deserializer;
import MessagePackage.Request;

/**
 *
 * @author Mik
 */
public class GetShellRequest extends Request
{
    private static final int CLASS_ID=211;
        
    public GetShellRequest()
    {
        super(Request.RequestType.GET_SHELL);
    }
    
    public static GetShellRequest Create(ByteList messageBytes) throws Exception
    {
        
        GetShellRequest result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekInt() != GetShellRequest.getClassID() )
        {
            throw new Exception("Invalid GetShellRequest");
        }
        else
        {
            result = new GetShellRequest();
            result.decode(messageBytes);
        }
        return result;
        /*
        return (GetShellRequest)Deserializer.Deserialize(messageBytes, new GetShellRequest());
        */
    }
    // <editor-fold defaultstate="collapsed" desc=" encode/decode ">
    
    @Override
    public void encode(ByteList messageBytes) 
    {
        messageBytes.add(GetShellRequest.getClassID());
        
        short messageLengthPos = messageBytes.getCurrentWritePosition();
        
        messageBytes.add((short) 0);
        super.encode(messageBytes);

        //Nothing to encode
                
        short length = (short) (messageBytes.getCurrentWritePosition() - messageLengthPos - 2);
        messageBytes.writeShortTo(messageLengthPos, length);
    }
    
    @Override
    public void decode(ByteList messageBytes) throws Exception {
        int objectType = messageBytes.getInt();
        System.out.println(objectType);
        if (objectType != GetShellRequest.getClassID()) {
            throw new Exception("Invalid byte array for GetShellRequest message");
        }
        
        short objectLength = messageBytes.getShort();
        
        messageBytes.setNewReadLimit(objectLength);
        
        super.decode(messageBytes);

        //nothing to decode
        
        messageBytes.restorePreviousReadLimit();
    }
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="getters/setters">
//getters
    public static int getClassID() {
        return GetShellRequest.CLASS_ID;
    }
    // </editor-fold>
}
