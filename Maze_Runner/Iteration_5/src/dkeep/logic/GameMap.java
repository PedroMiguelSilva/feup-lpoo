package dkeep.logic;

import java.util.ArrayList;

public abstract class GameMap {

	protected char[][] map;
	protected Hero hero;
	protected Guard guard;
	protected ArrayList<Ogre> ogres;
	protected int mapDimension;
	protected char[][] customMap;

	protected boolean doorsOpen = false;
	protected boolean lever = false;
	protected boolean ogreMove = false;
	protected boolean unlockDoor = false;

	/**
	 * Class Constructor
	 */
	public GameMap() {
	}
	
	/**
	 * @return Map matrix
	 */
	public char[][] getMap() {
		return map;
	}

	/**
	 * @return Hero
	 */
	public Hero getHero() {
		return hero;
	}

	/**
	 * @return Guard
	 */
	public Guard getGuard() {
		return guard;
	}

	/**
	 * Function that sets the personality of the guard
	 * @param personality String to be setted as personality
	 */
	public void setGuard(String personality) {
		if (guard != null)
			this.guard.setPersonality(personality);
	}

	/**
	 * @return Array list of the ogres
	 */
	public ArrayList<Ogre> getOgres() {
		return ogres;
	}
	
	/**
	 * Function that sets ogres' array
	 * @param size Variable of type int with the size wanted for the ogres' array
	 */
	public void setOgres(int size) {
		if (ogres != null) {
			for (int k = 0; k < size; k++) {
				Ogre ogre = new Ogre(4, 1);
				ogres.add(ogre);
			}
		}
	}

	/**
	 * @return Boolean if the ogre should move or not
	 */
	public boolean isOgreMove() {
		return ogreMove;
	}

	/**
	 * Function that sets if teh ogre can move
	 * @param ogreMove Boolean to be setted as the ogre move
	 */
	public void setOgreMove(boolean ogreMove) {
		this.ogreMove = ogreMove;
	}

	/**
	 * @return Boolean if 'k' means lever
	 */
	public boolean isLever() {
		return lever;
	}

	/**
	 * Function that sets if it's lever or key
	 * @param lever Boolean if it's lever
	 */
	public void setLever(boolean lever) {
		this.lever = lever;
	}

	/**
	 * @return Boolean if the doors are are ready to be unlocked
	 */
	public boolean isUnlockDoor() {
		return unlockDoor;
	}

	/**
	 * Function that gets doors ready to be unlocked
	 * @param unlockDoor boolean if the doors are ready to be unlocked
	 */
	public void setUnlockDoors(boolean unlockDoor) {
		this.unlockDoor = unlockDoor;
	}

	/**
	 * Function that opens doors
	 * @param doorsOpen boolean to open doors
	 */
	public void setDoorsOpen(boolean doorsOpen) {
		this.doorsOpen = doorsOpen;
	}
	
	/**
	 * @return Boolean if the doors are open
	 */
	public boolean isDoorsOpen() {
		return doorsOpen;
	}

	/**
	 * @return Variable of type int with the dimension of the map
	 */
	public int getMapDimension() {
		return mapDimension;
	}

	/**
	 * Function that sets the map dimension
	 * @param mapDimension Variable of type int with the dimension wanted for the map
	 */
	public void setMapDimension(int mapDimension) {
		this.mapDimension = mapDimension;
	}

	/**
	 * @return Matrix of the map made by the user
	 */
	public char[][] getCustomMap() {
		return customMap;
	}

	/**
	 * Function that sets the map made by the user
	 * @param customMap Matrix of the map to be setted
	 */
	public void setCustomMap(char[][] customMap) {
		this.customMap = customMap;
	}

	/**
	 * Function that checks if the character in the position passed can move
	 * @param x Position x of the character
	 * @param y Position y of the character 
	 * @return True if can move
	 */
	public boolean moveTo(int y, int x) {
		if (hero.getSymbol() == 'K' && map[y][x] == 'I') {
			setUnlockDoors(true);
			return false;
		} else if (map[y][x] == 'X' || map[y][x] == 'I')
			return false;
		else
			return true;
	}

	/**
	 * Function that checks if the ogre in the position passed can move
	 * @param x Position x of the character
	 * @param y Position y of the character 
	 * @return True if can move
	 */
	public boolean ogreMoveTo(int y, int x) {
		if (map[y][x] == 'X' || map[y][x] == 'I')
			return false;
		else
			return true;
	}

	/**
	 * Function that checks the position of the key
	 * @param x Position x to be checked
	 * @param y Position y to be checked
	 * @return True if there's a key in that position
	 */
	public boolean gotKey(int y, int x) {
		if (map[y][x] == 'k')
			return true;
		else
			return false;
	}

	/**
	 * Function that opens doors
	 */
	public void openDoors() {

		setDoorsOpen(true);

		for (int i = 0; i < mapDimension; i++) {
			for (int j = 0; j < mapDimension; j++) {
				if (map[i][j] == 'I')
					map[i][j] = 'S';
			}
		}
	}

	/**
	 * Function that checks the position of the door
	 * @param x Position x to be checked
	 * @param y Position y to be checked
	 * @return True if there's a door in that position
	 */
	public boolean foundDoor(int y, int x) {
		if (map[y][x] == 'S')
			return true;
		else
			return false;
	}

	
	public GameMap nextMap() {
		return null;
	}

	/**
	 * Function that deletes key of the map
	 */
	public void deleteKey() {
		for (int i = 0; i < mapDimension; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == 'k')
					map[i][j] = ' ';
			}
		}
	}

}
