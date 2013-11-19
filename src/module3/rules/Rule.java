package module3.rules;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

public class Rule {

	private RuleTree tree;
	private String name;
	private ComponentMatcher input;
	private OutputList outputActions;
	
	private Rule(Element e) {
		this.name = e.getAttributeValue("name");
		
		this.input = ComponentMatcher.createInstance(e.getChild("input").getChild("component"));
		
		Element output = e.getChild("output");
		List<Element> actionList = (List<Element>)output.getChildren();
		if (actionList == null)
			actionList = new ArrayList<Element>();
		
		this.outputActions = new OutputList();
		for(Element child: actionList)
			outputActions.addChild(new OutputAction(child));
		
	}

	public Rule(String ruleName) {
		this.name = ruleName;		
		input = ComponentMatcher.createInstance("cl");
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
	
	public String getName()
	{
		return name;
	}
	
	public String toString() {
		String m = "***********\n";
		
		m += "name: " + name + "\n";
		m += "input: " + input + "\n";
		m += "output:\n" + outputActions;
		
		return m + "***********\n";
	}
}
