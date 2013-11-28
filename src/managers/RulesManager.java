package managers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import module3.rules.Rule;
import module3.rules.RuleTree;
import module3.rules.UniMap;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import components.Component;
import components.InputXMLDocument;

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
	
	public static void main(String args[]) {
		// get test components
		InputXMLDocument doc = SemanticsManager.readSemanticsDocumentFromFile(new File("inputxml/testfiles/TestComponent.xml"));
		ArrayList<Component> comp = doc.getClauses();
		// get test rules
		RuleTree rt = RulesManager.initializeRules(new File("inputxml/testfiles/TestRule.xml"));
		ArrayList<Rule> list = rt.getChildren();
		

		
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < comp.size(); j++) {
				ArrayList<UniMap> u = list.get(i).unify(comp.get(j), list.get(i).getInput());
				System.out.print(i + " - " + list.get(i).getName() + ", component " + j + " - ");
				if (u==null) {
					System.out.println("false");
					continue;
				}
				if (u.size() == 0) {
					System.out.println("true");
					continue;
				}
				
				System.out.println(u);
			}
		}
	}
}
