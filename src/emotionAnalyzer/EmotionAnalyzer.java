package emotionAnalyzer;

import java.io.IOException;

public class EmotionAnalyzer {

	
	public static final String TESTFILE ="src/emotionAnalyzer/test.test.test.testFile.txt";
	public static final String TESTFILE2 ="src/emotionAnalyzer/test.test.test.testFile2.txt"; //(not normalized) Document vector should be (-8.43, -3.75, -7.04) using warriners (default) lexicon
	public static final  String TESTFILE3 ="src/emotionAnalyzer/test.test.test.testFile3.txt";
	public static final String TESTFILE_STEM = "src/emotionAnalyzer/test.test.test.testFile_Stem.txt";
	public static final  String DEFAULTLEXICON ="src/emotionAnalyzer/LexiconWarriner2013_transformed.txt";
	public static final String DEFAULTLEXICON_JAR ="emotionAnalyzer/LexiconWarriner2013_transformed.txt";
	public static final String TESTLEXICON="src/emotionAnalyzer/testLexicon.txt";
	public static final String TESTFILE_LEMMA = "src/emotionAnalyzer/test.test.test.testFile_Lemma.txt";
	public static final String TESTLEXICON_LEMMA = "src/emotionAnalyzer/testLexicon_Lemma.txt";
	public static final String TESTLEXICON_STEMMER = "src/emotionAnalyzer/testLexicon_Stemmer.txt";
	
	final private EmotionLexicon lexicon;
	final private File2BagOfWords_Processor f2tReader;
	final private BagOfWords2Vector_Processor t2Vectorizer;
	final private VectorNormalizer vectorNormalizer;
	
	/**
	 * Will only be assingned if a passed DocumentContainer requires stemming as preprocessing.
	 */
	private EmotionLexicon stemmedLexicon;
	/**
	 * Will only be assingned if a passed DocumentContainer requires stemming as preprocessing.
	 */
	private BagOfWords2Vector_Processor stemming_t2Vectorizer;
	
	
	/**
	 * Constructor for EmotionAnalyzer loads EmotionLexicon (for recycling purposes) and the compounts File2TokenReader and Token2Vectorizer.
	 * @param givenLexiconPath
	 * @throws IOException
	 */
	public EmotionAnalyzer(String givenLexiconPath) throws IOException{
		this.lexicon = new EmotionLexicon(givenLexiconPath);
		this.f2tReader = new File2BagOfWords_Processor();
		this.t2Vectorizer = new BagOfWords2Vector_Processor();
		this.vectorNormalizer = new VectorNormalizer();
	}
		
	
	DocumentContainer analyzeEmotions(String givenDocumentPath, Settings givenSettings) throws IOException{
		DocumentContainer documentContainer = new DocumentContainer(givenDocumentPath, givenSettings);
		//calculates BagOfWords in documentContainer using f2tReader
		documentContainer.calculateBagOfWords(this.f2tReader);
		documentContainer.calculateLetterTokenCount();
		//if the selected preprocessor is stemming, another lexicon (the stemmed one) must be used.
		if (documentContainer.settings.usedPreprocessing == Preprocessing.STEM){
			//checks if the lexicon has already been stemmed and does so if necessary
			if (this.stemmedLexicon == null){this.stemmedLexicon = this.lexicon.stemLexicon();
			//calculate the sum of vectors with the stemmed lexicon
			documentContainer.calculateSumOfVectors(t2Vectorizer, stemmedLexicon);
			}
		}
		//calculates the emotion vectors if preprocessor is different than stemmer
		else documentContainer.calculateSumOfVectors(t2Vectorizer, lexicon);
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
	
	void showStemmedLexicon(){
		this.stemmedLexicon.printLexicon();
	}

}
