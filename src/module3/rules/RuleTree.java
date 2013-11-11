package module3.rules;

import java.util.ArrayList;

public class RuleTree {
	private String setName, comments;
	private ArrayList<Rule> ruleList;
	
	public RuleTree(String name, String c, ArrayList<Rule> list){
		this.setName = name;
		this.comments = c;
		this.ruleList = list;
		if(ruleList == null)
			this.ruleList = new ArrayList<Rule>();
	}
	
	public void addChild(Rule rule){
		if(rule != null)
			ruleList.add(rule);
	}
	
	public void addChild(int index, Rule rule){
		if(rule != null)
			ruleList.add(index, rule);
	}
	
	public ArrayList<Rule> getChildren(){
		return ruleList;
	}
	
	public void removeChild(Rule rule){
		if(rule != null)
			ruleList.remove(rule);
	}
	
	public String toString() {
		String m = "name: " + setName;
		
		m += "\ncomments: " + comments;
		m += "\nrulecount : " + ruleList.size() + "\n";
		
		for (Rule r : ruleList)
			m += r;
		
		return m;
	}
}