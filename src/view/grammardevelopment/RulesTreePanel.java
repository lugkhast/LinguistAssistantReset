package view.grammardevelopment;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import managers.RulesManager;
import module3.rules.Rule;
import module3.rules.RuleTree;

import view.rules.NewFolderWindow;
import view.rules.NewRuleWindow;
import view.rules.RuleFrame;
import view.rules.RuleNode;

public class RulesTreePanel extends JPanel{
	JTree tree;
	ArrayList <RuleTree> ruleTrees;
	RuleTree selectedRuleTree;
	JButton addFolder;
	JButton addRule;
	JButton delete;
	JScrollPane scrollPane;
	RuleNode selectedNode = null;
	DefaultMutableTreeNode topNode;
	
	//supposed to take the parameters rules set and add to tree
	public RulesTreePanel()
	{
		loadRules();
		
		scrollPane = new JScrollPane();
			
		JPanel buttonPanel = new JPanel();
		addFolder = new JButton("Add Folder");
		addRule = new JButton("Add Rule");
		delete = new JButton("Delete");
		
		buttonPanel.add(addFolder);
		buttonPanel.add(addRule);
		buttonPanel.add(delete);
		
		add(scrollPane);
		add(buttonPanel);
	    addButtonListeners();

		initializeTree();
		
		scrollPane.setViewportView(tree);
//		scrollPane.getViewport().setPreferredSize(new Dimension(530,170));
		
		setBorder(new TitledBorder("Rules"));
	}
	
	public void loadRules()
	{
		ruleTrees = new ArrayList<RuleTree>();
		
		File folder = new File("InputXML/Rules");
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			RuleTree ruleTree = RulesManager.initializeRules(file);
			ruleTrees.add(ruleTree);
		}
	}
	
	public void setPanelSize()
	{
		scrollPane.getViewport().setPreferredSize(new Dimension(500,180));
	}
	
//	public void setRuleTree(RuleTree rTree)
//	{
//		ruleTree = rTree;
//	}

	public void initializeTree()
	{
		
		ArrayList<RuleNode> setNodes = new ArrayList<RuleNode>();
		
		for (RuleTree ruleTree : ruleTrees)
		{
			RuleNode setNode = new RuleNode(ruleTree);
			setNode.setParent(topNode);
			//add nodes
			for (int i = 0; i<ruleTree.getChildren().size(); i++)
			{
				RuleNode ruleNode = new RuleNode(ruleTree.getChildren().get(i));
	
				setNode.add(ruleNode);
				System.out.println(ruleNode.getRule().getName());
			}
			
			setNodes.add(setNode);
		}

		topNode = new DefaultMutableTreeNode("Rules");
		for (RuleNode setNode : setNodes)
			topNode.add(setNode);
		tree = new JTree(topNode);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addMouseListener(new TreeListener());
	    
	}
	
	public void addRuleToTree(Rule rule)
	{
	//add to gui
		RuleNode newRule = new RuleNode(rule);
		RuleNode parent = null;
		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		
		//--if rule is selected, add to tree of that rule
		if (selectedNode!=null && selectedNode.isRule())
		{
			parent = (RuleNode) selectedNode.getParent();
			parent.add(newRule);
		}
		
		//--if folder is selected
		if (selectedNode!=null && !selectedNode.isRule())
		{
			parent = selectedNode;
			parent.add(newRule);
		}
		
		model.reload();
	//add internally
		parent.getTree().addChild(rule);
		selectedNode = null;
		
	}
	
	public void addFolderToTree(RuleTree rTree)
	{
	//add to gui
		RuleNode newTree = new RuleNode(rTree);
		RuleNode parent = null;
		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		
		//--if rule is selected, add to tree of that rule
		if (selectedNode!= null && selectedNode.isRule())
		{
			parent = (RuleNode) selectedNode.getParent();
			parent.add(newTree);
		}
		
		//--if folder is selected
		if (selectedNode!=null && !selectedNode.isRule())
		{
			parent = selectedNode;
			parent.add(newTree);
		}
		
		if (selectedNode == null)
		{
			topNode.add(newTree);
		}
		
		model.reload();
	//add internally 
		//TO ADD: add new folder to main node
		parent.getTree().addTreeChild(rTree);
	}
	
	public void addButtonListeners()
	{
		addFolder.addActionListener(new AddFolderButtonListener(this));
		addRule.addActionListener(new AddRuleButtonListener(this));
		delete.addActionListener(new DeleteButtonListener(this));
	}
	
	class AddFolderButtonListener implements ActionListener
	{
		RulesTreePanel panel;
		public AddFolderButtonListener(RulesTreePanel panel)
		{
			this.panel = panel;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new NewFolderWindow(panel);
		}	
	}
	
	class AddRuleButtonListener implements ActionListener
	{
		RulesTreePanel panel;
		public AddRuleButtonListener(RulesTreePanel panel)
		{
			this.panel = panel;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (selectedNode!=null)
				new NewRuleWindow(panel);
			else
			{
				JOptionPane.showMessageDialog(null,
							"Select a rule or a folder",
							"Nothing is selected",
							JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	class DeleteButtonListener implements ActionListener
	{
		RulesTreePanel panel;
		public DeleteButtonListener(RulesTreePanel panel)
		{
			this.panel = panel;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			
			DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
			if (selectedNode.getParent() != null)
			{
				int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this?",
						"Delete Confirmation", JOptionPane.DEFAULT_OPTION);

		        if (option == JOptionPane.OK_OPTION) {
		        	//delete from ui
					model.removeNodeFromParent(selectedNode);
					//delete internally
					if (selectedNode.isRule())
					{
						selectedRuleTree.removeChild(selectedNode.getRule());
					}
					
					selectedNode = null;
		        }
				
			}
			else
			{
			
				JOptionPane.showMessageDialog(null,
						"Nothing is selected",
						"Nothing is selected",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	class TreeListener implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent e) {

			// TODO Auto-generated method stub
			try
			{
				selectedNode = (RuleNode) tree.getLastSelectedPathComponent();
				RuleNode parent = (RuleNode) selectedNode.getParent();
				selectedRuleTree = parent.getTree();
				
				/* if nothing is selected */ 
				 if (selectedNode == null)
					 return;
				
				 //if a rule is clicked
				 
				/* retrieve the node that was selected */ 
	//			 Object nodeInfo = selectedRule.getUserObject();
				 if (e.getClickCount() == 2)
				 {
					 if (selectedNode!=null && selectedNode.isRule()) 
					 {
						 RuleFrame ruleFrame = new RuleFrame(selectedNode.getRule(), selectedRuleTree.getComment());
					 }
				 }
			}catch(Exception ex){
				selectedNode = null;
				tree.setSelectionPath(null);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}

}