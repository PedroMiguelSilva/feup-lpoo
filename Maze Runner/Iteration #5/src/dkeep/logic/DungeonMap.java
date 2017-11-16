package dkeep.logic;

import java.util.ArrayList;

public class DungeonMap extends GameMap {

	private char[][] dungeonMap = { { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
			{ 'X', ' ', ' ', ' ', 'I', ' ', 'X', ' ', ' ', 'X' }, { 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
			{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X' }, { 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
			{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' }, { 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
			{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X' }, { 'X', ' ', 'I', ' ', 'I', ' ', 'X', 'k', ' ', 'X' },
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };

	/**
	 * Class constructor
	 */
	public DungeonMap() {
		map = dungeonMap;
		hero = new Hero(1, 1, 'H');
		guard = new Guard(8, 1);
		lever = true;
		setMapDimension(10);
	}
	
	@Override
	/**
	 * Function that opens doors
	 * @see dkeep.logic.GameMap#openDoors()
	 */
	public void openDoors() {

		setDoorsOpen(true);

		for (int i = 0; i < map.length; i++) {
			if (map[i][0] == 'I')
				map[i][0] = 'S';
		}
	}

	@Override
	/**
	 * @return Next map to be played
	 * @see dkeep.logic.GameMap#nextMap()
	 */
	public GameMap nextMap() {
		GameMap Map;

		if (customMap == null) {
			Map = new CrazyOgreMap();
		} else {
			Map = new Map(customMap);
			Map.getHero().setIsArmed(true);
			Map.ogreMove = true;
		}

		return Map;
	}
}
