package emotionAnalyzer;

/**
 * This class models an entry of the external lexicon (currently Warriners Lexicon).
 * @author sven
 *
 */
public class Token {
	public Token(String tokenName, EmotionVector emotionVector) {
		super();
		this.tokenName = tokenName;
		this.emotionVector = emotionVector;
	}
	public final String tokenName;
	public final EmotionVector emotionVector;
	
	

}
