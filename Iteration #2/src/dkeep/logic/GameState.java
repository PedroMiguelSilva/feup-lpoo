package dkeep.logic;

import java.util.Random;

public class GameState {

	public int gameOver = 0;
	public int gameWon = 0;
	public int gameEnd = 0;
	public int level = 1;

	GameMap Map1 = new DungeonMap();
	GameMap Map2 = new CrazyOgreMap();
	GameMap currentMap;

	Hero hero = new Hero(1, 1);
	Guard guard;
	Ogre ogres[];

	public GameState() {
	}

	/*
	 * Function that sets the current map
	 */

	public void setCurrentMap() {
		switch (level) {
		case 1:
			currentMap = Map1;
			break;
		case 2:
			currentMap = Map2;
			break;
		case 3:
			break;
		default:
			break;
		}
	}

	/*
	 * Function that sets a random guard
	 */

	public void setGuard() {

		Random r = new Random();

		int i = r.nextInt(3);

		if (i == 0)
			guard = new Guard(8, 1, "Rookie");

		if (i == 1)
			guard = new Guard(8, 1, "Drunken");

		if (i == 2)
			guard = new Guard(8, 1, "Suspicious");

	}

	/*
	 * Function that sets a random number of Ogres in an array [1;3]
	 */

	public void setOgres() {

		Random r = new Random();

		int i = r.nextInt(3);

		if (i == 0)
			ogres = new Ogre[1];

		if (i == 1)
			ogres = new Ogre[2];

		if (i == 2)
			ogres = new Ogre[3];

		for (int k = 0; k < ogres.length; k++) {
			Ogre ogre = new Ogre();
			ogres[k] = ogre;
		}
	}

	/*
	 * Function that manages the user's input - hero's movement
	 */

	public void updatePos(char input) {

		if (input == 'a') {

			if (currentMap.moveTo(hero.getY(), hero.getX() - 1))
				hero.setX(hero.getX() - 1);
		}

		if (input == 's') {

			if (currentMap.moveTo(hero.getY() + 1, hero.getX()))
				hero.setY(hero.getY() + 1);
		}

		if (input == 'd') {

			if (currentMap.moveTo(hero.getY(), hero.getX() + 1))
				hero.setX(hero.getX() + 1);
		}

		if (input == 'w') {

			if (currentMap.moveTo(hero.getY() - 1, hero.getX()))
				hero.setY(hero.getY() - 1);
		}

		checkGame();
	}

	/*
	 * Function that sets random positions for each Ogre
	 */

	public void ogreCircuit(Ogre ogre) {

		while (true) {

			int tempX = ogre.getX();
			int tempY = ogre.getY();

			Random random = new Random();

			int r = random.nextInt(4);

			if (r == 0)
				tempX++;
			if (r == 1)
				tempX--;
			if (r == 2)
				tempY++;
			if (r == 3)
				tempY--;

			if (currentMap.moveTo(tempY, tempX) && !currentMap.foundDoor(tempY, tempX)) {
				ogre.setX(tempX);
				ogre.setY(tempY);
				break;
			}
		}

	}

	/*
	 * Function that sets random positions for each Ogre's Club
	 */

	public void ogreClub(Ogre ogre) {

		while (true) {

			int tempX = ogre.getX();
			int tempY = ogre.getY();

			Random random = new Random();

			int r = random.nextInt(4);

			if (r == 0)
				tempX += 1;
			if (r == 1)
				tempX -= 1;
			if (r == 2)
				tempY += 1;
			if (r == 3)
				tempY -= 1;

			if (currentMap.moveTo(tempY, tempX) && !currentMap.foundDoor(tempY, tempX)) {
				ogre.setClubX(tempX);
				ogre.setClubY(tempY);
				break;
			}
		}

	}

	/*
	 * Function that checks the game state - if won or lost AND updates
	 * positions
	 */

	public void checkGame() {

		if (level == 1) {

			guard.action();

			if (currentMap.gotKey(hero.getY(), hero.getX()))
				currentMap.openDoors();

			if (guard.collision(hero.getX(), hero.getY()))
				gameOver = 1;

			if (currentMap.foundDoor(hero.getY(), hero.getX()))
				gameWon = 1;
		}

		if (level == 2) {

			for (int i = 0; i < ogres.length; i++) {

				if (!ogres[i].getStun())
					ogreCircuit(ogres[i]);
				else
					ogres[i].incStunCounter();

				ogreClub(ogres[i]);
			}

			if (currentMap.gotKey(hero.getY(), hero.getX())) {
				currentMap.openDoors();
				hero.setSymbol('K');
			}

			for (int i = 0; i < ogres.length; i++) {

				if (ogres[i].collision(hero.getX(), hero.getY()))
					gameOver = 1;

				if (ogres[i].stun(hero.getX(), hero.getY())) {
					ogres[i].setSymbol('8');
					ogres[i].setStun();
				}

				if (currentMap.gotKey(ogres[i].getY(), ogres[i].getX()))
					ogres[i].setSymbol('$');
				else if (!ogres[i].getStun())
					ogres[i].setSymbol('O');

				if (currentMap.gotKey(ogres[i].getClubY(), ogres[i].getClubX()))
					ogres[i].setClubSymbol('$');
				else
					ogres[i].setClubSymbol('*');

			}

			if (currentMap.foundDoor(hero.getY(), hero.getX()))
				gameEnd = 1;
		}


	}

	/*
	 * Function that checks level and updates its map and new positions/symbols
	 */

	public void checkLevel() {

		if (level < 2) {

			if (gameWon == 1) {

				if (level == 1) {
					hero.setX(1);
					hero.setY(8);
					hero.setSymbol('A');
				}

				level++;
				setCurrentMap();
				gameWon = 0;
				printMap();
			}
		} else if (gameWon == 1)
			gameEnd = 1;
	}

	/*
	 * Function that manages the display
	 */

	public void display() {
		printMap();

		if (gameEnd == 1)
			System.out.print("-- YOU WON --");
		else if (gameWon == 1)
			System.out.print("-- LEVEL UP --\n");
		else if (gameOver == 1)
			System.out.print("-- YOU LOST --\n");

		checkLevel();
	}

	/*
	 * Function that prints the characters
	 */

	public boolean updateMap(int y, int x) {

		if (level == 1) {

			// hero
			if (hero.getY() == y && hero.getX() == x) {
				System.out.print(hero.getSymbol() + " ");
				return true;
			}

			// guard
			if (guard.getY() == y && guard.getX() == x) {
				System.out.print(guard.getSymbol() + " ");
				return true;
			}

		} else {

			// hero
			if (hero.getY() == y && hero.getX() == x) {
				System.out.print(hero.getSymbol() + " ");
				return true;
			}

			// ogre and club
			for (int i = 0; i < ogres.length; i++) {

				if (ogres[i].getY() == y && ogres[i].getX() == x) {
					System.out.print(ogres[i].getSymbol() + " ");
					return true;
				}

				if (ogres[i].getClubY() == y && ogres[i].getClubX() == x) {
					System.out.print(ogres[i].getClubSymbol() + " ");
					return true;
				}
			}
		}

		return false;
	}

	/*
	 * Function that prints the current map
	 */

	public void printMap() {

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (!updateMap(i, j))
					System.out.print(currentMap.getMap()[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
}
