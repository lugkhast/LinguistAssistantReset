package view.rules;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;

import module3.rules.Rule;

import view.grammardevelopment.RulesTreePanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewRuleWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField nameField;
	
	/**
	 * Create the dialog.
	 */
	public NewRuleWindow(final RulesTreePanel panel) {
		setTitle("Add New Rule");
		setBounds(100, 100, 497, 109);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		this.setVisible(true);
		contentPanel.setLayout(null);
		{
			JLabel lblRuleName = new JLabel("Rule Name:");
			lblRuleName.setBounds(32, 13, 103, 14);
			contentPanel.add(lblRuleName);
		}
		{
			nameField = new JTextField();
			nameField.setBounds(145, 10, 305, 20);
			contentPanel.add(nameField);
			nameField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//panel.addRuleToTree(new Rule(nameField.getText()));
						panel.addRuleToTree(Rule.createInstance(nameField.getText()));
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	
	
}
