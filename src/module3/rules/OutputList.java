package module3.rules;

import java.util.ArrayList;

public class OutputList {
	private ArrayList<OutputAction> rules;
	
	public OutputList(){
		this.rules = new ArrayList<OutputAction>();
	}

	public OutputList (ArrayList<OutputAction> r){
		this.rules = r;
		if(rules == null)
			this.rules = new ArrayList<OutputAction>();
	}
	
	public void addChild(OutputAction r){
		if(r!=null)
			rules.add(r);
	}
	
	public void addChild(int index, OutputAction r){
		if(r!=null)
			rules.add(index, r);
	}
	
	public ArrayList<OutputAction> getChildren(){
		return rules;
	}
	
	public void removeChild(OutputAction r){
		if(r != null)
			rules.remove(r);
	}
	
	public String toString() {
		String m = "*******\n";
		for (OutputAction a : rules)
			m += a + "\n";
		
		return m + "\n*******\n";
	}
}
