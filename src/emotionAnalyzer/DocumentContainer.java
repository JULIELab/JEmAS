package emotionAnalyzer;

import java.io.IOException;

import org.w3c.dom.DocumentType;

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
	
	public enum Preprocessing {TOKENIZE, STEM, LEMMATIZE};
	
	Preprocessing usedPreprocessing = null;

	public DocumentContainer(String documentPath, Preprocessing givenPreprocessor) {
		this.documentPath = documentPath;
		this.usedPreprocessing = givenPreprocessor;
	}

	public String getDocumentPath() {
		return this.documentPath;
	}

	private EmotionVector normalizedEmotionVector;

	public EmotionVector getNormalizedEmotionVector() {
		return normalizedEmotionVector;
	}
	
	private EmotionVector sumOfVectors;

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
	
	public void calculateBagOfWords(File2BagOfWords_Processor givenF2TReader) throws IOException{
		switch (this.usedPreprocessing){
		case TOKENIZE:
			this.bagOfWords = givenF2TReader.produceBagOfWords_Token(this.documentPath);
			break;
		case LEMMATIZE:
			this.bagOfWords = givenF2TReader.produceBagOfWords_Lemma(this.documentPath);
			break;
		case STEM:
			 throw new IllegalArgumentException( "Not yet implemented!" );
		}
		
		this.tokenCount = this.bagOfWords.size();
	}
	
	public void calculateSumOfVectors(BagOfWords2Vector_Processor givenToken2Vectorizer) throws IOException{
		VectorizationResult result = givenToken2Vectorizer.calculateDocumentVector(this.bagOfWords); //calcualtes not normalized emotion vector (sum of found vectors
		this.sumOfVectors = result.getEmotionVector();
//		this.sumOfVectors.print();
		this.vectorCount = result.getNumberOfAddedVectors();
	}
	
	
	public void normalizeDocumentVector(VectorNormalizer givenVectorNormalizer){
		this.normalizationParameter = givenVectorNormalizer.calculateNormalizationParameter(this.vectorCount);
		this.normalizedEmotionVector = givenVectorNormalizer.calculateNormalizedDocumentVector(this.sumOfVectors, this.normalizationParameter);
	}

	public EmotionVector getSumOfVectors() {
		return sumOfVectors;
	}



}
