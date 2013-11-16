package controller.listener.grammardev.editsemantics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.grammardevelopment.ComponentPanel;
import view.grammardevelopment.editsemantics.CreationRightPanel;

import components.Leaf;
import controller.GrammarDevController;

public class LeafEditPaletteBtnListener implements ActionListener{

	private GrammarDevController grammarDevController;
	private CreationRightPanel creationPanel;
	
	public LeafEditPaletteBtnListener(GrammarDevController grammarDevController, CreationRightPanel creationPanel){
		this.grammarDevController = grammarDevController;
		this.creationPanel = creationPanel;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		ComponentPanel compPanel = grammarDevController.getCurrSelectedComponentPanel();
		Leaf comp = (Leaf)compPanel.getComponent();
		
		String newName = creationPanel.getLeafConceptName();
		String newSense = creationPanel.getLeafConceptSense();
		
		comp.setConcept(newName);
		comp.setLexicalSense(newSense);
		compPanel.refreshLabelText();
	}
}
