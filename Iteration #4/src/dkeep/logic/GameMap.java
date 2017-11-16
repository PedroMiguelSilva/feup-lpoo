package dkeep.logic;

import java.util.ArrayList;

public abstract class GameMap {

	protected char[][] map;
	protected Hero hero;
	protected Guard guard;
	protected ArrayList<Ogre> ogres;

	protected boolean lever = false;
	protected boolean ogreMove = false;
	protected boolean unlockDoor = false;

	public GameMap() {
	}

	/*
	 * GET AND SET
	 */

	public char[][] getMap() {
		return map;
	}

	public Hero getHero() {
		return hero;
	}

	public Guard getGuard() {
		return guard;
	}

	public void setGuard(String personality) {
		if (guard != null)
			this.guard.setPersonality(personality);
	}

	public ArrayList<Ogre> getOgres() {
		return ogres;
	}

	public void setOgres(int size) {

		if (ogres != null)
			for (int k = 0; k < size; k++) {
				Ogre ogre = new Ogre(4, 1);
				ogres.add(ogre);
			}
	}

	public boolean isOgreMove() {
		return ogreMove;
	}

	public void setOgreMove(boolean ogreMove) {
		this.ogreMove = ogreMove;
	}

	public boolean isLever() {
		return lever;
	}

	public void setLever(boolean lever) {
		this.lever = lever;
	}
	
	public boolean isUnlockDoor(){
		return unlockDoor;
	}
	
	public void setUnlockDoors(boolean unlockDoor) {
		this.unlockDoor = unlockDoor;
	}

	/*
	 * FUNCTIONS
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
	
	public boolean ogreMoveTo(int y, int x) {
		if (map[y][x] == 'X' || map[y][x] == 'I')
			return false;
		else
			return true;
	}

	public boolean gotKey(int y, int x) {
		if (map[y][x] == 'k')
			return true;
		else
			return false;
	}

	public void openDoors() {
		for (int i = 0; i < map.length; i++) {
			if (map[i][0] == 'I')
				map[i][0] = 'S';
		}
	}

	public boolean foundDoor(int y, int x) {
		if (map[y][x] == 'S')
			return true;
		else
			return false;
	}

	public GameMap nextMap() {
		return null;
	}

	public void deleteKey() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == 'k')
					map[i][j] = ' ';
			}
		}
	}
}
