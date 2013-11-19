package module3.rules.outputactions;

import module3.rules.LeafMatcher;

import org.jdom2.Element;

import components.Leaf;

public abstract class LeafAction extends OutputAction{
	private String parentTag;
	
	public LeafAction(Element e) {
		super(e);
		
		parentTag = args.get(0).getAttributeValue("label");
	}
	
	public abstract Leaf doAction(Leaf c, LeafMatcher m);
}
