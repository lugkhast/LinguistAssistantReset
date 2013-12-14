package module3.rules.outputactions;

import java.util.List;

import module3.rules.PhraseMatcher;

import org.jdom2.Element;

import components.Component;

public class OutputAction {
	
	String type;
	private String tag;
	List<Element> args;
	
	public OutputAction(Element e) {
		this.type = e.getAttributeValue("name");
		this.tag = e.getAttributeValue("parentTag");
		
		args = (List<Element>)e.getChildren("argument");
	}
	
	public OutputAction() {
		type = "";
	}
	
	public String toString() {
		return "type - " + type;
	}

	public Element generateXML() {
		Element xmlElement = new Element("output");
		xmlElement.setAttribute("name", type);
		xmlElement.setAttribute("parentTag", tag);
		
		for (Element e : args) {
			xmlElement.addContent(e);
		}
		
		return xmlElement;

	}

	public String getType() {
		return type;
	}

	public String getTag() {
		return tag;
	}
}