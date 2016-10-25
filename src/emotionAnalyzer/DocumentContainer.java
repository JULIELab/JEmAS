package emotionAnalyzer;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;


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
			File givenNormalizedDocumentFolder, File givenDocumentTermVectorFolder
			) {
		// initialize final fields for files of the document, the normalized
		// document and the document-term-vector
		this.document = givenDocument;
		this.normalizedDocument = new File(
				givenNormalizedDocumentFolder.getPath()+"/"
						+ givenDocument.getName());

		this.non_stopword_tokenCount = -1; 
		this.documentTermVector = new File(givenDocumentTermVectorFolder.getPath()+"/"+givenDocument.getName());
	}
	
	final File documentTermVector;


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
		
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(','); 
		DecimalFormat df = new DecimalFormat("#.#####", otherSymbols); // Anzahl der Dezimalstellen festlegen.
		System.out.println(this.document.getName() + "\t" 	
				+ df.format(this.documentEmotionVector.getValence()) + "\t"
				+ df.format(this.documentEmotionVector.getArousal()) + "\t"
				+ df.format(this.documentEmotionVector.getDominance()) + "\t"
				+ df.format(this.standardDeviationVector.getValence()) + "\t"
				+ df.format(this.standardDeviationVector.getArousal()) + "\t"
				+ df.format(this.standardDeviationVector.getDominance()) + "\t"
				+ df.format(this.tokenCount) + "\t"  
				+ df.format(this.alphabeticTokenCount) + "\t"
				+ df.format(this.non_stopword_tokenCount) + "\t"
				+ df.format(this.recognizedTokenCount)	+	"\t"
				+ df.format(this.numberCount));
	}
//	public void printData() {
//	System.out.println(this.document.getName() + "\t" 	
//			+ this.documentEmotionVector.getValence() + "\t"
//			+ this.documentEmotionVector.getArousal() + "\t"
//			+ this.documentEmotionVector.getDominance() + "\t"
//			+ this.standardDeviationVector.getValence() + "\t"
//			+ this.standardDeviationVector.getArousal() + "\t"
//			+ this.standardDeviationVector.getDominance() + "\t"
//			+ this.tokenCount + "\t"  
//			+ this.alphabeticTokenCount + "\t"
//			+ this.non_stopword_tokenCount + "\t"
//			+ this.recognizedTokenCount	+	"\t"
//			+ this.numberCount);
//}
	
	

	
	/**
	 * Number of tokens in the input text. Tokenization is done be Stanfords
	 * PTBTokenizer.
	 * @throws IOException 
	 */
	
	public void writeDocumentTermVector(int[] vec) throws IOException{
		FileWriter writer = new FileWriter(this.documentTermVector);
		for (int i: vec)
			writer.write(i +"\n");
		writer.close();
	}
	
	public int[] getDocumentTermVector() throws IOException{
		List<String> vecList = Util.readFile2List(this.documentTermVector.getPath());
		int[] vec = new int[vecList.size()];
		for (int i = 0; i < vecList.size(); i++)
			vec[i] =  Integer.parseInt(vecList.get(i));
		return vec;
	}

	public int getTokenCount() {
		return tokenCount;
	}


	public int getRecognizedTokenCount() {
		return recognizedTokenCount;
	}
}
