import java.awt.Font;
import java.io.File;

import javax.swing.UIManager;

import managers.RulesManager;
import module3.rules.RuleTree;

import controller.Controller;
import controller.MainController;

public class Driver {

	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){}
		
		Font oldLabelFont = UIManager.getFont("Label.font");
		UIManager.put("Label.font", oldLabelFont.deriveFont(Font.PLAIN,(float)14));
		
		new MainController();
	}
}
