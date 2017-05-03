package bship.logic;

import java.util.ArrayList;
import java.util.HashMap;

import bship.network.data.BattleShipData;
import bship.network.data.LoginData;
import bship.network.sockets.ClientThread;
import bship.network.sockets.Server;

public class BattleShipServer
{
	private Server server;
	private ArrayList<Player> onlinePlayers;
	private HashMap<String, Player> battleshipPlayers;
	
	public static void main(String argv[])
	{
		BattleShipServer battleShipServer = new BattleShipServer();
	}
	
	public BattleShipServer()
	{
		server = new Server(this);
		server.startServer();
		onlinePlayers = new ArrayList<Player>();
	}
	
	public boolean newPlayerConnected(ClientThread thread, BattleShipData data)
	{
		LoginData login = (LoginData) data;
		String username = login.getUsername();
		
		Player newPlayer;
		if(battleshipPlayers.containsKey(username))
		{
			newPlayer = battleshipPlayers.get(username);
			if(login.getPassword() == newPlayer.getPassword())
				return false;
		}
		else
		{
			newPlayer = new Player();
			battleshipPlayers.put(username, newPlayer);
		}
		
		thread.setPlayer(newPlayer);
		onlinePlayers.add(newPlayer);
		
		return true;
	}
}
