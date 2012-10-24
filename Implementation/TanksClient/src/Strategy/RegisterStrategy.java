package Strategy;

import MessagePackage.DeterminableEnum;
import MessagePackage.RegisterProtocol.RegisterRequest;
import TanksCommon.Communicator;
import TanksCommon.Envelope;
import tanks.TanksClientModel;
import java.net.InetAddress;
import java.net.InetSocketAddress;


public class RegisterStrategy extends Strategy
{
    public RegisterStrategy(InetSocketAddress address)
    {
        super(address, null);
        this.getLogger().info("RegisterStrategy constructor\n\tRegisterStrategy created");
    }
    
    @Override
    public void strategize(int communicatorNumber)
    {
        this.setCommunicator(Communicator.getCommunicatorInstance(communicatorNumber));
        
        this.createPackAndSendRequest();
    }
    
    private void createPackAndSendRequest()
    {
        this.getLogger().info("RegisterStrategy createPackAndSendRequest\n\tRegisterRequest created with name: " + TanksClientModel.getPlayerName()+"\n\tTo: "+TanksClientModel.getFightManagerAddress());
        RegisterRequest request = new RegisterRequest(TanksClientModel.getPlayerName());
        Envelope e = Envelope.createOutgoingEnvelope(request, TanksClientModel.getFightManagerAddress());
        this.getCommunicator().addToOutputQueue(e);
    }
    
    @Override
    protected String getLogName()
    {
        return this.getClass().getName();
    }
}
