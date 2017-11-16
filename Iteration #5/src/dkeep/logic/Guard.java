package dkeep.logic;

import java.util.Random;

public class Guard extends Character {

	private String personality;
	private static boolean reversePath = false;
	private int path[][] = { { 1, 8 }, { 1, 7 }, { 2, 7 }, { 3, 7 }, { 4, 7 }, { 5, 7 }, { 5, 6 }, { 5, 5 }, { 5, 4 },
			{ 5, 3 }, { 5, 2 }, { 5, 1 }, { 6, 1 }, { 6, 2 }, { 6, 3 }, { 6, 4 }, { 6, 5 }, { 6, 6 }, { 6, 7 },
			{ 6, 8 }, { 5, 8 }, { 4, 8 }, { 3, 8 }, { 2, 8 } };
	// array de 2 colunas e 24 linhas

	/**
	 * Class constructor
	 */
	public Guard(int x, int y) {
		super(x, y, 'G');
	}

	/**
	 * @return String of the Guard's personality
	 */
	public String getPersonality() {
		return personality;
	}
	
	/**
	 * Function that sets the personality of the guard
	 * @param personality String of the personality to be setted
	 */
	public void setPersonality(String personality) {
		this.personality = personality;
	}

	/**
	 * Function that checks which action the guard should do
	 */
	public void action() {

		if (personality == "Rookie")
			circuit();
		else {
			if (personality == "Drunken")
				actionDrunken();
			else
				actionSuspicious();
		}
	}
	
	/**
	 * Function that moves the guard with drunken characteristics
	 */
	public void actionDrunken() {

		Random r1 = new Random();
		Random r2 = new Random();

		int i = r1.nextInt(4);
		int j = r2.nextInt(4);

		if (i == 0) {
			this.setSymbol('g');
		} else {

			this.setSymbol('G');

			if (j == 0)
				reversePath = true;
			else
				reversePath = false;
			
			circuit();
		}
	}

	/**
	 * Function that moves the guard with suspicious characteristics
	 */
	public void actionSuspicious() {

		Random r = new Random();

		int i = r.nextInt(4);

		if (i == 0)
			reversePath = true;
		else
			reversePath = false;
		
		circuit();
	}

	/**
	 * Function that moves the guard in the normal circuit
	 */
	public void circuit() {

		for (int i = 0; i < 24; i++) {
			if (path[i][0] == this.y && path[i][1] == this.x) {

				if (reversePath) {
					if (i == 0) {
						this.setY(path[23][0]);
						this.setX(path[23][1]);
					} else {
						this.setY(path[i - 1][0]);
						this.setX(path[i - 1][1]);
					}
				} else {
					if (i == 23) {
						this.setY(path[0][0]);
						this.setX(path[0][1]);
					} else {
						this.setY(path[i + 1][0]);
						this.setX(path[i + 1][1]);
					}
				}

				break;
			}
		}
	}

	/**
	 * Function that checks if the position passed is in collision with the guard
	 * @param x Variable of type int with the x of the position to check
	 * @param y Variable of type int with the y of the position to check
	 * @return True if collision
	 */
	public boolean collision(int x, int y) {

		if (this.getSymbol() == 'G') {

			// caso esteja na mesma coluna que G
			if (x == this.getX()) {

				if (y == this.getY() - 1 || y == this.getY() + 1 || y==this.getY())
					return true;
			}

			// caso esteja na mesma linha que G
			if (y == this.getY()) {
				if (x == this.getX() - 1 || x == this.getX() + 1 || x ==this.getX())
					return true;
			}
		}

		return false;
	}
}
