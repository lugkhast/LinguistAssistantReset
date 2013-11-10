package view.grammardevelopment;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.BoxLayout;

import controller.listener.ExitAppActionListener;
import controller.listener.LoadSemanticsActionListener;
import controller.listener.NewSemanticsActionListener;
import controller.listener.SaveActionListener;
import controller.listener.SaveAsListener;
import controller.ontoAndLex.MenuCreateLangListener;
import controller.ontoAndLex.MenuLexiconListener;
import controller.ontoAndLex.MenuOntologyListener;
import controller.ontoAndLex.MenuSelectLanguageListener;

public class MainFrame extends JFrame {
		
	
	private JPanel mainPanel;
	
	private static MainFrame instance;
	
	public static MainFrame getInstance(){
		if(instance == null){
			instance = new MainFrame();
		}
		return instance;
	}
	
	private MainFrame() {
		InitializeBars();
		setSize(800,600);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	
	private void InitializeBars(){
		 //Create the menu bar.
        JMenuBar menuBar = new JMenuBar();

        menuBar.setSize(new Dimension(1200, 30));
        
        //Add a JMenu
        JMenu fileMenu = new JMenu("File");
        JMenuItem newSemantics = new JMenuItem("New Document");
        JMenuItem saveSemantics = new JMenuItem("Save Document");
        JMenuItem saveAsSemantics = new JMenuItem("Save Document As");
        JMenuItem loadSemantics = new JMenuItem("Load Document");
        JMenuItem exitApp = new JMenuItem("Exit Application");
        menuBar.add(fileMenu);
        
        //Action Listeners
        loadSemantics.addActionListener(new LoadSemanticsActionListener());
        saveSemantics.addActionListener(new SaveActionListener());
        newSemantics.addActionListener(new NewSemanticsActionListener());
        saveAsSemantics.addActionListener(new SaveAsListener());
        exitApp.addActionListener(new ExitAppActionListener());
      
        //Add to the JMenu
        fileMenu.add(newSemantics);
        fileMenu.add(saveSemantics);
        fileMenu.add(saveAsSemantics);
        fileMenu.add(loadSemantics);
        fileMenu.add(exitApp);
        
        JMenu ontoAndLex = new JMenu("Ontology and Lexicon");
        JMenuItem ontology = new JMenuItem("Ontology");
        ontology.addActionListener(new MenuOntologyListener());
        JMenuItem lexicon = new JMenuItem("Lexicon");
        lexicon.addActionListener(new MenuLexiconListener());
        JMenu language = new JMenu("Language");
        
        JMenuItem selectLang = new JMenuItem("Select Language");
        selectLang.addActionListener(new MenuSelectLanguageListener());
        JMenuItem createNewLang = new JMenuItem("Create New Language");
        createNewLang.addActionListener(new MenuCreateLangListener());
        language.add(selectLang);
        language.add(createNewLang);
        
        ontoAndLex.add(ontology);
        ontoAndLex.add(lexicon);
        ontoAndLex.add(language);
        
        menuBar.add(ontoAndLex);
        
        this.setJMenuBar(menuBar);
	}
	
	public void setPanel(JPanel newPanel){
		if(mainPanel != null)
			getContentPane().remove(mainPanel);
		
		mainPanel = newPanel;	
	
		if(mainPanel != null)
			getContentPane().add(mainPanel, BorderLayout.CENTER);
		getContentPane().validate();
		getContentPane().repaint();
	}
	
}
