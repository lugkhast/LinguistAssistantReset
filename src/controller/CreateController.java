package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import managers.FeatureManager;

import components.Component;
import components.Leaf;
import controller.listener.SelectComponentActionListener;

import features.Feature;

import view.grammardevelopment.ComponentPanel;
import view.grammardevelopment.DisplayScreen;
import view.grammardevelopment.editsemantics.ComponentPaletteScrollPane;
import view.grammardevelopment.editsemantics.FeaturePaletteScrollPane;
import view.grammardevelopment.editsemantics.LeafEditPalette;

public class CreateController {

	DisplayScreen displayScreen; 
	FeaturePaletteScrollPane featPalette;
	ComponentPaletteScrollPane compPalette;
	LeafEditPalette lePalette;
	
	
	public CreateController(DisplayScreen displayScreen,FeaturePaletteScrollPane featPalette, ComponentPaletteScrollPane compPalette,
										LeafEditPalette lePalette){
		this.displayScreen = displayScreen; 
		this.featPalette = featPalette; // pwedeng instantiated dito yung palettes
		this.compPalette = compPalette;
		this.lePalette = lePalette;
		
		addListenerToCompPalette();
		addListenersToFeatPalette();
		addListenersToLeafEditPalette();
	}

	private void addListenersToLeafEditPalette() {
		lePalette.setButtonListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				ComponentPanel compPanel = SelectComponentActionListener.getSelectedPanel();
				Leaf comp = (Leaf)compPanel.getComponent();
				String newName = lePalette.getNameTextFieldContent();
				comp.setConcept(newName);
				String newSense = lePalette.getSenseTextFieldContent();
				comp.setLexicalSense(newSense);
				compPanel.refreshLabelText();
				System.out.println("Saved");
			}});
	}

	private void addListenersToFeatPalette() {
		
		featPalette.addListenerForReset(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Object[] options = {"Yes","No"};
				int n = JOptionPane.showOptionDialog(new JFrame(),
							    "This would RESET the values of ALL FEATURES to default\nContinue?",
							    "WARNING",
							    JOptionPane.YES_NO_CANCEL_OPTION,
							    JOptionPane.WARNING_MESSAGE,
							    null,
							    options,
							    options[1]);
				if(n == 0){
					Component comp = null;
					try{
					comp = SelectComponentActionListener.getSelectedPanel().getComponent();
					}catch(Exception x){}
					ArrayList<Feature> featList = FeatureManager.getDefaultFeatures(comp.getName());
					for(Feature feature : featList){
						comp.setFeature(feature);
					}
					if(comp!= null){
						featPalette.renewCmbValues(comp);
						featPalette.resetCmbFeatIndex();
					}
				}
			}});
					
					
			featPalette.addCmbFeaturesListener(new ItemListener(){
				
				public void itemStateChanged(ItemEvent e) {
					Component comp = null;
					try{
					comp = SelectComponentActionListener.getSelectedPanel().getComponent();
					}catch(Exception x){}
					((JComboBox)e.getSource()).getSelectedItem();
						featPalette.renewCmbValues(comp);
				
			}});	
	}

	private void addListenerToCompPalette() {
		
		compPalette.addListenersForAllButtons(new MouseListener(){
			public void mousePressed(MouseEvent e){
                JButton button = (JButton)e.getSource();
                //add 
            }
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});
	}
	
}