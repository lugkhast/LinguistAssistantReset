package view.grammardevelopment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;
import java.util.ArrayList;
import java.util.TooManyListenersException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

import view.MainFrame;

import components.Component;
import components.InputXMLDocument;
import controller.listener.grammardev.SelectComponentActionListener;
import controller.listener.grammardev.editsemantics.ComponentPaletteDnDListener;


public class DisplayScreen extends JPanel{

	//This class is used for the display screen in the LoadPanel (used for Viewing or Editing of Semantics)
	
	private JScrollPane scrollPane;
	
	public static final int MODE_INITIALIZE = 0;
	public static final int MODE_GENERATE = 1;
	
	
	//This was made static because there is only one document to be displayed at a time anyway.
	private InputXMLDocumentPanel documentPanel;
	
	public InputXMLDocumentPanel getCurrentlyDisplayedDocumentPanel(){
		return documentPanel;
	}
	
	public DisplayScreen(){
		setLayout(new GridLayout());
		setBackground(Color.BLACK);
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(MainFrame.getInstance().getWidth()/2, MainFrame.getInstance().getHeight()));
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		add(scrollPane);
	}
	
	//Used to change the display
	public void display(InputXMLDocumentPanel panel){
		documentPanel = panel;
		scrollPane.setViewportView(documentPanel);
		revalidate();
		repaint();
	}
		
	//Gets the displaysentence of the document panel 
	public String getDisplaySentence(){
		return documentPanel.toSentence();
	}
	
	//Just for setting the mode to either initialize or generated
	public void setMode(int mode){
		
		switch(mode){
			case MODE_INITIALIZE: 
				documentPanel.setGenerated(false);
				break;
			case MODE_GENERATE: 
				documentPanel.setGenerated(true);
				break;
		}
	}
}
