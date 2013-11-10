package controller.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import components.InputXMLDocument;

import view.grammardevelopment.DisplayScreen;
import view.grammardevelopment.FileSaveLoad;
import view.grammardevelopment.InputXMLDocumentPanel;

public class SaveAsListener implements ActionListener{

	public void actionPerformed(ActionEvent arg0) {
		FileSaveLoad fsl = new FileSaveLoad();
		
		if( DisplayScreen.getCurrentlyDisplayedDocumentPanel()!=null){
			InputXMLDocument document = DisplayScreen.getCurrentlyDisplayedDocumentPanel().getXMLDocument();
			fsl.saveFile(document);
		}
		else{
			JOptionPane.showMessageDialog(null,
				    "No file to save, please select a file before saving",
				    "Save Error",
				    JOptionPane.ERROR_MESSAGE);
		}
	}

}
