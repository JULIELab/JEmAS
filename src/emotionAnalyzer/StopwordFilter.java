package emotionAnalyzer;

import java.util.ArrayList;
import java.util.List;

public class StopwordFilter {
	
	public StopwordFilter(List<String> stopwordList) {
		super();
		this.stopwordList = stopwordList;
	}

	final private List<String> stopwordList;
	
	public ArrayList<String> filter(List<String> document){
		ArrayList<String> filteredList = new ArrayList<String>();
		for (String token: document){
			if (!stopwordList.contains(token)) filteredList.add(token);
		}
		return filteredList;
	}
	
	

}
