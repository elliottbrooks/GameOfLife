package bbc.gameoflifestub;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
	
public class LifeTest {

	protected Set<Cell> setOfCells;
	protected Life life;
	
	@Before
	public void setUp(){
    	setOfCells = new HashSet<Cell>();
    	setOfCells.add(new Cell(1,1));
    	setOfCells.add(new Cell(2,2));
        life = new Life(setOfCells);
    }
	
	@Test
	public void testInitialisation()
	{
		assertEquals(2, life.getLiveCells().size());
	}

    @Test
    public void testUnderpopulation()
    {
        assertFalse(life.cellShouldSurvive(0));
		assertFalse(life.cellShouldSpawn(0));
        assertFalse(life.cellShouldSurvive(1));
		assertFalse(life.cellShouldSpawn(1));
		assertFalse(life.cellShouldSpawn(2));
    }

	@Test
	public void testOvercrowding(){
		assertFalse(life.cellShouldSurvive(4));
		assertFalse(life.cellShouldSurvive(5));
		assertFalse(life.cellShouldSurvive(6));
		assertFalse(life.cellShouldSurvive(7));
		assertFalse(life.cellShouldSurvive(8));

		assertFalse(life.cellShouldSpawn(4));
		assertFalse(life.cellShouldSpawn(5));
		assertFalse(life.cellShouldSpawn(6));
		assertFalse(life.cellShouldSpawn(7));
		assertFalse(life.cellShouldSpawn(8));
	}

	@Test
	public void testSpawning(){
		assertTrue(life.cellShouldSpawn(3));
	}

	@Test
	public void testSurvival(){
		assertTrue(life.cellShouldSurvive(2));
		assertTrue(life.cellShouldSurvive(3));
	}
}
