package bship.logic;

import java.io.IOException;
import java.util.HashMap;

import bship.network.data.BattleShipData;
import bship.network.data.LobbyData;
import bship.network.data.LobbyInviteData;
import bship.network.data.LobbyInviteResponseData;
import bship.network.data.LobbyInviteResponseData.Response;
import bship.network.data.LobbyInvitedData;

public class InLobby extends PlayerState
{
	public InLobby(Player player) 
	{
		super(player);
	}

	@Override
	public void HandleReceivedData(BattleShipData data) 
	{
		if (!(data instanceof LobbyData))
			return;
		
		if(data instanceof LobbyInviteData)
		{
			LobbyInviteData inviteData = (LobbyInviteData) data;
			BattleShipServer battleShipServer = player.getBattleShipServer();
			boolean succeeded = battleShipServer.invitePlayer(player.getUsername(), inviteData.getInvitedPlayerName());
			LobbyInviteResponseData response;
			if (!succeeded)
			{
				response = new LobbyInviteResponseData(Response.UNSUCESSFUL); //TODO meter o define a dizer unsucceeded
				try
				{
					player.sendData(response);
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else if(data instanceof LobbyInviteResponseData)
		{
			LobbyInviteResponseData responseData = (LobbyInviteResponseData)data;
			try
			{
				Player opponent = player.getOpponent();
				if (opponent == null)
					return;
				opponent.sendData(responseData);
				if (responseData.wasAccepted())
				{
					opponent.setOpponent(player);
					player.setState(new InShipPlacement(player));
					opponent.setState(new InShipPlacement(opponent));
				}
				else
					player.setOpponent(null);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
			if(responseData.wasAccepted())
				player.setState(new InShipPlacement(player));
		}
				
		
	}

}
