package dkeep.gui;

import javax.swing.JPanel;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dkeep.gui.PanelManager.Event;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelCustom extends JPanel implements MouseListener, MouseMotionListener {

	private DataBase dataBase;
	private PanelManager panelManager;

	private JLabel lblCreateYourMaze;
	private JLabel lblMazeDimension;
	private JSlider slider;
	private JButton btnSave;
	private JButton btnCancel;

	private char[][] map;
	private int dimension = 10;

	@SuppressWarnings("unused")
	private BufferedImage chosenPicture;

	private int xWall, yWall;
	private int xDoor, yDoor;
	private int xHero, yHero;
	private int xOgre, yOgre;
	private int xKey, yKey;
	private int xGround, yGround;

	private char symbolchosenPicture;
	private int xPressed, yPressed;

	public class SliderListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent arg0) {
			dimension = slider.getValue();
			createMap();
			repaint();
		}
	}

	/**
	 * Create the panel.
	 * 
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public PanelCustom(DataBase data, PanelManager manager) throws IOException, FontFormatException {
		dataBase = data;
		panelManager = manager;

		setVisible(false);
		addMouseListener(this);
		addMouseMotionListener(this);
		setLayout(null);

		initializeLabels();
		initializeSlider();
		initializeButtons();
		dataBase.setFrame(dimension);
		createMap();
	}

	public void initializeLabels() {
		lblCreateYourMaze = new JLabel("Create Your Maze");
		lblCreateYourMaze.setBounds(0, 0, 0, 0);
		lblCreateYourMaze.setFont(dataBase.getCustomFont());
		add(lblCreateYourMaze);

		lblMazeDimension = new JLabel("Maze Dimension");
		lblMazeDimension.setBounds(0, 0, 0, 0);
		lblMazeDimension.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(lblMazeDimension);
	}

	public void initializeSlider() {
		slider = new JSlider();
		slider.setBounds(0, 0, 0, 0);
		slider.setMajorTickSpacing(2);
		slider.setMinorTickSpacing(1);
		slider.setMinimum(7);
		slider.setMaximum(11);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setValue(10);
		slider.addChangeListener(new SliderListener());
		add(slider);
	}

	public void initializeButtons() {
		buttonSave();
		buttonCancel();
	}

	public void buttonSave() {
		btnSave = new JButton("Save");
		btnSave.setBounds(0, 0, 0, 0);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (canSaveMap()) {
					dataBase.setCustomMap(map);
					panelManager.updateState(Event.leaveCustom);
					dataBase.setFrame(dataBase.getMapDimension());
				} else
					JOptionPane.showMessageDialog(getRootPane(),
							"This map is not valid. The map has to have at least one key and one door, one and only one hero and no more than five ogres.");
			}
		});
		add(btnSave);
	}

	public void buttonCancel() {
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(0, 0, 0, 0);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelManager.updateState(Event.leaveCustom);
				dataBase.setFrame(dataBase.getMapDimension());
			}
		});
		add(btnCancel);
	}

	public void setCoordinates() {
		xWall = dataBase.getWidth() - 140;
		yWall = 180;
		xDoor = dataBase.getWidth() - 240;
		yDoor = 180;
		xHero = dataBase.getWidth() - 140;
		yHero = 270;
		xOgre = dataBase.getWidth() - 240;
		yOgre = 270;
		xKey = dataBase.getWidth() - 140;
		yKey = 360;
		xGround = dataBase.getWidth() - 240;
		yGround = 360;
	}

	public void createMap() {
		this.map = new char[dimension][dimension];

		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {

				if (i == 0 || i == dimension - 1 || j == 0 || j == dimension - 1) {
					map[i][j] = 'X';
				}
			}
		}
	}

	public void updateFrame() {
		dataBase.setFrame(dimension);
		setCoordinates();
		lblCreateYourMaze.setBounds(dataBase.getWidth() - 270, 13, 322, 32);
		lblMazeDimension.setBounds(dataBase.getWidth() - 220, 70, 128, 16);
		slider.setBounds(dataBase.getWidth() - 260, 100, 200, 53);
		btnSave.setBounds(dataBase.getWidth() - 270, dataBase.getHeight() - 90, 97, 25);
		btnCancel.setBounds(dataBase.getWidth() - 150, dataBase.getHeight() - 90, 97, 25);
	}

	@Override
	public void paintComponent(Graphics g) {
		updateFrame();
		super.paintComponent(g);

		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				int x = j * 70;
				int y = i * 70;

				if (map[i][j] == 'X')
					g.drawImage(dataBase.getWall(), x, y, this);
				else if (map[i][j] == 'I')
					g.drawImage(dataBase.getDoor(), x, y, this);
				else if (map[i][j] == 'k') {
					g.drawImage(dataBase.getKey(), x, y, this);
				} else if (map[i][j] == 'A') {
					g.drawImage(dataBase.getGround(), x, y, this);
					g.drawImage(dataBase.getHeroArmed(), x, y, this);
				} else if (map[i][j] == 'O') {
					g.drawImage(dataBase.getGround(), x, y, this);
					g.drawImage(dataBase.getOgre(), x, y, this);
				} else
					g.drawImage(dataBase.getGround(), x, y, this);
			}
		}

		g.drawImage(dataBase.getWall(), xWall, yWall, this);
		g.drawImage(dataBase.getDoor(), xDoor, yDoor, this);
		g.drawImage(dataBase.getHeroArmed(), xHero, yHero, this);
		g.drawImage(dataBase.getOgre(), xOgre, yOgre, this);
		g.drawImage(dataBase.getKey(), xKey, yKey, this);
		g.drawImage(dataBase.getGround(), xGround, yGround, this);
	}

	public void choosePiece(int x, int y) {
		chooseWall(x, y);
		chooseDoor(x, y);
		chooseHero(x, y);
		chooseOgre(x, y);
		chooseKey(x, y);
		chooseGround(x, y);
	}

	public void chooseWall(int x, int y) {
		if (x >= xWall && x <= xWall + 70)
			if (y >= yWall && y <= yWall + 70) {
				this.chosenPicture = dataBase.getWall();
				symbolchosenPicture = 'X';
			}
	}

	public void chooseDoor(int x, int y) {
		if (x >= xDoor && x <= xDoor + 70)
			if (y >= yDoor && y <= yDoor + 70) {
				this.chosenPicture = dataBase.getDoor();
				symbolchosenPicture = 'I';
			}
	}

	public void chooseHero(int x, int y) {
		if (x >= xHero && x <= xHero + 70)
			if (y >= yHero && y <= yHero + 70) {
				this.chosenPicture = dataBase.getHeroArmed();
				symbolchosenPicture = 'A';
			}
	}

	public void chooseOgre(int x, int y) {
		if (x >= xOgre && x <= xOgre + 70)
			if (y >= yOgre && y <= yOgre + 70) {
				this.chosenPicture = dataBase.getOgre();
				symbolchosenPicture = 'O';
			}
	}

	public void chooseKey(int x, int y) {
		if (x >= xKey && x <= xKey + 70)
			if (y >= yKey && y <= yKey + 70) {
				this.chosenPicture = dataBase.getKey();
				symbolchosenPicture = 'k';
			}
	}

	public void chooseGround(int x, int y) {
		if (x >= xGround && x <= xGround + 70)
			if (y >= yGround && y <= yGround + 70) {
				this.chosenPicture = dataBase.getGround();
				symbolchosenPicture = ' ';
			}
	}

	public boolean canPaintMap(int i, int j) {

		if (map[i][j] != 'X' && symbolchosenPicture == 'I')
			return false;

		if (map[i][j] == 'X')
			if (symbolchosenPicture != 'I')
				return false;
			else {
				if (i == 0 || j == 0 || i == dimension - 1 || j == dimension - 1)
					return true;
				else
					return false;
			}

		return true;
	}

	public void paintMap() {

		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {

				int x = j * 70;
				int y = i * 70;

				if (xPressed > x && xPressed < x + 70)
					if (yPressed > y && yPressed < y + 70) {

						if (canPaintMap(i, j)) {
							map[i][j] = symbolchosenPicture;
							repaint();
						}
					}
			}
		}
	}

	public boolean canSaveMap() {

		int heroNum = 0;
		int ogreNum = 0;
		int doorNum = 0;
		int keyNum = 0;

		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (map[i][j] == 'A')
					heroNum++;
				if (map[i][j] == 'I')
					doorNum++;
				if (map[i][j] == 'k')
					keyNum++;
				if (map[i][j] == 'O')
					ogreNum++;
			}
		}

		if (heroNum != 1 || doorNum < 1 || keyNum < 1 || ogreNum >5)
			return false;
		else
			return true;

	}

	@Override
	public void mousePressed(MouseEvent e) {
		xPressed = e.getX();
		yPressed = e.getY();
		choosePiece(e.getX(), e.getY());
		paintMap();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		xPressed = e.getX();
		yPressed = e.getY();
		paintMap();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
