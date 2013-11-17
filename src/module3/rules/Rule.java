package module3.rules;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

import components.Component;

public class Rule {

	private String name;
	private PhraseMatcher input;
	private OutputList outputActions;
	
	private Rule(Element e) {
		this.name = e.getAttributeValue("name");
		
		this.input = (PhraseMatcher)Component.createMatcher(e.getChild("input").getChild("component"));
		
		Element output = e.getChild("output");
		List<Element> actionList = (List<Element>)output.getChildren();
		if (actionList == null)
			actionList = new ArrayList<Element>();
		
		this.outputActions = new OutputList();
		//for(Element child: actionList)
			//outputActions.addChild(new OutputAction(child));
		
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
		
		
		input.addAdditionalXMLContent(xmlElement);
		return xmlElement;
	}
}
