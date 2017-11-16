	package dkeep.gui;

import java.awt.EventQueue;
import java.awt.FontFormatException;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameGUI {

	private JFrame frame;
	private JPanel panelMain;
	private JPanel panelGame;
	private JPanel panelCustom;

	private DataBase dataBase;
	private PanelManager panelManager;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameGUI window = new GameGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public GameGUI() throws IOException, FontFormatException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 * @throws FontFormatException
	 */
	private void initialize() throws IOException, FontFormatException {
		frame = new JFrame();

		frame.setBounds(100, 100, 1018, 747);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		dataBase = new DataBase(frame);
		panelManager = new PanelManager();

		panelMain = new PanelMenu(dataBase, panelManager);
		panelMain.setBounds(0, 0, 1000, 700);
		frame.getContentPane().add(panelMain);

		panelGame = new PanelGame(dataBase, panelManager);
		panelGame.setBounds(0, 0, 1070, 770);
		frame.getContentPane().add(panelGame);

		panelCustom = new PanelCustom(dataBase, panelManager);
		panelCustom.setBounds(0, 0, 1070, 770);
		frame.getContentPane().add(panelCustom);

		panelManager.setPanels(panelMain, panelGame, panelCustom);
	}
}
