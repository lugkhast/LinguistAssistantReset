package view.grammardevelopment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import view.grammardevelopment.editsemantics.CreationRightPanel;

import managers.SemanticsManager;

import components.Component;
import components.InputXMLDocument;
import controller.DnDController;
import controller.CreateController;
import controller.listener.SelectComponentActionListener;
import controller.listener.editsemantics.EditDocInfoButtonListener;
import controller.listener.toolbar.LoadPanelToolbarBtnListener;
import controller.listener.toolbar.NextPrevListener;

public class ViewSemanticsPanel extends JPanel{

	//Variables related to mode
	public static final int MODE_VIEW = 0;
	public static final int MODE_EDIT = 1;
	public static final int MODE_GENERATE = 2;
		
	private int currMode;
	
	//Other object references needed
	//private LinkedHashMap<File, InputXMLDocument> fileVerseMap;
	private InputXMLDocument initialDocument;
	
	//GUI Components
	private ViewSemanticsPanelToolBar toolBar;
	private JSplitPane splitPane;
	private JPanel viewPanel;
	private CreationRightPanel creationPanel;
	private DisplayScreen display;
	private TextAreaWithScrollPane generatedArea;
	private TextAreaWithScrollPane docInfoArea;
	private TextAreaWithScrollPane infoArea;
	private ArrayList<InputXMLDocument> XMLDocsList;
		
	public ViewSemanticsPanel(ArrayList<InputXMLDocument> loadedDocuments){ // Used for Loading a document
		XMLDocsList = loadedDocuments;
		this.initialDocument = XMLDocsList.get(0);
		
		//Create GUI elements
		initializePanelSettings();
		initializeBar();
		initializeDisplay();	
	}
		
	public ViewSemanticsPanel(String name, String category, String comments){ //Used for Creating a new document
		this.initialDocument = new InputXMLDocument(null, name, category, comments, null);
		initializePanelSettings();
		initializeBar();
		initializeDisplay();
	}
		
	//Creation of the right panels for viewing
	private JPanel createRightViewPanel(){
		//Initialize right panel
		docInfoArea = new TextAreaWithScrollPane("Document Information");
		infoArea= new TextAreaWithScrollPane("Component Information");
		generatedArea = new TextAreaWithScrollPane("Generated Sentence");

		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.add(docInfoArea);
		rightPanel.add(infoArea);
		
		int panelHeight = rightPanel.getPreferredSize().height;
		generatedArea.setPreferredSize(new Dimension(generatedArea.getPreferredSize().width, panelHeight/5));
		docInfoArea.setPreferredSize(new Dimension(generatedArea.getPreferredSize().width, panelHeight/5));
		infoArea.setPreferredSize(new Dimension(generatedArea.getPreferredSize().width, panelHeight*3/5));
		
		
		return rightPanel;
	}
	
	//Initialize methods
	private void initializePanelSettings(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.ORANGE);
	}
	
	private void initializeBar(){
		toolBar = new ViewSemanticsPanelToolBar();
		toolBar.setEditButtonListener(new LoadPanelToolbarBtnListener(this, MODE_EDIT));
		toolBar.setGenerateButtonListener(new LoadPanelToolbarBtnListener(this, MODE_GENERATE));
		toolBar.setDoneEditingButtonListener(new LoadPanelToolbarBtnListener(this, MODE_VIEW));
		toolBar.setInitializeButtonListener(new LoadPanelToolbarBtnListener(this, MODE_VIEW));
		toolBar.setEditDocInfoButtonListener(new EditDocInfoButtonListener());
		
		if(XMLDocsList != null){
			toolBar.setCurrFileSelectedText("Current File Selected: "+initialDocument.getName()+ "     File: 1/"+XMLDocsList.size());
			toolBar.setBtnNextListener(new NextPrevListener(NextPrevListener.MODE_NEXT,XMLDocsList,this, toolBar));
			toolBar.setBtnPrevListener(new NextPrevListener(NextPrevListener.MODE_PREV,XMLDocsList,this, toolBar));
		}
		add(toolBar);
	}
	
	private void initializeDisplay(){
		//right panels
		viewPanel = createRightViewPanel();
		creationPanel = new CreationRightPanel();
	
		//LeftPanel
		display = new DisplayScreen();
		display.display(new InputXMLDocumentPanel(initialDocument, new SelectComponentActionListener(this))); //displays the first
				
		//Controller for creation and DnD
		new CreateController(display,creationPanel.getFpScrollPane(),creationPanel.getCpScrollPane(),creationPanel.getLeScrollPane());
		new DnDController(display,creationPanel.getCpScrollPane());
		
		//Split Pane
		if(XMLDocsList != null)
			splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, display, viewPanel);
		else{
			splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, display, creationPanel);
			setMode(MODE_EDIT);
		}
		splitPane.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
		splitPane.setDividerLocation( (int)(MainFrame.getInstance().getWidth()*3.0/5 + splitPane.getInsets().left));
		splitPane.setDividerSize(0);
		add(splitPane);
		
		//Set initially displayed info
		setDocumentInfo();
		generatedArea.setTextAreaContent(display.getDisplaySentence());
	}

	public void initializeSentences(){
		display.setMode(DisplayScreen.MODE_INITIALIZE);
		generatedArea.setTextAreaContent("");
	}
	
	//Setters
	public void setDocumentInfo(){
		docInfoArea.setTextAreaContent(initialDocument.getComments());
	}
	
	public void setComponentInfo(Component component){
		
		StringBuilder info = new StringBuilder("Information about ");
		
		if(!component.getDescription().equals(component.getName())){
			info.append("(");
			info.append(component.getDescription());
			info.append(") ");
		}
		
		info.append(component.toString());
		
		info.append("\n\n");
		
		if(component.isLeaf())
			info.append("***Target Lexicon***\n"+component.toSentence()+"\n\n");
		info.append(component.getFeaturesInString(true));
		
		
		if(component != null){
			switch(currMode){
				case MODE_VIEW:
					infoArea.setTextAreaContent(info.toString());
					break;
				case MODE_EDIT:
					creationPanel.setComponent(component);
					if(component.isLeaf())
						creationPanel.setLeaf(component);
					break;
			}
		}
			infoArea.setTextAreaContent(info.toString());
	}
	
	public void setMode(int mode){
		
		toolBar.setMode(mode);
		switch(mode){
			case MODE_VIEW:
				display.setMode(DisplayScreen.MODE_INITIALIZE);
				infoArea.setTextAreaContent("");
				setDocumentInfo();
				splitPane.setRightComponent(viewPanel);
				splitPane.setDividerLocation(0.6);
				display.display(new InputXMLDocumentPanel(this.initialDocument, new SelectComponentActionListener(this)));
				generatedArea.setTextAreaContent(display.getDisplaySentence());
				viewPanel.remove(generatedArea);
				break;
			case MODE_EDIT:
				//reset input
				creationPanel.clearInput();
				
				//initialize with the panel currently selected
				if(SelectComponentActionListener.getSelectedPanel() != null){
					Component selected = SelectComponentActionListener.getSelectedPanel().getComponent();
					creationPanel.setComponent(selected);
					if(selected.isLeaf())
						creationPanel.setLeaf(selected);
				}
			
				splitPane.setRightComponent(creationPanel);
				splitPane.setDividerLocation(0.6);
				break;		
				
			case MODE_GENERATE:
				//this.initialDocument = DisplayScreen.getCurrentlyDisplayedDocumentPanel().getXMLDocument();
				//display.display(new InputXMLDocumentPanel(XMLManager.getVerse(new File("InputXML\\generated.xml")), new SelectComponentActionListener(this)));
				display.setMode(DisplayScreen.MODE_GENERATE);
				DisplayScreen.getCurrentlyDisplayedDocumentPanel().adjustPositioning();
				generatedArea.setTextAreaContent(display.getDisplaySentence());
				infoArea.setTextAreaContent("");
				viewPanel.add(generatedArea, 0);
				break;
		}

		this.currMode = mode;
	}
	
	public void setInitialDocument(InputXMLDocument initialDocument) {
		this.initialDocument = initialDocument;
	}

}