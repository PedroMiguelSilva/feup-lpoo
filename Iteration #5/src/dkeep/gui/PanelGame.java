package dkeep.gui;

import javax.swing.JPanel;

import dkeep.gui.PanelManager.Event;
import dkeep.logic.GameState.State;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.JButton;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelGame extends JPanel implements KeyListener {

	private DataBase dataBase;
	private PanelManager panelManager;

	private JButton btnUp;
	private JButton btnDown;
	private JButton btnLeft;
	private JButton btnRight;
	private JLabel lblControllers;

	/**
	 * Create the panel.
	 * 
	 * @throws IOException
	 */
	public PanelGame(DataBase data, PanelManager manager) throws IOException {
		setVisible(false);
		dataBase = data;
		panelManager = manager;
		addKeyListener(this);
		setLayout(null);

		lblControllers = new JLabel("Controllers");
		lblControllers.setFont(dataBase.getCustomFont());
		add(lblControllers);

		initializeButtons();
		dataBase.loadGame();
	}

	public void initializeButtons() {
		buttonUp();
		buttonDown();
		buttonLeft();
		buttonRight();
	}

	public void buttonUp() {
		btnUp = new JButton("Up");
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				processKeys('w');
				requestFocusInWindow();
			}
		});
		btnUp.setFont(new Font("Tahoma", Font.PLAIN, 17));
		add(btnUp);
	}

	public void buttonDown() {
		btnDown = new JButton("Down");
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processKeys('s');
				requestFocusInWindow();
			}
		});
		btnDown.setFont(new Font("Tahoma", Font.PLAIN, 17));
		add(btnDown);
	}

	public void buttonLeft() {
		btnLeft = new JButton("Left");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processKeys('a');
				requestFocusInWindow();
			}
		});
		btnLeft.setFont(new Font("Tahoma", Font.PLAIN, 17));
		add(btnLeft);
	}

	public void buttonRight() {
		btnRight = new JButton("Right");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processKeys('d');
				requestFocusInWindow();
			}
		});
		btnRight.setFont(new Font("Tahoma", Font.PLAIN, 17));
		add(btnRight);
	}

	public void processKeys(char move) {

		dataBase.updateMove(move);
		repaint();

		if (dataBase.getGameState() == State.gameOver) {
			JOptionPane.showMessageDialog(getRootPane(), "Game Over!");
			panelManager.updateState(Event.gameOver);
			dataBase.deleteGame();
			dataBase.loadGame();
			dataBase.setFrame(dataBase.getMapDimension());
		} else if (dataBase.getGameState() == State.gameWin) {
			JOptionPane.showMessageDialog(getRootPane(), "You Won!");
			panelManager.updateState(Event.gameOver);
			dataBase.deleteGame();
			dataBase.loadGame();
			dataBase.setFrame(dataBase.getMapDimension());
		} else if (dataBase.getNewLevel()) {
			dataBase.setNewLevel();
			JOptionPane.showMessageDialog(getRootPane(), "Level Up!");
		}
	}

	public void updateDisplay() {
		dataBase.setFrame(dataBase.getMapDimension());
		btnUp.setBounds(dataBase.getWidth() - 200, 80, 90, 40);
		btnDown.setBounds(dataBase.getWidth() - 200, 200, 90, 40);
		btnLeft.setBounds(dataBase.getWidth() - 260, 140, 90, 40);
		btnRight.setBounds(dataBase.getWidth() - 140, 140, 90, 40);
		lblControllers.setBounds(dataBase.getWidth() - 240, 10, 180, 40);
	}

	@Override
	public void paintComponent(Graphics g) {
		updateDisplay();
		super.paintComponent(g);

		for (int i = 0; i < dataBase.getMapDimension(); i++) {
			for (int j = 0; j < dataBase.getMapDimension(); j++) {

				if (dataBase.getCurrentMap().getMap()[i][j] == 'X')
					g.drawImage(dataBase.getWall(), j * 70, i * 70, this);
				else if (dataBase.getCurrentMap().getMap()[i][j] == 'I')
					g.drawImage(dataBase.getDoor(), j * 70, i * 70, this);
				else if (dataBase.getCurrentMap().getMap()[i][j] == 'k') {
					if (dataBase.getCurrentMap().isLever()) {
						if (dataBase.getCurrentMap().isDoorsOpen())
							g.drawImage(dataBase.getLeverActive(), j * 70, i * 70, this);
						else
							g.drawImage(dataBase.getLever(), j * 70, i * 70, this);
					} else
						g.drawImage(dataBase.getKey(), j * 70, i * 70, this);
				} else if (dataBase.getCurrentMap().getMap()[i][j] == 'S')
					g.drawImage(dataBase.getDoorOpen(), j * 70, i * 70, this);
				else
					g.drawImage(dataBase.getGround(), j * 70, i * 70, this);
			}
		}

		paintCharacters(g);
	}

	public void paintCharacters(Graphics g) {
		paintOgres(g);
		paintGuard(g);
		paintHero(g);
	}

	public void paintOgres(Graphics g) {
		if (dataBase.getCurrentMap().getOgres() != null)
			for (int i = 0; i < dataBase.getCurrentMap().getOgres().size(); i++) {

				if (dataBase.getCurrentMap().getOgres().get(i).getStun())
					g.drawImage(dataBase.getOgreStun(), dataBase.getCurrentMap().getOgres().get(i).getX() * 70,
							dataBase.getCurrentMap().getOgres().get(i).getY() * 70, this);
				else
					g.drawImage(dataBase.getOgre(), dataBase.getCurrentMap().getOgres().get(i).getX() * 70,
							dataBase.getCurrentMap().getOgres().get(i).getY() * 70, this);

				g.drawImage(dataBase.getClub(), dataBase.getCurrentMap().getOgres().get(i).getClubX() * 70,
						dataBase.getCurrentMap().getOgres().get(i).getClubY() * 70, this);
			}
	}

	public void paintGuard(Graphics g) {
		if (dataBase.getCurrentMap().getGuard() != null) {

			if (dataBase.getCurrentMap().getGuard().getSymbol() == 'G')
				g.drawImage(dataBase.getGuard(), dataBase.getCurrentMap().getGuard().getX() * 70,
						dataBase.getCurrentMap().getGuard().getY() * 70, this);
			else
				g.drawImage(dataBase.getGuardSleep(), dataBase.getCurrentMap().getGuard().getX() * 70,
						dataBase.getCurrentMap().getGuard().getY() * 70, this);
		}
	}

	public void paintHero(Graphics g) {
		if (dataBase.getCurrentMap().getHero().getIsArmed())
			g.drawImage(dataBase.getHeroArmed(), dataBase.getCurrentMap().getHero().getX() * 70,
					dataBase.getCurrentMap().getHero().getY() * 70, this);
		else
			g.drawImage(dataBase.getHero(), dataBase.getCurrentMap().getHero().getX() * 70,
					dataBase.getCurrentMap().getHero().getY() * 70, this);
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			processKeys('a');
			break;
		case KeyEvent.VK_RIGHT:
			processKeys('d');
			break;
		case KeyEvent.VK_UP:
			processKeys('w');
			break;
		case KeyEvent.VK_DOWN:
			processKeys('s');
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
