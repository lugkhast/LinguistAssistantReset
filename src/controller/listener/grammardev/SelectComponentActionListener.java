package controller.listener.grammardev;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import components.Component;

import view.grammardevelopment.ComponentPanel;
import view.grammardevelopment.ViewSemanticsPanel;

public class SelectComponentActionListener extends MouseAdapter {

	private ComponentPanel prevSelectedPanel;
	private ViewSemanticsPanel loadPanel;
	
	public SelectComponentActionListener(ViewSemanticsPanel loadPanel){
		this.loadPanel = loadPanel;
	}
	
	public ComponentPanel getSelectedPanel(){
		return prevSelectedPanel;
	}
	
	public void deselectCurrentPanel(){
		if(prevSelectedPanel != null)
			prevSelectedPanel.setHighlighted(false);
		prevSelectedPanel = null;
	}
	 	
	public void mousePressed(MouseEvent e) {
		ComponentPanel selectedPanel = (ComponentPanel) e.getSource();
		deselectCurrentPanel();
		selectedPanel.setHighlighted(true);
		prevSelectedPanel = selectedPanel;
		loadPanel.setComponent(selectedPanel.getComponent());	
	}
}