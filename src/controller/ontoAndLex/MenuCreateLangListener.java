package controller.ontoAndLex;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import managers.LexiconManager;

import view.grammardevelopment.MainFrame;
import view.ontoandlex.LexiconPanel;
import view.ontoandlex.NewLanguagePopUp;

public class MenuCreateLangListener implements ActionListener{

	private NewLanguagePopUp popUp;
	
	public void actionPerformed(ActionEvent e) {
		popUp = new NewLanguagePopUp(this);
	}
	
	public void proceed(){
		LexiconManager.getInstance().addLanguage(popUp.getNewLanguageName());
		LexiconManager.getInstance().loadLexicon(popUp.getNewLanguageName());
		MainFrame.getInstance().setPanel(new LexiconPanel());
		MainFrame.getInstance().setTitle(popUp.getNewLanguageName());
	}
}
