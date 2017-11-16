package dkeep.gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import dkeep.logic.DungeonMap;
import dkeep.logic.GameMap;
import dkeep.logic.GameState;
import dkeep.logic.GameState.State;

public class DataBase {

	/*
	 * Utilities
	 */

	private JFrame frame;
	private int width;
	private int height;
	private Font customFont;
	private BufferedImage mainMenu;
	private BufferedImage wall;
	private BufferedImage ground;
	private BufferedImage door;
	private BufferedImage doorOpen;
	private BufferedImage guard;
	private BufferedImage guardSleep;
	private BufferedImage ogre;
	private BufferedImage ogreStun;
	private BufferedImage club;
	private BufferedImage hero;
	private BufferedImage heroArmed;
	private BufferedImage lever;
	private BufferedImage leverActive;
	private BufferedImage key;

	
	/*
	 * Game Data
	 */

	private GameState game;
	private String guardType = "Rookie";
	private int enemyNum = 2;
	private char[][] customMap;

	public DataBase(JFrame frame) throws FileNotFoundException, FontFormatException, IOException {
		this.frame = frame;
		this.width = frame.getWidth();
		this.height = frame.getHeight();
		setNewFont();
		loadImages();
	}

	public void setNewFont() throws FileNotFoundException, FontFormatException, IOException {
		Font f = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("Font/pirulen.ttf")))
				.deriveFont(Font.PLAIN, 16);
		customFont = f;
	}

	public Font getCustomFont() {
		return customFont;
	}

	public void loadImages() throws IOException {
		this.mainMenu = ImageIO.read(new File("Images/menu.png"));
		this.wall = ImageIO.read(new File("Images/brick.png"));
		this.ground = ImageIO.read(new File("Images/grass.png"));
		this.door = ImageIO.read(new File("Images/door.png"));
		this.doorOpen = ImageIO.read(new File("Images/opendoor.png"));
		this.guard = ImageIO.read(new File("Images/skull.png"));
		this.guardSleep = ImageIO.read(new File("Images/skullsleep.png"));
		this.ogre = ImageIO.read(new File("Images/robot.png"));
		this.ogreStun = ImageIO.read(new File("Images/robotstun.png"));
		this.club = ImageIO.read(new File("Images/club.png"));
		this.hero = ImageIO.read(new File("Images/thomas.png"));
		this.heroArmed = ImageIO.read(new File("Images/thomasarmed.png"));
		this.lever = ImageIO.read(new File("Images/lever.png"));
		this.leverActive = ImageIO.read(new File("Images/leveropen.png"));
		this.key = ImageIO.read(new File("Images/key.png"));
	}

	public BufferedImage getMainMenu() {
		return mainMenu;
	}

	public BufferedImage getWall() {
		return wall;
	}

	public BufferedImage getGround() {
		return ground;
	}

	public BufferedImage getDoor() {
		return door;
	}

	public BufferedImage getDoorOpen() {
		return doorOpen;
	}

	public BufferedImage getGuard() {
		return guard;
	}

	public BufferedImage getGuardSleep() {
		return guardSleep;
	}

	public BufferedImage getOgre() {
		return ogre;
	}

	public BufferedImage getOgreStun() {
		return ogreStun;
	}

	public BufferedImage getClub() {
		return club;
	}

	public BufferedImage getHero() {
		return hero;
	}

	public BufferedImage getHeroArmed() {
		return heroArmed;
	}

	public BufferedImage getLever() {
		return lever;
	}

	public BufferedImage getLeverActive() {
		return leverActive;
	}

	public BufferedImage getKey() {
		return key;
	}

	public String getGuardType() {
		return guardType;
	}

	public void setGuardType(String guardType) {
		this.guardType = guardType;
	}

	public int getEnemyNum() {
		return enemyNum;
	}

	public void setEnemyNum(int enemyNum) {
		this.enemyNum = enemyNum;
	}

	public void loadGame() {
		game = new GameState();
		game.currentMap = new DungeonMap();

		if (customMap != null)
			game.currentMap.setCustomMap(customMap);

		game.setGuard(guardType);
		game.setOgres(enemyNum);
	}

	public void setData() {
		game.setGuard(guardType);
		game.setOgres(enemyNum);
	}

	public GameMap getCurrentMap() {
		return game.currentMap;
	}

	public State getGameState() {
		return game.currentState;
	}

	public boolean getNewLevel() {
		return game.newLevel;
	}

	public void setNewLevel() {
		game.newLevel = false;
	}

	public void updateMove(char move) {
		game.updatePos(move);
	}


	public void deleteGame() {
		game = null;
	}

	public int getMapDimension() {
		return game.currentMap.getMapDimension();
	}

	public void setMapDimension(int mapDimension) {
		game.currentMap.setMapDimension(mapDimension);
	}

	public void setFrame(int dimension) {
		setWidth(dimension * 70 + 300);
		setHeight(dimension * 70 + 47);
		frame.setSize(width, height);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public char[][] getCustomMap() {
		return customMap;
	}

	public void setCustomMap(char[][] customMap) {
		this.customMap = customMap;
		game.currentMap.setCustomMap(customMap);
	}

}
