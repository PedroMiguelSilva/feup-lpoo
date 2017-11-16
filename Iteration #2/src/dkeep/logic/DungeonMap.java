package dkeep.logic;

public class DungeonMap implements GameMap {

	private char[][] map = { { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
			{ 'X', ' ', ' ', ' ', 'I', ' ', 'X', ' ', ' ', 'X' }, { 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
			{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X' }, { 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
			{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' }, { 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
			{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X' }, { 'X', ' ', 'I', ' ', 'I', ' ', 'X', 'k', ' ', 'X' },
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };

	public DungeonMap() {
	}

	public boolean moveTo(int y, int x) {
		if (map[y][x] == 'X' || map[y][x] == 'I' || x < 0 || x >= 10 || y < 0 || y >= 10)
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
		for (int i = 0; i < 10; i++) {
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

	public char[][] getMap() {
		return map;
	}
}
