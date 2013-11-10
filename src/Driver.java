import java.awt.Font;

import javax.swing.UIManager;

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
