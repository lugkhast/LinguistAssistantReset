package components;

import java.util.ArrayList;
import java.util.List;

import managers.ComponentManager;
import managers.FeatureManager;
import managers.SemanticsManager;

import org.jdom2.Element;

import features.Feature;
import features.FeatureList;

public abstract class Component {
	
	public static final String ATTRIBUTE_NAME = "name";
	
	protected ComponentInfo info;
	protected String name;
	protected FeatureList featureList;
	
	//Constructor
	protected Component(Element componentElement){
		
		//Set the features defined in the XML
		this.name = componentElement.getAttributeValue(ATTRIBUTE_NAME);
		this.featureList = new FeatureList(FeatureManager.getDefaultFeatures(name));
		
		//get info (may be null for now as input xml may contain user defined phrases)
		info = ComponentManager.getInstance().getComponentInfo(name);
		
		//override defaults
		ArrayList<Feature> specifiedFeatures = SemanticsManager.getSpecifiedFeatures(componentElement);
		for(Feature feature: specifiedFeatures)
			setFeature(feature);
	}
		
	protected Component(String componentName){
		this.name = componentName;
		this.featureList = new FeatureList(FeatureManager.getDefaultFeatures(componentName));
	}
	
	//this creator creates a component with all default features. this was made for adding new components in the editor.
	public static Component createInstance(String componentName){
		Component newComponent;
		
		if(ComponentManager.getInstance().isLeaf(componentName))
			newComponent = new Leaf(componentName);
		else
			newComponent = new Phrase(componentName);
		
		return newComponent;
	}
	
	//this creator is used for creating components from a loaded XML file
	public static Component createInstance(Element e){
		String componentName = e.getAttributeValue(ATTRIBUTE_NAME);
		
		if(ComponentManager.getInstance().isLeaf(componentName)){
			return new Leaf(e);
		}
		else{
			List<Element> childrenElements = (List<Element>)e.getChildren(SemanticsManager.COMPONENT);
			if(childrenElements == null)
				childrenElements = new ArrayList<Element>();

			Phrase phrase = new Phrase(e);
			for(Element child: childrenElements)
				phrase.addChild(createInstance(child));
			
			return phrase;
		}
	}
		
	//Abstract Methods
	protected abstract String getFeatures(boolean includeDefaults, String nextLineToken);
	
	protected abstract void addAdditionalXMLContent(Element parentElement);
	
	public abstract String toString();

	public abstract String toGeneratedString();
	
	public abstract String toConceptSentence();
	
	public abstract String toLexiconSentence();
	
	public abstract boolean isLeaf();
	
	public abstract Children getChildren();
	
	//Getters

	public String getDescription(){
		if(info != null)
			return info.getDescription();
		return name;
	}
	
	public String getName(){
		return name;
	}

	public String getFeaturesInHTML(boolean includeDefaults){
		return getFeatures(includeDefaults, "<br>");
	}
	
	public String getFeaturesInString(boolean includeDefaults) {
		return getFeatures(includeDefaults, "\n");
	}
	
	public Feature getFeature(String featureName){
		if(featureList == null)
			return null;
		
		return featureList.getFeature(featureName);
	}

	//Setters
	public void setFeature(Feature newFeature){
		featureList.setFeature(newFeature);
	}
		
	//Methods related to generating XML file
	public Element generateXMLElement(){
		Element xmlElement = new Element("component");
		xmlElement.setAttribute(ATTRIBUTE_NAME, name);
		if(featureList != null){
			Element featuresElement = featureList.generateXMLElementForComponent(name);
			if(featuresElement != null)
				xmlElement.addContent(featuresElement);
		}
		addAdditionalXMLContent(xmlElement);
		return xmlElement;
	}
		
}