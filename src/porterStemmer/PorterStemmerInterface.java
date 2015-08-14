package porterStemmer;

public class PorterStemmerInterface {
	
	public PorterStemmerInterface(){	
	}
	
	public String stem (String inputString){
		PorterStemmer stemmer = new PorterStemmer();
		stemmer.add(inputString.toCharArray(), inputString.length());
		stemmer.stem();
		String output = stemmer.toString();
		return output;
	}
	
	public static void main (String[] args){
		PorterStemmerInterface stemmer = new PorterStemmerInterface();
		for (String currentString: args){
			System.out.println(stemmer.stem(currentString));
		}
	}
	

}
