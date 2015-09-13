package emotionAnalyzer;

import java.io.File;
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
	final private File documentFile;
	Preprocessing usedPreprocessing; //TODO das sollte komplett auf this.settings.preprocessing umstellen/ersetzen
	final private String reportCategory;
	final private String origin;
	final private String organization;
	final private String year;
	/**
	 * Number of "letter tokens" (Tokens which purely of letters and can therefore be regarded as "real words". This deviation may be important in this context to interprete the difference between token count and count of identified tokens during look-up because especially in annual reports, many tokens may be numbers.)
	 */
	private int letterTokenCount;
	final Settings settings;


	public DocumentContainer(String documentPath, Settings givenSettings) {
		this.documentPath = documentPath;
		this.settings = givenSettings;
		this.usedPreprocessing = this.settings.usedPreprocessing;
		this.documentFile = new File(this.documentPath);
		String[] nameParts = this.documentFile.getName().split("\\.");
		this.reportCategory = nameParts[0];
		this.origin = nameParts[1];
		this.organization = nameParts[2];
		this.year = nameParts[3];
		this.letterTokenCount = -1;
	}

	public String getDocumentPath() {
		return this.documentPath;
	}
	
	/**
	 * Prints the results of the lemmatization. With the momentarily definition, the vector count is identical to the normalization paremter.
	 * The pieces of information printed include the file name, the the type of the report, the originating stock markte index, the enterprise, the year, the values of the vector itself, the lenght of the vector, the token count, the letter token count and the vector count (tokens mapped successfully to a lexicon entry).
	 */
	public void printData(){
		//i dont use this because it would not only give the identified tokens, but all of them.
//		if (this.settings.printIdentifiedTokens){
//			System.out.println("\nIdentified Tokens:\n\n");
//			for (String token: this.bagOfWords){
//				System.out.println(token);
//			}
//		}
		System.out.println(this.documentFile.getName() + "\t" + this.reportCategory+ "\t" + this.origin+ "\t" + this.organization + "\t" + this.year + "\t" + this.normalizedEmotionVector.getValence() + "\t" + this.normalizedEmotionVector.getArousal() + "\t" + this.normalizedEmotionVector.getDominance() + "\t" + this.normalizedEmotionVector.getLength() + "\t" + this.tokenCount + "\t" + this.letterTokenCount + "\t" + this.vectorCount);
		
	}

	private EmotionVector normalizedEmotionVector;

	public EmotionVector getNormalizedEmotionVector() {
		return normalizedEmotionVector;
	}
	
	private EmotionVector sumOfVectors;

	/**
	 * Number of tokens in the input text. Tokenization is done be Stanfords PTBTokenizer.
	 */
	private int tokenCount;

	public int getTokenCount() {
		return tokenCount;
	}

	/**
	 * Number of word vectors which contribute to the document vector. Unlike in prior versions, only the words which can be found in the lexicon contribute to the vector count (unidentified words will be evaluated as null vecotor and not neutral vector anymore.)
	 */
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
			 this.bagOfWords = givenF2TReader.produceBagOfWords_Stems(this.documentPath);
		}
		
		this.tokenCount = this.bagOfWords.size();
	}
	
	
	/**
	 * Calculates the number of letter tokens ((Tokens which purely of letters and can therefore be regarded as "real words". This deviation may be important in this context to interprete the difference between token count and count of identified tokens during look-up because especially in annual reports, many tokens may be numbers.).
	 */
	public void calculateLetterTokenCount(){
		int letterTokenCount = 0;
		for (String currentToken: this.bagOfWords){
			if (Util.isLetterToken(currentToken)) letterTokenCount++;
		}
//		if (letterTokenCount==-1) Throw new Exception("Error in calculation of letter token count!")
		this.letterTokenCount = letterTokenCount;
	}
	
	public void calculateSumOfVectors(BagOfWords2Vector_Processor givenToken2Vectorizer, EmotionLexicon givenLexicon) throws IOException{
		VectorizationResult result = givenToken2Vectorizer.calculateDocumentVector(this.bagOfWords, givenLexicon, this.settings); //calcualtes not normalized emotion vector (sum of found vectors
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
