package controller.listener.lexicon;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controller.MainController;

import managers.LexiconManager;

import view.MainFrame;
import view.lexicon.LexiconPanel;
import view.lexicon.NewLanguagePopUp;

public class MenuCreateLangListener implements ActionListener{

	private NewLanguagePopUp popUp;
	private MainController mainController;
	
	public MenuCreateLangListener(MainController mainController){
		this.mainController = mainController;
	}
	
	public void actionPerformed(ActionEvent e) {
		popUp = new NewLanguagePopUp(this);
	}
	
	public void proceed(){
		mainController.createLanguage(popUp.getNewLanguageName());
	}
}
