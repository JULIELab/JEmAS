package emotionAnalyzer;

import java.util.LinkedList;
import java.util.List;


/**
 * This class models an entry in the internal lexicon, therefore consisting of a list of tokens
 * (entries of the external lexicon) which are mapped to a specific lemma.
 * @author Buechel
 *
 */
public class Lemma {
	public String lemmaName;
	public List<Token> tokenList = new LinkedList<Token>();
	public EmotionVector emotionVector;
	
	public List<EmotionVector> getVectorList(){
		List<EmotionVector> vectorList = new LinkedList<EmotionVector>();
		for (Token token: tokenList){
			vectorList.add(token.emotionVector);
		}
		return vectorList;
	}
	
	/**
	 * 
	 * @return Returns a String of the tokens concatenated in the format "token1 + token2 + token3 +..."
	 */
	public String concatTokens(){
		String str ="";
		for (Token token: tokenList){
			str = str + token.tokenName + " + ";
		}
		str = str.substring(0,str.length()-3);
		return str;
	}

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
	
	public EmotionVector getMeanVector(){
		return EmotionVector.calculateMean(this.getVectorList());
	}
	
	public EmotionVector getMinVector(){
		return EmotionVector.calculateMinimumVector(this.getVectorList());
	}
	
	public EmotionVector getMaxVector(){
		return EmotionVector.calculateMaximumVector(this.getVectorList());
	}
	
	public EmotionVector getSDVector(){
		return EmotionVector.calculateStandardDeviation(this.getVectorList(), this.getMeanVector());
	}
	
	
	/**
	 * Prints LemmaName, associated tokens and univariante statistics for this lemma.
	 */
	public void printLemmaStats(){
		EmotionVector minVector = getMinVector();
		EmotionVector maxVector = getMaxVector();
		EmotionVector meanVector = getMeanVector();
		EmotionVector sdVector = getSDVector();
		
		System.out.println(lemmaName + "\t" + tokenList.size() + "\t" + concatTokens() + "\t"
				//Valence
				+ minVector.getValence() + "\t" + maxVector.getValence() + "\t"
				+ meanVector.getValence() + "\t" + sdVector.getValence() + "\t" 
				//Arousal
				+ minVector.getArousal() + "\t" + maxVector.getArousal() + "\t"
				+ meanVector.getArousal() + "\t" + sdVector.getArousal() + "\t" 
				//Dominance
				+ minVector.getDominance() + "\t" + maxVector.getDominance() + "\t"
				+ meanVector.getDominance() + "\t" + sdVector.getDominance()
				);
	}
	
	public static void printLemmaStatsHeader(){
		System.out.println("Lemma\tTokenCount\tTokens\t"
				//Valence
				+ "VMin\tVMax\tVMean\tVSd\t"
				//Arousal
				+ "AMin\tAMax\tAMean\tASd\t"
				//Dominance
				+ "DMin\tDMax\tDMean\tDSd"
				);
	}

}
