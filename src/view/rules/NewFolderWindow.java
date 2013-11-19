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
import module3.rules.RuleTree;

import view.grammardevelopment.RulesTreePanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

public class NewFolderWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField nameField;
	private JTextPane commentField;
	
	/**
	 * Create the dialog.
	 */
	public NewFolderWindow(final RulesTreePanel panel) {
		setTitle("Add New Rule");
		setBounds(100, 100, 497, 205);
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
			nameField.setBounds(122, 10, 328, 20);
			contentPanel.add(nameField);
			nameField.setColumns(10);
		}
		
		JLabel lblComment = new JLabel("Comment:");
		lblComment.setBounds(32, 38, 60, 14);
		contentPanel.add(lblComment);
		
		commentField = new JTextPane();
		commentField.setBounds(122, 42, 328, 81);
		contentPanel.add(commentField);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						panel.addFolderToTree(new RuleTree(nameField.getText(), commentField.getText(), null));
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
