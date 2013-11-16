package module3.rules;

import java.util.ArrayList;
import java.util.List;

import managers.ComponentManager;
import managers.FeatureManager;
import managers.SemanticsManager;

import org.jdom2.Element;

import components.Component;
import components.ComponentInfo;
import features.Feature;
import features.FeatureList;

public abstract class ComponentMatcher {

	public static final String ATTRIBUTE_NAME = "name";
	public static final String MATCHER_NAME = "matcher";
	
	protected ComponentInfo info;
	protected String name;
	protected FeatureList featureList;
	protected String matcher;
	
	//Constructor
	protected ComponentMatcher (Element componentElement){
		
		//Set the features defined in the XML
		this.name = componentElement.getAttributeValue(ATTRIBUTE_NAME);
		this.featureList = new FeatureList(FeatureManager.getDefaultFeatures(name));
		this.matcher = componentElement.getAttributeValue(MATCHER_NAME);
		
		if(matcher != null)
			matcher = matcher.trim();
		else
			matcher = "";
		
		//get info (may be null for now as input xml may contain user defined phrases)
		info = ComponentManager.getInstance().getComponentInfo(name);
		
		//override defaults
		ArrayList<Feature> specifiedFeatures = SemanticsManager.getSpecifiedFeatures(componentElement);
		for(Feature feature: specifiedFeatures)
			setFeature(feature);
	}
	
	protected ComponentMatcher(String componentName){
		this.name = componentName;
		this.featureList = new FeatureList(FeatureManager.getDefaultFeatures(componentName));
	}
	
	//this creator creates a component with all default features. this was made for adding new components in the editor.
	public static ComponentMatcher createInstance(String componentName){
		ComponentMatcher newComponent;
		
		if(ComponentManager.getInstance().isLeaf(componentName))
			newComponent = new LeafMatcher(componentName);
		else
			newComponent = new PhraseMatcher(componentName);
		
		return newComponent;
	}
	
	//this creator is used for creating components from a loaded XML file
	public static ComponentMatcher createInstance(Element e){
		String componentName = e.getAttributeValue(ATTRIBUTE_NAME);
		
		if(ComponentManager.getInstance().isLeaf(componentName)){
			return new LeafMatcher(e);
		}
		else{
			List<Element> childrenElements = (List<Element>)e.getChildren("component");
			if(childrenElements == null)
				childrenElements = new ArrayList<Element>();
			
			PhraseMatcher phrase = new PhraseMatcher(e);
			for(Element child: childrenElements)
				phrase.addChild(createInstance(child));
			
			return phrase;
		}
	}

	protected abstract String getFeatures(boolean includeDefaults, String nextLineToken);
	
	protected abstract void addAdditionalXMLContent(Element parentElement);
	
	public abstract String toString();

	public abstract String toGeneratedString();
	
	public abstract String toConceptSentence();
	
	public abstract String toLexiconSentence();
	
	public abstract boolean isLeaf();
	
	public abstract ChildrenList getChildren();
	
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
