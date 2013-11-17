package controller.listener.grammardev;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import components.Component;
import components.Phrase;

import view.grammardevelopment.ComponentPanel;
import view.grammardevelopment.InputXMLDocumentPanel;
import view.grammardevelopment.ViewSemanticsPanel;

public class SelectComponentActionListener extends MouseAdapter {

	private ComponentPanel prevSelectedPanel;
	private ViewSemanticsPanel loadPanel;
	
	boolean copyComponent = false;
	
	public SelectComponentActionListener(ViewSemanticsPanel loadPanel){
		this.loadPanel = loadPanel;
	}
	
	public ComponentPanel getSelectedPanel(){
		return prevSelectedPanel;
	}
	
	public void deselectCurrentPanel(){
		if(prevSelectedPanel != null)
			prevSelectedPanel.setHighlighted(false);
		
	}
	
	public void mousePressed(MouseEvent e) {
		ComponentPanel selectedPanel = (ComponentPanel) e.getSource();
		deselectCurrentPanel();
		selectedPanel.setHighlighted(true);
		
		if (copyComponent)
			copySelectedComponentToPanel(selectedPanel, e.getPoint());
		
		prevSelectedPanel = null;
		prevSelectedPanel = selectedPanel;
		loadPanel.setComponent(selectedPanel.getComponent());	
	}
	
	public void copySelectedComponentToPanel(ComponentPanel panel, Point p)
	{		
		int index = panel.determineInsertIndex(p);
		Component parent = panel.getComponent();
		if(!parent.isLeaf())
		{ //only add if the target is not a leaf
			
			Component clone = (Component) prevSelectedPanel.getComponent().clone();
			((Phrase)parent).insertChildAt(index, clone);
			
			InputXMLDocumentPanel xmlPanel = panel.getParentDocPanel();
			
			ComponentPanel newPanel = ComponentPanel.CreateInstance(clone, xmlPanel);
			newPanel.setSelectListener(panel.getSelectListener());
						
			panel.addChildAt(index, newPanel);
			xmlPanel.adjustPositioning();
						
//			MouseEvent me = new MouseEvent(newPanel, 0,0,0,100,100,1,false);
//			for(MouseListener ml: newPanel.getMouseListeners()){
//				ml.mousePressed(me);
//			}
		}
		else
			JOptionPane.showMessageDialog(null, "You can't add a child to a leaf.\nLeaves have black labels, Phrases have white ones.", "Oops!", JOptionPane.WARNING_MESSAGE);
		
		setCopy(false);
		System.out.println("lakjdlaksjd COPPYYYYYYYY");
	}
	
	public void setCopy(boolean b)
	{
		copyComponent = b;
	}
	
	
	public boolean canCopy()
	{
		return copyComponent;
	}
}