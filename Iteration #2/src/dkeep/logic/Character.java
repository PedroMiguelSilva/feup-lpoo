package dkeep.logic;

public class Character {

	protected int x;
	protected int y;
	protected char symbol;

	public Character(int x, int y, char symbol) {
		this.x = x;
		this.y = y;
		this.symbol = symbol;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public char getSymbol() {
		return symbol;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setSymbol(char s) {
		this.symbol = s;
	}

}
