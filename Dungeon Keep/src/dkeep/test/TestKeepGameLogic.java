package dkeep.test;

import static org.junit.Assert.*;

import org.junit.Test;

import dkeep.logic.Game;
import dkeep.logic.GameMap;
import dkeep.logic.Ogre;

public class TestKeepGameLogic
{
	@Test
    public void testHeroIsCapturedByOgre()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game(gameMap, new TestOgre(3,1,3,2));
        assertEquals(-1, game.MoveHero(2,1));// move hero right. 
    }
	
	@Test
    public void testHeroPicksUpKey()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game(gameMap, new TestOgre(3,1,3,2));
        assertEquals(0, game.MoveHero(1,2)); // move hero down.
        assertEquals(0, game.MoveHero(1,3)); // move hero down to the key cell
        assertEquals('K', game.GetHero().GetSymbol());   
    }
	
	@Test
    public void testHeroCantLeave()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game(gameMap, new TestOgre(3,1,3,2));
        assertEquals(0, game.MoveHero(0,1));// move hero left. 
        assertEquals(1, game.GetHero().GetX());//hero must remain in postion 1 at x
    }
	
	@Test
    public void testHeroOpenDoor()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game(gameMap, new TestOgre(3,1,3,2));
        game.MoveHero(1,2);//move hero down
        game.MoveHero(1,3);//move hero down
        assertTrue(game.GetMap().IsDoorOpen());
    }
	
	@Test
    public void testHeroWinsKeepLevel()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game(gameMap, new TestOgre(3,1,3,2));
        game.MoveHero(1,2);//moves down
        game.MoveHero(1,3);//moves down
        game.MoveHero(1,2);//moves up
        game.MoveHero(1,1);//moves up
        assertEquals(0, game.MoveHero(0,1));//moves up
        assertEquals(0, game.GetHero().GetX());//hero must be on top of the door cell
        game.MoveHero(-1,1);//moves up
        assertTrue(game.IsEndOfGame());
    }
	
	@Test
	public void TestOgreAndClubRandomness()
	{
		GameMap gameMap = new KeepMapTests();
		int init_x = 3;
		int init_y = 1;
        Game game = new Game(gameMap, new Ogre(init_x,init_y,3,2));
        game.MoveHero(1,2);//moves down
        Ogre ogre = game.GetOgres().get(0);
        if (!gameMap.MoveTo(ogre.GetX(), ogre.GetY()))
        	fail("Ogre moved onto forbiden position.");
        else if (!CellsAreAdjacent(init_x, init_y, ogre.GetX(), ogre.GetY()))
        	fail("Ogre moved onto not adjacent position (relative to his initial position)");
        else if (!CellsAreAdjacent(ogre.GetX(), ogre.GetY(), ogre.GetClubX(), ogre.GetClubY()))
        	fail("Club new position is not adjacent to new Ogre position");
	}
	
	public boolean CellsAreAdjacent(int x1, int y1, int x2, int y2)
	{
		if (x1 == x2 - 1 && y1 == y2)
			return true;
		else if (x1 == x2 + 1 && y1 == y2)
        	return true;
		else if (x1 == x2 && y1 == y2 - 1)
        	return true;
		else if (x1 == x2 && y1 == y2 + 1)
        	return true;
		else
			return false;
	}
}
