package view.grammardevelopment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import managers.XMLManager;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import components.InputXMLDocument;
import controller.GrammarDevController;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileSaveLoad extends JFrame{
	private JFileChooser jfChooser;
	private static final String LAST_ACCESSED_DIRECTORY_PATH = "LastAccessedDirectory";
	private File fileDirectory;
	private GrammarDevController grammarDevController;
	
	public FileSaveLoad(GrammarDevController grammarDevController){
		this.grammarDevController = grammarDevController;
		
		String directoryLocation = LAST_ACCESSED_DIRECTORY_PATH;
		fileDirectory = new File(directoryLocation);
		
		 try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		 SwingUtilities.updateComponentTreeUI(this);
		 this.pack();
	     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     jfChooser = new JFileChooser();
	     
	     FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter(
	                "xml files (*.xml)", "xml");
	     jfChooser.setFileFilter(xmlfilter);
	     jfChooser.setAcceptAllFileFilterUsed(false);
	     jfChooser.setMultiSelectionEnabled(true);
	     jfChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	     this.add(jfChooser);
		 
	}
	
	public File[] loadFiles(){ //RETURNS FILES
		setDirectory();
		jfChooser.showOpenDialog(this);
		File[] selectedFiles = jfChooser.getSelectedFiles();
	
		if(selectedFiles.length!=0){
			updateLastAccessedDirectory(selectedFiles[0].getAbsolutePath());
		}
		
		return selectedFiles;
	}
	
	public void saveFile(InputXMLDocument document){
		setDirectory();
		jfChooser.showSaveDialog(null);
		boolean successfullySaved = false;

		if(jfChooser.getSelectedFile()!=null){
			String fileName = jfChooser.getSelectedFile().toString();
			if(fileName.contains(".xml") || fileName.contains(".XML")){
				fileName = fileName.substring(0, fileName.length()-4);
			}
			
			Element documentElement = document.generateXMLCopy();
			
			//Desired file already exists
			if (jfChooser.getSelectedFile().exists()) {
				Object[] options = { "Yes", "Cancel","Keep both files"};
				int response =  JOptionPane.showOptionDialog(null, "The file " + fileName + 
				          " already exists.\nDo you want to replace the existing file?",
				          "Ovewrite File",
						  JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
						  null, options, options[0]);
				
		        if (response == 0){
	        		if(XMLManager.getInstance().writeToXML(fileName+".xml",  documentElement))
	        			successfullySaved = true;
		        }
		        else if(response == 1)
		        	return;
		        else if (response == 2 ){
	        		if(XMLManager.getInstance().writeToXML(fileName+"(Copy).xml", documentElement)){
	        			successfullySaved = true;
	        			fileName += "(Copy)";
	        		}
		        }
			}
			else{
        		if(XMLManager.getInstance().writeToXML(fileName+".xml", documentElement))
        			successfullySaved = true;
			}
			
			//Actions to take if successful/unsuccessful
			if(successfullySaved){
				grammarDevController.getCurrentlyDisplayedDocumentPanel().getXMLDocument().setXMLFile(new File(fileName+".xml"));
				JOptionPane.showMessageDialog(null,
					    "Successfully Saved '"+fileName+"'",
					    "Save Success!",
					    JOptionPane.INFORMATION_MESSAGE);

        		updateLastAccessedDirectory(fileName+".xml");
				
			}
			else{
				JOptionPane.showMessageDialog(null,
					    "Error saving '"+document.getXmlFile().getName()+"'!",
					    "Save Error!",
					    JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	private void updateLastAccessedDirectory(String lastAccessedDir){
		FileWriter fw;
		try {
			fw = new FileWriter(LAST_ACCESSED_DIRECTORY_PATH);
			fw.write(lastAccessedDir);
	        fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	private void setDirectory(){
		if (fileDirectory.exists()){
			try {
				String lastAccessed = new Scanner(fileDirectory).useDelimiter("\\A").next();
				if(fileDirectory.exists()){	
					File directory = new File(lastAccessed);
					if(directory.exists())
						jfChooser.setCurrentDirectory(directory);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
