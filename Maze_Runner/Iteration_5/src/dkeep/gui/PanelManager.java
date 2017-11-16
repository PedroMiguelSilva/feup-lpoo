package dkeep.gui;

import javax.swing.JPanel;

public class PanelManager {

	// State Machine
	public enum State {
		mainPanel, gamePanel, customPanel
	};

	public enum Event {
		newGame, createMap, leaveCustom, gameOver
	};

	public State state;
	private JPanel pm;
	private JPanel pg;
	private JPanel pc;

	public PanelManager() {
		state = State.mainPanel;
	}

	public void setPanels(JPanel pm, JPanel pg, JPanel pc) {
		this.pm = pm;
		this.pg = pg;
		this.pc = pc;
	}

	public void updateState(Event evt) {
		if (evt == Event.newGame) {
			state = State.gamePanel;
			pm.setVisible(false);
			pg.setVisible(true);
			pg.setFocusable(true);
			pg.requestFocusInWindow();
			pc.setVisible(false);
		} else if (evt == Event.createMap) {
			state = State.customPanel;
			pm.setVisible(false);
			pg.setVisible(false);
			pc.setVisible(true);
		} else if (evt == Event.leaveCustom) {
			state = State.mainPanel;
			pm.setVisible(true);
			pg.setVisible(false);
			pc.setVisible(false);
		} else if (evt == Event.gameOver) {
			state = State.mainPanel;
			pm.setVisible(true);
			pg.setVisible(false);
			pc.setVisible(false);
		}
	}

}
