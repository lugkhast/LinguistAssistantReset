package controller.ontoAndLex;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.grammardevelopment.MainFrame;
import view.ontoandlex.LexiconPanel;

public class MenuLexiconListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		new LexiconController();
		
	}
}
