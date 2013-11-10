package view.grammardevelopment.editsemantics;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import components.Leaf;
import components.Component;

public class LeafEditPalette extends JScrollPane{
	JPanel panel;
	JLabel label; 
	JLabel nameLabel;
	JTextField nameTxtField; 
	JButton button;
	JLabel senseLabel;
	JTextField senseTxtField;
	
	
	public LeafEditPalette(){
		this.setBorder(BorderFactory.createTitledBorder("Concept Editing Palette"));
		panel = new JPanel();  
		setViewportView(panel);
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		int palettewidth = (int)(width*0.4);
		int paletteheight = (int)(height*0.25);
		this.setPreferredSize(new Dimension(palettewidth,paletteheight));
		addComponents();
	}
	
	private void addComponents(){
		label = new JLabel("Edit Concept: ");
		label.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
		nameLabel = new JLabel("Name:");
		nameLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
		nameTxtField = new JTextField("");
		nameTxtField.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
		nameTxtField.setMinimumSize(new Dimension(200,20));
		nameTxtField.setMaximumSize(new Dimension(200,20));
		senseLabel = new JLabel("Lexical Sense:");
		senseLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
		senseTxtField = new JTextField("");
		senseTxtField.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
		senseTxtField.setMinimumSize(new Dimension (200,20));
		senseTxtField.setMaximumSize(new Dimension (200,20));
		button = new JButton("Update");
		button.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
		JLabel blankLabel = new JLabel("");
		blankLabel.setMinimumSize(new Dimension(200,20));
		blankLabel.setMaximumSize(new Dimension(200,20));
		blankLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
		panel.add(label);
		panel.add(nameLabel);
		panel.add(nameTxtField);
		panel.add(senseLabel);
		panel.add(senseTxtField);
		panel.add(blankLabel);
		panel.add(button);
		
		disableComponents();
	}
	
	public void disableComponents(){
		label.setEnabled(false);
		label.setText("Edit Concept: ");
		nameLabel.setEnabled(false);
		nameTxtField.setEnabled(false);
		nameTxtField.setText("");
		senseLabel.setEnabled(false);
		senseTxtField.setEnabled(false);
		senseTxtField.setText("");
		button.setEnabled(false);
	}
	
	public void enableComponents(){
		label.setEnabled(true);
		nameLabel.setEnabled(true);
		nameTxtField.setEnabled(true);
		senseLabel.setEnabled(true);
		senseTxtField.setEnabled(true);
		button.setEnabled(true);
	}
	
	public void setValues(Component comp){
		if (comp!=null){
			label.setText(label.getText()+comp.getName());
			nameTxtField.setText(((Leaf)comp).getConcept());
			senseTxtField.setText(((Leaf)comp).getLexicalSense());
		}
	}
	
	public void setButtonListener(ActionListener actionListener){
		button.addActionListener(actionListener);
	}
	
	public void setLabelConcept(String text){
		label.setText(label.getText()+text);
	}
	
	public String getNameTextFieldContent(){
		return nameTxtField.getText();
	}
	
	public String getSenseTextFieldContent(){
		return senseTxtField.getText();
	}
	
}
