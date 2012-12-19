package TanksCommon;

import GeneralPackage.ByteList;
import MessagePackage.Message;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.slf4j.LoggerFactory;

public class Communicator extends BackgroundThread
{
    private DatagramSocket socket;
    private int portNumber;
    private int communicatorNumber;
    
    private static final int MAX_PACKET_SIZE=1024;
    private static final int RECEIVE_TIMEOUT_INTERVAL=50;
    private static final int WORKER_SLEEP_INTERVAL=50;
    
    private static final ArrayList<Communicator> communicatorList = new ArrayList();

    private ConcurrentLinkedQueue<Envelope> inputQueue;
    private ConcurrentLinkedQueue<Envelope> outputQueue;
    
    public Communicator()
    {
        this(0);
    }
    
    public Communicator(int portNumber)
    {
        this.setInstanceNumber();
        Communicator.incrementNumberOfCommunicators();
        this.setLogger(LoggerFactory.getLogger(this.getLogName()));
        this.getLogger().debug("Communicator constructor:\n\t communicator created");
        inputQueue = new ConcurrentLinkedQueue();
        outputQueue = new ConcurrentLinkedQueue();
        
        this.setPortNumber(portNumber);
        int number = Communicator.addCommunicator(this);
        this.setCommunicatorNumber(number);
    }
    
    public void addToOutputQueue(Envelope envelope)
    {
        this.getOutputQueue().add(envelope);
    }
    
    //used in testing, debug
    public void addToInputQueue(Envelope e)
    {
        this.getInputQueue().add(e);
    }
    
    public Envelope getFromInputQueue()
    {
        return (Envelope)this.getInputQueue().poll();
    }
    
    @Override
    public void stop() throws Exception
    {
        super.stop();
        this.getSocket().close();
    }
    
    @Override
    public void run()
    {
        Envelope e=null;
        while(this.continueRunning)
        {
            this.getLogger().debug("Communicator run:\n\t at beginning of run");
            e = this.getMessage();
            if(e!=null)
            {
                this.getLogger().debug("Communicator run:\n\t adding envelope to inputQueue");
                this.getInputQueue().add(e);
            }
            e = (Envelope)this.getOutputQueue().poll();
            if(e!=null)
            {
                this.getLogger().debug("Communicator run:\n\t outputQueue not empty, trying to send the message");
                this.sendMessage(e);
            }
            e=null;
            this.sleepWorker();
        }
    }
    
    private void sleepWorker()
    {
        this.getLogger().trace("Communicator sleepWorker:\n\t sleeping worker");
        try
        {
            //this.getWorker().wait(Communicator.WORKER_SLEEP_INTERVAL);
            Thread.sleep(Communicator.WORKER_SLEEP_INTERVAL);
        }
        catch(InterruptedException e)
        {
            this.getLogger().error("Communicator sleepWorker:\n\t could not sleep worker");
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Initialization ">
    private static void incrementNumberOfCommunicators()
    {
        Communicator.setNumberOfInstances(Communicator.getNumberOfInstances()+1);
    }
    
    private void initializeSocket() 
    {
        this.getLogger().info("Communicator initializeSocket:\n\t Socket initialized");
        if(this.getSocket()!=null)
        {
            this.getSocket().close();
        }
        try 
        {
            //ommunicator.setSocket(new DatagramSocket(Communicator.getPortNumber()));
            this.setSocket(new DatagramSocket(this.getPortNumber()));
        } 
        catch (SocketException e) 
        {
            System.err.println("Socket Exception");
            this.getLogger().error("ERROR CREATING SOCKET:\n\tPort Number: " + this.getPortNumber());
            e.printStackTrace();
        }
    }

// </editor-fold>
    
    public void sendMessage(Envelope envelope)
    {
        byte[] messageBytes = this.encodeMessage(envelope);
        
        try
        {
            this.getLogger().debug("Communicator sendMessage\n\t Starting to try to send message of length: " + messageBytes.length);
            DatagramPacket p = new DatagramPacket(messageBytes, messageBytes.length, envelope.getReceieverEndPoint());
            socket.send(p);
            this.getLogger().debug("Communicator sendMessage\n\t Sent successfully length: " + messageBytes.length);
        }
        catch(SocketException e)
        {
            System.err.println("Error sending packet: SocketException");
            this.getLogger().error("ERROR SENDING MESSAGE, SocketException:\n\tLength: " + messageBytes.length);
        }
        catch(IOException e)
        {
            System.err.println("Error sending packet");
            this.getLogger().error("ERROR SENDING MESSAGE:\n\tLength: " + messageBytes.length);
        }
    }
    
    private byte[] encodeMessage(Envelope envelope)
    {
        if(envelope.getMessage()==null)
        {
            this.getLogger().error("Communicator encodeMessage ERROR ENCODING MESSAGE:\n\t Message is null");
        }
        ByteList bytes = new ByteList();
        envelope.getMessage().encode(bytes);
        return bytes.toBytes();
    }
    
    private Envelope getMessage()
    {
        byte[] messageByteArray = new byte[Communicator.getMaxPacketSize()];
        DatagramPacket packet = new DatagramPacket(messageByteArray, messageByteArray.length);
        Message message = null;
        try
        {
            this.getLogger().debug("getMessage:\n\t starting to try to get message");
            socket.setSoTimeout(Communicator.getReceiveTimeoutInterval());
            socket.receive(packet);
            this.getLogger().debug("getMessage:\n\t got message successfully");
            message = MessageConstructor.constructMessage(packet.getData());
            return Envelope.createIncomingEnvelope(message, (InetSocketAddress) packet.getSocketAddress());
            //return Envelope.createIncomingEnvelope(packet.getData(), (InetSocketAddress) packet.getSocketAddress(), this.getInstanceNumber());
        }
        catch(SocketTimeoutException e)
        {
            this.getLogger().debug("getMessage socket timed out:\n\t SocketTimeoutException");
        }
        catch(IOException e)
        {
            System.err.println("Error getting message, IOException");
            this.getLogger().error("ERROR GETTING MESSAGE:\n\tIOEXCEPTION");
            e.printStackTrace();
        }
        catch(Exception e)
        {
            System.err.println("Error getting message, General Exception");
            this.getLogger().error("ERROR GETTING MESSAGE:\n\t General Exception");
            e.printStackTrace();
        }
        
        return null;
    }
    
    private static int addCommunicator(Communicator communicator)
    {
        Communicator.getCommunicatorList().add(communicator);
        return Communicator.getCommunicatorList().size()-1;
    }

    // <editor-fold defaultstate="collapsed" desc=" Getters ">

    public static int getWorkerSleepInterval()
    {
        return Communicator.WORKER_SLEEP_INTERVAL;
    }
    public static int getMaxPacketSize()
    {
        return Communicator.MAX_PACKET_SIZE;
    }
    public ConcurrentLinkedQueue getInputQueue()
    {
        return this.inputQueue;
    }
    public ConcurrentLinkedQueue getOutputQueue()
    {
        return this.outputQueue;
    }
    public DatagramSocket getSocket() 
    {
        return this.socket;
    }
    public int getPortNumber() 
    {
        return this.portNumber;
    }
    public static Communicator getCommunicatorInstance(int instanceNumber)
    {
        return (Communicator)Communicator.getCommunicatorList().get(instanceNumber);
    }
    @Override
    public String getThreadName()
    {
        return "Communicator";
    }
    public static int getReceiveTimeoutInterval()
    {
        return Communicator.RECEIVE_TIMEOUT_INTERVAL;
    }
    public static int getNumberOfInstances()
    {
        return Communicator.numberOfInstances;
    }
    public String getLogName()
    {
        return Communicator.class.getName() + this.getInstanceNumber();
    }
    
    private static ArrayList getCommunicatorList()
    {
        return Communicator.communicatorList;
    }
    private int getCommunicatorNumber()
    {
        return this.communicatorNumber;
    }
    public static Communicator getMainCommunicator()
    {
        return (Communicator)Communicator.getCommunicatorList().get(0);
    }
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Setters ">
    public void setSocket(DatagramSocket socket) {
        this.socket = socket;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
        this.initializeSocket();
    }
    public void setInputQueue(ConcurrentLinkedQueue queue)
    {
        this.inputQueue = queue;
    }
    public void setInstanceNumber()
    {
        this.instanceNumber = Communicator.numberOfInstances + 1;
    }
    public static void setNumberOfInstances(int number)
    {
        Communicator.numberOfInstances = number;
    }
    public void setOutputQueue(ConcurrentLinkedQueue queue)
    {
        this.outputQueue=queue;
    }
    private void setCommunicatorNumber(int number)
    {
        this.communicatorNumber = number;
    }
// </editor-fold>
}
