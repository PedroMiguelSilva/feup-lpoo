package dkeep.logic;

public interface GameMap {
	public boolean moveTo(int x, int y);

	public boolean gotKey(int x, int y);
	
	public void openDoors();

	public boolean foundDoor(int x, int y);

	public char[][] getMap();
}
