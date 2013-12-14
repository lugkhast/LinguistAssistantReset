package module3.rules;

import java.util.ArrayList;

import managers.XMLManager;

import org.jdom2.Element;

public class RuleTree {
	
	String path;
	
	private String setName, comments;
	private ArrayList<Rule> ruleList;
	private ArrayList<RuleTree> treeList;
	
	public RuleTree(String fileName, String name, String c, ArrayList<Rule> list){
		this.path = "InputXML/Rules/" + fileName;
		
		this.setName = name;
		this.comments = c;
		this.ruleList = list;
		if(ruleList == null)
			this.ruleList = new ArrayList<Rule>();
		
		//temporary
		this.treeList = new ArrayList<RuleTree>();
	}
	
	public void addTreeChild(RuleTree tree)
	{
		treeList.add(tree);
	}
	
	public void addChild(Rule rule){
		if(rule != null)
			ruleList.add(rule);
		
		saveToXML();
	}
	
	public void addChild(int index, Rule rule){
		if(rule != null)
			ruleList.add(index, rule);
		
		saveToXML();
	}
	
	public ArrayList<Rule> getChildren(){
		return ruleList;
	}
	
	public void removeChild(Rule rule){
		if(rule != null)
			ruleList.remove(rule);
		
		saveToXML();
	}
	
	public String getName()
	{
		return setName;
	}
	
	public String toString() {
		String m = "name: " + setName;
		
		m += "\ncomments: " + comments;
		m += "\nrulecount : " + ruleList.size() + "\n";
		
		for (Rule r : ruleList)
			m += r;
		
		return m;
	}
	
	public String getComment()
	{
		return comments;
	}
	
	public void saveToXML()
	{
		XMLManager.getInstance().writeToXML(path, generateXMLElement());
	}
	
	public Element generateXMLElement() {
		Element xmlElement = new Element("ruleset");
		xmlElement.setAttribute("name", setName);
		xmlElement.setAttribute("comments", comments);
		
		for (Rule r : ruleList)
			xmlElement.addContent(r.generateXMLElement());
		return xmlElement;
	}
}