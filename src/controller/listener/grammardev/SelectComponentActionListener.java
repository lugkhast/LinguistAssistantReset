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
import view.rules.StructuralAdjustmentPanel;

public class SelectComponentActionListener extends MouseAdapter {

	public static final int RULE_INPUT = 1;
	public static final int RULE_OUTPUT = 2;
	
	int mode; 
	
	private ComponentPanel prevSelectedPanel;
	private ViewSemanticsPanel loadPanel = null;
	private StructuralAdjustmentPanel rulePanel = null;
	
	boolean copyComponent = false;
	boolean moveComponent = false;
	boolean copyStruct = false;
	
	public SelectComponentActionListener(ViewSemanticsPanel loadPanel){
		this.loadPanel = loadPanel;
	}
	
	public SelectComponentActionListener(StructuralAdjustmentPanel rulePanel, int mode){
		this.mode = mode;
		this.rulePanel = rulePanel;
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
		setInputOrOutput();
		
		if (canCopy())
			copySelectedComponentToPanel(selectedPanel, e.getPoint());
		
		if (canMove())
			moveSelectedComponentToPanel(selectedPanel, e.getPoint());
		
		if (canCopyStruct())
			copyStruct(selectedPanel);
		
		
		prevSelectedPanel = null;
		prevSelectedPanel = selectedPanel;
		if (loadPanel != null)
			loadPanel.setComponent(selectedPanel.getComponent());
		else
		{
			rulePanel.setComponent(selectedPanel.getComponent());
			rulePanel.setSelectListener(this);
		}
	}
	
	//copy functions
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
	}
	
	public void setCopy(boolean b)
	{
		copyComponent = b;
		if (copyComponent)
		{
			if (moveComponent)
				setMove(false);
		}
	}
	
	public boolean canCopy()
	{
		return copyComponent;
	}
	
	//move functions
	public void moveSelectedComponentToPanel(ComponentPanel panel, Point p)
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
			
			 //delete previous ComponentPanel
			if(prevSelectedPanel.getParentComponentPanel() != null)
			{
				//remove internally
				Phrase parentComponent = (Phrase)prevSelectedPanel.getParentComponentPanel().getComponent();
				parentComponent.removeChild(prevSelectedPanel.getComponent());
								
				//remove in gui
				prevSelectedPanel.getParentComponentPanel().removeChild(prevSelectedPanel);
				xmlPanel.adjustPositioning();
			}
			else
			{
				//remove internally
				xmlPanel.getXMLDocument().removeSentence(prevSelectedPanel.getComponent());
				//remove in gui
				xmlPanel.removeChild(prevSelectedPanel);
			}

		}
		else
			JOptionPane.showMessageDialog(null, "You can't move a child to a leaf.\nLeaves have black labels, Phrases have white ones.", "Oops!", JOptionPane.WARNING_MESSAGE);
		
		setMove(false);
	}
	
	public void setMove(boolean b)
	{
		moveComponent = b;
		if (moveComponent)
		{
			if (copyComponent)
				setCopy(false);
		}
	}
	
	public boolean canMove()
	{
		return moveComponent;
	}
	
	// copy structure
	public void copyStruct(ComponentPanel panel)
	{
		InputXMLDocumentPanel xmlPanel = panel.getParentDocPanel();
		xmlPanel.adjustPositioning();
		System.out.println("COPY STRUCT HERE");
		 //delete previous ComponentPanel
		if(prevSelectedPanel.getParentComponentPanel() != null)
		{
			//remove internally
			Phrase parentComponent = (Phrase)prevSelectedPanel.getParentComponentPanel().getComponent();
			parentComponent.removeChild(prevSelectedPanel.getComponent());
							
			//remove in gui
			prevSelectedPanel.getParentComponentPanel().removeChild(prevSelectedPanel);
			xmlPanel.adjustPositioning();
		}
		else
		{
			//remove internally
			xmlPanel.getXMLDocument().removeSentence(prevSelectedPanel.getComponent());
			//remove in gui
			xmlPanel.removeChild(prevSelectedPanel);
		}
	}

	public void setCopyStruct(boolean b)
	{
		copyStruct = b;
		if (copyStruct)
		{
			if (copyComponent)
				setCopy(false);
			if (moveComponent)
				setMove(false);
		}
	}
	
	public boolean canCopyStruct()
	{
		return copyStruct;
	}
	
	public void setInputOrOutput()
	{
		if (rulePanel != null)
			rulePanel.setCurrentXMLPanel(mode);
	}
}