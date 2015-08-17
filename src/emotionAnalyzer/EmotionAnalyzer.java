package emotionAnalyzer;

import java.io.IOException;

import org.w3c.dom.views.DocumentView;

import com.google.common.collect.HashMultiset;

public class EmotionAnalyzer {
//	private String documentPath =null;
//	private EmotionVector documentVector=null;
	
	public static String TESTFILE ="src/emotionAnalyzer/testFile.txt";
	public static String TESTFILE2 ="src/emotionAnalyzer/testFile2.txt"; //(not normalized) Document vector should be (-8.43, -3.75, -7.04) using warriners (default) lexicon
	public static String TESTFILE3 ="src/emotionAnalyzer/testFile3.txt";
	public static String DEFAULTLEXICON ="src/emotionAnalyzer/LexiconWarriner2013_transformed.txt";
	public static String TESTLEXICON="src/emotionAnalyzer/testLexicon.txt";
	static EmotionVector neutralVector = new EmotionVector(0,0,0);  //TODO maybe not hard coded?
	
	EmotionLexicon lexicon=null;
	File2BagOfWords_Processor f2tReader =null;
	BagOfWords2Vector_Processor t2Vectorizer =null;
	VectorNormalizer vectorNormalizer = null;
//	String lastProcessedDocument="";
	
	
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
	
	
//	EmotionVector analyzeEmotions(String givenDocumentPath, boolean printLookUps, boolean normalize) throws IOException{
////		lastProcessedDocument = givenDocumentPath;
//		//TODO wrap in document emotionContainer class (to be written)
//		HashMultiset<String> bagOfWords = this.f2tReader.produceBagOfWords(givenDocumentPath);
//		//TODO return EmotionContainer, so that all information (and the input settings aswell) about the document and its processing is available to the UI
//		return this.t2Vectorizer.calculateDocumentVector(bagOfWords,printLookUps,normalize);	
//	}
//	
//	EmotionVector calculateEmotionVector(String givenDocumentPath) throws IOException{
//		return analyzeEmotions(givenDocumentPath, false, true);
//	}
	
	
	DocumentContainer analyzeEmotions(String givenDocumentPath) throws IOException{ //viel weniger Argumente, weil erstmal alles berechnet wird und dann wird in der main-methode entschieden, was ausgegeben wird und was nicht...
		DocumentContainer documentContainer = new DocumentContainer(givenDocumentPath);
		//calculates BagOfWords in documentContainer using f2tReader
		documentContainer.calculateBagOfWords(this.f2tReader);
		documentContainer.calculateSumOfVectors(t2Vectorizer);
		documentContainer.normalizeDocumentVector(vectorNormalizer);
		return documentContainer; //return 
		
	}
	
//	void presentResults(EmotionVector documentVector, boolean printTemplate){
//		if (printTemplate){
//			EmotionVector.printTemplate(true);
//		}
//		System.out.print(lastProcessedDocument + "\t ");
//		documentVector.print();
//	}
	
	void showLexicon(){
		this.lexicon.printLexicon();
	}
	
//	public DocumentContainer analyzeEmotions (String DocumentPath){
//		return null;
//	}

}
