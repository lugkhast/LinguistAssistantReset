package view.rules;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.TitledBorder;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.GridBagLayout;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JTabbedPane;
import java.awt.GridBagConstraints;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SimpleSpelloutRulePanel extends JPanel {
	private JTable table;
	private JTextField textField;
	private JTextField prefixField;
	private JTextField suffixField;

	/**
	 * Create the panel.
	 */
	public SimpleSpelloutRulePanel() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JPanel criteriaPanel = new JPanel();
		criteriaPanel.setBorder(new TitledBorder(null, "Criteria", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(criteriaPanel);
		criteriaPanel.setLayout(new BoxLayout(criteriaPanel, BoxLayout.X_AXIS));
		
		JTabbedPane criteriaTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		criteriaPanel.add(criteriaTabbedPane);
		
		JPanel featurePanel = new JPanel();
		criteriaTabbedPane.addTab("Feature", null, featurePanel, null);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Feature", "Value"
			}
		));
		
		JPanel panel = new JPanel();
		GroupLayout gl_featurePanel = new GroupLayout(featurePanel);
		gl_featurePanel.setHorizontalGroup(
			gl_featurePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_featurePanel.createSequentialGroup()
					.addComponent(table, GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_featurePanel.setVerticalGroup(
			gl_featurePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_featurePanel.createSequentialGroup()
					.addGroup(gl_featurePanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(table, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
						.addGroup(gl_featurePanel.createSequentialGroup()
							.addGap(12)
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)))
					.addContainerGap())
		);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		JButton btnAdd = new JButton("Add");
		panel.add(btnAdd);
		
		JButton btnRemove = new JButton("Remove");
		panel.add(btnRemove);
		featurePanel.setLayout(gl_featurePanel);
		
		JPanel wordPanel = new JPanel();
		criteriaTabbedPane.addTab("Word", null, wordPanel, null);
		
		JLabel lblWord = new JLabel("Word");
		
		textField = new JTextField();
		textField.setColumns(10);
		GroupLayout gl_wordPanel = new GroupLayout(wordPanel);
		gl_wordPanel.setHorizontalGroup(
			gl_wordPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_wordPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblWord)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textField, GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_wordPanel.setVerticalGroup(
			gl_wordPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_wordPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_wordPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblWord)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(337, Short.MAX_VALUE))
		);
		wordPanel.setLayout(gl_wordPanel);
		
		JPanel actionPanel = new JPanel();
		actionPanel.setBorder(new TitledBorder(null, "Action", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(actionPanel);
		
		JLabel lblPrefix = new JLabel("Add Prefix");
		
		prefixField = new JTextField();
		prefixField.setColumns(10);
		
		JLabel lblAddSuffix = new JLabel("Add Suffix");
		
		suffixField = new JTextField();
		suffixField.setColumns(10);
		GroupLayout gl_actionPanel = new GroupLayout(actionPanel);
		gl_actionPanel.setHorizontalGroup(
			gl_actionPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_actionPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_actionPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPrefix, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAddSuffix))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_actionPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(suffixField, GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
						.addComponent(prefixField, GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_actionPanel.setVerticalGroup(
			gl_actionPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_actionPanel.createSequentialGroup()
					.addGroup(gl_actionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPrefix)
						.addComponent(prefixField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_actionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAddSuffix)
						.addComponent(suffixField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		actionPanel.setLayout(gl_actionPanel);
		
	}
}
