package emotionAnalyzer;

import java.io.IOException;
import emotionAnalyzer.DocumentContainer.Preprocessing;

public class EmotionAnalyzer {

	
	public static String TESTFILE ="src/emotionAnalyzer/testFile.txt";
	public static String TESTFILE2 ="src/emotionAnalyzer/testFile2.txt"; //(not normalized) Document vector should be (-8.43, -3.75, -7.04) using warriners (default) lexicon
	public static String TESTFILE3 ="src/emotionAnalyzer/testFile3.txt";
	public static String DEFAULTLEXICON ="src/emotionAnalyzer/LexiconWarriner2013_transformed.txt";
	public static String DEFAULTLEXICON_JAR ="emotionAnalyzer/LexiconWarriner2013_transformed.txt";
	public static String TESTLEXICON="src/emotionAnalyzer/testLexicon.txt";
	static EmotionVector neutralVector = new EmotionVector(0,0,0);  //TODO maybe not hard coded?
	public static String TESTFILE_LEMMA = "src/emotionAnalyzer/testFile_Lemma.txt";
	public static String TESTLEXICON_LEMMA = "src/emotionAnalyzer/testLexicon_Lemma.txt";
	
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
		
	
	DocumentContainer analyzeEmotions(String givenDocumentPath, DocumentContainer.Preprocessing givenPreprocessor) throws IOException{ //viel weniger Argumente, weil erstmal alles berechnet wird und dann wird in der main-methode entschieden, was ausgegeben wird und was nicht...
		DocumentContainer documentContainer = new DocumentContainer(givenDocumentPath, givenPreprocessor);
		//calculates BagOfWords in documentContainer using f2tReader
		documentContainer.calculateBagOfWords(this.f2tReader);
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
		return analyzeEmotions(givenDocumentPath, Preprocessing.LEMMATIZE);
	}
	
	
	void showLexicon(){
		this.lexicon.printLexicon();
	}

}
