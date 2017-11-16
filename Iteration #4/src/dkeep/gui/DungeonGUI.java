package dkeep.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dkeep.logic.CrazyOgreMap;
import dkeep.logic.DungeonMap;
import dkeep.logic.GameState;
import dkeep.logic.GameState.State;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.Font;

public class DungeonGUI {

	private JFrame frame;
	private JTextField textField;
	private GameState game;
	private JTextArea textArea;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DungeonGUI window = new DungeonGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DungeonGUI() {
		initialize();
	}

	public void processButtons(char move) {		

		if ((game.currentState != State.gameOver) && (game.currentState != State.gameWin)) {
			game.updatePos(move);
			game.setPrintString();
			textArea.setText(game.mapString);
		}
		
		if (game.currentState == State.gameWin)
			label.setText("You won.");
		else if (game.newLevel) {
			label.setText("Level up.");
			game.newLevel = false;
		} else if (game.currentState == State.gameOver){
			label.setText("You lost.");
		}else{
			label.setText("Keep going...");
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 560, 410);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setBounds(433, 325, 97, 25);
		frame.getContentPane().add(btnExit);

		JLabel lblNumberOfOgres = new JLabel("Number of Ogres");
		lblNumberOfOgres.setBounds(12, 13, 106, 16);
		frame.getContentPane().add(lblNumberOfOgres);

		textField = new JTextField();
		textField.setBounds(130, 10, 37, 22);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblGuardPersonality = new JLabel("Guard personality");
		lblGuardPersonality.setBounds(12, 48, 106, 16);
		frame.getContentPane().add(lblGuardPersonality);

		JComboBox<String> comboBox = new JComboBox();
		comboBox.setBounds(130, 45, 150, 22);
		frame.getContentPane().add(comboBox);
		comboBox.addItem("Rookie");
		comboBox.addItem("Drunken");
		comboBox.addItem("Suspicious");

		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				game = new GameState();
				game.currentMap = new DungeonMap();
				game.setGuard(comboBox.getSelectedItem().toString());

				if (textField.getText().equals(""))
					JOptionPane.showMessageDialog(frame, "You have to insert a positive number!");
				else {
					game.setOgres(Integer.parseInt(textField.getText()));
					game.setPrintString();
					textArea.setText(game.mapString);
				}

				label.setText("You can play now.");
			}
		});
		btnNewGame.setBounds(433, 44, 97, 25);
		frame.getContentPane().add(btnNewGame);

		textArea = new JTextArea();
		textArea.setFont(new Font("Courier New", Font.PLAIN, 20));
		textArea.setBounds(12, 77, 330, 245);
		frame.getContentPane().add(textArea);

		JButton btnUp = new JButton("Up");
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processButtons('w');
			}
		});
		btnUp.setBounds(409, 135, 75, 25);
		frame.getContentPane().add(btnUp);

		JButton btnLeft = new JButton("Left");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				processButtons('a');
			}
		});
		btnLeft.setBounds(354, 173, 75, 25);
		frame.getContentPane().add(btnLeft);

		JButton btnDown = new JButton("Down");
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				processButtons('s');
			}
		});
		btnDown.setBounds(409, 211, 75, 25);
		frame.getContentPane().add(btnDown);

		JButton btnRight = new JButton("Right");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				processButtons('d');
			}
		});
		btnRight.setBounds(455, 173, 75, 25);
		frame.getContentPane().add(btnRight);

		label = new JLabel("");
		label.setBounds(12, 334, 330, 16);
		frame.getContentPane().add(label);
	}
}
