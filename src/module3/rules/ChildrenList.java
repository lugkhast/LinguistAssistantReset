package module3.rules;

import java.util.ArrayList;

public class ChildrenList {
	ArrayList<ComponentMatcher> children;
	
	public ChildrenList(ArrayList<ComponentMatcher> children){
		this.children = children;
		if(children == null)
			this.children = new ArrayList<ComponentMatcher>();
	}
	
	public void addChild(ComponentMatcher child){
		if(child!=null)
			children.add(child);
	}
	
	public void addChild(int index, ComponentMatcher child){
		if(child!=null)
			children.add(index, child);
	}
	
	public ArrayList<ComponentMatcher> getChildren(){
		return children;
	}
	
	public void removeChild(ComponentMatcher child){
		if(child != null)
			children.remove(child);
	}
}