package emotionAnalyzer;

import emotionAnalyzer.DocumentContainer.Preprocessing;

public class Settings {
	
	DocumentContainer.Preprocessing usedPreprocessing;
	final boolean printIdentifiedTokens;
	
	public Settings(Preprocessing usedPreprocessing, boolean printIdentifiedTokens) {
		super();
		this.usedPreprocessing = usedPreprocessing;
		this.printIdentifiedTokens = printIdentifiedTokens;
	}
	
	

}
