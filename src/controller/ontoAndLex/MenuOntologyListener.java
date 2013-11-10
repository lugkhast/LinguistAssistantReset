package controller.ontoAndLex;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.grammardevelopment.MainFrame;
import view.ontoandlex.OntologyPanel;

public class MenuOntologyListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		MainFrame.getInstance().setPanel(new OntologyPanel());
	}

}
