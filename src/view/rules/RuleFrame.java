package view.rules;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.BoxLayout;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextPane;

import view.grammardevelopment.ViewSemanticsPanel;

import module3.rules.Rule;

import java.awt.SystemColor;

public class RuleFrame extends JFrame {

	private JPanel contentPane;
	private JTextField nameField;
	JTabbedPane tabbedPane;
	Rule rule;
	String comment;
	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	public RuleFrame(Rule selectedRule, String comment) {
		rule = selectedRule;
		this.comment = comment;
		
		setTitle("Rule Settings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 538, 341);
		//the panels that you'll be making will be placed in tabbedPane
		initializeComponents();
		addTabs();
		setVisible(true);
	}

	public void addTabs()
	{
		tabbedPane.add("Simple", new SimpleSpelloutRulePanel());
		tabbedPane.add("Morphophonemic", new MorphophonemicPanel());
		tabbedPane.add("Structural Adjustment", new StructuralAdjustmentPanel());
	}
	public void initializeComponents()
	{
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{512, 0};
		gbl_contentPane.rowHeights = new int[]{90, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{88, 341, 0};
		gbl_panel.rowHeights = new int[]{14, 61, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblNewLabel = new JLabel("Rule Name:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		nameField = new JTextField();
		nameField.setText(rule.getName());
		GridBagConstraints gbc_nameField = new GridBagConstraints();
		gbc_nameField.insets = new Insets(0, 0, 5, 0);
		gbc_nameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameField.gridx = 1;
		gbc_nameField.gridy = 0;
		panel.add(nameField, gbc_nameField);
		nameField.setColumns(10);
		
		JLabel lblComments = new JLabel("Comments:");
		GridBagConstraints gbc_lblComments = new GridBagConstraints();
		gbc_lblComments.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblComments.insets = new Insets(0, 0, 0, 5);
		gbc_lblComments.gridx = 0;
		gbc_lblComments.gridy = 1;
		panel.add(lblComments, gbc_lblComments);
		
		JTextPane commentField = new JTextPane();
		commentField.setText(comment);
		GridBagConstraints gbc_commentField = new GridBagConstraints();
		gbc_commentField.fill = GridBagConstraints.BOTH;
		gbc_commentField.gridx = 1;
		gbc_commentField.gridy = 1;
		panel.add(commentField, gbc_commentField);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(SystemColor.scrollbar);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 1;
		contentPane.add(tabbedPane, gbc_tabbedPane);
	}
}
