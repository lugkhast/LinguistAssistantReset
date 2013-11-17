package controller.listener.grammardev.editsemantics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import view.grammardevelopment.ComponentPanel;
import view.grammardevelopment.InputXMLDocumentPanel;

import components.Phrase;

import controller.GrammarDevController;

public class MoveComponentBtnListener implements ActionListener{
	
	private GrammarDevController grammarDevController;
	
	public MoveComponentBtnListener(GrammarDevController grammarDevController){
		this.grammarDevController = grammarDevController;
	}

	public void actionPerformed(ActionEvent e) {
		ComponentPanel selectedPanel = grammarDevController.getCurrSelectedComponentPanel();
		if(selectedPanel != null)
		{
			grammarDevController.activateMove();
		}
		else{
			JOptionPane.showMessageDialog(null,
				    "No panel selected",
				    "No panel selected",
				    JOptionPane.ERROR_MESSAGE);
		}
	}
}
