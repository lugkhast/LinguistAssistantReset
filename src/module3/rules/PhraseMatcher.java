package module3.rules;

import org.jdom2.Element;

import components.Component;
import components.Phrase;
import features.FeatureList;

public class PhraseMatcher extends Phrase {
	
	private String tag;
	
	public PhraseMatcher(Element e) {
		super(e);
		
		tag = e.getAttributeValue("matcher");
	}

	public PhraseMatcher(String componentName) {
		super(componentName);
		tag = "";
	}

	public String getStringForPrinting() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(name+"/"+tag+"(");
		for(Component child: children.getChildren())
			stringBuilder.append(child.toString());
		stringBuilder.append(")");
		
		stringBuilder.append(getFeatures(true, "\n"));
		return stringBuilder.toString();
	}
	
	public void addAdditionalXMLContent(Element e) { 
		for(Component child: children.getChildren())
			e.addContent(child.generateXMLElement());
		e.setAttribute("matcher", tag);
	}

	public void setTag(String s) {
		tag = s;
	}

	public String getTag() {
		return tag;
	}
	
	public FeatureList getFeatureList() {
		return featureList;
	}
}