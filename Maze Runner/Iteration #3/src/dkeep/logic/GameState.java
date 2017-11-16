package dkeep.logic;

import java.util.Random;

public class GameState {

	public int gameOver = 0;
	public int gameWon = 0;
	public int gameEnd = 0;

	public GameMap currentMap;

	public GameState() {
	}

	/*
	 * Functions that set the current map
	 */

	public void setNextMap() {

		currentMap = currentMap.nextMap();

		if (currentMap != null) {
			setGuard();
			setOgres();
			gameWon = 0;
			printMap();
		} else
			gameEnd = 1;

	}

	public void setCurrentMap(GameMap map) {
		currentMap = map;
	}

	/*
	 * Function that sets a random guard
	 */

	public void setGuard() {

		Random r = new Random();

		int i = r.nextInt(3);

		if (i == 0)
			currentMap.setGuard("Rookie");

		if (i == 1)
			currentMap.setGuard("Drunken");

		if (i == 2)
			currentMap.setGuard("Suspicious");

	}

	/*
	 * Function that sets a random number of Ogres in an array [1;3]
	 */

	public void setOgres() {

		Random r = new Random();

		int i = r.nextInt(2);

		if (i == 0)
			currentMap.setOgres(1);

		if (i == 1)
			currentMap.setOgres(2);
	}

	/*
	 * Function that manages the user's input - hero's movement
	 */

	public void updatePos(char input) {

		if (input == 'a') {

			if (currentMap.moveTo(currentMap.getHero().getY(), currentMap.getHero().getX() - 1))
				currentMap.getHero().setX(currentMap.getHero().getX() - 1);
		}

		if (input == 's') {

			if (currentMap.moveTo(currentMap.getHero().getY() + 1, currentMap.getHero().getX()))
				currentMap.getHero().setY(currentMap.getHero().getY() + 1);
		}

		if (input == 'd') {

			if (currentMap.moveTo(currentMap.getHero().getY(), currentMap.getHero().getX() + 1))
				currentMap.getHero().setX(currentMap.getHero().getX() + 1);
		}

		if (input == 'w') {

			if (currentMap.moveTo(currentMap.getHero().getY() - 1, currentMap.getHero().getX()))
				currentMap.getHero().setY(currentMap.getHero().getY() - 1);
		}

		checkGame();
	}

	/*
	 * Function that sets random positions for each Ogre
	 */

	public void ogreCircuit(Ogre ogre) {

		if (currentMap.isOgreMove()) {

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

	}

	/*
	 * Function that sets random positions for each Ogre's Club
	 */

	public void ogreClub(Ogre ogre) {

		if (currentMap.isOgreMove()) {

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

	}

	/*
	 * Function that checks the game state - if won or lost AND updates
	 * positions
	 */

	public void checkGame() {

		if (currentMap.getGuard() != null) {

			currentMap.getGuard().action();

			if (currentMap.getGuard().collision(currentMap.getHero().getX(), currentMap.getHero().getY()))
				gameOver = 1;

		}

		if (currentMap.getOgres() != null) {

			for (int i = 0; i < currentMap.getOgres().size(); i++) {

				if (!currentMap.getOgres().get(i).getStun())
					ogreCircuit(currentMap.getOgres().get(i));
				else
					currentMap.getOgres().get(i).incStunCounter();

				ogreClub(currentMap.getOgres().get(i));
			}

			for (int i = 0; i < currentMap.getOgres().size(); i++) {

				if (currentMap.getOgres().get(i).collision(currentMap.getHero().getX(), currentMap.getHero().getY()))
					gameOver = 1;

				if(currentMap.getHero().getIsArmed()==true){
					if (currentMap.getOgres().get(i).stun(currentMap.getHero().getX(), currentMap.getHero().getY())) {
						currentMap.getOgres().get(i).setSymbol('8');
						currentMap.getOgres().get(i).setStun();
					}
				}
				

				if (currentMap.gotKey(currentMap.getOgres().get(i).getY(), currentMap.getOgres().get(i).getX()))
					currentMap.getOgres().get(i).setSymbol('$');
				else if (!currentMap.getOgres().get(i).getStun())
					currentMap.getOgres().get(i).setSymbol('O');

				if (currentMap.gotKey(currentMap.getOgres().get(i).getClubY(), currentMap.getOgres().get(i).getClubX()))
					currentMap.getOgres().get(i).setClubSymbol('$');
				else
					currentMap.getOgres().get(i).setClubSymbol('*');

			}
		}

		if (currentMap.gotKey(currentMap.getHero().getY(), currentMap.getHero().getX())) {

			if (!currentMap.isLever()) {
				currentMap.getHero().setSymbol('K');
				currentMap.deleteKey();
			} else {
				currentMap.openDoors();
			}
		}
		
		if(currentMap.isUnlockDoor())
			currentMap.openDoors();

		if (currentMap.foundDoor(currentMap.getHero().getY(), currentMap.getHero().getX()))
			gameWon = 1;

	}

	/*
	 * Function that manages the display
	 */

	public void display() {
		printMap();

		if (gameEnd == 1)
			System.out.print("-- YOU WON --");
		else if (gameWon == 1) {
			System.out.print("-- LEVEL UP --\n");
			setNextMap();
		} else if (gameOver == 1)
			System.out.print("-- YOU LOST --\n");
	}

	/*
	 * Function that prints the characters
	 */

	public boolean updateMap(int y, int x) {

		// hero
		if (currentMap.getHero().getY() == y && currentMap.getHero().getX() == x) {
			System.out.print(currentMap.getHero().getSymbol() + " ");
			return true;
		}

		if (currentMap.getGuard() != null) {

			// guard
			if (currentMap.getGuard().getY() == y && currentMap.getGuard().getX() == x) {
				System.out.print(currentMap.getGuard().getSymbol() + " ");
				return true;
			}

		}

		if (currentMap.getOgres() != null) {

			// ogre and club
			for (int i = 0; i < currentMap.getOgres().size(); i++) {

				if (currentMap.getOgres().get(i).getY() == y && currentMap.getOgres().get(i).getX() == x) {
					System.out.print(currentMap.getOgres().get(i).getSymbol() + " ");
					return true;
				}

				if (currentMap.getOgres().get(i).getClubY() == y && currentMap.getOgres().get(i).getClubX() == x) {
					System.out.print(currentMap.getOgres().get(i).getClubSymbol() + " ");
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
