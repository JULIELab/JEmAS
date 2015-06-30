package rumprobieren;

import java.io.IOException;

import com.google.common.collect.HashMultiset;

public class EmotionAnalyzer {
//	private String documentPath =null;
//	private EmotionVector documentVector=null;
	
	public static String TESTFILE ="src/rumprobieren/testFile.txt";
	public static String TESTFILE2 ="src/rumprobieren/testFile2.txt"; //(not normalized) Document vector should be (-8.43, -3.75, -7.04) using warriners (default) lexicon
	public static String DEFAULTLEXICON ="/Users/sven/Documents/workspace/EmotionAnalyzer/src/rumprobieren/LexiconWarriner2013_transformed.txt";
	public static String TESTLEXICON="src/rumprobieren/testLexicon.txt";
	static EmotionVector neutralVector = new EmotionVector(0,0,0);  //TODO maybe not hard coded?
	
	EmotionLexicon lexicon=null;
	File2TokenReader f2tReader =null;
	Token2Vectorizer t2Vectorizer =null;
	String lastProcessedDocument="";
	
	
	/**
	 * Constructor for EmotionAnalyzer loads EmotionLexicon (for recycling purposes)
	 * @param givenLexiconPath
	 * @throws IOException
	 */
	public EmotionAnalyzer(String givenLexiconPath) throws IOException{
		this.lexicon = new EmotionLexicon(givenLexiconPath);
		this.f2tReader = new File2TokenReader();
		this.t2Vectorizer = new Token2Vectorizer(this.lexicon);
	}
	
	//performs the steps of the architecture
	EmotionVector calculateEmotionVector(String givenDocumentPath, boolean printLookUps, boolean normalize) throws IOException{
		lastProcessedDocument = givenDocumentPath;
		HashMultiset<String> bagOfWords = this.f2tReader.produceBagOfWords(givenDocumentPath);
		return this.t2Vectorizer.calculateDocumentVector(bagOfWords,printLookUps,normalize);	
	}
	
	EmotionVector calculateEmotionVector(String givenDocumentPath) throws IOException{
		return calculateEmotionVector(givenDocumentPath, false, true);
	}
	
	void presentResults(EmotionVector documentVector, boolean printTemplate){
		if (printTemplate){
			EmotionVector.printTemplate(true);
		}
		System.out.print(lastProcessedDocument + "\t ");
		documentVector.print();
	}
	
	void showLexicon(){
		this.lexicon.printLexicon();
	}

}
