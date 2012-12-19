/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralPackage;

import MessagePackage.DeterminableEnum;
import MessagePackage.MessageNumber;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author McKinley
 */
//serializes object, these objects MUST have a static getClassID() method
public class Serializer 
{
    private static Logger logger = LoggerFactory.getLogger(Serializer.getLogName());
    private static ByteList currentList;
    
    private Serializer(){}
    public static void Serialize(Object messageObject, ByteList messageBytes) throws Exception
    {
        Class messageClass = messageObject.getClass();
        List<Method> list = Arrays.asList(messageClass.getMethods());
        
        Serializer.setCurrentList(messageBytes);
        
        int classID  = Serializer.findGetClassID(messageClass);
        
        messageBytes.add(classID);
        
        for(Method method : list)
        {
            Serializer.addTraceLog("Serialize\n\tLooking at method: "+method.getName());
            if("get".equals(method.getName().substring(0, 3)))
            {
                Object returned = Serializer.invokeGetter(messageObject, method);
                if(Serializer.isIgnoredMessage(method.getName()))
                {
                    Serializer.addTraceLog("Serialize\n\tSkipped method");
                    continue;
                }
                Serializer.addTraceLog("Serialize\n\tSerializing: "+method.getName());
                Type type = method.getReturnType();
                if(type == Integer.TYPE)
                {
                    
                    int i = (int)returned;
                    messageBytes.add(i);
                    Serializer.addTraceLog("Serialize\n\tSerializing int: "+i);
                }
                else if(type == Boolean.TYPE)
                {
                    boolean b = (boolean)returned;
                    messageBytes.add(b);
                    Serializer.addTraceLog("Serialize\n\tSerializing bool: "+b);
                }
                else if(type == Short.TYPE)
                {
                    short s = (short)returned;
                    messageBytes.add(s);
                    Serializer.addTraceLog("Serialize\n\tSerializing short: "+s);
                }
                else if(type == String.class)
                {
                    String s = (String)returned;
                    messageBytes.add(s);
                    Serializer.addTraceLog("Serialize\n\tSerializing String: "+s);
                }
                else if(type == Shell.class)
                {
                    Shell s = (Shell)returned;
                    s.encode(messageBytes);
                    Serializer.addTraceLog("Serialize\n\tSerializing Shell: "+s);
                }
                else if(type == Fight.class)
                {
                    Fight f = (Fight)returned;
                    f.encode(messageBytes);
                    Serializer.addTraceLog("Serialize\n\tSerializing Fight: "+f);
                }
                else if(type == Location.class)
                {
                    Location l = (Location)returned;
                    l.encode(messageBytes);
                    Serializer.addTraceLog("Serialize\n\tSerializing Location: "+l);
                }
                else if(type == Player.class)
                {
                    Player p = (Player)returned;
                    p.encode(messageBytes);
                    Serializer.addTraceLog("Serialize\n\tSerializing Player: "+p);
                }
                else if(type == Rate.class)
                {
                    Rate r = (Rate)returned;
                    r.encode(messageBytes);
                    Serializer.addTraceLog("Serialize\n\tSerializing Rate: "+r);
                }
                else if(type == MessageNumber.class)
                {
                    MessageNumber m = (MessageNumber)returned;
                    m.encode(messageBytes);
                    Serializer.addTraceLog("Serialize\n\tSerializing MessageNumber: "+m);
                }
                else if(returned instanceof DeterminableEnum)
                {
                    DeterminableEnum e = (DeterminableEnum)returned;
                    messageBytes.add(e.ordinal());
                    Serializer.addTraceLog("Serialize\n\tSerializing DeterminableEnum: "+e);
                }
                else
                {
                    Serializer.getLogger().error("Serializer Serialize\n\tUnable to serialize: "+returned);
                }
            }
        } //end for
    }
    
    public static Object invokeGetter(Object messageObject, Method method) throws Exception
    {
        try
        {
            return method.invoke(messageObject, null);
        }
        catch(IllegalAccessException e)
        {
            throw new Exception("Cannot access method");
        }
    }
    
    public static int findGetClassID(Class messageClass) throws Exception
    {
        Method[] methods = messageClass.getDeclaredMethods();
        for(Method m : methods)
        {
            if("getClassID".equals(m.getName()))
            {
                return (int)Serializer.invokeGetter(messageClass, m);
            }
        }
        return -1;
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
    
    private static void addTraceLog(String messageToLog)
    {
        Serializer.getLogger().trace("Serializer "+messageToLog+"\n\tWritePosition: "+Serializer.getCurrentList().getCurrentWritePosition()+"\n\tReadPosition: "+Serializer.getCurrentList().getCurrentReadPosition());
    }
    public static String getLogName()
    {
        return Serializer.class.getName();
    }
    public static Logger getLogger()
    {
        return Serializer.logger;
    }
    private static ByteList getCurrentList()
    {
        return Serializer.currentList;
    }
    private static void setCurrentList(ByteList list)
    {
        Serializer.currentList = list;
    }
}
