package controller.listener.editsemantics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import components.InputXMLDocument;
import controller.listener.SemanticsInfoListener;

import view.grammardevelopment.DisplayScreen;
import view.grammardevelopment.ViewSemanticsPanel;
import view.grammardevelopment.MainFrame;
import view.grammardevelopment.editsemantics.NewSemanticsInfoDialog;

public class EditDocInfoButtonListener extends SemanticsInfoListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		dialog = new NewSemanticsInfoDialog(MainFrame.getInstance(), "New Semantics", this, DisplayScreen.getCurrentlyDisplayedDocumentPanel().getXMLDocument());
		dialog.setVisible(true);
		dialog.setModal(true);
	}
	
	public void proceed(boolean isCanceled){
		if(!isCanceled){
			InputXMLDocument doc = DisplayScreen.getCurrentlyDisplayedDocumentPanel().getXMLDocument();
			doc.setName(dialog.getName());
			doc.setCategory(dialog.getCategory());
			doc.setOtherInfo(dialog.getComments());
			DisplayScreen.getCurrentlyDisplayedDocumentPanel().refreshTitle();
		}
	}
}