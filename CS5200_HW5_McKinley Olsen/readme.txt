1.
Modified Player List protocol and Fight List protocol so that no new connection is initiated to transfer the list. 
Initially the protocol required the two endpoints to open a TCP connection to transfer their lists, to facilitate a reliable transfer for a (possibly) large message. 
The communication of these lists has now been made reliable, and the new connection messages seemed superfluous, so I simplified it by making it a request-reply protocol.
The diagrams and descriptions should be updated with the removal of this extra connection.
Section 6 in the document is a new section which explains how conversations act and how messages are perceived across endpoints. The concerns we were required to address in this homework are addressed in this section.

2.
Reliable communications are implemented for the following protocols:
Register: in Client (ClientRegisterConversation). It was decided that the FightManager keeping track of the reply would be useless, as a duplicate request of a registration cannot be detected when the requesting process has not been assigned an ID by the FightManager.
Fight list: in Client (ClientFightListConversation) and FightManager (FightManagerFightListConversation)
Player list: in Client (ClientPlayerListConversation) and FightManager (FightManagerPlayerListConversation)
Join fight: in Client (ClientJoinFightConversation) and FightManager (FightManagerJoinFightConversation)
Unregister: in Client (ClientUnregisterInitiatorConversation and ClientUnregisterReceiverConversation) and FightManager (FightManagerUnregisterInitiatorConversation and FightManagerUnregisterReceiverConversation)
Get shell: in Client (ClientShellConversation) and ShellManager(ShellManagerShellConversation)
Fill shell: in Client (ClientFillConversation) and GunpowderManager(GunpowderManagerFillConversation)
Location list: in Client (ClientLocationsRequester and ClientLocationsRequestee) and FightManager (FightManagerLocationListConversation)

I decided not to implement reliable communications for statistics updates, as updates will continually be attempted with more up-to-date data anyway.

3.
My project contains 10 protocols, and I've implemented the above 8. I believe I have implemented 80% of the protocols and functionality in the game. 
You should be able to test this on the client by the added buttons and status log.

4.
You should be able to test them by opening the projects in Netbeans and starting them. Alternatively, you can try running the jars in the dist folders of each project. They should start right up.
If there is some strange popup error about a link error on the client, please ignore it. Something got messed up recently when I worked on it on a different machine. Just hit okay and it will continue on fine.
If there are any problems, please understand that it took me 30 hours to create the conversations, test them, and refactor. I've hardly had any time to test the real implemenation, and I'm now significantly over the project's time estimate.

5.
After opening each project in netbeans, you can navigate to the testpackages folder and right click, run test on each test file
The Client, FightManager, ShellManager, and GunpowderManager all have tests for their conversations.

6.
I believe I have completed:
"Implement a general-­-purpose, reusable solution to managing conversations" (15 points)
by using the implementation that manages conversations in TanksModel in the TanksCommon project.
All other models used in the managers and client inherit and use this implementation to manage their conversations

I have also added additional unit test cases for the following messages:
Reply
Request
FightListRequest
FightListReply
JoinFightRequest
PlayerListRequest
PlayerListReply
ShellFiredRequest
ShellFiredReply
FireShellRequest

Each of these message types have their Encode/Decode operations tested, so I believe I would receive 10 (or more) points for their testing. These test cases are found in the TanksMessageClasses project.
I believe I have completed advanced requirements to earn the full 25 points