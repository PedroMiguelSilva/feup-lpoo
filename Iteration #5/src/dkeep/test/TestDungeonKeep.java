package dkeep.test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import dkeep.logic.CrazyOgreMap;
import dkeep.logic.DungeonMap;
import dkeep.logic.GameMap;
import dkeep.logic.GameState;
import dkeep.logic.GameState.Event;

public class TestDungeonKeep {
	
	char[][] customMap = {
			{ 'X', 'X', 'X', 'X', 'X' }, 
			{ 'X', 'H', ' ', ' ', 'X' }, 
			{ 'I', ' ', ' ', ' ', 'X' },
			{ 'I', 'k', ' ', 'O', 'X' },
			{ 'X', 'X', 'X', 'X', 'X' } };
	
	char[][] customMapNoHeroAndOgre = {
			{ 'X', 'X', 'X', 'X', 'X' }, 
			{ 'X', ' ', ' ', ' ', 'X' }, 
			{ 'I', ' ', ' ', ' ', 'X' },
			{ 'I', 'k', ' ', ' ', 'X' },
			{ 'X', 'X', 'X', 'X', 'X' } 
	};

	@Test
	public void testDungeonNextMap() {
		GameMap keepMap= new DungeonMap();
		GameState state = new GameState();
		state.setCurrentMap(keepMap);
		state.setNextMap();
		assertTrue(state.currentMap instanceof CrazyOgreMap);
	}
	
	@Test
	public void testCustomMap(){
		GameMap keepMap= new DungeonMap();
		GameState state = new GameState();
		state.setCurrentMap(keepMap);
		state.currentMap.setCustomMap(customMap);
		state.setNextMap();
		Assert.assertArrayEquals(state.currentMap.getMap(), customMapNoHeroAndOgre);
		
	}
	
	@Test
	public void testNumOgres(){
		GameMap keepMap= new DungeonMap();
		GameState state = new GameState();
		state.setCurrentMap(keepMap);
		state.setOgres(4);
		state.setNextMap();
		assertTrue(state.currentMap instanceof CrazyOgreMap);
		assertEquals(state.currentMap.getOgres().size(),4);		
		
	}
	
	@Test
	public void testRookieCircuit(){
		GameMap keepMap= new DungeonMap();
		GameState state = new GameState();
		state.setCurrentMap(keepMap);
		state.currentMap.setLever(true);
		state.setGuard("Rookie");
		state.updateState( Event.newGame);
		
		assertEquals(state.currentMap.getGuard().getX(),8);
		assertEquals(state.currentMap.getGuard().getY(),1);
		
		state.updatePos('a');
		state.updatePos('a');
		assertEquals(state.currentMap.getGuard().getX(),7);
		assertEquals(state.currentMap.getGuard().getY(),2);
		
		
		for(int i =0; i < 22; i++){
			state.updatePos('a');
		}
		
		assertEquals(state.currentMap.getGuard().getX(),8);
		assertEquals(state.currentMap.getGuard().getY(),1);
		
	}
	
	@Test (timeout = 5000)
	public void testDrunkenCircuit(){
		GameMap keepMap= new DungeonMap();
		GameState state = new GameState();
		state.setCurrentMap(keepMap);
		state.currentMap.setLever(true);
		state.setGuard("Drunken");
		state.updateState( Event.newGame);
		
		assertEquals(state.currentMap.getGuard().getX(),8);
		assertEquals(state.currentMap.getGuard().getY(),1);
		
		boolean g=false;
		
		while(true){
			
			state.updatePos('a');
			
			int x=state.currentMap.getGuard().getX();
			int y=state.currentMap.getGuard().getY();
			
			if(state.currentMap.getGuard().getSymbol()=='g'){
				g=true;
				break;
			}
			
		}
		assertTrue(g);
	}
}
