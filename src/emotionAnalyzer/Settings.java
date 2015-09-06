package emotionAnalyzer;

import emotionAnalyzer.DocumentContainer.Preprocessing;

public class Settings {
	
	DocumentContainer.Preprocessing usedPreprocessing;
	final boolean printTokens;
	final boolean printLetterTokens;
	final boolean printIdentifiedTokens;
	
	public Settings(Preprocessing usedPreprocessing, boolean printTokens,
			boolean printLetterTokens, boolean printIdentifiedTokens) {
		super();
		this.usedPreprocessing = usedPreprocessing;
		this.printTokens = printTokens;
		this.printLetterTokens = printLetterTokens;
		this.printIdentifiedTokens = printIdentifiedTokens;
	}
	
	

}
