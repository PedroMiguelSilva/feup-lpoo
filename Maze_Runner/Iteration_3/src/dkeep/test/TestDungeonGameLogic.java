package dkeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import dkeep.logic.GameState;
import dkeep.logic.Map;
import dkeep.logic.DungeonMap;
import dkeep.logic.GameMap;

public class TestDungeonGameLogic {

	char[][] map = { { 'X', 'X', 'X', 'X', 'X' }, { 'X', 'H', ' ', 'G', 'X' }, { 'I', ' ', ' ', ' ', 'X' },
			{ 'I', 'k', ' ', ' ', 'X' }, { 'X', 'X', 'X', 'X', 'X' } };
	
	
	/*
	 *  TASK 1
	 */

	@Test // 1
	public void testMoveHeroIntoFreeCell() {
		GameMap Map = new Map(map);
		GameState state = new GameState();
		state.setCurrentMap(Map);
		state.currentMap.setLever(true);
		assertEquals(1, Map.getHero().getX());
		assertEquals(1, Map.getHero().getY());
		state.updatePos('s');
		assertEquals(1, Map.getHero().getX());
		assertEquals(2, Map.getHero().getY());
	}
	
	@Test // 2
	public void testMoveHeroIntoWall() {
		GameMap Map = new Map(map);
		GameState state = new GameState();
		state.setCurrentMap(Map);
		state.currentMap.setLever(true);
		assertEquals(1, Map.getHero().getX());
		assertEquals(1, Map.getHero().getY());
		state.updatePos('a');
		assertEquals(1, Map.getHero().getX());
		assertEquals(1, Map.getHero().getY());
	}
	
	@Test // 3
	public void testMoveHeroIntoGuard() {
		GameMap Map = new Map(map);
		GameState state = new GameState();
		state.setCurrentMap(Map);
		state.currentMap.setLever(true);
		assertEquals(1, Map.getHero().getX());
		assertEquals(1, Map.getHero().getY());
		assertEquals(0, state.gameOver);
		state.updatePos('d');
		assertEquals(1, state.gameOver);
	}
	
	@Test // 4
	public void testMoveHeroIntoCloseDoor() {
		GameMap Map = new Map(map);
		GameState state = new GameState();
		state.setCurrentMap(Map);
		state.currentMap.setLever(true);
		assertEquals(1, Map.getHero().getX());
		assertEquals(1, Map.getHero().getY());
		state.updatePos('s');
		state.updatePos('a');
		assertEquals(0, state.gameWon);
	}
	
	@Test // 5
	public void testMoveHeroIntoLeverCell() {
		GameMap Map = new Map(map);
		Map.setLever(true);
		GameState state = new GameState();
		state.setCurrentMap(Map);
		assertEquals(1, Map.getHero().getX());
		assertEquals(1, Map.getHero().getY());
		state.updatePos('s');
		state.updatePos('s');
		assertEquals(true, Map.gotKey(Map.getHero().getY(), Map.getHero().getX()));
	}
	
	@Test // 6
	public void testMoveHeroIntoOpenDoor() {
		GameMap Map = new Map(map);
		Map.setLever(true);
		GameState state = new GameState();
		state.setCurrentMap(Map);
		assertEquals(1, Map.getHero().getX());
		assertEquals(1, Map.getHero().getY());
		state.updatePos('s');
		state.updatePos('s');
		assertEquals(true, Map.gotKey(Map.getHero().getY(), Map.getHero().getX()));
		state.updatePos('a');
		assertEquals(1, state.gameWon);
	}
	

}
