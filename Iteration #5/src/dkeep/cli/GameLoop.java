package dkeep.cli;

import java.util.Scanner;

import dkeep.logic.DungeonMap;
import dkeep.logic.GameState;
import dkeep.logic.GameState.State;

public class GameLoop {

	public static void main(String[] args) {

		GameState state = new GameState();
		state.currentMap = new DungeonMap();
		state.setGuard();
		state.setOgres();
		state.printMap();

		while ((state.currentState != State.gameOver) && (state.currentState != State.gameWin)) {

			Scanner s = new Scanner(System.in);
			char move = s.next().charAt(0);
			s.close();

			state.updatePos(move);
			state.display();
		}

		return;
	}
}
