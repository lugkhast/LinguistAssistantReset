package view.grammardevelopment.editsemantics;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;

import javax.swing.JButton;
import javax.swing.JPanel;

import components.Component;
import features.Feature;

public class CreationRightPanel extends JPanel{
	private ComponentPaletteScrollPane cpScrollPane;
	private FeaturePaletteScrollPane fpScrollPane;
	private LeafEditPalette leScrollPane;
	private JButton copyBtn;
	private JButton moveBtn;
	private JButton deleteBtn;
	private Component comp;
	
	public CreationRightPanel(){
		cpScrollPane = new ComponentPaletteScrollPane();
		fpScrollPane = new FeaturePaletteScrollPane();
		leScrollPane = new LeafEditPalette();
		
		createDeleteButton();
		createCopyButton();
		createMoveButton();
		
		add(deleteBtn);
		add(copyBtn);
		add(moveBtn);
		add(cpScrollPane);
		add(fpScrollPane);
		add(leScrollPane);
	}
	
	private void createMoveButton(){
		moveBtn = new JButton("Move Selected Component");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		int palettewidth = (int)(width*0.3);
		int paletteheight = (int)(height*0.05);
		Dimension dimension = new Dimension(palettewidth,paletteheight);
		moveBtn.setFont(new Font(this.getFont().getFontName(), Font.PLAIN, paletteheight/3));
		moveBtn.setPreferredSize(dimension);
	}

	private void createCopyButton(){
		copyBtn = new JButton("Copy Selected Component");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		int palettewidth = (int)(width*0.3);
		int paletteheight = (int)(height*0.05);
		Dimension dimension = new Dimension(palettewidth,paletteheight);
		copyBtn.setFont(new Font(this.getFont().getFontName(), Font.PLAIN, paletteheight/3));
		copyBtn.setPreferredSize(dimension);
	}
	
	private void createDeleteButton(){
		deleteBtn = new JButton("Delete Selected Component");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		int palettewidth = (int)(width*0.3);
		int paletteheight = (int)(height*0.05);
		Dimension dimension = new Dimension(palettewidth,paletteheight);
		deleteBtn.setFont(new Font(this.getFont().getFontName(), Font.PLAIN, paletteheight/3));
		deleteBtn.setPreferredSize(dimension);
	}
	
	public void clearInput(){
		fpScrollPane.initComponents();
		leScrollPane.disableComponents();
	}

	//Setters
	public void setComponent(Component comp){
		if(comp != null){
			clearInput();
			this.comp = comp;
			fpScrollPane.initCmbValues(comp);
			if(comp.isLeaf()){
				leScrollPane.setValues(comp);
				leScrollPane.enableComponents();
			}
			else
				leScrollPane.disableComponents();	
		}
	}
		
	public void addDnDListenerForAllButtons(MouseAdapter mouseAdapter){
		cpScrollPane.addListenersForAllButtons(mouseAdapter);
	}
	
	/*
	public void resetFeaturesDisplayToDefault(){
		if(comp != null){
			fpScrollPane.renewCmbValues(comp);
			fpScrollPane.resetCmbFeatIndex();
		}
	}
	*/
	
	public void refreshFeaturesDisplay(){
		fpScrollPane.renewCmbValues(comp);
	}
	
	//Getters
	public Feature getCurrDisplayedFeature(){
		return fpScrollPane.getFeatureForSaving();
	}
	
	public String getLeafConceptName(){
		return leScrollPane.getNameTextFieldContent();
	}
	
	public String getLeafConceptSense(){
		return leScrollPane.getSenseTextFieldContent();
	}
	
	public Component getComponent(){
		return comp;
	}
	
	//Move Listener
	public void addMoveBtnListener(ActionListener listener){
		moveBtn.addActionListener(listener);
	}
	
	//Copy Listener
	public void addCopyBtnListener(ActionListener listener){
		copyBtn.addActionListener(listener);
	}
	
	//Delete Listener
	public void addDeleteBtnListener(ActionListener listener){
		deleteBtn.addActionListener(listener);
	}
	
	//CompPalette Listener
	public void addCompPaletteDragListener(MouseAdapter mouseAdapter){
		cpScrollPane.addListenersForAllButtons(mouseAdapter);
	}
	
	//Leaf Palette Listener
	public void addLeafEditBtnListener(ActionListener listener){
		leScrollPane.setButtonListener(listener);
	}
	
	//Feature listeners
	public void addSaveFeatureListener(ItemListener listener){
		fpScrollPane.addCmbValuesListener(listener);
	}
	
	public void addResetFeatureBtnListener(ActionListener listener){
		fpScrollPane.addResetListener(listener);
	}
	
	public void addSelectFeatureComboBoxListener(ItemListener listener){
		fpScrollPane.addCmbFeaturesListener(listener);
	}

}
