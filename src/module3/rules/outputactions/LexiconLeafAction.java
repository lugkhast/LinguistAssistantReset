package module3.rules.outputactions;

import module3.rules.LeafMatcher;

import org.jdom2.Element;

import components.Leaf;

public class LexiconLeafAction extends OutputAction{
	private String parentTag, lexicon;
	
	public LexiconLeafAction(Element e) {
		super(e);
		
		parentTag = args.get(0).getAttributeValue("label");
		parentTag = args.get(1).getAttributeValue("lexicon");
		
	}
	
	public Leaf doAction(Leaf c, LeafMatcher m) {
		// insert lexicon here
		return c;
	}

	@Override
	public Element generateXML() {
		Element e = new Element("action");
		
		return e;
	}
}
