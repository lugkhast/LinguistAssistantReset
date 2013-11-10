package controller.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import view.grammardevelopment.ViewSemanticsPanel;
import view.grammardevelopment.MainFrame;
import view.grammardevelopment.editsemantics.NewSemanticsInfoDialog;

public class NewSemanticsActionListener extends SemanticsInfoListener {

	protected NewSemanticsInfoDialog dialog;
	
	public void actionPerformed(ActionEvent e) {
		dialog = new NewSemanticsInfoDialog(MainFrame.getInstance(), "New Semantics", this);
		dialog.setVisible(true);
		dialog.setModal(true);
	}
	
	//called when ok or cancel is pressed
	public void proceed(boolean isCanceled){
		if(!isCanceled){
			SelectComponentActionListener.deselectCurrentPanel();
			MainFrame.getInstance().setPanel(new ViewSemanticsPanel(dialog.getCategory(), dialog.getName(), dialog.getComments())); 
		}
	}
}