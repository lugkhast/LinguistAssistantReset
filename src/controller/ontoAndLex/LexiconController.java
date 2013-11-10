package controller.ontoAndLex;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import lexicon.LexiconList;
import managers.LexiconManager;

import view.grammardevelopment.MainFrame;
import view.ontoandlex.AddEditFeatureFrame;
import view.ontoandlex.AddEditFormFrame;
import view.ontoandlex.LexiconPanel;

public class LexiconController {

	LexiconPanel lexPanel;
	LexiconList lexList;
	
	public LexiconController(){
		lexPanel = new LexiconPanel();
		lexList = LexiconManager.getInstance().getLexiconList(lexPanel.getCodeFromSelectedPOS());
		MainFrame.getInstance().setPanel(lexPanel);
		
		addListeners();
	}
	
	public void addListeners(){
		lexPanel.getTable().addKeyListener(new LexiconTableKeyListener(lexPanel));
		
		lexPanel.getTable().getModel().addTableModelListener(new LexiconTableListener(lexPanel));
		
		lexPanel.getCmbCategory().addItemListener(new LexComboCategoryItemListener(lexPanel));
		
		lexPanel.getBtnViewStems().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				lexPanel.setTable(lexPanel.TABLE_STEM);
			}
		});
		
		lexPanel.getBtnViewFeatures().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				lexPanel.setTable(lexPanel.TABLE_FEATURE);
			}
		});
		lexPanel.getBtnViewForms().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				lexPanel.setTable(lexPanel.TABLE_FORM);
			}
		});
		
		lexPanel.getBtnNewEntry().addActionListener(new LexNewEntryButtonListener(lexPanel));
		
		lexPanel.getBtnMapToNewConcept().addActionListener(new LexMapToNewConcept(lexPanel));
		
		lexPanel.getBtnAddEditFeatures().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				new AddEditFeatureFrame(lexList); 
				lexPanel.initTable(); // para bumalik nalang sa viewstem, hassle kasi pag irerefresh mo pa if nasa viewfeature ba siya or ano man
			}});
		lexPanel.getBtnAddEditForms().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				new AddEditFormFrame(lexList);
				lexPanel.initTable();
			}
			
		});
		
		lexPanel.getBtnAddPOS().addActionListener(new LexAddNewPOSActionListener(lexPanel));
	}
}
