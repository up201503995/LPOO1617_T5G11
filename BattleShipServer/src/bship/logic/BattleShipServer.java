package bship.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import bship.network.data.BattleShipData;
import bship.network.data.LobbyInvitedData;
import bship.network.data.LoginRequestData;
import bship.network.data.LoginResponseData;
import bship.network.sockets.ClientThread;
import bship.network.sockets.Server;

public class BattleShipServer
{
	private Server server;
	private ArrayList<Player> inLobbyPlayers;
	private ArrayList<Player> inGamePlayers;
	private HashMap<String, Player> battleshipPlayers;
	
	public static void main(String argv[])
	{
		BattleShipServer battleShipServer = new BattleShipServer();
	}
	
	public BattleShipServer()
	{
		server = new Server(this);
		server.startServer();
		battleshipPlayers = new HashMap<String, Player>();
		inLobbyPlayers = new ArrayList<Player>();
		inGamePlayers = new ArrayList<Player>();
	}
	
	public ArrayList<Player> getInLobbyPlayers()
	{
		return inLobbyPlayers;
	}
	
	public ArrayList<Player> getInGamePlayers()
	{
		return inGamePlayers;
	}
	
	public ArrayList<Player> getOnlinePlayers() 
	{
		ArrayList<Player> onlinePlayers = new ArrayList<Player>();
		onlinePlayers.addAll(inLobbyPlayers);
		onlinePlayers.addAll(inGamePlayers);
		
		return onlinePlayers;
	}

	public HashMap<String, Player> getBattleshipPlayers()
	{
		return battleshipPlayers;
	}

	public LoginResponseData newPlayerConnected(ClientThread thread, BattleShipData data)
	{
		LoginRequestData login = (LoginRequestData) data;
		String username = login.getUsername();
		String password = login.getPassword();
		
		Player newPlayer;
		if(battleshipPlayers.containsKey(username))
		{
			newPlayer = battleshipPlayers.get(username);
			if(!password.equals(newPlayer.getPassword()) || !(newPlayer.getState() instanceof Offline))
				return new LoginResponseData(false);
		}
		else
		{
			newPlayer = new Player(this, username, password);
			battleshipPlayers.put(username, newPlayer);
		}
		
		newPlayer.setThread(thread);
		thread.setPlayer(newPlayer);
		inLobbyPlayers.add(newPlayer);
		
		newPlayer.setState(new InLobby(newPlayer));
		//TODO:reenviar a todos so online players in lobby a nova informašao
		return new LoginResponseData(true);
	}
	
	public void playerDisconnected(ClientThread thread)
	{
		Player player = thread.getPlayer();
		
		//In case the player failed to log in, the thread will terminate but no PLayer has been assign to it
		if(player == null)
			return;
		player.setState(new Offline(player));
		
		//only is removed from one of the lists, but we dont know in which one the player is, so we need to check in both
		inLobbyPlayers.remove(player);
		inGamePlayers.remove(player);
		
		//TODO:reenviar a todos so online players in lobby a nova informašao
	}
	
	public void stopServer()
	{
		server.stopServer();
	}

	public boolean invitePlayer(String inviterPlayerName, String invitedPlayerName) 
	{
		Player invitedPlayer = battleshipPlayers.get(invitedPlayerName);
		
		if(invitedPlayer.isBusy())
			return false;
		
		LobbyInvitedData inviteData = new LobbyInvitedData(inviterPlayerName);
		try
		{
			invitedPlayer.sendData(inviteData);
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean canGameBeInitiated(Player player1, Player player2)
	{
		PlayerState player1State = player1.getState();
		PlayerState player2State = player2.getState();
		
		return (player1State instanceof ReadyForGame) && (player2State instanceof ReadyForGame);
	}
	
}
