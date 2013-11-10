package controller.listener.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import view.grammardevelopment.DisplayScreen;
import view.grammardevelopment.ViewSemanticsPanel;
import view.grammardevelopment.ViewSemanticsPanelToolBar;
import components.InputXMLDocument;
import controller.listener.SelectComponentActionListener;

public class NextPrevListener implements ActionListener{

	//This class takes care of switching files (previous or next) in the viewing of documents
	
	public static final int MODE_PREV = 0;
	public static final int MODE_NEXT = 1;
	
	private int mode;
	private ArrayList<InputXMLDocument> XMLList;
	private ViewSemanticsPanel loadPanel;
	private ViewSemanticsPanelToolBar toolBar;
	
	public NextPrevListener(int mode,ArrayList<InputXMLDocument> xmlList,ViewSemanticsPanel loadPanel, ViewSemanticsPanelToolBar toolBar){
		this.XMLList = xmlList;
		this.mode = mode;
		this.loadPanel = loadPanel;
		this.toolBar = toolBar;
	}
	
	public void actionPerformed(ActionEvent e) {
		int index = -1;
		switch(mode){
		case MODE_NEXT:   
			index = checkIndex(DisplayScreen.getCurrentlyDisplayedDocumentPanel().getXMLDocument());
									
			//System.out.println(index+1+ " "+ XMLList.size());
			if(index+1 < XMLList.size()){
				System.out.println("Test");
				toolBar.setCurrFileSelectedText("Current File Selected: "+XMLList.get(index+1).getName()+"  File: "+(index+2)+"/"+XMLList.size());
				loadPanel.setInitialDocument(XMLList.get(index+1));
				loadPanel.setMode(ViewSemanticsPanel.MODE_VIEW);
				SelectComponentActionListener.deselectCurrentPanel();
			}
			/*
			else{
				JOptionPane.showMessageDialog(null,
					    "Reached the rightmost file in the list",
					    "Bounds error",
					    JOptionPane.ERROR_MESSAGE);
			}
			*/
			break;
									
									
		case MODE_PREV:   
			index = checkIndex(DisplayScreen.getCurrentlyDisplayedDocumentPanel().getXMLDocument());
			System.out.println(index-1+ " "+ XMLList.size());
			if(index-1 >=0){
				toolBar.setCurrFileSelectedText("Current File Selected: "+XMLList.get(index-1).getName()+"  File: "+
																				(index)+"/"+XMLList.size());
				loadPanel.setInitialDocument(XMLList.get(index-1));
				loadPanel.setMode(ViewSemanticsPanel.MODE_VIEW);
				SelectComponentActionListener.deselectCurrentPanel();
			}
			/*
				else{
					JOptionPane.showMessageDialog(null,
						    "Reached the leftmost file in the list",
						    "Bounds error",
						    JOptionPane.ERROR_MESSAGE);
						}
			*/
		    break;
		}
		
	}
	
	private int checkIndex(InputXMLDocument docu){
		int index = -1;
		for(int i = 0 ; i <XMLList.size();i++){
			if (XMLList.get(i).equals(docu))
				index = i ;
		}
		return index;
			
	}

}
