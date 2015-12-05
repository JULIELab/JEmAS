package emotionAnalyzer;

import java.util.LinkedList;
import java.util.List;


/**
 * This class models an entry in the internal lexicon, therefore consisting of a list of tokens
 * (entries of the external lexicon) which are mapped to a specific lemma.
 * @author sven
 *
 */
public class Lemma {
	String lemmaName;
	List<Token> tokenList = new LinkedList<Token>();
	EmotionVector emotionVector;
	

	/**
	 * Calculate the emotion of a Lemma. This is currently done by calculating the mean of the associated
	 * tokens.
	 */
	public void calculateEmotion(){
		EmotionVector emo = new EmotionVector();
		for (Token tok: tokenList){
			emo.addVector(tok.emotionVector);
		}
		emo.divide(tokenList.size());
		emotionVector = emo;
	}

}
