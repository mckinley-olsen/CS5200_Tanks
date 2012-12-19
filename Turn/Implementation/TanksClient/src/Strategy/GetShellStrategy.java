package Strategy;

import MessagePackage.GetShellProtocol.GetShellRequest;
import TanksCommon.Communicator;
import TanksCommon.Envelope;
import tanks.TanksClientModel;
import java.net.InetSocketAddress;

public class GetShellStrategy extends Strategy
{
    
    public GetShellStrategy(InetSocketAddress address)
    {
        super(address, null);
        this.getLogger().info("GetShellStrategy constructor\n\tGetShellStrategy created");
    }

    @Override
    public void strategize(int communicatorNumber)
    {
        this.setCommunicator(Communicator.getCommunicatorInstance(communicatorNumber));
        createPackAndSendRequest();
    }
    
    public void createPackAndSendRequest()
    {
        this.getLogger().info("GetShellStrategy createPackAndSendRequest\n\tGetShellRequest created\n\tTo: "+TanksClientModel.getShellManagerAddress());
        GetShellRequest request = new GetShellRequest();
        Envelope e = Envelope.createOutgoingEnvelope(request, TanksClientModel.getShellManagerAddress());
        this.getCommunicator().addToOutputQueue(e);
    }

    @Override
    protected String getLogName()
    {
        return this.getClass().getName();
    }
}
