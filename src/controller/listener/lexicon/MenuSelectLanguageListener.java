package controller.listener.lexicon;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.MainController;

import view.MainFrame;

import managers.LexiconManager;

public class MenuSelectLanguageListener implements ActionListener{
	
	private MainController mainController;
	
	
	public MenuSelectLanguageListener(MainController mainController){
		this.mainController = mainController;
	}
	
	public void actionPerformed(ActionEvent e) {
		ArrayList<String> languages = LexiconManager.getInstance().getLanguages(); // changed to all languages currently stored.
		if(languages!=null){
			Object[] array = languages.toArray();
			String s = (String)JOptionPane.showInputDialog(
			                    null,
			                    "Choose the language",
			                    "Select Language",
			                    JOptionPane.PLAIN_MESSAGE,
			                    null,
			                    array,
			                    LexiconManager.getInstance().getCurrSelectedLanguage());
			mainController.loadLanguage(s);
		}
	}
}
