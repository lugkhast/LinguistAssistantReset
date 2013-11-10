package view.grammardevelopment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.datatransfer.Clipboard;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TooManyListenersException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import components.Children;
import components.Component;
import components.Leaf;
import components.Phrase;
import controller.listener.grammardev.CollapseButtonListener;
import controller.listener.grammardev.SelectComponentActionListener;
import controller.listener.grammardev.editsemantics.ComponentPaletteDnDListener;

import managers.ColorManager;

import view.MainFrame;;

public class ComponentPanel extends JPanel {

	private Component component;
	private ArrayList<ComponentPanel> children;
	private ComponentPanel parent;
	private InputXMLDocumentPanel parentDocPanel;
	private int desiredHeight;
	
	private JLabel nameLabel;
	private SelectComponentActionListener selectListener;
	private JButton collapseButton;
	
	private boolean isGenerateMode;
	private boolean isCollapsed;
	
	private static final int LEAF_HEIGHT = 15;
	private static final int HORIZONTAL_MARGIN = 20;
	private static final int VERTICAL_MARGIN = 15;
	private static final int BTN_LABEL_MARGIN = 5;		
	
	
	private static ImageIcon imgCollapse;
	private static ImageIcon imgExpand;
	
	public static final String COMMAND_COLLAPSE = "collapse";
	public static final String COMMAND_EXPAND = "expand";
	
	private static ImageIcon getImgCollapse(){
		if(imgCollapse == null)
			imgCollapse = new ImageIcon("Resources\\collapse.png");
		if(imgCollapse == null)
			System.out.println("NULL");
		return imgCollapse;
	}
	
	private static ImageIcon getImgExpand(){
		if(imgExpand == null)
			imgExpand = new ImageIcon("Resources\\expand.png");
		if(imgExpand == null)
			System.out.println("NULL");
		return imgExpand;
	}
	
	//constructor
	private ComponentPanel(Component component, InputXMLDocumentPanel parentDocPanel){
		this.parentDocPanel = parentDocPanel;
		this.component = component;
		children = new ArrayList<ComponentPanel>();
		setBackground(ColorManager.getInstance().getColor(component.getName()));
		setLayout(null);

		//Initialize Collapse/Expand Button
		if(!component.isLeaf()){
			collapseButton = new JButton();
			collapseButton.setBounds(HORIZONTAL_MARGIN, 0, LEAF_HEIGHT, LEAF_HEIGHT);
			collapseButton.addActionListener(new CollapseButtonListener(this));
			add(collapseButton);
		}
		
		//Initialize label
		int lblStartX = HORIZONTAL_MARGIN;
		if(!component.isLeaf())
			lblStartX= collapseButton.getBounds().x + collapseButton.getBounds().width + 5;
		nameLabel = new JLabel(component.toString());
		nameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
		nameLabel.setBounds(lblStartX, 0, (int)nameLabel.getPreferredSize().getWidth(),LEAF_HEIGHT);
		nameLabel.setToolTipText("<html>"+component.getDescription()+"<br>"+component.getFeaturesInHTML(false)+"</html>");
		
		if(!component.isLeaf())
			nameLabel.setForeground(Color.WHITE);
		
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		add(nameLabel);	
		
		if(!component.isLeaf())
			setCollapsed(false); //must be done after initializing nameLabel
	}
	
	//static method for creating a custompanel
	public static ComponentPanel CreateInstance(Component component, InputXMLDocumentPanel parentDocPanel){
		
		ComponentPanel newCP = new ComponentPanel(component, parentDocPanel);
		DropTarget dt = new DropTarget();
		try {
			dt.addDropTargetListener(new ComponentPaletteDnDListener(parentDocPanel));
			newCP.setDropTarget(dt);
			
		} catch (TooManyListenersException e) {}
		
		Children children = component.getChildren();
		if(children!=null){
			for(Component child: children.getChildren()){
				ComponentPanel currChildPanel = CreateInstance(child, parentDocPanel);
				newCP.addChild(currChildPanel);
			}
		}
		return newCP;
	}
		
	//Logic for laying out everything

	public void adjustPositioning(int leftX, int topY){
		calculateDesiredHeightBasedOnChildren();
		resize(leftX, topY);
	}
	
	public void resize(int leftX, int topY){
		if(parent!=null){
			int x = HORIZONTAL_MARGIN;
			int y;

			ArrayList<ComponentPanel> parentChildren = parent.children;
			
			if( parentChildren.get(0) != this){
				int prevIndex = parentChildren.indexOf(this) -1;
				y = parentChildren.get(prevIndex).getBottomY() + VERTICAL_MARGIN;
			}
			else
				y = VERTICAL_MARGIN;
				
			int width = parent.getWidth() - 2 * HORIZONTAL_MARGIN;
			setBounds(x,y,width,desiredHeight);
			this.setPreferredSize(new Dimension(width,desiredHeight));
		}
		else{
			int desiredWidth = (MainFrame.getInstance().getWidth()-50)*3/5;
			setBounds(leftX, topY,desiredWidth,desiredHeight);
			this.setPreferredSize(new Dimension(desiredWidth,desiredHeight));
		}
		
		if(!component.isLeaf() && !isCollapsed){
			for(ComponentPanel child: children)
				child.resize(leftX, topY);
		}
	}

	//setters	
	public void setCollapsed(boolean isCollapsed){
		if(isCollapsed){
			if(isGenerateMode)
				setLabelText(component.toString()+" = "+component.toLexiconSentence());
			else
				setLabelText(component.toString()+" = "+component.toConceptSentence());
			collapseButton.setActionCommand(COMMAND_EXPAND);
			collapseButton.setIcon(getImgExpand());
		}
		else{
			setLabelText(component.toString());
			collapseButton.setActionCommand(COMMAND_COLLAPSE);
			collapseButton.setIcon(getImgCollapse());
		}
		collapseButton.setBounds(HORIZONTAL_MARGIN, 0, LEAF_HEIGHT, LEAF_HEIGHT);
		this.isCollapsed = isCollapsed;
	}
	
	public void setGenerateMode(boolean isGenerateMode){
		if(isGenerateMode){
			if(isCollapsed)	
				setLabelText(component.toLexiconSentence());
			else
				setLabelText(component.toGeneratedString());
		}
		else{
			if(isCollapsed)
				setLabelText(component.toConceptSentence());
			else
				setLabelText(component.toString());
		}
	
		for(ComponentPanel child: children)
			child.setGenerateMode(isGenerateMode);
		this.isGenerateMode = isGenerateMode;
	}
	
	public void calculateDesiredHeightBasedOnChildren(){
		if(component.isLeaf())
			this.desiredHeight = LEAF_HEIGHT;
		else{
			if(isCollapsed)
				desiredHeight = LEAF_HEIGHT;
			else{
				this.desiredHeight = VERTICAL_MARGIN;
				for(ComponentPanel child: children){
					child.calculateDesiredHeightBasedOnChildren();
					this.desiredHeight += child.getDesiredHeight() + VERTICAL_MARGIN;
				}
			}
		}
	}

	public int getDesiredHeight(){
		return desiredHeight;
	}
		
	public void setParent(ComponentPanel parent){
		this.parent = parent;
	}
	
	public void setHighlighted(boolean isHighlighted){
		if(isHighlighted){
			//if(component.isLeaf() || isCollapsed)
				setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			//else
				//setBorder(BorderFactory.createLineBorder(Color.RED, 5));
		}
		else
			setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	}
	
	public void addChild(ComponentPanel child){
		children.add(child);
		add(child);
		child.setParent(this);
	}
	
	public void addChildAt(int index, ComponentPanel child){
		children.add(index, child);
		add(child);
		child.setParent(this);
	}

	public void refreshSemanticLexicons(){
		if(component.isLeaf()){
			Leaf leaf = ((Leaf)component);
			leaf.refreshLexicon();
			refreshLabelText();
			refreshLabelToolTip();
		}
		
		for(ComponentPanel child: children)
			child.refreshSemanticLexicons();
	}
	
	//method also removes children internally in data structure
	public void removeChild(ComponentPanel child){
		if(child != null){
			children.remove(child);
			remove(child);
		}
	}
	
	public void setSelectListener(SelectComponentActionListener listener){
		addMouseListener(listener);
		this.selectListener = listener;
		
		for(ComponentPanel child: children)
			child.setSelectListener(listener);
	}
	
	private void setLabelText(String labelText){
		nameLabel.setText(labelText);
		nameLabel.setBounds(nameLabel.getBounds().x, nameLabel.getBounds().y, (int)nameLabel.getPreferredSize().getWidth(), LEAF_HEIGHT);
	}
	
	public void refreshLabelText(){
		setLabelText(component.toString());
		for(ComponentPanel child: children)
			child.refreshLabelText();
	}
	
	public void refreshLabelToolTip(){
		nameLabel.setToolTipText("<html>"+component.getDescription()+"<br>"+component.getFeaturesInHTML(false)+"</html>");
		for(ComponentPanel child: children)
			child.refreshLabelToolTip();
	}
	
	//Getters
	public InputXMLDocumentPanel getParentDocPanel(){
		return parentDocPanel;
	}
	
	public String toSentence(){
		return component.toLexiconSentence();
	}
		
	public int getBottomY(){
		return getY() + getHeight();
	}
	
	public Component getComponent(){
		return component;
	}

	public SelectComponentActionListener getSelectListener(){
		return selectListener;
	}
	
	public ComponentPanel getParentComponentPanel(){
		return parent;
	} 
	
	//For Drag and Drop
	public int determineInsertIndex(Point p){
		//first
		if(children.size() == 0)
			return 0;
		
		if(p.y <= children.get(0).getY())
			return 0;
		
		//middle
		for(int i = 0; i < children.size(); i++){
			ComponentPanel child = children.get(i);
			if(p.y > child.getY() && p.y < child.getBottomY()) //click isn't between any panels and it's "inside"  a panel
				return i;
			if( i+1 < children.size() && p.y >= child.getBottomY() && p.y <= children.get(i+1).getY())
				return i+1;
		}
				
		return children.size(); //last
	}

}