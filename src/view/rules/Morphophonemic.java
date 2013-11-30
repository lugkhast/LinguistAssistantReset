package view.rules;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Morphophonemic extends JPanel {
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Create the panel.
	 */
	public Morphophonemic() {
		setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(222, 43, 86, 20);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(116, 31, 46, 14);
		add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setBounds(174, 76, 86, 20);
		add(textField_1);
		textField_1.setColumns(10);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Prefix", "Infix", "Suffix"}));
		comboBox.setBounds(63, 76, 67, 20);
		add(comboBox);

	}
}
