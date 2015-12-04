package emotionAnalyzer;

import java.util.ArrayList;
import java.util.List;

public class NonAlphabeticFilter {
	
	/**
	 * Since annual reports consist to a great extend of numbers and numbers are not important in this context, this method filters the wordlist so that only alphabetic tokens will be returned.
	 * This will also delete all tokens, which do not begin with a unicode letter.
	 * @param document
	 * @return
	 */
	public ArrayList<String> filter(List<String> document){
		ArrayList<String> filteredList = new ArrayList<String>();
		for (String token: document){
			if ( token.matches("\\p{L}.*")){ 	//starts with a letter (therfore contains
												//least one letter).
				filteredList.add(token);	
			}
		}
		return filteredList;
	}
}
