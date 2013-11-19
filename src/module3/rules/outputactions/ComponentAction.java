package module3.rules.outputactions;

import module3.rules.PhraseMatcher;

import org.jdom2.Element;

import components.Component;
import components.Phrase;

public class ComponentAction extends OutputAction{
	private String parentTag, targetTag, position;
	private Phrase toAdd;
	
	public ComponentAction(Element e) {
		super(e);
		
		parentTag = args.get(0).getAttributeValue("parentTag");
		targetTag = args.get(0).getAttributeValue("targetTag");
		position = args.get(0).getAttributeValue("position");
		
		if (args.size() == 2)
			toAdd = (Phrase) Phrase.createInstance(args.get(1).getChild("component"));
	}
	
	public Phrase doAction(Phrase c, PhraseMatcher m) {
		if (type.equals("delete")) {
			// delete targetTag under parentTag
		}
		else if (type.equals("move")) {
			// move targetTag to position under parentTag
		}
		else if (type.equals("copy")) {
			// copy targetTag to position under parentTag
		}
		else if (type.equals("add")) {
			// add toAdd under parentTag at position
		}
		return c;
	}

	@Override
	public Element generateXML() {
		Element e = new Element("action");
		
		e.setAttribute("target", "component");
		e.setAttribute("type", type);
		
		Element arg1 = new Element("argument"), arg2 = new Element("argument");
		
		arg1.setAttribute("parentTag", parentTag);
		arg1.setAttribute("targetTag", targetTag);
		arg1.setAttribute("position", position);
		
		if (toAdd != null)
			arg2.addContent(toAdd.generateXMLElement());
		
		e.addContent(arg1);
		e.addContent(arg2);
		
		return e;
	}
}
