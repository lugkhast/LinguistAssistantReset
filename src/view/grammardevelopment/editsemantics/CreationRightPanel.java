package view.grammardevelopment.editsemantics;

import javax.swing.JPanel;

import components.Component;

public class CreationRightPanel extends JPanel{
	ComponentPaletteScrollPane cpScrollPane;
	FeaturePaletteScrollPane fpScrollPane;
	LeafEditPalette leScrollPane;
	DeleteButton deleteBtn;
	Component comp = null;
	
	public CreationRightPanel(){
		cpScrollPane = new ComponentPaletteScrollPane();
		fpScrollPane = new FeaturePaletteScrollPane();
		leScrollPane = new LeafEditPalette();
		deleteBtn = new DeleteButton();
		add(deleteBtn);
		add(cpScrollPane);
		add(fpScrollPane);
		add(leScrollPane);
		
		
	}

	public LeafEditPalette getLeScrollPane() {
		return leScrollPane;
	}
	
	public void clearInput(){
		fpScrollPane.initComponents();
		leScrollPane.disableComponents();
	}

	public void setComponent(Component comp){
		this.comp = comp;
		fpScrollPane.initCmbValues(comp);
		leScrollPane.disableComponents();
	}
	
	public void setLeaf(Component comp){
		this.comp = comp;
		leScrollPane.setValues(comp);
		leScrollPane.enableComponents();
	}
	
	
	public ComponentPaletteScrollPane getCpScrollPane() {
		return cpScrollPane;
	}

	public FeaturePaletteScrollPane getFpScrollPane() {
		return fpScrollPane;
	}
}
