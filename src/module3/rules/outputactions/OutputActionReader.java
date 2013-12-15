package module3.rules.outputactions;

import java.util.List;

import managers.FeatureManager;

import org.jdom2.Element;

import components.Component;
import features.Feature;

public class OutputActionReader {
	public static void DoOutputAction(Component c, OutputAction a) {
		String action = a.type;
		
		// i kind of love the switch format <33
		if (action.equals("addFeature")) {
			addFeatureAction(c, a.args);
			return;
		}
		if (action.equals("editFeature")) {
			editFeatureAction(c, a.args);
			return;
		}
		if (action.equals("deleteFeature")) {
			deleteFeatureAction(c, a.args);
			return;
		}
		if (action.equals("addConstit")) {
			addConstituentAction(c, a.args);
			return;
		}
		if (action.equals("moveConstit")) {
			//moveConstituentAction(c, a.args);
			return;
		}
		if (action.equals("deleteConstit")) {
			//deleteConstituentAction(c, a.args);
			return;
		}
		if (action.equals("copyConstit")) {
			return;
		}
		if (action.equals("orderSubConst")) {
			return;
		}
		if (action.equals("addLexicon")) {
			return;
		}
		if (action.equals("changeLexicon")) {
			return;
		}
		if (action.equals("deleteLexicon")) {
			return;
		}
		if (action.equals("selectForm")) {
			return;
		}
 		
	}
	
	private static void addConstituentAction(Component c, List<Element> args) {
		Component child = Component.createMatcher(args.get(0).getChild("component"));
		c.getChildren().addChild(child);
		
		return;
	}
	
	private static void addFeatureAction(Component c, List<Element> args) {
		String featureName = args.get(0).getChild("feature").getAttributeValue("name");
		String featureValue = args.get(0).getChild("feature").getAttributeValue("value");
		
		Feature f = new Feature(featureName, featureValue, FeatureManager.isFeatureStandard(c.getName(), featureName));
		c.setFeature(f);
	}

	private static void editFeatureAction(Component c, List<Element> args) {
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
