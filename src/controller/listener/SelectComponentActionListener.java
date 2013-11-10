package controller.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import components.Component;

import view.grammardevelopment.ComponentPanel;
import view.grammardevelopment.ViewSemanticsPanel;

public class SelectComponentActionListener extends MouseAdapter {

	private static ComponentPanel prevSelectedPanel;
	private ViewSemanticsPanel loadPanel;
	
	
	
	public SelectComponentActionListener(ViewSemanticsPanel loadPanel){
		this.loadPanel = loadPanel;
	}
	
	public static ComponentPanel getSelectedPanel(){
		return prevSelectedPanel;
	}
	
	public static void deselectCurrentPanel(){
		prevSelectedPanel = null;
	}
	 	
	public void mousePressed(MouseEvent e) {
		ComponentPanel selectedPanel = (ComponentPanel) e.getSource();
		//set the old one to un-highlighted
		if(prevSelectedPanel != null)
			prevSelectedPanel.setHighlighted(false);
		
		selectedPanel.setHighlighted(true);
		prevSelectedPanel = selectedPanel;
		loadPanel.setComponentInfo(selectedPanel.getComponent());	
	}
}