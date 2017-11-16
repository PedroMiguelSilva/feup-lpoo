package dkeep.logic;

import java.util.ArrayList;

public class CrazyOgreMap extends GameMap {

	private char[][] keepMap = { { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
			{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', 'k', 'X' }, { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' }, { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' }, { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' }, { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' }, };

	/**
	 * Class constructor
	 */
	public CrazyOgreMap() {

		map = keepMap;
		hero = new Hero(1, 7, 'A');
		hero.setIsArmed(true);
		ogres = new ArrayList<Ogre>();
		ogreMove = true;
		setMapDimension(9);
	}
}