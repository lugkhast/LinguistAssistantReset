package controller.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;

import javax.swing.JOptionPane;

import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import components.InputXMLDocument;

import view.grammardevelopment.DisplayScreen;
import view.grammardevelopment.FileSaveLoad;
import view.grammardevelopment.ViewSemanticsPanel;
import view.grammardevelopment.MainFrame;

public class SaveActionListener implements ActionListener {

	public void actionPerformed(ActionEvent arg0){
		
		if(DisplayScreen.getCurrentlyDisplayedDocumentPanel() != null){ // Not working with any file yet.
			InputXMLDocument document = DisplayScreen.getCurrentlyDisplayedDocumentPanel().getXMLDocument();
			if(document.getXmlFile()!=null){
				Element documentElement = document.generateXMLCopy();
				XMLOutputter xmlOutput = new XMLOutputter();
				xmlOutput.setFormat(Format.getPrettyFormat());
				try{
					xmlOutput.output(documentElement, new FileWriter(document.getXmlFile()));
					JOptionPane.showMessageDialog(null,
						    "Successfully Saved '"+document.getXmlFile().getName()+"'",
						    "Save Success!",
						    JOptionPane.INFORMATION_MESSAGE);
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(null,
						    "Error in saving file!",
						    "Save Error",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
			else{
				FileSaveLoad fsl = new FileSaveLoad();
				fsl.saveFile(document);
			}
		}
		else{
			JOptionPane.showMessageDialog(null,
				    "No file to save, please select a file before saving",
				    "Save Error",
				    JOptionPane.ERROR_MESSAGE);
		}
	}
}