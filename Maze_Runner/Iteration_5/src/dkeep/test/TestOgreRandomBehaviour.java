package dkeep.test;

import static org.junit.Assert.*;

import org.junit.Test;

import dkeep.logic.GameMap;
import dkeep.logic.GameState;
import dkeep.logic.Map;

public class TestOgreRandomBehaviour {

	char[][] map = { { 'X', 'X', 'X', 'X', 'X' },
					{ 'I', ' ', ' ', 'k', 'X' },
					{ 'X', ' ', ' ', 'O', 'X' },
					{ 'X', 'H', ' ', ' ', 'X' }, 
					{ 'X', 'X', 'X', 'X', 'X' } };
	

	@Test (timeout = 1000)
	public void testSomeRandomBehaviour() {
		boolean aa = false, aw = false, as = false, ad = false;
		boolean wa = false, ww = false, ws = false, wd = false;
		boolean sa = false, sw = false, ss = false, sd = false;
		boolean da = false, dw = false, ds = false, dd = false;

		GameMap Map = new Map(map);
		Map.setOgreMove(true);
		GameState state = new GameState();
		state.setCurrentMap(Map);

		while (!aa || !aw || !as || !ad || !wa || !ww || !ws || !wd || !sa || !sw || !ss || !sd || !da || !dw || !ds
				|| !dd) {

			int xOld = state.currentMap.getOgres().get(0).getX();
			int yOld = state.currentMap.getOgres().get(0).getY();

			state.updatePos('s');

			int x = state.currentMap.getOgres().get(0).getX();
			int y = state.currentMap.getOgres().get(0).getY();
			int xClub = state.currentMap.getOgres().get(0).getClubX();
			int yClub = state.currentMap.getOgres().get(0).getClubY();
			
			//System.out.print(xOld + " " + yOld + " " + x + " " + y + " " + xClub + " " + yClub + "\n"); 

			if (xOld - 1 == x && yOld == y && x - 1 == xClub && y == yClub)
				aa = true;
			else if (xOld - 1 == x && yOld == y && x == xClub && y - 1 == yClub)
				aw = true;
			else if (xOld - 1 == x && yOld == y && x == xClub && y + 1 == yClub)
				as = true;
			else if (xOld - 1 == x && yOld == y && x + 1 == xClub && y == yClub)
				ad = true;
			else if (xOld == x && yOld - 1 == y && x - 1 == xClub && y == yClub)
				wa = true;
			else if (xOld == x && yOld - 1 == y && x == xClub && y - 1 == yClub)
				ww = true;
			else if (xOld == x && yOld - 1 == y && x == xClub && y + 1 == yClub)
				ws = true;
			else if (xOld == x && yOld - 1 == y && x + 1 == xClub && y == yClub)
				wd = true;
			else if (xOld == x && yOld + 1 == y && x - 1 == xClub && y == yClub)
				sa = true;
			else if (xOld == x && yOld + 1 == y && x == xClub && y - 1 == yClub)
				sw = true;
			else if (xOld == x && yOld + 1 == y && x == xClub && y + 1 == yClub)
				ss = true;
			else if (xOld == x && yOld + 1 == y && x + 1 == xClub && y == yClub)
				sd = true;
			else if (xOld + 1 == x && yOld == y && x - 1 == xClub && y == yClub)
				da = true;
			else if (xOld + 1 == x && yOld == y && x == xClub && y - 1 == yClub)
				dw = true;
			else if (xOld + 1 == x && yOld == y && x == xClub && y + 1 == yClub)
				ds = true;
			else if (xOld + 1 == x && yOld == y && x + 1 == xClub && y == yClub)
				dd = true;
			else
				fail("ERROR !\n");
		}
	}

}