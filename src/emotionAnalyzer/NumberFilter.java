package emotionAnalyzer;

import java.util.ArrayList;
import java.util.List;

public class NumberFilter {
	
	public ArrayList<String> filter(List<String> document){
		ArrayList<String> filteredList = new ArrayList<String>();
		for (String token: document){
			if (! (token.matches("[0-9]+"))){
				filteredList.add(token);	
			}
		}
		return filteredList;
	}

}
