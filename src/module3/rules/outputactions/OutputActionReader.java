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
			return;
		}
 		
	}
	
	private static void addFeatureAction(Component c, List<Element> args) {
		String featureName = args.get(0).getChild("feature").getAttributeValue("name");
		String featureValue = args.get(0).getChild("feature").getAttributeValue("value");
		
		Feature f = new Feature(featureName, featureValue, FeatureManager.isFeatureStandard(c.getName(), featureName));
		c.setFeature(f);
	}

	private static void editFeatureAction(Component c, List<Element> args) {
		String featureName = args.get(0).getAttributeValue("name");
		String featureValue = args.get(0).getAttributeValue("value");
		
		Feature f = new Feature(featureName, featureValue, FeatureManager.isFeatureStandard(c.getName(), featureName));
		c.setFeature(f);
	}
}
