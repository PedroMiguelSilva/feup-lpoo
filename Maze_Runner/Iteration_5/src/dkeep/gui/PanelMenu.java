package dkeep.gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.Graphics;
import javax.swing.JPanel;

import dkeep.gui.PanelManager.Event;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FontFormatException;

public class PanelMenu extends JPanel {

	private DataBase dataBase;
	private PanelManager panelManager;
	private JDialog settings;

	/**
	 * Create the panel.
	 * 
	 * @throws IOException
	 * @throws FontFormatException
	 * @throws FileNotFoundException
	 * 
	 */
	public PanelMenu(DataBase data, PanelManager manager)
			throws FileNotFoundException, FontFormatException, IOException {

		setVisible(true);
		dataBase = data;
		panelManager = manager;

		this.settings = new DialogSettings(dataBase, panelManager);
		setLayout(null);

		initializeButtons();
	}

	public void initializeButtons() {
		buttonNewGame();
		buttonSettings();
		buttonCreateMaze();
		buttonExit();
	}

	public void buttonNewGame() {
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.setFont(dataBase.getCustomFont());
		btnNewGame.setBounds(500, 330, 200, 50);
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelManager.updateState(Event.newGame);
			}
		});
		add(btnNewGame);
	}

	public void buttonSettings() {
		JButton btnSettings = new JButton("Settings");
		btnSettings.setFont(dataBase.getCustomFont());
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setVisible(true);
			}
		});
		btnSettings.setBounds(500, 400, 200, 50);
		add(btnSettings);
	}

	public void buttonCreateMaze() {
		JButton btnCreateMaze = new JButton("Create Maze");
		btnCreateMaze.setFont(dataBase.getCustomFont());
		btnCreateMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelManager.updateState(Event.createMap);
			}
		});
		btnCreateMaze.setBounds(500, 470, 200, 50);
		add(btnCreateMaze);
	}

	public void buttonExit() {
		JButton btnExit = new JButton("Exit");
		btnExit.setFont(dataBase.getCustomFont());
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setBounds(500, 540, 200, 50);
		add(btnExit);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(dataBase.getMainMenu(), 0, 0, this);
	}
}
