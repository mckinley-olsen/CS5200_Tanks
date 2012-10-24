package GeneralPackage;

import MessagePackage.DeterminableEnum;
import MessagePackage.MessageNumber;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Deserializer 
{
    private static Logger logger = LoggerFactory.getLogger(Deserializer.getLogName());
    private static ByteList currentList;
    
    public static Object Deserialize(ByteList messageBytes, Object messageObject) throws Exception
    {
        Deserializer.setCurrentList(messageBytes);
        Deserializer.addTraceLog("Deserialize\n\tDeserialized classID: "+messageBytes.peekInt());

        Class messageClass = messageObject.getClass();
        List<Method> methods = Arrays.asList(messageClass.getMethods());
        int classID = findAndReturnClassID(messageObject, methods);
        
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            Deserializer.getLogger().error("Deserializer Deserialize\n\tInvalid ByteList received as messageBytes: "+messageBytes);
            throw new Exception("DESERIALIZE Invalid message byte array");
        }
        int a = messageBytes.getInt();
        if (a != classID )
        {
            Deserializer.getLogger().error("Deserializer Deserialize\n\tUnexpected classID found in ByteList\n\t Found: "+a+"\n\tExpected: "+classID);
            throw new Exception("Invalid message type");
        }
        else
        {
            Deserializer.addTraceLog("Deserialize\n\tCalling decode");
            Deserializer.decode(messageBytes, messageObject, methods);
        }
        return messageObject;
    }
    
    private static int findAndReturnClassID(Object messageObject, List<Method> methods) throws Exception
    {
        Deserializer.addTraceLog("Deserialize\n\tFinding getClassID method");
        for(Method m : methods)
        {
            if("getClassID".equals(m.getName()))
            {
                Object temp = Serializer.invokeGetter(messageObject, m);
                return (int)temp;
            }
        }
        return -1;
    }
    
    private static void decode(ByteList messageBytes, Object messageObject, List<Method> methods) throws Exception
    {
        Class c = messageObject.getClass();
        for(Method method : methods)
        {
            Deserializer.addTraceLog("Deserialize\n\tLooking at method: "+method.getName());
            if("get".equals(method.getName().substring(0, 3)))
            {
                Method getMethod = method;
                Object returned = Deserializer.invokeGetter(messageObject, getMethod);
                
                Class getReturnType = getMethod.getReturnType();
                
                if(Deserializer.isIgnoredMessage(method.getName()))
                {
                    Deserializer.addTraceLog("decode\n\tSkipped method: "+method.getName());
                    continue;
                }
                
                Method setMethod = Deserializer.findCorrespondingSet(c, getMethod, getReturnType);
                
                if(setMethod==null) { continue;} //continue onto the next method, as there is no corresponding set
                
                //begin type checking
                Type type = getMethod.getReturnType();
                
                if(type == Integer.TYPE)
                {
                    int i = messageBytes.getInt();
                    Deserializer.invokeSetter(messageObject, setMethod, i);
                    Deserializer.addTraceLog("decode\n\tDeserializing int: "+i);
                }
                else if(type == Boolean.TYPE)
                {
                    boolean b = messageBytes.getBool();
                    Deserializer.invokeSetter(messageObject, setMethod, b);
                    Deserializer.addTraceLog("decode\n\tDeserializing boolean: "+b);
                }
                else if(type == Short.TYPE)
                {
                    short s = messageBytes.getShort();
                    Deserializer.invokeSetter(messageObject, setMethod, s);
                    Deserializer.addTraceLog("decode\n\tDeserializing short: "+s);
                }
                else if(type == String.class)
                {
                    String string = messageBytes.getString();
                    Deserializer.invokeSetter(messageObject, setMethod, string);
                    Deserializer.addTraceLog("decode\n\tDeserializing String: "+string);
                }
                else if(type == Shell.class)
                {
                    Shell shell = Shell.Create(messageBytes);
                    Deserializer.invokeSetter(messageObject, setMethod, shell);
                    Deserializer.addTraceLog("decode\n\tDeserializing Shell: "+shell);
                }
                else if(type == Fight.class)
                {
                    Fight fight = Fight.Create(messageBytes);
                    Deserializer.invokeSetter(messageObject, setMethod, fight);
                    Deserializer.addTraceLog("decode\n\tDeserializing Fight: "+fight);
                }
                else if(type == Location.class)
                {
                    Location location = Location.Create(messageBytes);
                    Deserializer.invokeSetter(messageObject, setMethod, location);
                    Deserializer.addTraceLog("decode\n\tDeserializing Location: "+location);
                }
                else if(type == Player.class)
                {
                    Player player = Player.Create(messageBytes);
                    Deserializer.invokeSetter(messageObject, setMethod, player);
                    Deserializer.addTraceLog("decode\n\tDeserializing Player: "+player);
                }
                else if(type == Rate.class)
                {
                    Rate rate = Rate.Create(messageBytes);
                    Deserializer.invokeSetter(messageObject, setMethod, rate);
                    Deserializer.addTraceLog("decode\n\tDeserializing Rate: "+rate);
                }
                else if(type == MessageNumber.class)
                {
                    MessageNumber messageNumber = MessageNumber.Create(messageBytes);
                    Deserializer.invokeSetter(messageObject, setMethod, messageNumber);
                    Deserializer.addTraceLog("decode\n\tDeserializing MessageNumber: "+messageNumber);
                }
                else if(returned instanceof DeterminableEnum)
                {
                    DeterminableEnum a = messageBytes.getEnum((DeterminableEnum)returned);
                    Deserializer.invokeSetter(messageObject, setMethod, a);
                    Deserializer.addTraceLog("decode\n\tDeserializing DeterminableEnum: "+a);
                }
                else
                {
                    Deserializer.getLogger().error("Deserializer decode\n\tCannot determine the type of returned");
                }
            }
        }
    }
    
    private static Method findCorrespondingSet(Class c, Method method, Class expectedType)
    {
        Deserializer.addTraceLog("findCorrespondingSet\n\tLooking for corresponding set to: "+method.getName());
        Class[] typeArray = new Class[1];
        typeArray[0] = expectedType;
        
        try
        {
            return c.getMethod("set"+method.getName().substring(3, method.getName().length()), typeArray); //check to ensure this method also has a get
        }
        catch(NoSuchMethodException e)
        {
            Deserializer.getLogger().error("Deserializer findCorrespondingSet\n\tNo setter found for: "+method.getName());
            return null;
        }
    }
    
    private static void invokeSetter(Object messageObject, Method method, Object setTo)
    {
        Deserializer.addTraceLog("invokeSetter\n\tInvoking setter: "+method.getName());
        try
        {
            method.invoke(messageObject, setTo);
        }
        catch(Exception e)
        {
            Deserializer.getLogger().error("Deserializer invokeSetter\n\tError invoking setter method: "+method.getName());
        }
    }
    
    public static Object invokeGetter(Object messageObject, Method method) throws Exception
    {
        Deserializer.addTraceLog("invokeGetter\n\tInvoking getter: "+method.getName());
        try
        {
            return method.invoke(messageObject, null);
        }
        catch(IllegalAccessException e)
        {
            Deserializer.getLogger().error("Deserializer invokeGetter\n\tCould not access method: "+method.getName());
            throw new Exception("Cannot access method");
        }
    }
    
    private static void addTraceLog(String messageToLog)
    {
        Deserializer.getLogger().trace("Deserializer "+messageToLog+"\n\tWritePosition: "+Deserializer.getCurrentList().getCurrentWritePosition()+"\n\tReadPosition: "+Deserializer.getCurrentList().getCurrentReadPosition());
    }
    
    private static boolean isIgnoredMessage(String methodName)
    {
        if("getClassID".equals(methodName))
        {
            return true;
        }
        else if("getClass".equals(methodName))
        {
            return true;
        }
        else if("getLogger".equals(methodName))
        {
            return true;
        }
        else if("getLogName".equals(methodName))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
// <editor-fold defaultstate="collapsed" desc=" Getters ">
    public static String getLogName()
    {
        return Deserializer.class.getName();
    }

    public static Logger getLogger()
    {
        return Deserializer.logger;
    }

    private static ByteList getCurrentList()
    {
        return Deserializer.currentList;
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc=" Setters ">
    private static void setCurrentList(ByteList list)
    {
        Deserializer.currentList = list;
    }
// </editor-fold>
}
