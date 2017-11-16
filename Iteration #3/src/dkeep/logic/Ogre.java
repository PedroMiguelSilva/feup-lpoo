package dkeep.logic;

public class Ogre extends Character {

	private int clubX;
	private int clubY;
	private char clubSymbol;
	private boolean stun = false;
	private int stunCounter = 0;

	public Ogre(int x, int y) {
		super(x, y, 'O');
		this.clubX = x - 1;
		this.clubY = y;
		this.clubSymbol = '*';
	}

	public int getClubX() {
		return clubX;
	}

	public int getClubY() {
		return clubY;
	}

	public void setClubX(int x) {
		this.clubX = x;
	}

	public void setClubY(int y) {
		this.clubY = y;
	}

	public void setClubSymbol(char symbol) {
		this.clubSymbol = symbol;
	}

	public char getClubSymbol() {
		return clubSymbol;
	}

	public boolean getStun() {
		return stun;
	}

	public void setStun() {
		stun = true;
	}

	public int getStunCounter() {
		return stunCounter;
	}

	public void incStunCounter() {
		stunCounter++;

		if (stunCounter == 3) {
			stun = false;
			stunCounter = 0;
		}
	}

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
