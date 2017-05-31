package bship.logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class ShipPlacement 
{
	private GameMap map;
	private HashMap<String, Ship> shipsByName = new HashMap<String, Ship>();
	private HashMap<String, Boolean> shipIsAlreadyPlaced = new HashMap<String, Boolean>();
	
	
	public ShipPlacement(GameMap map)
	{
		this.map = map;
		fillShipsHashMaps();
	}

	private void fillShipsHashMaps() 
	{
		ArrayList<String> shipsNames  = new ArrayList<String>();
		shipsNames.add("BattleShip");
		shipsNames.add("Carrier");
		shipsNames.add("Cruiser1");
		shipsNames.add("Cruiser2");
		shipsNames.add("Destroyer");
		shipsNames.add("Submarine");
		
		shipsByName.put(shipsNames.get(0), new BattleShip());
		shipsByName.put(shipsNames.get(1), new Carrier());
		shipsByName.put(shipsNames.get(2), new Cruiser());
		shipsByName.put(shipsNames.get(3), new Cruiser());
		shipsByName.put(shipsNames.get(4), new Destroyer());
		shipsByName.put(shipsNames.get(5),new Submarine());
		
		for (String shipName : shipsNames)
		{
			shipIsAlreadyPlaced.put(shipName, false);
		}		
	}
	
	public boolean isShipsAlreadyPlaced(String shipName)
	{
		return shipIsAlreadyPlaced.get(shipName);
	}

	public boolean isShipDropValid(Ship ship)
	{
		ArrayList<Coords> shipCoords = ship.getCoords();
		if(!map.areListOfCoordsInMapRange(shipCoords))
			return false;
		
		ArrayList<Coords> coordsArray = new ArrayList<Coords>();
		coordsArray = Coords.getSurroundingCoords(shipCoords);
		for(Coords currCoords: coordsArray)
		{
			if(!map.areCoordsInMapRange(currCoords))
				continue;
			
			if(map.getCellState(currCoords) != null)
				if(map.getCellState(currCoords).hasShip())
					return false;
		}
		
		return true;
	}
	
	public void pickUpShip(String shipName)
	{
		if (shipIsAlreadyPlaced.get(shipName))
		{
			Ship ship = shipsByName.get(shipName);
			for(Coords currCoords: ship.getCoords())
			{
				map.setCellState(currCoords, null);
			}
			ship.clearCoords();
			shipIsAlreadyPlaced.put(shipName, false);
		}
	}
	
	public boolean dropShip(Coords initCoord, String shipName, String direction)
	{
		Ship ship = shipsByName.get(shipName);
		ship.setDirection(direction);
		ship.fillCoordsByInitCoord(initCoord, ship.getDirection());
		
		if(!isShipDropValid(ship))
		{
			ship.clearCoords();
			return false;
		}
		
		ArrayList<Coords> shipCoords = ship.getCoords();
		CellState cellState = new AllyCellState(ship);
		for(Coords currCoords: shipCoords)
		{
			map.setCellState(currCoords, cellState);
		}
		
		shipIsAlreadyPlaced.put(shipName, true);
		return true;
	}
}
