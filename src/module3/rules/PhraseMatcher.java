package module3.rules;

import org.jdom2.Element;

import components.Component;
import components.Phrase;
import features.FeatureList;

public class PhraseMatcher extends Phrase {

	private String tag;
	private boolean optional;
	
	public PhraseMatcher(Element componentElement) {
		super(componentElement);
		tag = componentElement.getAttributeValue("matcher");
	}
	
	public PhraseMatcher(String componentName) {
		super(componentName);
		tag = "";
	}

	protected void setDefaults(String cName) {
		this.featureList = new FeatureList(null);
	}

	public String getTag(){
		return tag;
	}
	
	public void setTag(String tag){
		this.tag = tag;
	}

	public boolean isOptional(){
		return optional;
	}
	public void setOptional(boolean optional){
		this.optional = optional;
	}
	
	public Element generateXMLElement() {
		Element xmlElement = new Element("component");
		xmlElement.setAttribute(ATTRIBUTE_NAME, name);
		if(optional)
			xmlElement.setAttribute("optional", Boolean.toString(optional));
		if(tag != null)
			xmlElement.setAttribute("matcher", tag);
		if(featureList != null){
			Element featuresElement = featureList.generateXMLElementForComponent(name);
			if(featuresElement != null)
				xmlElement.addContent(featuresElement);
		}
		addAdditionalXMLContent(xmlElement);
		return xmlElement;
	}
	
	public void addAdditionalXMLContent(Element e) { 
		for(Component child: children.getChildren())
			e.addContent(child.generateXMLElement());
		if (tag != null)
			e.setAttribute("matcher", tag);
	}

}




/*
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
		stringBuilder.append(name+"/"+tag);
		/*
		stringBuilder.append(name+"/"+tag+"(");
		stringBuilder.append(name+"(");
		for(Component child: children.getChildren())
			stringBuilder.append(child.toString());
		stringBuilder.append(")");
		
		stringBuilder.append(getFeatures(true, "\n"));
		
		return stringBuilder.toString();
	}
	
	public void addAdditionalXMLContent(Element e) { 
		for(Component child: children.getChildren())
			e.addContent(child.generateXMLElement());
		if (tag != null)
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
*/