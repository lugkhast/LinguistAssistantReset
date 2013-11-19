package module3.rules.outputactions;

import java.util.List;

import module3.rules.PhraseMatcher;

import org.jdom2.Element;

import components.Component;

public abstract class OutputAction {
	
	protected String type;
	protected List<Element> args;
	
	public OutputAction(Element e) {
		this.type = e.getAttributeValue("type");
		
		args = (List<Element>)e.getChildren("argument");
	}
	
	public OutputAction() {
		type = "";
	}
	
	public String toString() {
		return "*****\ntype: " + type + "\nargs length: " + args.size() + "\n*****\n";
	}

	public abstract Element generateXML();
}