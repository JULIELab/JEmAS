package emotionAnalyzer;

import com.google.common.collect.HashMultiset;

/**
 * contains document path, settings for the processing and its results and
 * statistics
 * 
 * @author buechel
 * 
 */
public class DocumentContainer {

	final private String documentPath;

	public DocumentContainer(String documentPath) {
		this.documentPath = documentPath;
	}

	public String getDocumentPath() {
		return this.documentPath;
	}

	private EmotionVector emotionVector;

	public EmotionVector getEmotionVector() {
		return emotionVector;
	}

	private int tokenCount;

	public int getTokenCount() {
		return tokenCount;
	}

	private int vectorCount;

	private double normalizationParameter;

	public int getVectorCount() {
		return vectorCount;
	}

	public double getNormalizationParameter() {
		return normalizationParameter;
	}

	private HashMultiset<String> bagOfWords;

	public HashMultiset<String> getBagOfWords() {
		return bagOfWords;
	}

	public void setBagOfWords(HashMultiset<String> bagOfWords) {
		this.bagOfWords = bagOfWords;
	}

	// TODO Anzahl zugeordneter Vektoren, Normalisierungsparameter,...

}
