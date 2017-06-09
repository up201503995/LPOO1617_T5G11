package bship.logic;

import java.util.ArrayList;

public class Destroyer extends Ship
{
	private static final long serialVersionUID = 3576946065291967481L;

	public Destroyer()
	{
		super(2, "Destroyer");
	}
	
	public Destroyer(ArrayList<Coords> coords,  String direction)
	{
		super(2, coords, direction, "Destroyer");
	}

	@Override
	public Ship getCopy()
	{
		Ship newShip = new Cruiser(coords, direction);
		return newShip;
	}
}