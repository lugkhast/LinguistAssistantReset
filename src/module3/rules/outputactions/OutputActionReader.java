package module3.rules.outputactions;

import java.util.ArrayList;
import java.util.List;

import managers.FeatureManager;
import module3.rules.UniMap;

import org.jdom2.Element;

import components.Component;
import components.Leaf;
import features.Feature;

public class OutputActionReader {
	
	public static Component DoOutputAction(Component c, OutputAction a, ArrayList<UniMap> u) {
		String action = a.type;
		
		// i kind of love the switch format <33
		if (action.equals("addFeature")) {
			addFeatureAction(c, a.args);
			return c;
		}
		if (action.equals("editFeature")) {
			addFeatureAction(c, a.args);
			return c;
		}
		if (action.equals("deleteFeature")) {
			deleteFeatureAction(c, a.args);
			return c;
		}
		if (action.equals("addConstit")) {
			addConstituentAction(c, a.args);
			return c;
		}
		if (action.equals("moveConstit")) {
			moveConstituentAction(c, a.args);
			return c;
		}
		if (action.equals("deleteConstit")) {
			deleteConstituentAction(c, a.args);
			return c;
		}
		if (action.equals("copyConstit")) {
			copyConstituentAction(c, a.args);
			return c;
		}
		if (action.equals("orderSubConst")) {
			orderSubConstituentAction(c, a.args, u);
			return c;
		}
		if (action.equals("addLexicon")) {
			addLexiconAction(c, a.args);
			return c;
		}
		if (action.equals("changeLexicon")) {
			changeLexiconAction(c, a.args);
			return c;
		}
		if (action.equals("deleteLexicon")) {
			deleteLexiconAction(c, a.args);
			return c;
		}
		if (action.equals("selectForm")) {
			selectFormAction(c, a.args);
			return c;
		}
 		
		return null;
	}
	
	private static void selectFormAction(Component c, List<Element> args) {	
		//Leaf l = (Leaf)(c);
		
		//l.setConcept();
		return;
	}
	
	private static void deleteLexiconAction(Component c, List<Element> args) {	
		Leaf leaf = (Leaf) c;
		
		if (leaf.getConcept() != null)
			leaf.setConcept("");
	}
	
	
	private static void changeLexiconAction(Component c, List<Element> args) {	
		Leaf leaf = (Leaf) c;
		String lexicon = args.get(0).getAttributeValue("lexiconName");
		
		if (leaf.getConcept() != null)
			leaf.setConcept(lexicon);
	}
	
	private static void addLexiconAction(Component c, List<Element> args) {	
		Leaf leaf = (Leaf) c;
		String lexicon = args.get(0).getAttributeValue("lexiconName");
		
		if (leaf.getConcept() == null)
			leaf.setConcept(lexicon);
	}
	
	private static void orderSubConstituentAction(Component c, List<Element> args, ArrayList<UniMap> u) {	
		String order1 = args.get(0).getAttributeValue("order");
		String[] order = order1.split(" ");
		
		
		for (int i = order.length-1; i >= 0; i--) {
			String firstTag = order[i];
			for (UniMap p : u) {
				if (firstTag.equals(p.getTag())) {
					if (c.getChildren().getChildren().contains(p.getVar())) {
						c.getChildren().removeChild(p.getVar());
						c.getChildren().addChild(0, p.getVar());
					}
				}
			}
		}
	}
	
	private static void copyConstituentAction(Component c, List<Element> args) {	
		return;
	}
	
	private static void deleteConstituentAction(Component c, List<Element> args) {	
		Component child = Component.createMatcher(args.get(0).getChild("component"));
		c.getChildren().removeChild(child);
	}
	
	private static void moveConstituentAction(Component c, List<Element> args) {	
		//indexTag and component
		//what am i doing D:
		String indexTag = args.get(0).getChild("argument").getAttributeValue("indexTag");
		String moveTag = args.get(1).getChild("argument").getAttributeValue("moveTag");
		//return indexes?
		return;
	}
	
	private static void addConstituentAction(Component c, List<Element> args) {
		Component child = Component.createMatcher(args.get(0).getChild("component"));
		c.getChildren().addChild(child);
	}
	
	private static void addFeatureAction(Component c, List<Element> args) {
		String featureName = args.get(0).getChild("feature").getAttributeValue("name");
		String featureValue = args.get(0).getChild("feature").getAttributeValue("value");
		
		Feature f = new Feature(featureName, featureValue, FeatureManager.isFeatureStandard(c.getName(), featureName));
		c.setFeature(f);
	}
	
	private static void deleteFeatureAction(Component c, List<Element> args) {
		String featureName = args.get(0).getAttributeValue("name");
		
		c.getFeatureList().removeFeature(featureName);
	}
}
