package controller;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ToolTipManager;

import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import ontology.Concept;
import ontology.ConceptList;

import components.ComponentInfo;

import features.DBFeatureValues;
import features.Feature;
import features.FeatureList;

import lexicon.Lexicon;
import lexicon.LexiconList;
import managers.ComponentManager;
import managers.LexiconManager;
import managers.OntologyManager;
import managers.SemanticsManager;
import view.MainFrame;
import view.grammardevelopment.DisplayScreen;

public class Controller {
	public void start(){
		ToolTipManager.sharedInstance().setDismissDelay(60000);
		ToolTipManager.sharedInstance().setInitialDelay(100);
		MainFrame.getInstance().setVisible(true);
	}
		/*
	private void testLoadOntology(){
		ArrayList<ConceptList> ontology = OntologyManager.getInstance().getOntology();

		StringBuilder sb = new StringBuilder();
		for(ConceptList cl: ontology){
			sb.append("**********\n");
			sb.append(cl.getPOS());
			sb.append("\n");
			
			for(Concept concept: cl.getConceptList()){
				sb.append(concept.toString());
				sb.append("\n");
			}
			
			sb.append("**********\n");	
		}
		
		System.out.println(sb.toString());
	}
*/
	private void testLoadLexicons(){
		StringBuilder sb = new StringBuilder("FILIPINO\n");
		ArrayList<LexiconList> languageLexicon = LexiconManager.getInstance().getLanguageLexicon();
		
		for(LexiconList ll: languageLexicon){
			sb.append("**********\n");
			sb.append(ll.getPOS());
			sb.append("\n");
			
			//Forms
			sb.append("Possible Forms:\n");
			for(String form: ll.getPossibleForms())
				sb.append(form+", ");
			sb.append("\n");
			
			//Features
			sb.append("Possible features:\n");
			for(DBFeatureValues fl: ll.getPossibleFeatures())
				sb.append(fl.print());
			
			//Lexicons
			sb.append("Stems:\n");
			for(Lexicon lexicon: ll.getLexiconList()){
				sb.append(lexicon.getName()+", gloss: "+lexicon.getGloss()+", mapped to: "+lexicon.getMappedConcept()+" features: ");
				for(Feature feat: lexicon.getFeatureList().getFeatureList())
					sb.append(feat.getName()+"="+feat.getValue()+", ");
				sb.append("\n");
			}
			
			sb.append("\n*********\n");
			
		
		}
		
		System.out.println(sb.toString());
	}
	
	private void testMappings(){
		/*
		ArrayList<Lexicon> lexicons = LexiconManager.getInstance().getMappedLexicons("adj", "big", "A");
		for(Lexicon lexicon: lexicons){
			Concept concept = OntologyManager.getInstance().getConcept(lexicon.getMappedConceptPos(), lexicon.getMappedConceptName(), lexicon.getMappedConceptSense());
			System.out.println(concept.getName()+" = "+concept.getDefinition()+", "+concept.getComments());
			System.out.println(lexicon.getName()+" = "+lexicon.getGloss()+" mapped to:");
		}
		*/
	}
	
	private void testRenameFeature(){
		ArrayList<LexiconList> languageLexicon = LexiconManager.getInstance().getLanguageLexicon();
		for(LexiconList ll: languageLexicon){
			//test rename feature name and value
			ll.renameFeatureName("feature1","feature110");
			ll.renameFeatureValue("feature110", "No", "Noooo");
			
			//test generate xml
			Element xmlElement = ll.generateXMLElement();
			
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			try{
				//xmlOutput.output(xmlElement, new FileWriter("C:\\"+ll.getPOS()+".xml"));
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}
