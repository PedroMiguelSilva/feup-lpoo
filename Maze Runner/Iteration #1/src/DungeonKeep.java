import java.util.Scanner;
import java.util.Random;

public class DungeonKeep {

	// variaveis globais
	public static char[] map;
	public static char[] map2;
	public static int posH = 11;
	public static int oldPosH = 11;
	public static int posG = 18;
	public static int oldPosG = 18;
	public static int posH2 = 81;
	public static int oldPosH2 = 81;
	public static int posO = 14;
	public static int oldPosO = 14;
	public static int posA = 15; // asterisk pos
	public static int oldPosA = 15;
	public static int gotKey = 0;
	public static int endGame = 0; // 0 - level 1; 1 - game over; 2 - won the
									// game; 3 - level 2

	public static void printMap(char[] map) {
		int cnt = 0;

		for (int i = 0; i < 100; i++) {
			System.out.print(map[i]);
			cnt++;
			if (cnt == 10) {
				System.out.print('\n');
				cnt = 0;
			}
		}
	}

	public static void step(char move, int level) {

		int posTemp = 0;

		if (level == 1)

			posTemp = posH;

		if (level == 2)
			posTemp = posH2;
		

		if (move == 'w')
			posTemp -= 10;

		else if (move == 'a')
			posTemp -= 1;

		else if (move == 's')
			posTemp += 10;

		else if (move == 'd')
			posTemp += 1;

		updatePos(posTemp, level);

	}

	public static void updatePos(int posTemp, int level) {

		if (level == 1) {
			guardCircuit();

			if (map[posTemp] != 'X' && map[posTemp] != 'I') {


				if (posTemp == (posG - 1) || posTemp == (posG + 1) || posTemp == (posG + 10) || posTemp == (posG - 10)
						|| posTemp == posG) {
					endGame = 1;
				}

				if (map[posTemp] == 'S') {
					endGame = 3;
				}

				oldPosH = posH;
				posH = posTemp;

			}

			updateMap(1);
		}

		if (level == 2) {

			ogreCircuit();
			gun();

			if (map2[posTemp] != 'X' && map2[posTemp] != 'I') {


				if (posTemp == (posO - 1) || posTemp == (posO + 1) || posTemp == (posO + 10) || posTemp == (posO - 10)
						|| posTemp == posO || posTemp == (posA - 1) || posTemp == (posA + 1) || posTemp == (posA + 10)
						|| posTemp == (posA - 10) || posTemp == posA) {
					endGame = 1;
				}

				if (map2[posTemp] == 'S') {
					endGame = 2;
				}

				oldPosH2 = posH2;
				posH2 = posTemp;

			}

			updateMap(2);
		}

	}

	public static void guardCircuit() {

		oldPosG = posG;

		if (posG == 18) {
			posG -= 1;
			return;
		}

		if (posG == 17 || posG == 27 || posG == 37 || posG == 47) {
			posG += 10;
			return;
		}

		if (posG == 57 || posG == 56 || posG == 55 || posG == 54 || posG == 53 || posG == 52) {
			posG -= 1;
			return;
		}

		if (posG == 51) {
			posG += 10;
			return;
		}

		if (posG == 61 || posG == 62 || posG == 63 || posG == 64 || posG == 65 || posG == 66 || posG == 67) {
			posG += 1;
			return;
		}

		if (posG == 68 || posG == 58 || posG == 48 || posG == 38 || posG == 28) {
			posG -= 10;
			return;
		}

	}

	public static void ogreCircuit() {

		while (true) {

			int posTemp = posO;

			Random random = new Random();

			int r = random.nextInt(4);

			if (r == 0)
				posTemp += 1;
			if (r == 1)
				posTemp -= 1;
			if (r == 2)
				posTemp += 10;
			if (r == 3)
				posTemp -= 10;

			if (map2[posTemp] != 'X' && map2[posTemp] != 'I' && map2[posTemp] != 'S' && posTemp > 0 && posTemp < 100) {
				oldPosO = posO;
				posO = posTemp;
				break;
			}
		}
	}

	public static void gun() {

		while (true) {

			int posTemp = posA;

			Random random = new Random();

			int r = random.nextInt(4);

			if (r == 0)
				posTemp = posO + 1;
			if (r == 1)
				posTemp = posO - 1;
			if (r == 2)
				posTemp = posO + 10;
			if (r == 3)
				posTemp = posO + 10;

			if (map2[posTemp] != 'X' && map2[posTemp] != 'I' && map2[posTemp] != 'S' && posTemp > 0 && posTemp < 100) {
				oldPosA = posA;
				posA = posTemp;
				break;
			}
		}

	}

	public static void updateMap(int level) {

		if (level == 1) {
			map[posH] = 'H';

			if (oldPosH == 87)
				map[oldPosH] = 'k';
			else
				map[oldPosH] = ' ';

			map[posG] = 'G';
			map[oldPosG] = ' ';
			
			if (map[posH] == 'k') {
				map[50] = 'S';
				map[60] = 'S';
			}
		}

		if (level == 2) {

			// hero
			if (gotKey == 0)
				map2[posH2] = 'H';
			else
				map2[posH2] = 'K';

			if (oldPosH2 == 17)
				if (gotKey == 1)
					map2[oldPosH2] = ' ';
				else
					map2[oldPosH2] = 'k';
			else
				map2[oldPosH2] = ' ';

			if (oldPosO == 17)
				if (gotKey == 1)
					map2[oldPosO] = ' ';
				else
					map2[oldPosO] = 'k';
			else
				map2[oldPosO] = ' ';

			if (oldPosA == 17)
				if (gotKey == 1)
					map2[oldPosA] = ' ';
				else
					map2[oldPosA] = 'k';
			else
				map2[oldPosA] = ' ';

			if (posO == 17)
				if (gotKey == 1)
					map2[posO] = 'O';
				else
					map2[posO] = '$';
			else
				map2[posO] = 'O';

			if (posA == 17)
				if (gotKey == 1)
					map2[posA] = '*';
				else
					map2[posA] = '$';
			else
				map2[posA] = '*';
			
			if (map2[posH2] == 'k') {
				map2[10] = 'S';
				gotKey = 1;
			}

		}

	}

	public static void main(String[] args) {

		map = new char[100];

		// [0;10]

		for (int i = 0; i < 10; i++) {
			map[i] = 'X';
		}

		// [10;89]

		map[10] = 'X';
		map[11] = 'H';
		map[12] = ' ';
		map[13] = ' ';
		map[14] = 'I';
		map[15] = ' ';
		map[16] = 'X';
		map[17] = ' ';
		map[18] = 'G';
		map[19] = 'X';
		map[20] = 'X';
		map[21] = 'X';
		map[22] = 'X';
		map[23] = ' ';
		map[24] = 'X';
		map[25] = 'X';
		map[26] = 'X';
		map[27] = ' ';
		map[28] = ' ';
		map[29] = 'X';
		map[30] = 'X';
		map[31] = ' ';
		map[32] = 'I';
		map[33] = ' ';
		map[34] = 'I';
		map[35] = ' ';
		map[36] = 'X';
		map[37] = ' ';
		map[38] = ' ';
		map[39] = 'X';
		map[40] = 'X';
		map[41] = 'X';
		map[42] = 'X';
		map[43] = ' ';
		map[44] = 'X';
		map[45] = 'X';
		map[46] = 'X';
		map[47] = ' ';
		map[48] = ' ';
		map[49] = 'X';
		map[50] = 'I';
		map[51] = ' ';
		map[52] = ' ';
		map[53] = ' ';
		map[54] = ' ';
		map[55] = ' ';
		map[56] = ' ';
		map[57] = ' ';
		map[58] = ' ';
		map[59] = 'X';
		map[60] = 'I';
		map[61] = ' ';
		map[62] = ' ';
		map[63] = ' ';
		map[64] = ' ';
		map[65] = ' ';
		map[66] = ' ';
		map[67] = ' ';
		map[68] = ' ';
		map[69] = 'X';
		map[70] = 'X';
		map[71] = 'X';
		map[72] = 'X';
		map[73] = ' ';
		map[74] = 'X';
		map[75] = 'X';
		map[76] = 'X';
		map[77] = 'X';
		map[78] = ' ';
		map[79] = 'X';
		map[80] = 'X';
		map[81] = ' ';
		map[82] = 'I';
		map[83] = ' ';
		map[84] = 'I';
		map[85] = ' ';
		map[86] = 'X';
		map[87] = 'k';
		map[88] = ' ';
		map[89] = 'X';

		// [91;100]

		for (int i = 90; i < 100; i++) {
			map[i] = 'X';
		}

		// MAP2

		map2 = new char[100];

		// [0;10]

		for (int i = 0; i < 10; i++) {
			map2[i] = 'X';
		}

		// [10;89]

		map2[10] = 'I';
		map2[11] = ' ';
		map2[12] = ' ';
		map2[13] = ' ';
		map2[14] = 'O';
		map2[15] = ' ';
		map2[16] = ' ';
		map2[17] = 'k';
		map2[18] = ' ';
		map2[19] = 'X';
		map2[20] = 'X';
		map2[21] = ' ';
		map2[22] = ' ';
		map2[23] = ' ';
		map2[24] = ' ';
		map2[25] = ' ';
		map2[26] = ' ';
		map2[27] = ' ';
		map2[28] = ' ';
		map2[29] = 'X';
		map2[30] = 'X';
		map2[31] = ' ';
		map2[32] = ' ';
		map2[33] = ' ';
		map2[34] = ' ';
		map2[35] = ' ';
		map2[36] = ' ';
		map2[37] = ' ';
		map2[38] = ' ';
		map2[39] = 'X';
		map2[40] = 'X';
		map2[41] = ' ';
		map2[42] = ' ';
		map2[43] = ' ';
		map2[44] = ' ';
		map2[45] = ' ';
		map2[46] = ' ';
		map2[47] = ' ';
		map2[48] = ' ';
		map2[49] = 'X';
		map2[50] = 'X';
		map2[51] = ' ';
		map2[52] = ' ';
		map2[53] = ' ';
		map2[54] = ' ';
		map2[55] = ' ';
		map2[56] = ' ';
		map2[57] = ' ';
		map2[58] = ' ';
		map2[59] = 'X';
		map2[60] = 'X';
		map2[61] = ' ';
		map2[62] = ' ';
		map2[63] = ' ';
		map2[64] = ' ';
		map2[65] = ' ';
		map2[66] = ' ';
		map2[67] = ' ';
		map2[68] = ' ';
		map2[69] = 'X';
		map2[70] = 'X';
		map2[71] = ' ';
		map2[72] = ' ';
		map2[73] = ' ';
		map2[74] = ' ';
		map2[75] = ' ';
		map2[76] = ' ';
		map2[77] = ' ';
		map2[78] = ' ';
		map2[79] = 'X';
		map2[80] = 'X';
		map2[81] = 'H';
		map2[82] = ' ';
		map2[83] = ' ';
		map2[84] = ' ';
		map2[85] = ' ';
		map2[86] = ' ';
		map2[87] = ' ';
		map2[88] = ' ';
		map2[89] = 'X';

		// [91;100]

		for (int i = 90; i < 100; i++) {
			map2[i] = 'X';
		}

		printMap(map);

		while (endGame == 0) {
			Scanner s = new Scanner(System.in);

			char move = s.next().charAt(0);

			step(move, 1);

			printMap(map);

			if (endGame == 1)
				System.out.print("-- GAME OVER --\n");

			else if (endGame == 3)
				break;
		}

		// endGame=3;

		// LEVEL 2
		if (endGame == 3) {

			endGame = 0;

			printMap(map2);

			while (endGame == 0) {
				Scanner s2 = new Scanner(System.in);

				char move2 = s2.next().charAt(0);

				step(move2, 2);

				printMap(map2);

				if (endGame == 1)
					System.out.print("-- GAME OVER --\n");

				else if (endGame == 2)
					System.out.print("-- YOU WON --\n");
			}

		}

	}

}
