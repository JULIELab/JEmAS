package emotionAnalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
//	final File documentTermVector;

	// final private String documentPath;
	// final private File documentFile;
	// Preprocessing usedPreprocessing;

//	final String reportCategory;
//	final String origin;
//	final String organization;
//	final String year;
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
	// final Settings settings;

	EmotionVector documentEmotionVector;
	EmotionVector standardDeviationVector;

	public DocumentContainer(File givenDocument,
			File givenNormalizedDocumentFolder
//			File givenDocumentTermVectorFolder
			) {
		// initialize final fields for files of the document, the normalized
		// document and the document-term-vector
		this.document = givenDocument;
		this.normalizedDocument = new File(
				givenNormalizedDocumentFolder.getPath()+"/"
						+ givenDocument.getName());
//		this.documentTermVector = new File(
//				givenDocumentTermVectorFolder.getPath()+"/"
//						+ givenDocument.getName());
//		// Initialize the attributes of the document as coded in the filename.
//		String[] nameParts = getDocumentAttributes(this.document.getName());
//		this.reportCategory = nameParts[0];
//		this.origin = nameParts[1];
//		this.organization = nameParts[2];
//		this.year = nameParts[3];

		this.non_stopword_tokenCount = -1; //wird erstmal auf einen Wert initialisert, der, wenn nicht verändert, eindeutig als Fehler zu erkennen ist.
	}
	
//	/**
//	 * Returns the attributes of the document (e.g. report type, origin, year,...) based on the documents name.
//	 * @param document
//	 * @return index 0: category type, 1: origin: 2: organization, 3: year, 4: suffix
//	 */
//	public static String[] getDocumentAttributes(String documentName){
//		String[] nameParts = documentName.split("\\.");
//		String[] attributes;
//		//performing a few corrections to deal with some irregularities in the file namings.
//		if (nameParts.length==5){
//			attributes = nameParts;
//			// wrong format in the year part, e. g. CSR_Reports.Dax.Henkel.Henkel_ustainability-report_2009.txt
//			if (!attributes[3].matches("[0-9]{4}")){
//				Pattern pattern = Pattern.compile("[0-9]{4}");
//				Matcher matcher = pattern.matcher(attributes[3]);
//				//If there is a year in the specific part, rewrite this part, else leave it as it is.
//				if (matcher.find()){
//				attributes[3] = matcher.group();	
//				}		
//			}
//		}
//		else if (nameParts.length == 6){
//			//in this case  the file name looks like type.origin.enterprise.year.pdf.txt. e. g. : Annual_Reports.DowJones.Johnson_Johnson.2009.PDF.txt
//			if ( nameParts[5].toLowerCase().equals("txt") && nameParts[4].toLowerCase().equals("pdf") ){
//				//build new string without pdf and call method again.
//				String concat = nameParts[0] + "." + nameParts[1] + "." + nameParts[2] + "." + nameParts[3]+ "." + nameParts[5];
// 				attributes = getDocumentAttributes(concat);
//			}
//			//Probelems with the dax firm e.on written as E.ON (leading to a split). E. g.: CSR_Reports.Dax.EON.E.ON_CR_Report_2004.txt
//			else if (nameParts[3].equals("E") && nameParts[4].startsWith("ON")){
//				//concat 3 and 4 and call method again
//				String concat = nameParts[0] + "." + nameParts[1] + "." + nameParts[2] + "." + nameParts[3] + nameParts[4] +  "." + nameParts[5];
//				attributes = getDocumentAttributes(concat);
//			}
//			
//			else attributes = new String[]{null, null , null , null, null};
//		}
//		
//		else {
//			attributes = new String[]{null , null , null , null, null};
//		}
//		
//		return attributes;
//	}

	// public String getDocumentPath() {
	// return this.documentPath;
	// }

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
		// i dont use this because it would not only give the identified tokens,
		// but all of them.
		// if (this.settings.printIdentifiedTokens){
		// System.out.println("\nIdentified Tokens:\n\n");
		// for (String token: this.bagOfWords){
		// System.out.println(token);
		// }
		// }
		System.out.println(this.document.getName() + "\t" 	
//				+ this.reportCategory
//				+ "\t" + this.origin + "\t" + this.organization + "\t"
//				+ this.year + "\t" 
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

	// public EmotionVector getNormalizedEmotionVector() {
	// return documentEmotionVector;
	// }

	// private EmotionVector sumOfVectors;

	/**
	 * Number of tokens in the input text. Tokenization is done be Stanfords
	 * PTBTokenizer.
	 */

	public int getTokenCount() {
		return tokenCount;
	}

	// private double normalizationParameter;

	public int getRecognizedTokenCount() {
		return recognizedTokenCount;
	}

//	public int[] getDocumentTermVector(int vocabularySize) 
//			throws NumberFormatException, IOException {
//		int[] vector = new int[vocabularySize];
//		BufferedReader reader = new BufferedReader(new FileReader(
//				this.documentTermVector));
//		String currentLine;
//		int i = 0;
//		while ((currentLine = reader.readLine()) != null) {
//			vector[i] = Integer.parseInt(currentLine);
//			i++;
//		}
//		reader.close();
//		return vector;
//	}

	// public double getNormalizationParameter() {
	// return normalizationParameter;
	// }

	// private HashMultiset<String> bagOfWords;

	// public HashMultiset<String> getBagOfWords() {
	// return bagOfWords;
	// }

	// public void setBagOfWords(HashMultiset<String> bagOfWords) {
	// this.bagOfWords = bagOfWords;
	// }

	// public void calculateBagOfWords(File2BagOfWords_Processor givenF2TReader)
	// throws IOException{
	// switch (this.usedPreprocessing){
	// case TOKENIZE:
	// this.bagOfWords =
	// givenF2TReader.produceBagOfWords_Token(this.documentPath);
	// break;
	// case LEMMATIZE:
	// this.bagOfWords =
	// givenF2TReader.produceBagOfWords_Lemma(this.documentPath);
	// break;
	// case STEM:
	// this.bagOfWords =
	// givenF2TReader.produceBagOfWords_Stems(this.documentPath);
	// }
	//
	// this.tokenCount = this.bagOfWords.size();
	// }
	//

	// /**
	// * Calculates the number of letter tokens ((Tokens which purely of letters
	// and can therefore be regarded as "real words". This deviation may be
	// important in this context to interprete the difference between token
	// count and count of identified tokens during look-up because especially in
	// annual reports, many tokens may be numbers.).
	// */
	// public void calculateLetterTokenCount(){
	// int letterTokenCount = 0;
	// for (String currentToken: this.bagOfWords){
	// if (Util.isLetterToken(currentToken)) letterTokenCount++;
	// }
	// // if (letterTokenCount==-1) Throw new
	// Exception("Error in calculation of letter token count!")
	// this.normalizedTokenCount = letterTokenCount;
	// }
	//
	// public void calculateSumOfVectors(BagOfWords2Vector_Processor
	// givenToken2Vectorizer, EmotionLexicon givenLexicon) throws IOException{
	// VectorizationResult result =
	// givenToken2Vectorizer.calculateDocumentVector(this.bagOfWords,
	// givenLexicon, this.settings); //calcualtes not normalized emotion vector
	// (sum of found vectors
	// this.sumOfVectors = result.getEmotionVector();
	// // this.sumOfVectors.print();
	// this.recognizedTokenCount = result.getNumberOfAddedVectors();
	// }

	// public void normalizeDocumentVector(VectorNormalizer
	// givenVectorNormalizer){
	// this.normalizationParameter =
	// givenVectorNormalizer.calculateNormalizationParameter(this.recognizedTokenCount);
	// this.normalizedEmotionVector =
	// givenVectorNormalizer.calculateNormalizedDocumentVector(this.sumOfVectors,
	// this.normalizationParameter);
	// }

	// public EmotionVector getSumOfVectors() {
	// return sumOfVectors;
	// }

}
