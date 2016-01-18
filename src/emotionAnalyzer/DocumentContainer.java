package emotionAnalyzer;


import java.io.File;


/**
 * contains document path, settings for the processing and its results and
 * statistics
 * 
 * @author buechel
 * 
 */
public class DocumentContainer {

	final File document;
	final File normalizedDocument;

	/**
	 * Number of "letter tokens" (Tokens which purely of letters and can
	 * therefore be regarded as "real words". This deviation may be important in
	 * this context to interprete the difference between token count and count
	 * of identified tokens during look-up because especially in annual reports,
	 * many tokens may be numbers.)
	 */

	int tokenCount;
	int alphabeticTokenCount;
	int numberCount;
	/**
	 * alphabetic non-stopwords
	 */
	int non_stopword_tokenCount;
	/**
	 * Number of word vectors which contribute to the document vector. Unlike in
	 * prior versions, only the words which can be found in the lexicon
	 * contribute to the vector count (unidentified words will be evaluated as
	 * null vecotor and not neutral vector anymore.)
	 */
	int recognizedTokenCount;


	EmotionVector documentEmotionVector;
	EmotionVector standardDeviationVector;

	public DocumentContainer(File givenDocument,
			File givenNormalizedDocumentFolder
			) {
		// initialize final fields for files of the document, the normalized
		// document and the document-term-vector
		this.document = givenDocument;
		this.normalizedDocument = new File(
				givenNormalizedDocumentFolder.getPath()+"/"
						+ givenDocument.getName());

		this.non_stopword_tokenCount = -1; 
	}


	/**
	 * Prints the results of the lemmatization. With the momentarily definition,
	 * the vector count is identical to the normalization paremter. The pieces
	 * of information printed include the file name, the the type of the report,
	 * the originating stock markte index, the enterprise, the year, the values
	 * of the vector itself, the lenght of the vector, the token count, the
	 * letter token count and the vector count (tokens mapped successfully to a
	 * lexicon entry).
	 */
	public void printData() {
		System.out.println(this.document.getName() + "\t" 	
				+ this.documentEmotionVector.getValence()
				+ "\t" + this.documentEmotionVector.getArousal() + "\t"
				+ this.documentEmotionVector.getDominance() + "\t"
				+ this.standardDeviationVector.getValence() + "\t"
				+ this.standardDeviationVector.getArousal() + "\t"
				+ this.standardDeviationVector.getDominance() + "\t"
				+ this.tokenCount + "\t"  
				+ this.alphabeticTokenCount + "\t"
				+ this.non_stopword_tokenCount + "\t"
				+ this.recognizedTokenCount	+	"\t"
				+ this.numberCount);
	}

	
	/**
	 * Number of tokens in the input text. Tokenization is done be Stanfords
	 * PTBTokenizer.
	 */

	public int getTokenCount() {
		return tokenCount;
	}


	public int getRecognizedTokenCount() {
		return recognizedTokenCount;
	}
}
