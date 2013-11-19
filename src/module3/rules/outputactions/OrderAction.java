package module3.rules.outputactions;

import module3.rules.PhraseMatcher;

import org.jdom2.Element;

import components.Phrase;

public class OrderAction extends OutputAction{
	private String parentTag, orderString;
	
	public OrderAction(Element e) {
		super(e);
		
		parentTag = args.get(0).getAttributeValue("parentTag");
		orderString = args.get(0).getAttributeValue("order");
	}
	
	public Phrase doAction(Phrase c, PhraseMatcher m) {
		if (type.equals("order")) {
			// delete targetTag under parentTag
		}
		
		return c;
	}

	@Override
	public Element generateXML() {
		Element e = new Element("action");
		
		e.setAttribute("target", "other");
		e.setAttribute("type", type);
		
		Element arg1 = new Element("argument");
		
		arg1.setAttribute("parentTag", parentTag);
		arg1.setAttribute("orderString", orderString);
		
		e.addContent(arg1);
		
		return e;
	}
}
