package module3.rules;

import java.util.ArrayList;
import java.util.List;

import module3.rules.outputactions.OutputAction;
import module3.rules.outputactions.OutputActionReader;

import org.jdom2.Element;

import components.Component;
import features.Feature;

public class Rule {

	private String name;
	private PhraseMatcher input;
	private OutputList outputActions;
	
	private Rule(Element e) {
		this.name = e.getAttributeValue("name");
		System.out.println(name + " - name");
		
		this.input = (PhraseMatcher)Component.createMatcher(e.getChild("input").getChild("component"));
		
		Element output = e.getChild("output");
		List<Element> actionList = (List<Element>)output.getChildren();
		if (actionList == null)
			actionList = new ArrayList<Element>();
		
		this.outputActions = new OutputList();
		for(Element child: actionList)
			outputActions.addChild(new OutputAction(child));
		
	}

	private Rule(String ruleName) {
		this.name = ruleName;		
		input = new PhraseMatcher("cl");
		outputActions = new OutputList();
	}

	// use to make a new rule
	public static Rule createInstance(String ruleName){
		return new Rule(ruleName);
	}
	
	// new rule from xml
	public static Rule createInstance(Element e){
		Rule rule = new Rule(e);
			
		return rule;
	}
	
	public Component getInput() {
		return input;
	}
	
	public String toString() {
		String m = "Rule:\n";
		
		m += "name: " + name + "\n";
		m += "input: " + input + "\n";
		m += "output:\n" + outputActions;
		
		return m + "\n";
	}

	public Element generateXMLElement() {
		Element xmlElement = new Element("rule");
		xmlElement.setAttribute("name", name);
		
		Element inputElement = new Element("input");
		input.addAdditionalXMLContent(inputElement);
		
		Element outputElement = new Element("output");
		for (OutputAction o : outputActions.getChildren())
			outputElement.addContent(o.generateXML());
		
		xmlElement.addContent(inputElement);
		xmlElement.addContent(outputElement);
		
		
		return xmlElement;
	}

	public String getName() {
		return name;
	}
	
	public ArrayList<UniMap> unify(Component constit, Component pattern) {
		// returns null if false
		// a list if true (DUH)
		System.out.println("Comparing " + constit.getName() + " " + pattern.getName());
		if (!constit.getName().equals(pattern.getName())) {
			return null;
		}
		
		for (Feature f : pattern.getFeatureList().getFeatureList()) {
			Feature m = constit.getFeature(f.getName());
			System.out.println("Comparing features " + f.getValue() + " " + m.getValue());
			if (m == null) {
				return null;
			}
			if (!f.getValue().equals(m.getValue())) {
				return null;
			}
		}
		
		ArrayList<UniMap> results = new ArrayList<UniMap>();
		
		if (!pattern.isLeaf()) {
			for (Component subPattern : pattern.getChildren().getChildren()) {
				boolean match = false;
				
				for (Component subConstit : constit.getChildren().getChildren()) {
					ArrayList<UniMap> subResult = unify(subConstit, subPattern);
					if (subResult != null) {
						results.addAll(subResult);
						match = true;
					}
				}
				
				if (!match) {
					return null;
				}
			}
			
			if (((PhraseMatcher)pattern).getTag() != null)
				results.add(new UniMap(((PhraseMatcher)pattern).getTag(), constit));
			// pattern matches and is a phrase
		}
		else {
			// no need to check for subconstits because it is a leaf
			// it matches features
			if (((LeafMatcher)pattern).getTag() != null)
				results.add(new UniMap(((LeafMatcher)pattern).getTag(), constit));
			
		}
		
		return results;
	}
	
	public boolean apply(Component constit) {
		ArrayList<UniMap> mapList = unify(constit, this.input);
		
		if (mapList == null)
			return false;
		
		for (UniMap mapping : mapList) {
			for (OutputAction action : outputActions.getChildren()) {
				if (action.getTag().equals(mapping.getTag())) {
					OutputActionReader.DoOutputAction(mapping.getVar(), action);
				}
			}
		}
		return true;
	}
}
