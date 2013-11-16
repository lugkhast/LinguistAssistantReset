package view.grammardevelopment;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

public class RulesTreePanel extends JScrollPane{
	JTree tree;
	DefaultMutableTreeNode topNode;
	
	//supposed to take the parameters rules set and add to tree
	public RulesTreePanel()
	{
		topNode = new DefaultMutableTreeNode("Rules");
		initializeRules();
		
		tree = new JTree(topNode);
	    tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	    
	    tree.addTreeSelectionListener(new TreeListener());

		setViewportView(tree);
		initializeRules();
	}
	

	public void initializeRules()
	{
		DefaultMutableTreeNode groupNode;
		DefaultMutableTreeNode ruleNode;
		
		groupNode = new DefaultMutableTreeNode("GROUP 1");
		topNode.add(groupNode);
		
		ruleNode = new DefaultMutableTreeNode("rule 1");
		groupNode.add(ruleNode);
		ruleNode = new DefaultMutableTreeNode("rule 2");
		groupNode.add(ruleNode);
		
		groupNode = new DefaultMutableTreeNode("GROUP 2");
		topNode.add(groupNode);
		
		ruleNode = new DefaultMutableTreeNode("rule 1");
		groupNode.add(ruleNode);
		ruleNode = new DefaultMutableTreeNode("rule 2");
		groupNode.add(ruleNode);
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
				 JFrame ruleWindow = new JFrame("Rule Type Blah");
				 ruleWindow.setSize(new Dimension(100,100));
				 ruleWindow.setVisible(true);
			 }

			
		}
	}
}