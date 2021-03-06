package module3.rules;

import java.util.ArrayList;

import managers.FeatureManager;

import org.jdom2.Element;

import features.Feature;

public class PhraseMatcher extends ComponentMatcher {
	
	private ChildrenList children;
	
	public PhraseMatcher(Element e) {
		super(e);
		this.children = new ChildrenList(new ArrayList<ComponentMatcher>());
	}

	public PhraseMatcher(String componentName) {
		super(componentName);
		this.children = new ChildrenList(new ArrayList<ComponentMatcher>());
	}

	public void addChild(ComponentMatcher child){
		children.addChild(child);
	}

	public String getStringForPrinting() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(name+"(");
		for(ComponentMatcher child: children.getChildren())
			stringBuilder.append(child.toString());
		stringBuilder.append(")");
		return stringBuilder.toString();
	}
	
	protected String getFeatures(boolean includeDefaults, String nextLineToken){
		StringBuilder featureString = new StringBuilder();
		featureString.append("*****Features");
		if(!includeDefaults)
			featureString.append("(Non-default)");
		
		featureString.append("*****");
		featureString.append(nextLineToken);
		
		for(Feature feature: featureList.getFeatureList()){	
			if(includeDefaults || !FeatureManager.isFeatureDefault(name, feature.getName(), feature.getValue())){
				featureString.append(feature.getName()+" = "+feature.getValue());
				if(!feature.isStandard())
					featureString.append(" [Custom Feature]");
				featureString.append(nextLineToken);
			}
		}
		featureString.append(nextLineToken);
		return featureString.toString();
	}
	
	public boolean isLeaf() {
		return false;
	}

	public ChildrenList getChildren() {
		return children;
	}

	public String toString() {
		return getStringForPrinting();
	}

	@Override
	public String toGeneratedString() {
		return toString();
	}
	
	@Override
	protected void addAdditionalXMLContent(Element parentElement) {
		for(ComponentMatcher child: children.getChildren())
			parentElement.addContent(child.generateXMLElement());
	}

	@Override
	public String toLexiconSentence() {
		StringBuilder sb = new StringBuilder();
		int numChildren = children.getChildren().size();
		ArrayList<ComponentMatcher> childList = children.getChildren();
		
		for(int i=0; i < numChildren; i++){
			ComponentMatcher child = children.getChildren().get(i);
			String childSentence = child.toLexiconSentence();
			if(!childSentence.isEmpty()){
				sb.append(child.toLexiconSentence());
				if(i < numChildren - 1 && !childList.get(i+1).toLexiconSentence().equals("."))
					sb.append(" ");
			}
		}
		return sb.toString();
	}
	
	@Override
	public String toConceptSentence() {
		StringBuilder sb = new StringBuilder();
		int numChildren = children.getChildren().size();
		ArrayList<ComponentMatcher> childList = children.getChildren();
		
		for(int i=0; i < numChildren; i++){
			ComponentMatcher child = children.getChildren().get(i);
			String childSentence = child.toConceptSentence();
			if(!childSentence.isEmpty()){
				sb.append(childSentence);
				if(i < numChildren - 1 && !childList.get(i+1).toConceptSentence().equals("."))
					sb.append(" ");
			}
		}
		return sb.toString();
	}

	public void insertChildAt(int index, ComponentMatcher child){
		children.addChild(index, child);
		System.out.println(name+" now has "+children.getChildren().size()+" children");
	}

	public void removeChild(ComponentMatcher child){
		children.removeChild(child);
	}
}
