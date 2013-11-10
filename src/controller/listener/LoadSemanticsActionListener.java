package controller.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import components.InputXMLDocument;

import managers.SemanticsManager;

import view.grammardevelopment.FileSaveLoad;
import view.grammardevelopment.ViewSemanticsPanel;
import view.grammardevelopment.MainFrame;

public class LoadSemanticsActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		FileSaveLoad fsl = new FileSaveLoad();
		File[] filesLoaded = fsl.loadFiles();
		if(filesLoaded.length>0){

			ArrayList<InputXMLDocument> loadedDocuments = new ArrayList<InputXMLDocument>();
			for(File file: filesLoaded)
				loadedDocuments.add(SemanticsManager.readSemanticsDocumentFromFile(file));
		
			SelectComponentActionListener.deselectCurrentPanel(); //deselect whatever panel was selected 
			MainFrame.getInstance().setPanel(new ViewSemanticsPanel(loadedDocuments)); 
		}
	}
}
