package module3.rules.outputactions;

import java.util.List;

import managers.FeatureManager;

import org.jdom2.Element;

import components.Component;
import components.Leaf;
import features.Feature;

public class OutputActionReader {
	
	public static Component DoOutputAction(Component c, OutputAction a) {
		String action = a.type;
		
		// i kind of love the switch format <33
		if (action.equals("addFeature")) {
			addFeatureAction(c, a.args);
			return c;
		}
		if (action.equals("editFeature")) {
			editFeatureAction(c, a.args);
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
			orderSubConstituentAction(c, a.args);
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
		return;
	}
	
	private static Component deleteLexiconAction(Component c, List<Element> args) {	
		Leaf leaf = (Leaf) c;
		
		if (leaf.getConcept() != null)
			leaf.setConcept("");
		
		return leaf;
	}
	
	
	private static void changeLexiconAction(Component c, List<Element> args) {	
		return;
	}
	
	private static Component addLexiconAction(Component c, List<Element> args) {	
		Leaf leaf = (Leaf) c;
		String lexicon = args.get(0).getChild("argument").getAttributeValue("lexiconName");
		
		if (leaf.getConcept() == null)
			leaf.setConcept(lexicon);
		return leaf;
	}
	
	private static void orderSubConstituentAction(Component c, List<Element> args) {	
		String order = args.get(0).getChild("argument").getAttributeValue("order");
		//return new order or rearranged component
		return;
	}
	
	private static void copyConstituentAction(Component c, List<Element> args) {	
		
		return;
	}
	
	private static Component deleteConstituentAction(Component c, List<Element> args) {	
		Component child = Component.createMatcher(args.get(0).getChild("component"));
		c.getChildren().removeChild(child);
		
		//return modified componentParent (c)
		return c;
	}
	
	private static void moveConstituentAction(Component c, List<Element> args) {	
		//indexTag and component
		//what am i doing D:
		String indexTag = args.get(0).getChild("argument").getAttributeValue("indexTag");
		String moveTag = args.get(1).getChild("argument").getAttributeValue("moveTag");
		//return indexes?
		return;
	}
	
	private static Component addConstituentAction(Component c, List<Element> args) {
		Component child = Component.createMatcher(args.get(0).getChild("component"));
		c.getChildren().addChild(child);
		//return modified componentParent (c)
		return c;
	}
	
	private static Component addFeatureAction(Component c, List<Element> args) {
		String featureName = args.get(0).getChild("feature").getAttributeValue("name");
		String featureValue = args.get(0).getChild("feature").getAttributeValue("value");
		
		Feature f = new Feature(featureName, featureValue, FeatureManager.isFeatureStandard(c.getName(), featureName));
		c.setFeature(f);
		//return modified component (c)
		return c;
	}

	private static Component editFeatureAction(Component c, List<Element> args) {
		String featureName = args.get(0).getChild("feature").getAttributeValue("name");
		String featureValue = args.get(0).getChild("feature").getAttributeValue("value");
		
		Feature f = new Feature(featureName, featureValue, FeatureManager.isFeatureStandard(c.getName(), featureName));
		c.setFeature(f);
		
		//return modified component (c)
		return c;
	}
	
	private static Component deleteFeatureAction(Component c, List<Element> args) {
		String featureName = args.get(0).getAttributeValue("name");
		
		c.getFeatureList().removeFeature(featureName);
		
		//return modified component (c)
		return c;
	}
}
