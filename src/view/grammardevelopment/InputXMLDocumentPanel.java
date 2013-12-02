package view.grammardevelopment;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.TooManyListenersException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import module3.rules.PhraseMatcher;

import components.Component;
import components.InputXMLDocument;
import components.Phrase;
import controller.listener.grammardev.SelectComponentActionListener;
import controller.listener.grammardev.editsemantics.ComponentPaletteDnDListener;
import controller.listener.grammardev.editsemantics.InputXMLDocPanelDNDListener;

public class InputXMLDocumentPanel extends JPanel implements Cloneable{
	
	private InputXMLDocument doc;
	private ArrayList<ComponentPanel> sentencePanels;

	private static final int VERTICAL_MARGIN = 20;
	private static final int HORIZONTAL_MARGIN = 20;
	
	private SelectComponentActionListener selectListener;
	
	public InputXMLDocumentPanel()
	{
		addDropTargetListener();
		
		setPanelListener(this);
	}
	
	public void addDropTargetListener()
	{
		DropTarget dt = new DropTarget();
		try {
			dt.addDropTargetListener(new InputXMLDocPanelDNDListener(this));
			this.setDropTarget(dt);
			
		} catch (TooManyListenersException e) {}
	}
	
	public void removeDnDListener()
	{
		this.setDropTarget(null);
	}
	
	public InputXMLDocumentPanel(InputXMLDocument doc){
		this.doc = doc;
		DropTarget dt = new DropTarget();
		try {
			dt.addDropTargetListener(new InputXMLDocPanelDNDListener(this));
			this.setDropTarget(dt);
			
		} catch (TooManyListenersException e) {}
	
		setLayout(null);
		refreshDisplay();
		refreshTitle();
		
		setPanelListener(this);
	}
	
	public void setSelectComponentPanelListener(SelectComponentActionListener selectListener){
		this.selectListener = selectListener;
		for(ComponentPanel sentencePanel: sentencePanels)
			sentencePanel.setSelectListener(selectListener);
	}
	
	public void refreshTitle(){
		String titleString = "";
		if(!doc.getCategory().isEmpty())
			titleString += doc.getCategory()+" / ";
		setBorder(BorderFactory.createTitledBorder(titleString + doc.getName()));
	}
		
	public void refreshDisplay(){
		createSentencePanels();
		adjustPositioning();
	}
	
	public void refreshPanelToolTips(){
		for(ComponentPanel sentence: sentencePanels)
			sentence.refreshLabelToolTip();
	}
	
	public void refreshSemanticLexicons(){
		for(ComponentPanel sentencePanel: sentencePanels)
			sentencePanel.refreshSemanticLexicons();
	}
	
	private void createSentencePanels(){
		sentencePanels = new ArrayList<ComponentPanel>();
		
		for(Component sentence: doc.getClauses())
			addSentencePanel(ComponentPanel.CreateInstance(sentence, this));
	}
	
	public void addSentencePanel(ComponentPanel newPanel){
		sentencePanels.add(newPanel);
		add(newPanel);
	}
	
	public void addSentencePanelAt(int index, ComponentPanel newPanel){
		sentencePanels.add(index, newPanel);
		add(newPanel);
	}
	
	public void adjustPositioning(){
		int newDesiredHeight = 0;
		for(int i=0;i<sentencePanels.size();i++){
			ComponentPanel sentencePanel = sentencePanels.get(i);
			
			if(i > 0)
				sentencePanel.adjustPositioning(HORIZONTAL_MARGIN, sentencePanels.get(i-1).getBottomY() + VERTICAL_MARGIN);
			else
				sentencePanel.adjustPositioning(HORIZONTAL_MARGIN, VERTICAL_MARGIN*2);	
			
			newDesiredHeight += sentencePanel.getDesiredHeight() + VERTICAL_MARGIN;
		}
		
		//needed to let the parent scroll pane know that scrollbar is needed
		setPreferredSize(new Dimension((int)getPreferredSize().getWidth(), newDesiredHeight + VERTICAL_MARGIN*4)); 
		revalidate();
		repaint();
	}
	
	public InputXMLDocument getXMLDocument(){
		return doc;
	}
	
	public String toSentence(){
		StringBuilder sb = new StringBuilder();
		for(ComponentPanel sentencePanel: sentencePanels){
			sb.append(sentencePanel.toSentence());
			sb.append(" ");
		}
		
		return sb.toString();
	}
	
	public void setGenerated(boolean isGenerateMode){
		for(ComponentPanel sentencePanel: sentencePanels)
			sentencePanel.setGenerateMode(isGenerateMode);
	}
	
	public SelectComponentActionListener getSelectListener(){
		return selectListener;
	}
	
	//For Drag and Drop
	public int determineInsertIndex(Point p){
		//first
		if(sentencePanels.size() == 0)
			return 0;
		
		if(p.y <= sentencePanels.get(0).getY())
			return 0;
		
		//middle
		for(int i = 0; i < sentencePanels.size(); i++){
			ComponentPanel child = sentencePanels.get(i);
			if(p.y > child.getY() && p.y < child.getBottomY()) //click isn't between any panels and it's "inside"  a panel
				return i;
			if( i+1 < sentencePanels.size() && p.y >= child.getBottomY() && p.y <= sentencePanels.get(i+1).getY())
				return i+1;
		}
				
		return sentencePanels.size(); //last
	}
	
	public void removeChild(ComponentPanel child){
		if(child != null){
			sentencePanels.remove(child);
			remove(child);
		}
		adjustPositioning();
	}
	
	public void setPanelListener(final InputXMLDocumentPanel parentDocuPanel)
	{
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (selectListener.canCopy())
				{
					int index = determineInsertIndex(arg0.getPoint());
					Component newComponent = (Component) selectListener.getSelectedPanel().getComponent().clone();
					getXMLDocument().addClauseAt(index, newComponent);
					ComponentPanel newPanel = ComponentPanel.CreateInstance(newComponent, parentDocuPanel);
					newPanel.setSelectListener(parentDocuPanel.getSelectListener());
										
					parentDocuPanel.addSentencePanelAt(index, newPanel);
					parentDocuPanel.adjustPositioning();
					selectListener.setCopy(false);
					
				}
				if (selectListener.canMove())
				{
					int index = determineInsertIndex(arg0.getPoint());
					Component newComponent = (Component) selectListener.getSelectedPanel().getComponent().clone();
					getXMLDocument().addClauseAt(index, newComponent);
					ComponentPanel newPanel = ComponentPanel.CreateInstance(newComponent, parentDocuPanel);
					newPanel.setSelectListener(parentDocuPanel.getSelectListener());
										
					parentDocuPanel.addSentencePanelAt(index, newPanel);
					parentDocuPanel.adjustPositioning();
					
					//delete previous ComponentPanel
					ComponentPanel oldPanel = selectListener.getSelectedPanel();
					if(oldPanel.getParentComponentPanel() != null)
					{
						//remove internally
						Phrase parentComponent = (Phrase)oldPanel.getParentComponentPanel().getComponent();
						parentComponent.removeChild(oldPanel.getComponent());
										
						//remove in gui
						oldPanel.getParentComponentPanel().removeChild(oldPanel);
						parentDocuPanel.adjustPositioning();
					}
					else
					{
						//remove internally
						parentDocuPanel.getXMLDocument().removeSentence(oldPanel.getComponent());
						//remove in gui
						parentDocuPanel.removeChild(oldPanel);
					}
					selectListener.setMove(false);
					
				}
			}
		});
	}
	
	public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch( CloneNotSupportedException e )
		{
			return null;
		}
	}
	
}
