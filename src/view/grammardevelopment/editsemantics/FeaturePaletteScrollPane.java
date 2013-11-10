package view.grammardevelopment.editsemantics;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import managers.FeatureManager;

import components.Component;
import components.Phrase;
import controller.listener.grammardev.editsemantics.FeaturePaletteCmbValuesItemListener;
import features.DBFeatureValues;
import features.Feature;
import features.FeatureList;

public class FeaturePaletteScrollPane extends JScrollPane{
	
	private JPanel panel;
	private JLabel componentName;
	private JComboBox cmbFeatures;
	private JComboBox cmbValues;
	private JButton resetButton;
	private ItemListener saveFeatureListener;
	
	public FeaturePaletteScrollPane(){
		this.setBorder(BorderFactory.createTitledBorder("Feature Palette"));
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		setViewportView(panel);
		componentName = new JLabel("Component: ");
		componentName.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
		componentName.setMinimumSize(new Dimension(200,20));
		componentName.setMaximumSize(new Dimension(200,20));
		cmbFeatures = new JComboBox();
		cmbFeatures.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
		cmbFeatures.setMinimumSize(new Dimension(200,20));
		cmbFeatures.setMaximumSize(new Dimension(200,20));
		cmbValues = new JComboBox();
		cmbValues.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
		cmbValues.setMinimumSize(new Dimension(200,20));
		cmbValues.setMaximumSize(new Dimension(200,20));
		resetButton = new JButton("Set ALL Features to Default");
		resetButton.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
		resetButton.setMinimumSize(new Dimension(200,20));
		resetButton.setMaximumSize(new Dimension(200,20));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		int palettewidth = (int)(width*0.4);
		int paletteheight = (int)(height*0.25);
		this.setPreferredSize(new Dimension(palettewidth,paletteheight));
		JLabel blankLabel = new JLabel("");
		blankLabel.setMinimumSize(new Dimension(200,20));
		blankLabel.setMaximumSize(new Dimension(200,20));
		blankLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
		JLabel blankLabel2 = new JLabel("");
		blankLabel2.setMinimumSize(new Dimension(200,20));
		blankLabel2.setMaximumSize(new Dimension(200,20));
		blankLabel2.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
		JLabel blankLabel3 = new JLabel("");
		blankLabel3.setMinimumSize(new Dimension(200,10));
		blankLabel3.setMaximumSize(new Dimension(200,10));
		blankLabel3.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
		panel.add(componentName);
		panel.add(blankLabel);
		panel.add(cmbFeatures);
		panel.add(blankLabel3);
		panel.add(cmbValues);
		panel.add(blankLabel2);
		panel.add(resetButton);
		
		initComponents();
	}
	
	public void initComponents(){
		cmbFeatures.setEnabled(false);
		cmbFeatures.setSelectedIndex(-1);
		cmbValues.setEnabled(false);
		cmbValues.setSelectedIndex(-1);
		resetButton.setEnabled(false);
	}
	
	private void unlockComponents(){
		cmbFeatures.setEnabled(true);
		cmbValues.setEnabled(true);
		resetButton.setEnabled(true);
	}
	
	private String getSelectedFeatInCmb(){
		return cmbFeatures.getSelectedItem().toString();
	}
	
	public void initCmbValues(Component comp){ //first click in panel
		if(comp!=null){
			FeatureList featList = new FeatureList(FeatureManager.getDefaultFeatures(comp.getName()));
			componentName.setText("Component: "+comp.getName());
			cmbFeatures.removeAllItems();
			for(Feature feat  : featList.getFeatureList()){
				cmbFeatures.addItem(feat.getName());
			}
			renewCmbValues(comp);
			unlockComponents();
		}
		
	}
	
	public void renewCmbValues(Component comp){ //call when an item state changes in Cmb
		DBFeatureValues values = null;
		if(comp!=null && cmbFeatures.getSelectedItem() != null){
			//try{
				values = FeatureManager.getFeatureValues(comp.getName(), cmbFeatures.getSelectedItem().toString());
			//}catch(Exception e){}
			unlockComponents();
			
			//remove listeners first so that changing the values in combo box will not trigger saves
			removeCmbValuesListeners();
			
			cmbValues.removeAllItems();
			if(values != null){
				for(String value : values.getValues()){
					cmbValues.addItem(value);
				}
				cmbValues.setSelectedItem(comp.getFeature(cmbFeatures.getSelectedItem().toString()).getValue());
			}
			
			cmbValues.addItemListener(saveFeatureListener);
		}
		
	}
	
	private void removeCmbValuesListeners(){
		ItemListener[] itemListeners = cmbValues.getItemListeners().clone();
		for(ItemListener itemListener: itemListeners)
			cmbValues.removeItemListener(itemListener);
	}
	
	public Feature getFeatureForSaving(){
		if(cmbFeatures.getSelectedItem()!=null && cmbValues.getSelectedItem()!=null){
			if(cmbFeatures.getSelectedIndex()!=-1 && cmbValues.getSelectedIndex()!=-1){
				return new Feature(cmbFeatures.getSelectedItem().toString(),cmbValues.getSelectedItem().toString(),true);
			}
			else 
				return null;
		}
		else return null;
	}
	
	public void addCmbFeaturesListener(ItemListener itemListener){ // needed para malagyan ng listener everytime a new item from combo box is selected
		cmbFeatures.addItemListener(itemListener);
	}
	
	public void addCmbValuesListener(ItemListener itemListener){
		saveFeatureListener = itemListener;
		cmbValues.addItemListener(itemListener);
	}
	
	public void resetCmbFeatIndex(){
		if(cmbFeatures.getItemCount()>0)
		cmbFeatures.setSelectedIndex(0);
	}
	
	public void addResetListener(ActionListener listener){
		resetButton.addActionListener(listener);
	}
}
