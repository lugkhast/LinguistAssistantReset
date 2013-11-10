package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.TransferHandler;

import managers.FeatureManager;

import components.Component;
import components.Leaf;
import controller.listener.grammardev.SelectComponentActionListener;
import controller.listener.grammardev.editsemantics.FeaturePaletteCmbValuesItemListener;
import controller.listener.grammardev.editsemantics.DeleteComponentBtnListener;
import controller.listener.grammardev.editsemantics.FeaturePaletteResetBtnListener;
import controller.listener.grammardev.editsemantics.LeafEditPaletteBtnListener;

import features.Feature;

import view.grammardevelopment.ComponentPanel;
import view.grammardevelopment.DisplayScreen;
import view.grammardevelopment.editsemantics.ComponentPaletteScrollPane;
import view.grammardevelopment.editsemantics.CreationRightPanel;
import view.grammardevelopment.editsemantics.FeaturePaletteScrollPane;
import view.grammardevelopment.editsemantics.LeafEditPalette;

public class CreateController {

	private CreationRightPanel creationPanel;
	private GrammarDevController grammarDevController;
	
	public CreateController(GrammarDevController grammarDevController, CreationRightPanel creationPanel){
		this.creationPanel = creationPanel;
		this.grammarDevController = grammarDevController;
		
		addDeleteButtonListener();
		addListenersToCompPalette();
		addListenersToFeatPalette();
		addListenersToLeafEditPalette();
	}
	
	private void addDeleteButtonListener(){
		creationPanel.addDeleteBtnListener(new DeleteComponentBtnListener(grammarDevController));
	}

	private void addListenersToLeafEditPalette() {
		creationPanel.addLeafEditBtnListener(new LeafEditPaletteBtnListener(grammarDevController, creationPanel));
	}

	private void addListenersToFeatPalette() {
		creationPanel.addSaveFeatureListener(new FeaturePaletteCmbValuesItemListener(grammarDevController, creationPanel));
		creationPanel.addResetFeatureBtnListener(new FeaturePaletteResetBtnListener(creationPanel));
		creationPanel.addSelectFeatureComboBoxListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				creationPanel.refreshFeaturesDisplay();
			}
		});				
	}

	private void addListenersToCompPalette() {
		creationPanel.addCompPaletteDragListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                JButton button = (JButton)e.getSource();
                TransferHandler handle = button.getTransferHandler();
                handle.exportAsDrag(button, e, TransferHandler.COPY);
            }
        });
	}
	
}