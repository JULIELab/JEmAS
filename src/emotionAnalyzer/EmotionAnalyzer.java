package emotionAnalyzer;

import java.io.IOException;
import emotionAnalyzer.DocumentContainer.Preprocessing;

public class EmotionAnalyzer {

	
	public static final String TESTFILE ="src/emotionAnalyzer/test.test.test.testFile.txt";
	public static final String TESTFILE2 ="src/emotionAnalyzer/test.test.test.testFile2.txt"; //(not normalized) Document vector should be (-8.43, -3.75, -7.04) using warriners (default) lexicon
	public static final  String TESTFILE3 ="src/emotionAnalyzer/test.test.test.testFile3.txt";
	public static final  String DEFAULTLEXICON ="src/emotionAnalyzer/LexiconWarriner2013_transformed.txt";
	public static final String DEFAULTLEXICON_JAR ="emotionAnalyzer/LexiconWarriner2013_transformed.txt";
	public static final String TESTLEXICON="src/emotionAnalyzer/testLexicon.txt";
	public static final EmotionVector neutralVector = new EmotionVector(0,0,0);  //TODO maybe not hard coded?
	public static final String TESTFILE_LEMMA = "src/emotionAnalyzer/test.test.test.testFile_Lemma.txt";
	public static final String TESTLEXICON_LEMMA = "src/emotionAnalyzer/testLexicon_Lemma.txt";
	public static final String TESTLEXICON_STEMMER = "src/emotionAnalyzer/testLexicon_Stemmer.txt";
	
	EmotionLexicon lexicon=null;
	File2BagOfWords_Processor f2tReader =null;
	BagOfWords2Vector_Processor t2Vectorizer =null;
	VectorNormalizer vectorNormalizer = null;
	
	
	/**
	 * Constructor for EmotionAnalyzer loads EmotionLexicon (for recycling purposes) and the compounts File2TokenReader and Token2Vectorizer.
	 * @param givenLexiconPath
	 * @throws IOException
	 */
	public EmotionAnalyzer(String givenLexiconPath) throws IOException{
		this.lexicon = new EmotionLexicon(givenLexiconPath);
		this.f2tReader = new File2BagOfWords_Processor();
		this.t2Vectorizer = new BagOfWords2Vector_Processor(this.lexicon);
		this.vectorNormalizer = new VectorNormalizer();
	}
		
	
	DocumentContainer analyzeEmotions(String givenDocumentPath, Settings givenSettings) throws IOException{ //viel weniger Argumente, weil erstmal alles berechnet wird und dann wird in der main-methode entschieden, was ausgegeben wird und was nicht...
		DocumentContainer documentContainer = new DocumentContainer(givenDocumentPath, givenSettings);
		//calculates BagOfWords in documentContainer using f2tReader
		documentContainer.calculateBagOfWords(this.f2tReader);
		documentContainer.calculateLetterTokenCount();
		documentContainer.calculateSumOfVectors(t2Vectorizer);
		documentContainer.normalizeDocumentVector(vectorNormalizer);
		return documentContainer; //return 
		
	}
	
	/**
	 * Runs EmotionsAnalyzer using default settings (Lemmatizer).
	 * @param givenDocumentPath
	 * @return
	 * @throws IOException
	 */
	DocumentContainer analyzeEmotions (String givenDocumentPath) throws IOException{
		return analyzeEmotions(givenDocumentPath, Util.defaultSettings);
	}
	
	
	void showLexicon(){
		this.lexicon.printLexicon();
	}

}
