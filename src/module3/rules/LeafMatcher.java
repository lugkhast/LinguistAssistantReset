package module3.rules;

import org.jdom2.Element;

import components.Leaf;
import features.FeatureList;

public class LeafMatcher extends Leaf{

	private String tag;
	private boolean optional;
	
	public LeafMatcher(Element componentElement) {
		super(componentElement);
		tag = componentElement.getAttributeValue("matcher");
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
			xmlElement.setAttribute("optional", "true");
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
}