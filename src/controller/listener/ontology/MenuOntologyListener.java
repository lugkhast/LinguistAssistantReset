package controller.listener.ontology;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.MainController;

import view.MainFrame;
import view.ontology.OntologyPanel;

public class MenuOntologyListener implements ActionListener{

	private MainController mainController;
	
	public MenuOntologyListener(MainController mainController){
		this.mainController = mainController;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		mainController.goToOntology();
	}
}
