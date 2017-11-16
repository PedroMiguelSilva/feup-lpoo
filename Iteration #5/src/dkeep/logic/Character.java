package dkeep.logic;

public class Character {

	protected int x;
	protected int y;
	protected char symbol;
	
	/**
	 * Class constructor
	 */
	public Character(int x, int y, char symbol) {
		this.x = x;
		this.y = y;
		this.symbol = symbol;
	}

	/**
	 * @return x of the character
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return y of the character
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return Symbol of the character
	 */
	public char getSymbol() {
		return symbol;
	}

	/**
	 * Function that sets the x of the character
	 * @param x int to be setted as x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Function that sets the y of the character
	 * @param y int to be setted as y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Function that sets the symbol of the character
	 * @param s symbol chat to be setted as the symbol
	 */
	public void setSymbol(char s) {
		this.symbol = s;
	}

}
