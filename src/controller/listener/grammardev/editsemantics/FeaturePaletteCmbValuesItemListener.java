package controller.listener.grammardev.editsemantics;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import view.grammardevelopment.DisplayScreen;
import view.grammardevelopment.editsemantics.CreationRightPanel;
import view.grammardevelopment.editsemantics.FeaturePaletteScrollPane;

import components.Component;

import controller.GrammarDevController;
import controller.listener.grammardev.SelectComponentActionListener;
import features.Feature;

public class FeaturePaletteCmbValuesItemListener implements ItemListener{
	
	private CreationRightPanel creationPanel;
	private GrammarDevController grammarDevController;
	
	public FeaturePaletteCmbValuesItemListener(GrammarDevController grammarDevController, CreationRightPanel creationPanel){
		this.creationPanel = creationPanel;
		this.grammarDevController = grammarDevController;
	}
	
	
	public void itemStateChanged(ItemEvent arg0) {
		Feature feat = creationPanel.getCurrDisplayedFeature();
		Component comp = null;
		try{
			comp = grammarDevController.getCurrSelectedComponentPanel().getComponent();
			if(feat!=null){
				comp.setFeature(feat);
				grammarDevController.getCurrentlyDisplayedDocumentPanel().refreshPanelToolTips();
			}
		}catch(Exception x){}
	}

}
