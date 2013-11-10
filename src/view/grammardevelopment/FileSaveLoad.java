package view.grammardevelopment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import components.InputXMLDocument;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileSaveLoad extends JFrame{
	final JFileChooser jfChooser;
	boolean fileDirectoryIsThere = false;
	String dirLastAccessed;
	File fileDirectory;
	
	public FileSaveLoad(){
		String directoryLocation = "LastAccessedDirectory";
		fileDirectory = new File(directoryLocation);
		
		if (fileDirectory.exists()){
			fileDirectoryIsThere = true;
		}
		
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
		int retrival = jfChooser.showSaveDialog(null);
		String fileName = "";
		if(jfChooser.getSelectedFile()!=null){
			fileName = jfChooser.getSelectedFile().toString();
			if(fileName.contains(".xml") || fileName.contains(".XML")){
				fileName = fileName.substring(0, fileName.length()-4);
			
			}
			
			if (jfChooser.getSelectedFile().exists()) {
				Object[] options = { "Yes", "Cancel","Keep both files"};
				int response =  JOptionPane.showOptionDialog(null, "The file " + fileName + 
				          " already exists.\nDo you want to replace the existing file?",
				          "Ovewrite file",
						  JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
						  null, options, options[0]);
		        if (response == 0){
		        	try {
		        		Element documentElement = document.generateXMLCopy();
		        		XMLOutputter xmlOutput = new XMLOutputter();
		        		xmlOutput.setFormat(Format.getPrettyFormat());
		        		try{
		        			xmlOutput.output(documentElement, new FileWriter(fileName+".xml"));
		        			DisplayScreen.getCurrentlyDisplayedDocumentPanel().getXMLDocument().setXMLFile(new File(fileName+".xml"));
		        			JOptionPane.showMessageDialog(null,
								    "Successfully Saved '"+document.getXmlFile().getName()+"'",
								    "Save Success!",
								    JOptionPane.INFORMATION_MESSAGE);
		        		}catch(Exception e){e.printStackTrace();}
		        		
			            updateLastAccessedDirectory(fileName+".xml");
			        } catch (Exception ex) {
			            ex.printStackTrace();
			        }
		        }
		        else if (response == 2 ){
		        	try {
		        		Element documentElement = document.generateXMLCopy();
		        		XMLOutputter xmlOutput = new XMLOutputter();
		        		xmlOutput.setFormat(Format.getPrettyFormat());
		        		try{
		        			xmlOutput.output(documentElement, new FileWriter(fileName+"(1).xml"));
		        			DisplayScreen.getCurrentlyDisplayedDocumentPanel().getXMLDocument().setXMLFile(new File(fileName+".xml"));
		        			JOptionPane.showMessageDialog(null,
								    "Successfully Saved '"+document.getXmlFile().getName()+"'",
								    "Save Success!",
								    JOptionPane.INFORMATION_MESSAGE);
		        		}catch(Exception e){e.printStackTrace();}
		        		updateLastAccessedDirectory(fileName+".xml");
			        } catch (Exception ex) {
			            ex.printStackTrace();
			        }
		        }
			}
			else{
				try {
	        		Element documentElement = document.generateXMLCopy();
	        		XMLOutputter xmlOutput = new XMLOutputter();
	        		xmlOutput.setFormat(Format.getPrettyFormat());
	        		try{
	        			xmlOutput.output(documentElement, new FileWriter(fileName+".xml"));
	        			DisplayScreen.getCurrentlyDisplayedDocumentPanel().getXMLDocument().setXMLFile(new File(fileName+".xml"));
	        			JOptionPane.showMessageDialog(null,
							    "Successfully Saved '"+document.getXmlFile().getName()+"'",
							    "Save Success!",
							    JOptionPane.INFORMATION_MESSAGE);
	        		}catch(Exception e){e.printStackTrace();}
	        		updateLastAccessedDirectory(fileName+".xml");
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        
				}
			}
		}
		
	}
	private void updateLastAccessedDirectory(String lastAccessedDir){
		FileWriter fw;
		try {
			fw = new FileWriter("LastAccessedDirectory");
			fw.write(lastAccessedDir);
	        fw.close();
	        fileDirectoryIsThere = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	private void setDirectory(){
			if (fileDirectoryIsThere){
				String dir = "None";
				try {
					dir = new Scanner(fileDirectory).useDelimiter("\\A").next();
					if(new File(dir).exists()){
						jfChooser.setCurrentDirectory(new File(dir));
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
	}

}
