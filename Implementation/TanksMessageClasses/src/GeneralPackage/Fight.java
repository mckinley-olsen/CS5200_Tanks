/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralPackage;

/**
 *
 * @author McKinley
 */
public class Fight 
{
    public static final int CLASS_ID=105;
    private int fightID;
    
    protected Fight(){}
    
    public Fight(int fightID)
    {
        this.setFightID(fightID);
    }
    public static Fight Create(ByteList messageBytes) throws Exception
    {
        Fight result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekInt() != Fight.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new Fight();
            result.decode(messageBytes);
        }
        return result;
        
        //return (Fight)Deserializer.Deserialize(messageBytes, new Fight());
    }
    // <editor-fold defaultstate="collapsed" desc=" encode/decode ">
    public void encode(ByteList messageBytes) 
    {
        
        messageBytes.add(this.getClassID());
        
        short messageLengthPos = messageBytes.getCurrentWritePosition();
        
        messageBytes.add((short) 0);

        messageBytes.add(this.getFightID());
        
        short length = (short) (messageBytes.getCurrentWritePosition() - messageLengthPos - 2);
        messageBytes.writeShortTo(messageLengthPos, length);
        /*
        try
        {
            Serializer.Serialize(this, messageBytes);
        }
        catch(Exception e)
        {
            System.out.println("Error encoding Fight");
        }
        */
    }
    
    public void decode(ByteList messageBytes) throws Exception {
        int objectType = messageBytes.getInt();
        if (objectType != this.getClassID()) {
            throw new Exception("Invalid byte array for Request message");
        }
        
        short objectLength = messageBytes.getShort();
        
        messageBytes.setNewReadLimit(objectLength);
        
        this.setFightID(messageBytes.getInt());
        
        messageBytes.restorePreviousReadLimit();
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="getters/setters">
    //getters
    public static int getClassID() {
        return Fight.CLASS_ID;
    }
    public int getFightID()
    {
        return this.fightID;
    }
    //setters
    public void setFightID(int fightID)
    {
        this.fightID = fightID;
    }
// </editor-fold>
}
