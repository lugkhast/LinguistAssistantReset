package view.grammardevelopment;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import managers.RulesManager;
import module3.rules.Rule;
import module3.rules.RuleTree;

import view.rules.RuleFrame;

public class RulesTreePanel extends JScrollPane{
	JTree tree;
	DefaultMutableTreeNode topNode;
	RuleTree ruleTree;
	//supposed to take the parameters rules set and add to tree
	public RulesTreePanel()
	{
		RulesManager rulesManager;
		rulesManager = new RulesManager();
		ruleTree = rulesManager.initializeRules(new File("InputXML/Rules/Rulesets.xml"));
		
		initializeTree();

		setViewportView(tree);
	}

	public void initializeTree()
	{
		DefaultMutableTreeNode ruleNode;
		DefaultMutableTreeNode setNode;
		topNode = new DefaultMutableTreeNode("Rules");
		setNode = new DefaultMutableTreeNode(ruleTree.getName());
		topNode.add(setNode);
		//add nodes
		for (int i = 0; i<ruleTree.getChildren().size(); i++)
		{
			Rule rule = ruleTree.getChildren().get(i);
			ruleNode = new DefaultMutableTreeNode(rule.getName());
			setNode.add(ruleNode);
		}
		tree = new JTree(topNode);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	    
	    tree.addTreeSelectionListener(new TreeListener());
	}
	
	class TreeListener implements TreeSelectionListener
	{
		@Override
		public void valueChanged(TreeSelectionEvent arg0) {
	        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                    tree.getLastSelectedPathComponent();

			/* if nothing is selected */ 
			 if (node == null)
				 return;
			
			/* retrieve the node that was selected */ 
			 Object nodeInfo = node.getUserObject();
			 if (node.isLeaf()) {
				 //must create new class for this
				 RuleFrame ruleFrame = new RuleFrame(); 
			 }

			
		}
	}
}