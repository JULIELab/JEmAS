package emotionAnalyzer;

import java.util.ArrayList;
import java.util.List;

public class StopwordFilter {
	
	public StopwordFilter(List<String> stopwordList) {
		super();
		this.stopwordList = stopwordList;
	}

	final private List<String> stopwordList;
	
	/**
	 * Maps to lower-case (case-folding) and removes stopwords (stopword list only contains lowercase
	 * entries).
	 * @param document
	 * @return
	 */
	public ArrayList<String> filter(List<String> document){
		ArrayList<String> filteredList = new ArrayList<String>();
		for (String token: document){
			token = token.toLowerCase();
			if (!stopwordList.contains(token)) filteredList.add(token);
		}
		return filteredList;
	}
	
	

}
