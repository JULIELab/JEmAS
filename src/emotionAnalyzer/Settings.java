package emotionAnalyzer;


public class Settings {
	
	Preprocessing usedPreprocessing;
	final boolean printIdentifiedTokens;
	
	public Settings(Preprocessing usedPreprocessing, boolean printIdentifiedTokens) {
		super();
		this.usedPreprocessing = usedPreprocessing;
		this.printIdentifiedTokens = printIdentifiedTokens;
	}
	
	

}
