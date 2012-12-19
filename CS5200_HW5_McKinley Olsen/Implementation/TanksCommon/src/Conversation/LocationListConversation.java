package Conversation;

import MessagePackage.LocationListProtocol.LastLocationsRequest;
import MessagePackage.LocationListProtocol.LocationListReply;
import MessagePackage.LocationListProtocol.LocationListRequest;

public abstract class LocationListConversation extends Conversation
{
    LocationListRequest listRequest;
    LastLocationsRequest locationsRequest;
    LocationListReply listReply;
    
    public void setListRequest(LocationListRequest request)
    {
        this.listRequest = request;
    }
    public void setLocationsRequest(LastLocationsRequest request)
    {
        this.locationsRequest = request;
    }
    public void setListReply(LocationListReply reply)
    {
        this.listReply = reply;
    }
    
    public LocationListRequest getListRequest()
    {
        return this.listRequest;
    }
    public LastLocationsRequest getLocationsRequest()
    {
        return this.locationsRequest;
    }
    public LocationListReply getListReply()
    {
        return this.listReply;
    }
}
