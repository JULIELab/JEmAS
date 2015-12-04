package emotionAnalyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberFilter {
	//Pattern to match numbers, currencies and percent expressions
	Pattern numbersPattern = Pattern.compile("[\\$£€]?\\d+(\\.\\d+)?%?"); 
	//Pattern to match date expression in format dd/mm/yyyy or mm/dd/yyyy
	Pattern datePattern = Pattern.compile("(\\d){2}/(\\d){2}/(\\d){4}");
	Matcher numbersMatcher;
	Matcher dateMatcher;
	
	public ArrayList<String> filter(List<String> document){
		ArrayList<String> filteredList = new ArrayList<String>();
		for (String token: document){
			numbersMatcher = numbersPattern.matcher(token);
			dateMatcher = datePattern.matcher(token);
			
			if (!( (numbersMatcher.matches()) || (dateMatcher.matches()) ) ){
				filteredList.add(token);	
			}
		}
		return filteredList;
	}

}
