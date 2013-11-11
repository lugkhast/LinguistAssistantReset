package module3.rules;

import java.util.List;
import java.util.ArrayList;

import org.jdom2.Element;

public class OutputAction {
	
	private String target, type;
	private List<Element> args;
	
	public OutputAction(Element e) {
		this.target = e.getAttributeValue("target");
		this.type = e.getAttributeValue("type");
		
		args = (List<Element>)e.getChildren("argument");
	}
	
	public String toString() {
		return "*****\ntarget: " + target + "\ntype: " + type + "\nargs length: " + args.size() + "\n*****\n";
	}
}
