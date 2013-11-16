package managers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import module3.rules.Rule;
import module3.rules.RuleTree;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class RulesManager {
	
	public static RuleTree initializeRules(File xmlFile){
		SAXBuilder builder = new SAXBuilder();
		
		String rulesetName = "";
		
		try{
			Document document = (Document) builder.build(xmlFile);
			Element verseNode = document.getRootElement();
			
			//get name and category
			rulesetName = verseNode.getAttributeValue("name");
			
			//comments
			Element commentNode = verseNode.getChild("comments");
			String comments = "";
			if(commentNode != null)
				comments = commentNode.getText().trim();
			
			//get rules
			List<Element> nodes = (List<Element>) verseNode.getChildren("rule");
			ArrayList<Rule> rulesList = new ArrayList<Rule>();
			for(Element node: nodes)
				rulesList.add(Rule.createInstance(node));
			
			return new RuleTree(rulesetName, comments, rulesList);
		}catch(Exception e){e.printStackTrace();}
		
		return null;
	}

}
