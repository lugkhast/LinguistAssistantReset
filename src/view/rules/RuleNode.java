package view.rules;

import javax.swing.tree.DefaultMutableTreeNode;

import module3.rules.Rule;
import module3.rules.RuleTree;

public class RuleNode extends DefaultMutableTreeNode{

	Rule rule;
	RuleTree tree;
	boolean isRule;
	
	public RuleNode(Rule rule)
	{
		isRule = true;
		this.rule = rule;
		setNodeText(rule.getName());
	}
	
	public RuleNode(RuleTree tree)
	{
		isRule = false;
		this.tree = tree;
		setNodeText(tree.getName());
	}
	
	public Rule getRule()
	{
		return rule;
	}
	
	public RuleTree getTree()
	{
		return tree;
	}
	
	public void setNodeText(String ruleName)
	{
		setUserObject(ruleName);
	}
	
	public boolean isRule()
	{
		return isRule;
	}
}
