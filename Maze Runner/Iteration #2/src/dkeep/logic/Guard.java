package dkeep.logic;

import java.util.Random;

public class Guard extends Character {

	private String personality;
	private static boolean reversePath = false;
	private int path[][] = { { 1, 8 }, { 1, 7 }, { 2, 7 }, { 3, 7 }, { 4, 7 }, { 5, 7 }, { 5, 6 }, { 5, 5 }, { 5, 4 },
			{ 5, 3 }, { 5, 2 }, { 5, 1 }, { 6, 1 }, { 6, 2 }, { 6, 3 }, { 6, 4 }, { 6, 5 }, { 6, 6 }, { 6, 7 },
			{ 6, 8 }, { 5, 8 }, { 4, 8 }, { 3, 8 }, { 2, 8 } };
	// array de 2 colunas e 24 linhas

	public Guard(int x, int y, String personality) {
		super(x, y, 'G');
		this.personality = personality;
	}

	public String getPersonality() {
		return personality;
	}

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

	public void actionSuspicious() {

		Random r = new Random();

		int i = r.nextInt(4);

		if (i == 0)
			reversePath = true;
		else
			reversePath = false;
		
		circuit();
	}

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

	public boolean collision(int x, int y) {

		if (this.getSymbol() == 'G') {

			// caso esteja na mesma coluna que G
			if (x == this.getX()) {

				if (y == this.getY() - 1 || y == this.getY() + 1)
					return true;
			}

			// caso esteja na mesma linha que G
			if (y == this.getY()) {
				if (x == this.getX() - 1 || x == this.getX() + 1)
					return true;
			}
		}

		return false;
	}
}
