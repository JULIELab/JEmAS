package emotionAnalyzer;


public class Settings {
	
	Preprocessing usedPreprocessing;
	final boolean printIdentifiedTokens;
	final String weightFunction;
	
	public Settings(Preprocessing usedPreprocessing, boolean printIdentifiedTokens, String weightFunction) {
		super();
		this.usedPreprocessing = usedPreprocessing;
		this.printIdentifiedTokens = printIdentifiedTokens;
//		if (weightFunction.equals("absolute") || weightFunction.equals("tfidf"))
		this.weightFunction = weightFunction;
		
				}
	
	

}
