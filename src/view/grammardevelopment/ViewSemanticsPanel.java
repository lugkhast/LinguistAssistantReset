package view.grammardevelopment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;

import view.MainFrame;
import view.grammardevelopment.editsemantics.CreationRightPanel;

import managers.SemanticsManager;

import components.Component;
import components.InputXMLDocument;
import controller.CreateController;
import controller.GrammarDevController;
import controller.listener.grammardev.SelectComponentActionListener;
import controller.listener.grammardev.toolbar.EditDocInfoButtonListener;
import controller.listener.grammardev.toolbar.LoadPanelToolbarBtnListener;
import controller.listener.grammardev.toolbar.NextPrevListener;

public class ViewSemanticsPanel extends JPanel{

	//Variables related to mode
	public static final int MODE_VIEW = 0;
	public static final int MODE_EDIT = 1;
	public static final int MODE_GENERATE = 2;
		
	private int currMode;
	
	//Other object references needed
	private InputXMLDocumentPanel initialDocPanel;
	
	//GUI Components
	private ViewSemanticsPanelToolBar toolBar;
	private JSplitPane splitPane;
	private JPanel viewPanel;
	private CreationRightPanel creationPanel;
	private DisplayScreen display;
	private TextAreaWithScrollPane generatedArea;
	private TextAreaWithScrollPane docInfoArea;
	private TextAreaWithScrollPane infoArea;
	private RulesTreePanel rulesPanel;
	private ArrayList<InputXMLDocumentPanel> xmlDocPanels;
	
	private GrammarDevController grammarDevController;
	
	public ViewSemanticsPanel(GrammarDevController grammarDevController, ArrayList<InputXMLDocument> loadedDocuments){ // Used for Loading a document
		this.grammarDevController = grammarDevController;
		//this.XMLDocsList = loadedDocuments;
		
		xmlDocPanels = new ArrayList<InputXMLDocumentPanel>();
		for(InputXMLDocument doc: loadedDocuments)
			xmlDocPanels.add(new InputXMLDocumentPanel(doc));

		this.initialDocPanel = xmlDocPanels.get(0);
		
		//Create GUI elements
		initializePanelSettings();
		initializeBar();
		initializeDisplay(MODE_VIEW);	
	}
		
	public ViewSemanticsPanel(GrammarDevController grammarDevController, String name, String category, String comments){ //Used for Creating a new document
		this.grammarDevController = grammarDevController;
		this.initialDocPanel = new InputXMLDocumentPanel(new InputXMLDocument(null, name, category, comments, null));
		this.xmlDocPanels = new ArrayList<InputXMLDocumentPanel>();
		xmlDocPanels.add(initialDocPanel);
		
		initializePanelSettings();
		initializeBar();
		initializeDisplay(MODE_EDIT);
	}
		
	//Creation of the right panels for viewing
	private JPanel createRightViewPanel(){
		//Initialize right panel
		docInfoArea = new TextAreaWithScrollPane("Document Information");
		infoArea= new TextAreaWithScrollPane("Component Information");
		generatedArea = new TextAreaWithScrollPane("Generated Sentence");
		rulesPanel = new RulesTreePanel();
		
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.add(rulesPanel);
		rightPanel.add(docInfoArea);
		rightPanel.add(infoArea);
		rightPanel.setMinimumSize(new Dimension(400,200));
		int panelHeight = rightPanel.getPreferredSize().height;
		generatedArea.setPreferredSize(new Dimension(generatedArea.getPreferredSize().width, panelHeight/5));
		docInfoArea.setPreferredSize(new Dimension(generatedArea.getPreferredSize().width, panelHeight/5));
		infoArea.setPreferredSize(new Dimension(generatedArea.getPreferredSize().width, panelHeight*3/5));
		
		
		return rightPanel;
	}
	
	//Initialize methods
	private void initializePanelSettings(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	private void initializeBar(){
		toolBar = new ViewSemanticsPanelToolBar();
		InputXMLDocument initialDocument = initialDocPanel.getXMLDocument();
		if(xmlDocPanels != null){
			toolBar.setCurrFileSelectedText("Current File Selected: "+initialDocument.getName()+ "     File: 1/"+xmlDocPanels.size());
		}
		add(toolBar);
	}
	
	private void initializeDisplay(int initialMode){
		//right panels
		viewPanel = createRightViewPanel();
		creationPanel = new CreationRightPanel();
		
		JScrollPane viewScroll = new JScrollPane(viewPanel);
		JScrollPane creationScroll = new JScrollPane(creationPanel);
		
		//LeftPanel
		display = new DisplayScreen();
		display.display(this.initialDocPanel); //displays the first
					
		//Split Pane
		if(initialMode == MODE_VIEW)
			splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, display, viewScroll);
		else{
			splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, display, creationScroll);
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
	public void setSelectComponentPanelListener(SelectComponentActionListener selectListener){
		for(InputXMLDocumentPanel docPanel: xmlDocPanels)
			docPanel.setSelectComponentPanelListener(selectListener);	
	}
	
	public void setDocumentInfo(){
		if(this.initialDocPanel != null){
			InputXMLDocument initialDocument = initialDocPanel.getXMLDocument();
			docInfoArea.setTextAreaContent(initialDocument.getComments());
		}
	}
	
	public void setComponent(Component component){
		if(component != null){
			if(currMode == MODE_EDIT){
				creationPanel.setComponent(component);
			}
			else{
				StringBuilder info = new StringBuilder("Information about ");
				
				if(!component.getDescription().equals(component.getName())){
					info.append("(");
					info.append(component.getDescription());
					info.append(") ");
				}
				
				info.append(component.toString());
				info.append("\n\n");
				
				if(component.isLeaf())
					info.append("***Target Lexicon***\n"+component.toLexiconSentence()+"\n\n");
				info.append(component.getFeaturesInString(true));

				infoArea.setTextAreaContent(info.toString());
			}
		}
	}
	
	public void setMode(int mode){
		
		toolBar.setMode(mode);
		switch(mode){
			case MODE_VIEW:
				display.setMode(DisplayScreen.MODE_INITIALIZE);
				setDocumentInfo();
				splitPane.setRightComponent(viewPanel);
				splitPane.setDividerLocation(0.6);
				display.display(this.initialDocPanel);
				generatedArea.setTextAreaContent(display.getDisplaySentence());
				viewPanel.remove(generatedArea);
				break;
			case MODE_EDIT:
				//reset input
				creationPanel.clearInput();
				
				//initialize with the panel currently selected
				if(grammarDevController.getCurrSelectedComponentPanel() != null){
					Component selected = grammarDevController.getCurrSelectedComponentPanel().getComponent();
					creationPanel.setComponent(selected);
				}
			
				splitPane.setRightComponent(creationPanel);
				splitPane.setDividerLocation(0.6);
				break;		
				
			case MODE_GENERATE:
				this.initialDocPanel= display.getCurrentlyDisplayedDocumentPanel();
				display.setMode(DisplayScreen.MODE_GENERATE);
				display.getCurrentlyDisplayedDocumentPanel().adjustPositioning();
				generatedArea.setTextAreaContent(display.getDisplaySentence());
				viewPanel.add(generatedArea, 0);
				break;
		}

		this.currMode = mode;
	}
	
	public void setDocumentPanelIndex(int index) {
		if(index >=0 && index <xmlDocPanels.size()){
			System.out.println("INDEX"+index);
			InputXMLDocumentPanel desiredPanel = xmlDocPanels.get(index);
			toolBar.setCurrFileSelectedText("Current File Selected: "+desiredPanel.getXMLDocument().getName()+"  File: "+(index+1)+"/"+xmlDocPanels.size());
			display.display(desiredPanel);
		}
	}
	
	public void setDragAndDropListener(MouseAdapter mouseAdapter){
		if(mouseAdapter != null)
			creationPanel.addDnDListenerForAllButtons(mouseAdapter);
	}
	
	public void resetCreationPanel(){
		creationPanel.clearInput();
	}
	
	public void resetInfoPanel(){
		infoArea.setTextAreaContent("");
	}
	
	public void refreshSemanticLexicons(){
		for(InputXMLDocumentPanel panel: xmlDocPanels)
			panel.refreshSemanticLexicons();
	}
	
	//Getters
	public InputXMLDocumentPanel getCurrentlyDisplayedDocumentPanel(){
		if(display == null)
			return null;
		return display.getCurrentlyDisplayedDocumentPanel();
	
	}
	
	public CreationRightPanel getCreationPanel(){
		return creationPanel;
	}
	
	public ArrayList<InputXMLDocumentPanel> getDocumentPanelList(){
		return xmlDocPanels;
	}
	
	//Listener setters
	public void setEditButtonListener(LoadPanelToolbarBtnListener listener){
		toolBar.setEditButtonListener(listener);
	}
	
	public void setGenerateButtonListener(LoadPanelToolbarBtnListener listener){
		toolBar.setGenerateButtonListener(listener);
	}
	
	public void setDoneEditingButtonListener(LoadPanelToolbarBtnListener listener){
		toolBar.setDoneEditingButtonListener(listener);
	}

	public void setInitializeButtonListener(LoadPanelToolbarBtnListener listener){
		toolBar.setInitializeButtonListener(listener);
	}

	public void setEditDocInfoButtonListener(EditDocInfoButtonListener listener){
		toolBar.setEditDocInfoButtonListener(listener);
	}
	
	public void setNextButtonListener(NextPrevListener listener){
		toolBar.setBtnNextListener(listener);
	}
	
	public void setPrevButtonListener(NextPrevListener listener){
		toolBar.setBtnPrevListener(listener);
	}
	
}