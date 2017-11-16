package dkeep.logic;

import java.util.Random;

public class GameState {

	public GameMap currentMap;
	public int numberOgres = -1;

	// State Machine
	public enum State {
		init, game, gameOver, gameWin
	};

	public enum Event {
		newGame, levelUp, collision, win
	};

	public State currentState = State.init;
	public boolean newLevel = false;

	/**
	 * Class constructor.
	 */
	public GameState() {
	}

	/**
	 * Function that updates the current state
	 * 
	 * @param evt
	 *            A variable of type Event
	 */
	public void updateState(Event evt) {
		if (evt == Event.newGame)
			currentState = State.game;
		else if (evt == Event.levelUp) {
			newLevel = true;
			setNextMap();
		} else if (evt == Event.collision)
			currentState = State.gameOver;
		else if (evt == Event.win)
			currentState = State.gameWin;
	}

	/**
	 * Functions that set the current map
	 */
	public void setNextMap() {

		if (currentMap.nextMap() != null) {
			currentMap = currentMap.nextMap();
			setGuard();

			if (currentMap.getOgres().size() == 0) {
				if (numberOgres != -1)
					setOgres(numberOgres);
				else
					setOgres();
			}

		} else
			updateState(Event.win);
	}

	public void setCurrentMap(GameMap map) {
		currentMap = map;
	}

	/**
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

	public void setGuard(String personality) {
		currentMap.setGuard(personality);
	}

	/**
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

	/**
	 * Function that sets an array of Ogres with the size given
	 * 
	 * @param num
	 *            Variable of type int that is the size wanted for the ogres'
	 *            array
	 */
	public void setOgres(int num) {
		numberOgres = num;
		if (currentMap.getOgres() != null)
			currentMap.setOgres(num);
	}

	/**
	 * Function that manages the user's input - hero's movement
	 * 
	 * @param input
	 *            The char that was typed by the user
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

		updateGame();
	}

	/**
	 * Function that sets random positions for each Ogre
	 * 
	 * @param ogre
	 *            Ogre that's being moved
	 */
	public void ogreCircuit(Ogre ogre) {

		int n = 0;

		if (currentMap.isOgreMove()) {

			while (n < 50) {

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

				if (currentMap.ogreMoveTo(tempY, tempX) && !currentMap.foundDoor(tempY, tempX)) {
					ogre.setX(tempX);
					ogre.setY(tempY);
					break;
				}

				n++;
			}
		}

	}

	/**
	 * Function that sets random positions for each Ogre's Club
	 * 
	 * @param ogre
	 *            Ogre associated with the club
	 */

	public void ogreClub(Ogre ogre) {

		int n = 0;
		if (currentMap.isOgreMove()) {

			while (n < 50) {

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

				if (currentMap.ogreMoveTo(tempY, tempX) && !currentMap.foundDoor(tempY, tempX)) {
					ogre.setClubX(tempX);
					ogre.setClubY(tempY);
					break;
				}

				n++;
			}
		}

	}

	/**
	 * Function that updates the position of each character
	 */
	public void updateGame() {
		updateGuard();
		updateOgres();
		updateKey();
		updateDoor();
	}

	/**
	 * Function that updates the position of the guard and verifies collision
	 * with hero
	 */
	public void updateGuard() {
		if (currentMap.getGuard() != null) {
			currentMap.getGuard().action();

			if (currentMap.getGuard().collision(currentMap.getHero().getX(), currentMap.getHero().getY()))
				updateState(Event.collision);
		}
	}

	/**
	 * Function that updates the position of the ogres and verifies collision
	 * with hero
	 */
	public void updateOgres() {
		if (currentMap.getOgres() != null) {
			updateOgreStun();
			for (int i = 0; i < currentMap.getOgres().size(); i++) {
				if (currentMap.getOgres().get(i).collision(currentMap.getHero().getX(), currentMap.getHero().getY()))
					updateState(Event.collision);

				if (currentMap.getHero().getIsArmed() == true) {
					if (currentMap.getOgres().get(i).stun(currentMap.getHero().getX(), currentMap.getHero().getY())) {
						currentMap.getOgres().get(i).setSymbol('8');
						currentMap.getOgres().get(i).setStun();
					}
				}
				verifyOgreSymbol(i);
			}
		}
	}

	/**
	 * Function that checks if the ogre is stuned or not
	 */
	public void updateOgreStun() {
		for (int i = 0; i < currentMap.getOgres().size(); i++) {
			if (!currentMap.getOgres().get(i).getStun())
				ogreCircuit(currentMap.getOgres().get(i));
			else
				currentMap.getOgres().get(i).incStunCounter();
			ogreClub(currentMap.getOgres().get(i));
		}
	}

	/**
	 * Function that changes the symbol of the ogre
	 * 
	 * @param i
	 *            Postion of the ogre in array
	 */
	public void verifyOgreSymbol(int i) {
		if (currentMap.gotKey(currentMap.getOgres().get(i).getY(), currentMap.getOgres().get(i).getX()))
			currentMap.getOgres().get(i).setSymbol('$');
		else if (!currentMap.getOgres().get(i).getStun())
			currentMap.getOgres().get(i).setSymbol('O');

		if (currentMap.gotKey(currentMap.getOgres().get(i).getClubY(), currentMap.getOgres().get(i).getClubX()))
			currentMap.getOgres().get(i).setClubSymbol('$');
		else
			currentMap.getOgres().get(i).setClubSymbol('*');
	}

	/**
	 * Function that deletes the key of the map if hero takes it
	 */
	public void updateKey() {
		if (currentMap.gotKey(currentMap.getHero().getY(), currentMap.getHero().getX())) {

			if (!currentMap.isLever()) {
				currentMap.getHero().setSymbol('K');
				currentMap.deleteKey();
			} else {
				currentMap.openDoors();
			}
		}
	}

	/**
	 * Function that updates the doors
	 */
	public void updateDoor() {
		if (currentMap.isUnlockDoor())
			currentMap.openDoors();

		if (currentMap.foundDoor(currentMap.getHero().getY(), currentMap.getHero().getX()))
			updateState(Event.levelUp);
	}

	/**
	 * Function that manages the display of the status message
	 */
	public void display() {
		printMap();

		if (currentState == State.gameWin)
			System.out.print("-- YOU WON --");
		else if (newLevel) {
			System.out.print("-- LEVEL UP --\n");
			newLevel = false;
		} else if (currentState == State.gameOver)
			System.out.print("-- YOU LOST --\n");
	}

	/**
	 * Function that prints the characters
	 * 
	 * @param y
	 *            Position of y in the map
	 * @param x
	 *            Position of x in the map
	 * @return False if there's no character to print
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

	/**
	 * Function that prints the current map
	 */
	public void printMap() {

		for (int i = 0; i < currentMap.getMapDimension(); i++) {
			for (int j = 0; j < currentMap.getMapDimension(); j++) {
				if (!updateMap(i, j))
					System.out.print(currentMap.getMap()[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
}
