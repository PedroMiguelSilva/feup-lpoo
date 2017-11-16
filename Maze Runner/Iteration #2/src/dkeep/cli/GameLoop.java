package dkeep.cli;

import java.util.Scanner;
import dkeep.logic.GameState;

public class GameLoop {

	public static void main(String[] args) {

		GameState state = new GameState();
		state.setCurrentMap();
		state.setGuard();
		state.setOgres();
		state.printMap();

		while (state.gameEnd != 1 && state.gameOver != 1) {

			Scanner s = new Scanner(System.in);
			char move = s.next().charAt(0);

			state.updatePos(move);
			state.display();
		}

		return;
	}
}
