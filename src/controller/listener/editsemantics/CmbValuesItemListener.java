package controller.listener.editsemantics;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import view.grammardevelopment.DisplayScreen;
import view.grammardevelopment.editsemantics.FeaturePaletteScrollPane;

import components.Component;

import controller.listener.SelectComponentActionListener;
import features.Feature;

public class CmbValuesItemListener implements ItemListener{
	public void itemStateChanged(ItemEvent arg0) {
		//System.out.println("Pasok");
		Feature feat = FeaturePaletteScrollPane.saveFeature(); 
		Component comp = null;
		try{
		comp = SelectComponentActionListener.getSelectedPanel().getComponent();
		if(feat!=null){
			comp.setFeature(feat);
			DisplayScreen.getCurrentlyDisplayedDocumentPanel().refreshPanelToolTips();
		}
		}catch(Exception x){}
	}

}
