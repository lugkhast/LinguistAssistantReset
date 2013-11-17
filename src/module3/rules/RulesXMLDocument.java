package module3.rules;

import java.io.File;
import java.util.ArrayList;

import org.jdom2.Element;

public class RulesXMLDocument {

	private String name;
	private String comments;
	private File xmlFile;
	
	private ArrayList<Rule> sentences;
	
	public RulesXMLDocument(File xmlFile, String topicName, String comments, ArrayList<Rule> sentences){
		this.xmlFile = xmlFile;
		
		if(topicName == null)
			name = "";
		if(comments == null)
			comments = "";
		if(sentences == null)
			sentences = new ArrayList<Rule>();
		
		this.name = topicName;
		this.sentences = sentences;
		this.comments = comments;
	}
	
	//Getters
	public String getName() {
		return name;
	}
	
	public String getComments(){
		return comments;
	}
	
	public File getXmlFile(){
		return xmlFile;
	}
	
	public void setXMLFile(File file){
		xmlFile = file;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setOtherInfo(String otherInfo){
		this.comments = otherInfo;
	}
	
	public ArrayList<Rule> getRules(){
		return (ArrayList<Rule>)sentences.clone();
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder(name);
		if(comments != null)
			sb.append(" / "+comments);
		return sb.toString();
	}
	
	//Setters
	public void addRuleAt(int index, Rule r){
		sentences.add(index, r);
	}
	
	public void removeSentence(Rule r){
		if(r != null)
			sentences.remove(r);
	}
	
	//Complex getters
	public Element generateXMLCopy(){
		
		Element rootElement = new Element("ruleset");
		
		setElementAttribute(rootElement, "name", name);
		
		if(!comments.isEmpty()){
			Element commentsElement = new Element("comments");
			commentsElement.setText(comments);
			rootElement.addContent(commentsElement);
		}
		
		
		for(Rule r: sentences)
			rootElement.addContent(r.generateXMLElement());
		
		return rootElement;
	}

	private void setElementAttribute(Element element, String attributeName, String attributeValue){
		if(attributeValue != null && !attributeValue.isEmpty() && attributeName != null && !attributeName.isEmpty())
			element.setAttribute(attributeName, attributeValue);
	}
}