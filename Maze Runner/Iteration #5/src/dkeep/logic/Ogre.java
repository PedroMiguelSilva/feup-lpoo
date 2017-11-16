package dkeep.logic;

public class Ogre extends Character {

	private int clubX;
	private int clubY;
	private char clubSymbol;
	private boolean stun = false;
	private int stunCounter = 0;

	/**
	 * Class constructor
	 */
	public Ogre(int x, int y) {
		super(x, y, 'O');
		this.clubX = x - 1;
		this.clubY = y;
		this.clubSymbol = '*';
	}


	/**
	 * @return x of the ogre's club
	 */
	public int getClubX() {
		return clubX;
	}

	/**
	 * @return y of the ogre's club
	 */
	public int getClubY() {
		return clubY;
	}

	/**
	 * Function that sets the x of the ogre's club
	 * @param x int to be setted as club x
	 */
	public void setClubX(int x) {
		this.clubX = x;
	}

	/**
	 * Function that sets the y of the ogre's club
	 * @param y int to be setted as club y
	 */
	public void setClubY(int y) {
		this.clubY = y;
	}

	/**
	 * Function that sets the symbol of the ogre's club
	 * @param symbol char to be setted as club symbol
	 */
	public void setClubSymbol(char symbol) {
		this.clubSymbol = symbol;
	}

	/**
	 * @return Symbol of the ogre's club
	 */
	public char getClubSymbol() {
		return clubSymbol;
	}

	/**
	 * @return A boolean if the ogre is stuned or not
	 */
	public boolean getStun() {
		return stun;
	}

	/**
	 * Function that stuns ogre
	 */
	public void setStun() {
		stun = true;
	}

	/**
	 * @return Number of round that ogre has been stunned
	 */
	public int getStunCounter() {
		return stunCounter;
	}

	/**
	 * Function that increments the number of rounds that the ogre has been stunned
	 */
	public void incStunCounter() {
		stunCounter++;

		if (stunCounter == 3) {
			stun = false;
			stunCounter = 0;
		}
	}

	
	/**
	 * Function that checks if the position passed can stun the ogre
	 * @param x Variable of type int with the x of the position to check
	 * @param y Variable of type int with the y of the position to check
	 * @return True if it's stun
	 */
	public boolean stun(int x, int y) {

		// caso esteja na mesma coluna que O
		if (x == this.x) {
			if (y == this.y - 1 || y == this.y + 1)
				return true;
		}

		// caso esteja na mesma linha que O
		if (y == this.y) {
			if (x == this.x - 1 || x == this.x + 1)
				return true;
		}

		return false;
	}

	/**
	 * Function that checks if the position passed is in collision with the ogre
	 * @param x Variable of type int with the x of the position to check
	 * @param y Variable of type int with the y of the position to check
	 * @return True if collision
	 */
	public boolean collision(int x, int y) {

		// caso esteja na mesma coluna que *
		if (x == this.clubX) {

			if (y == this.clubY - 1 || y == this.clubY + 1)
				return true;
		}

		// caso esteja na mesma linha que *
		if (y == this.clubY) {
			if (x == this.clubX - 1 || x == this.clubX + 1)
				return true;
		}

		return false;
	}
}
