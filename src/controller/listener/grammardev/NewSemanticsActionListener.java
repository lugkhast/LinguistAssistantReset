package controller.listener.grammardev;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.GrammarDevController;
import controller.MainController;


import view.MainFrame;
import view.grammardevelopment.ViewSemanticsPanel;
import view.grammardevelopment.editsemantics.NewSemanticsInfoDialog;

public class NewSemanticsActionListener extends SemanticsInfoListener {

	protected NewSemanticsInfoDialog dialog;
	private GrammarDevController grammarDevController;
	
	public NewSemanticsActionListener(GrammarDevController grammarDevController){
		this.grammarDevController = grammarDevController;
	}
	
	public void actionPerformed(ActionEvent e) {
		dialog = new NewSemanticsInfoDialog(MainFrame.getInstance(), "New Semantics", this);
		dialog.setVisible(true);
		dialog.setModal(true);
	}
	
	//called when ok or cancel is pressed
	public void proceed(boolean isCanceled){
		if(!isCanceled)
			grammarDevController.createNewDocument(dialog.getCategory(), dialog.getName(), dialog.getComments());
	}
}