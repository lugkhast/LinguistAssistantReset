package module3.rules.outputactions;

import java.util.ArrayList;

import managers.FeatureManager;
import module3.rules.PhraseMatcher;

import org.jdom2.Element;

import components.Component;
import features.Feature;

public class FeatureAction extends OutputAction{
	private String parentTag, parentComponentName;
	private ArrayList<Feature> featureList;
	private String originalValue;
	
	public FeatureAction(Element e) {
		super(e);
		
		parentTag = args.get(0).getAttributeValue("parentTag");
		parentComponentName = args.get(0).getAttributeValue("parentComponent");
		
		String featureName, featureValue;
		
		for (int i = 1; i < args.size(); i++) {
			originalValue = args.get(1).getAttributeValue("value");
			featureName = args.get(1).getChild("feature").getAttributeValue("name");
			featureValue = args.get(1).getChild("feature").getAttributeValue("value");
			
			Feature feature = null;
			
			if(FeatureManager.doesFeatureExist(parentComponentName, featureName)) { //disregard the input feature if it does not exist in the database
				//check if value is in db
				if(FeatureManager.doesFeatureValueExist(parentComponentName, featureName, featureValue))
					feature = new Feature(featureName, featureValue, FeatureManager.isFeatureStandard(parentComponentName, featureName));
				else
					feature = FeatureManager.getDefaultFeatureCopy(parentComponentName, featureName);
			}
			
			if (feature != null)
				featureList.add(feature);
		}
	}
	
	public Component doAction(Component c, PhraseMatcher m) {
		if (type.equals("set")) {
			// set component's feature to f
		}
		if (type.equals("specset")) {
			// set component's feature from f1 to f2
		}
		else if (type.equals("reset")) {
			// delete component's feature
		}
		
		return c;
	}

	@Override
	public Element generateXML() {
		Element e = new Element("action");
		
		e.setAttribute("parentTag", parentTag);
		e.setAttribute("parentComponent", parentComponentName);
		
		for (int i = 1; i < args.size(); i++) {
			Element arg = new Element("argument");
			
			arg.setAttribute("value", originalValue);
			arg.addContent(featureList.get(0).generateXMLElement());
			
			e.addContent(arg);
		}
		
		return e;
	}
}
