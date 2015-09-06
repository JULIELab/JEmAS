package porterStemmer;

public class PorterStemmerWrapper {
	
	public PorterStemmerWrapper(){	
	}
	
	public String stem (String inputString){
		PorterStemmer stemmer = new PorterStemmer();
		stemmer.add(inputString.toCharArray(), inputString.length());
		stemmer.stem();
		String output = stemmer.toString();
		return output;
	}
	
	public static void main (String[] args){
		PorterStemmerWrapper stemmer = new PorterStemmerWrapper();
		for (String currentString: args){
			System.out.println(stemmer.stem(currentString));
		}
	}
	

}
