package dkeep.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.FontFormatException;

import javax.swing.JComboBox;
import javax.swing.JSlider;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class DialogSettings extends JDialog {

	private DataBase dataBase;
	private final JPanel contentPanel = new JPanel();

	private JComboBox<String> comboBox;
	private JSlider slider;
	private JPanel buttonPane;

	/**
	 * Create the dialog.
	 * 
	 * @throws IOException
	 * @throws FontFormatException
	 * @throws FileNotFoundException
	 */
	public DialogSettings(DataBase data, PanelManager manager)
			throws FileNotFoundException, FontFormatException, IOException {

		dataBase = data;

		setBounds(100, 100, 500, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		initializeLabels();
		initializeButtons();
		initializeSlider();
		initializeComboBox();
	}

	public void initializeLabels() {
		JLabel lblTypeOfGuard = new JLabel("Type of Guard");
		lblTypeOfGuard.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTypeOfGuard.setBounds(12, 105, 109, 16);
		contentPanel.add(lblTypeOfGuard);

		JLabel lblNumberOfEnemies = new JLabel("Number of Enemies");
		lblNumberOfEnemies.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNumberOfEnemies.setBounds(12, 190, 147, 16);
		contentPanel.add(lblNumberOfEnemies);

		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setFont(dataBase.getCustomFont());
		lblSettings.setBounds(186, 13, 161, 32);
		contentPanel.add(lblSettings);
	}

	public void initializeButtons() {
		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		buttonSave();
		buttonCancel();
	}

	public void buttonSave() {
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataBase.setGuardType(comboBox.getSelectedItem().toString());
				dataBase.setEnemyNum(slider.getValue());
				dataBase.setData();
				dispose();
			}
		});
		buttonPane.add(btnSave);
	}

	public void buttonCancel() {
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}

	public void initializeSlider() {
		slider = new JSlider();
		slider.setMajorTickSpacing(1);
		slider.setMinorTickSpacing(1);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setMaximum(5);
		slider.setMinimum(1);
		slider.setFont(new Font("Tahoma", Font.PLAIN, 11));
		slider.setBounds(189, 190, 250, 44);
		slider.setValue(2);
		contentPanel.add(slider);
	}

	public void initializeComboBox() {
		comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox.setBounds(189, 102, 132, 22);
		comboBox.addItem("Rookie");
		comboBox.addItem("Drunken");
		comboBox.addItem("Suspicious");
		contentPanel.add(comboBox);
	}
}
