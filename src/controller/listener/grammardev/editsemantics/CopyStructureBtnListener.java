package controller.listener.grammardev.editsemantics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import view.grammardevelopment.ComponentPanel;
import view.grammardevelopment.InputXMLDocumentPanel;

import components.Phrase;

import controller.GrammarDevController;

public class CopyStructureBtnListener implements ActionListener{
	
	private GrammarDevController grammarDevController;
	
	public CopyStructureBtnListener(GrammarDevController grammarDevController){
		this.grammarDevController = grammarDevController;
	}

	public void actionPerformed(ActionEvent e) {
			grammarDevController.copyInputToOutput();
	}
}
