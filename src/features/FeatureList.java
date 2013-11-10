package features;

import java.util.ArrayList;

import lexicon.Lexicon;
import lexicon.LexiconList;
import managers.FeatureManager;

import org.jdom2.Element;

public class FeatureList {
	private ArrayList<Feature> featureList;
	
	public FeatureList(ArrayList<Feature> featureList){
		this.featureList = featureList;
		if(featureList == null)
			this.featureList = new ArrayList<Feature>();
	}
	
	public void setFeature(Feature newFeature){
		for(Feature feature: featureList){
			if(feature.getName().equals(newFeature.getName())){
				feature.setValue(newFeature.getValue());
				return;
			}
		}
		
		//New Feature is user-defined
		featureList.add(newFeature.getCopy());
	}
	
	public Feature getFeature(String featureName){
		for(Feature feature: featureList)
			if(feature.getName().equals(featureName))
				return feature;
		
		return null;
	}
	
	public ArrayList<Feature> getFeatureList(){
		return featureList;
	}

	public Element generateXMLElementForComponent(String componentName){
		
		Element featuresElement = new Element("features");
	
		for(Feature feature: featureList)
			if(!FeatureManager.isFeatureDefault(componentName, feature.getName(), feature.getValue()))
				featuresElement.addContent(feature.generateXMLElement());
			
		if(featuresElement.getChildren("feature").size() == 0)
			return null;
		return featuresElement;
	}
	
	public Element generateXMLElementForLexicon(LexiconList parentList){
		Element featuresElement = new Element("features");
		
		for(Feature feature: featureList){
			if(!parentList.isFeatureDefault(feature))
				featuresElement.addContent(feature.generateXMLElement());
		}
			
		if(featuresElement.getChildren("feature").size() > 0)
			return featuresElement;
		
		return null;
	}

	public void renameFeatureName(String oldFeatureName, String newFeatureName){
		if(newFeatureName != null && !newFeatureName.trim().isEmpty()){
			for(Feature feature: featureList){
				System.out.println(feature);
				if(feature.getName().equals(oldFeatureName))
					feature.setName(newFeatureName);
			}
		}
	}
	
	public void renameFeatureValue(String featureName, String oldFeatureValue, String newFeatureValue){
		if(newFeatureValue != null && !newFeatureValue.trim().isEmpty()){
			for(Feature feature: featureList){
				//System.out.println("Testing "+feature.getName()+" = "+feature.getValue()+" with "+featureName+" = "+oldFeatureValue+" going to change to "+newFeatureValue);
				if(feature.getName().equalsIgnoreCase(featureName)){
					if(feature.getValue().equalsIgnoreCase(oldFeatureValue))
						feature.setValue(newFeatureValue);
					break;
				}
			}
		}
	}
	
	public void removeFeature(String featureName){
		Feature target = getFeature(featureName);
		if(target != null)
			featureList.remove(target);
	}
}