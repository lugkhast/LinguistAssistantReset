package module3.rules;

import components.Component;

public class UniMap {
	private String tag;
	private Component var;
	
	public UniMap(String s, Component p) {
		tag = s;
		var = p;
	}
	
	public String toString() {
		return "Tag: " + tag + ", Component: " + var.toGeneratedString();
	}
}
