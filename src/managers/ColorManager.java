package managers;

import java.awt.Color;
import java.util.HashMap;

import ontology.PartOfSpeech;

public class ColorManager {

	private static  HashMap<String, Color> colorMap;
	
	private static ColorManager instance;
	
	public static ColorManager getInstance(){
		if(instance == null)
			instance = new ColorManager();
		return instance;
	}
	
	private ColorManager(){
		loadColorMap();
	}
	
	//Getter
	private void loadColorMap(){
		colorMap = new HashMap<String, Color>();
		
		colorMap.put(PartOfSpeech.CLAUSE.toLowerCase(), Color.DARK_GRAY);
		
		colorMap.put(PartOfSpeech.NOUN.toLowerCase(), new Color(150, 200, 255));
		colorMap.put(PartOfSpeech.NOUN_PHRASE.toLowerCase(), new Color(0, 102, 204));
			
		colorMap.put(PartOfSpeech.VERB_PHRASE.toLowerCase(), new Color(255, 128, 0));
		colorMap.put(PartOfSpeech.VERB.toLowerCase(), new Color(255, 255, 50));
		
		colorMap.put(PartOfSpeech.ADPOSITION.toLowerCase(), new Color(255, 222, 173));
		
		colorMap.put(PartOfSpeech.ADVERB.toLowerCase(), new Color(204,204,0));
		colorMap.put(PartOfSpeech.ADVERBIAL_PHRASE.toLowerCase(), new Color(102,102,0));
		
		colorMap.put(PartOfSpeech.ADJECTIVE.toLowerCase(), new Color(255, 180, 255));
		colorMap.put(PartOfSpeech.ADJECTIVE_PHRASE.toLowerCase(), new Color(205, 0, 255));
		
		colorMap.put(PartOfSpeech.CONJUCTION.toLowerCase(), new Color(150, 255, 150));
		
		colorMap.put(PartOfSpeech.PARTICLE.toLowerCase(), new Color(224,224,224)); //violet
		
		colorMap.put(PartOfSpeech.PERIOD, new Color(255,153,153));
		
		colorMap.put(PartOfSpeech.MARKER.toLowerCase(), Color.WHITE);
	}
	
	public Color getColor(String partOfSpeech){
		return colorMap.get(partOfSpeech.toLowerCase());
	}
}
