package Strategy;

import ClientConversations.ClientShellConversation;
import MessagePackage.GetShellProtocol.GetShellRequest;
import TanksCommon.Communicator;
import TanksCommon.Envelope;
import tanks.TanksClientModel;
import java.net.InetSocketAddress;

public class GetShellStrategy extends Strategy
{
    
    public GetShellStrategy(InetSocketAddress address)
    {
        this.getLogger().info("GetShellStrategy constructor\n\tGetShellStrategy created");
    }

    @Override
    public void strategize()
    {
        ClientShellConversation.initiate();
    }
    
    @Override
    protected String getLogName()
    {
        return this.getClass().getName();
    }
}
