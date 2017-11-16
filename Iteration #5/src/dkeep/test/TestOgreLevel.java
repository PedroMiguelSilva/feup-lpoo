package dkeep.test;

import static org.junit.Assert.*;

import org.junit.Test;

import dkeep.logic.GameMap;
import dkeep.logic.GameState;
import dkeep.logic.Map;
import dkeep.logic.GameState.Event;
import dkeep.logic.GameState.State;

public class TestOgreLevel {

	char[][] map = { { 'X', 'X', 'X', 'X', 'X' }, 
			{ 'I', ' ', ' ', 'k', 'X' }, 
			{ 'X', ' ', ' ', 'O', 'X' },
			{ 'X', 'H', ' ', ' ', 'X' },
			{ 'X', 'X', 'X', 'X', 'X' } };
	
	/*
	 *  TASK 2
	 */

	@Test // 1
	public void testMoveHeroIntoOgre() {
		GameMap Map = new Map(map);
		GameState state = new GameState();
		state.setCurrentMap(Map);
		state.updateState( Event.newGame);
		state.updateState( Event.levelUp);
		assertEquals(1, Map.getHero().getX());
		assertEquals(3, Map.getHero().getY());
		state.updatePos('d');
		state.updatePos('d');
		state.updatePos('w');
		assertEquals(state.currentState, State.gameOver);
		state.setOgres();
	}
	
	@Test // 2
	public void testMoveHeroIntoKeyCell() {
		GameMap Map = new Map(map);
		GameState state = new GameState();
		state.setCurrentMap(Map);
		state.updateState( Event.newGame);
		state.updateState( Event.levelUp);
		assertEquals(1, Map.getHero().getX());
		assertEquals(3, Map.getHero().getY());
		state.updatePos('w');
		state.updatePos('w');
		state.updatePos('d');
		state.updatePos('d');
		assertEquals('K', Map.getHero().getSymbol());
		assertEquals(false, Map.gotKey(Map.getHero().getY(), Map.getHero().getX()));
	}
	
	@Test // 3
	public void testMoveHeroIntoClosedExitDoor() {
		GameMap Map = new Map(map);
		GameState state = new GameState();
		state.setCurrentMap(Map);
		state.updateState( Event.newGame);
		state.updateState( Event.levelUp);
		assertEquals(1, Map.getHero().getX());
		assertEquals(3, Map.getHero().getY());
		state.updatePos('w');
		state.updatePos('w');
		state.updatePos('a');
		assertEquals(state.currentState, State.gameOver);
		state.printMap();
	}
	
	@Test // 4
	public void testHeroOpensDoor() {
		GameMap Map = new Map(map);
		GameState state = new GameState();
		state.setCurrentMap(Map);
		state.updateState( Event.newGame);
		state.updateState( Event.levelUp);
		assertEquals(1, Map.getHero().getX());
		assertEquals(3, Map.getHero().getY());
		state.updatePos('w');
		state.updatePos('w');
		state.updatePos('d');
		state.updatePos('d');
		assertEquals(false, Map.gotKey(Map.getHero().getY(), Map.getHero().getX()));
		assertEquals('K', Map.getHero().getSymbol());
		state.updatePos('a');
		state.updatePos('a');
		state.updatePos('a');
		assertEquals(true, Map.foundDoor(Map.getHero().getY(), Map.getHero().getX() - 1));
	}
	
	@Test // 5
	public void testMoveHeroIntoOpenExitDoor() {
		GameMap Map = new Map(map);
		GameState state = new GameState();
		state.setCurrentMap(Map);
		state.updateState( Event.newGame);
		state.updateState( Event.levelUp);
		assertEquals(1, Map.getHero().getX());
		assertEquals(3, Map.getHero().getY());
		state.updatePos('w');
		state.updatePos('w');
		state.updatePos('d');
		state.updatePos('d');
		assertEquals(false, Map.gotKey(Map.getHero().getY(), Map.getHero().getX()));
		assertEquals('K', Map.getHero().getSymbol());
		state.updatePos('a');
		state.updatePos('a');
		state.updatePos('a');
		state.updatePos('a');
		assertEquals(state.currentState, State.gameWin);
	}
}