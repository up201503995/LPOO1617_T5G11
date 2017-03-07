package dkeep.logic;

import java.util.Arrays;

public class KeepMap implements GameMap
{
	private char map[][] = {
			{ 'X','X','X','X','X','X','X','X','X' },
			{ 'I',0,0,0,0,0,0,'k','X' },
			{ 'X',0,0,0,0,0,0,0,'X' },
			{ 'X',0,0,0,0,0,0,0,'X' },
			{ 'X',0,0,0,0,0,0,0,'X' },
			{ 'X',0,0,0,0,0,0,0,'X' },
			{ 'X',0,0,0,0,0,0,0,'X' },
			{ 'X',0,0,0,0,0,0,0,'X' },
			{ 'X','X','X','X','X','X','X','X','X' }
		};
	
	private int map_x_size = 9;
	private int map_y_size = 9;
	
	public boolean MoveTo(int x, int y)
	{
		if (x > 8 || x < 0) //Out of range of the map in x
			return false;
		
		if (y > 8 || y < 0) //Out of range of the map in y
			return false;
		
		if (map[y][x] == 'X' || (map[y][x] == 'I' && map[y][x+1] != 'K'))
			return false;
		
		return true;
	}
	
	public char[][] GetMap()
	{
		return map;
	}
	
	public int GetMapXSize()
	{
		return map_x_size;
	}
	
	public int GetMapYSize()
	{
		return map_y_size;
	}
	
	
	public GameMap NextMap()
	{
		return null;
	}
	
	public void SetCellAt(int x, int y, char symbol)
	{
		map[y][x] = symbol;
	}
	
	public char GetCellAt(int x, int y)
	{
		return map[y][x];
	}
	
	
	public char[][] GetMapCopy()
	{
		char map_copy[][] = new char[map_y_size][map_x_size];
		
		for (int y = 0; y < map_y_size; y++)
			map_copy[y] = Arrays.copyOf(map[y], map_x_size);
		
		return map_copy;
	}
}
