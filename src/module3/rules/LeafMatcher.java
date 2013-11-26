package module3.rules;

import lexicon.Lexicon;

import org.jdom2.Element;

import components.Leaf;
import features.FeatureList;

public class LeafMatcher extends Leaf {
	
	private String tag;
	
	public LeafMatcher(Element componentElement) {
		super(componentElement);
	}

	public LeafMatcher(String componentName) {
		super(componentName);
		
		tag = "";
	}
	
	public String toString() {
		StringBuilder string  = new StringBuilder();
		string.append(name);
		string.append(": ");
		string.append(concept);
		string.append("-");
		string.append(lexicalSense);
		string.append(getFeatures(true, "\n"));
		return string.toString();
	}
	
	public void addAdditionalXMLContent(Element e) { 
		e.setAttribute(ATTRIBUTE_CONCEPT, concept);
		e.setAttribute(ATTRIBUTE_LEXICAL_SENSE, lexicalSense);
		e.setAttribute("matcher", tag);
		Lexicon lexicon = getFirstMappedLexicon();
		if(lexicon!= null && !lexicon.getName().isEmpty())
			e.setAttribute(ATTRIBUTE_LEXICON, lexicon.getName());
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
