package controller.listener.grammardev.editsemantics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import managers.FeatureManager;

import components.Component;

import view.grammardevelopment.editsemantics.CreationRightPanel;
import controller.GrammarDevController;
import features.Feature;

public class FeaturePaletteResetBtnListener implements ActionListener{

	private CreationRightPanel creationPanel;
	
	public FeaturePaletteResetBtnListener(CreationRightPanel creationPanel){
		this.creationPanel = creationPanel;
	}
	
	public void actionPerformed(ActionEvent e) {
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
			Component comp = creationPanel.getComponent();
		
			ArrayList<Feature> featList = FeatureManager.getDefaultFeatures(comp.getName());
			for(Feature feature : featList)
				comp.setFeature(feature);
			
			if(comp!= null)
				creationPanel.setComponent(comp);
		}
	}
}
