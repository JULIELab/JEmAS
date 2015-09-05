package emotionAnalyzer;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.HashMultiset;

import edu.stanford.nlp.util.logging.NewlineLogFormatter;


public class Tests {
	final EmotionVector vectorAIDS = new EmotionVector(-3.67, 0.0, -1.45);
	final EmotionVector vectorCalm = new EmotionVector(1.89, -3.33, 2.44);
	final EmotionVector vectorLobotomy = new EmotionVector(-2.55, 0.32, -3.0);
	final EmotionVector vectorLovable = new EmotionVector(3.26, 0.41, 1.83);
	final EmotionVector testVector = new EmotionVector(4,5,6); //to test the calculated emotion vector of testFile.txt when using testLexicon.txt
	final EmotionVector testVectorNormalized = new EmotionVector(1.0, 5.0/4.0, 6.0/4.0);
	final EmotionVector[] testVectors = {vectorAIDS, vectorCalm, vectorLobotomy, vectorLovable, EmotionAnalyzer.neutralVector};
	final EmotionVector testVector2 = new EmotionVector(-8.43, -3.75, -7.04);
	final EmotionVector testVectorNormalized2 = new EmotionVector(-8.43/6.0, -3.75/6.0, -7.04/6.0); //this vector should be the normalized (divided by number of found lexicon entries) document vector of testFile2.txt using defaultLexicon.
	
	/**
	 * 
	 * @return Returns A HashMultiSet containing the expected tokens from testFile.txt
	 */
	public HashMultiset<String> getTestBagOfWords(){
		HashMultiset<String> testBagOfWords = HashMultiset.create();
		String[] strArray = {"test", "fish", "fish", "fish", ".",".",".", "ThisIsNotInLexicon"};
		for (String str: strArray){
			testBagOfWords.add(str);}
		return testBagOfWords;
	}
	
	/**
	 * 
	 * @return Returns A HashMultiSet containing the stemmed forms of testFile3.txt
	 */
	public HashMultiset<String> getTestBagOfWords_Stem(){
		HashMultiset<String> testBagOfWords = HashMultiset.create();
		String[] strArray = {"veri", "outsid", "happi", "go", "I", "to", "am", "."};
		for (String str: strArray){
			testBagOfWords.add(str);}
		return testBagOfWords;
	}
	
	/**
	 * 
	 * @return Returns A HashMultiSet containing the lemmatized forms of testFile3.txt
	 */
	public HashMultiset<String> getTestBagOfWords_Lemma(){
		HashMultiset<String> testBagOfWords = HashMultiset.create();
		String[] strArray = {"very", "be", "outside", "happy", "go", "I", "to", "."};
		for (String str: strArray){
			testBagOfWords.add(str);}
		return testBagOfWords;
	}
	
	
	@Test
	public void testFile2Tokens() throws IOException  {
		File2BagOfWords_Processor reader = new File2BagOfWords_Processor();
		HashMultiset<String> bagOfWords = reader.produceBagOfWords_Token(EmotionAnalyzer.TESTFILE);
		Util.printBagOfWords(bagOfWords);
//		Util.printBagOfWords(getTestBagOfWords());
		assertTrue(bagOfWords.equals(getTestBagOfWords()));
	}
	
	@Test
	public void testFile2Lemma() throws IOException{
		File2BagOfWords_Processor processor = new File2BagOfWords_Processor();
		HashMultiset<String> bagOfWords = processor.produceBagOfWords_Lemma(EmotionAnalyzer.TESTFILE3);
		Util.printBagOfWords(bagOfWords);
		assertEquals("Error in Lemmatization", getTestBagOfWords_Lemma(), bagOfWords);
	}
	
	@Test
	public void testFile2Stem() throws IOException{
		File2BagOfWords_Processor processor = new File2BagOfWords_Processor();
		HashMultiset<String> bagOfWords = processor.produceBagOfWords_Stems(EmotionAnalyzer.TESTFILE3);
		Util.printBagOfWords(bagOfWords);
		assertEquals("Error in stemming", getTestBagOfWords_Stem(), bagOfWords);
	}
	
//	@Test
//	public void testToken2Vectorizer() throws IOException{
//		Token2Vectorizer testVectorizer = new Token2Vectorizer(new EmotionLexicon(EmotionAnalyzer.TESTLEXICON)); //initiates an instance of Token2Vectorizer with test lexicon
//		VectorizationResult result = testVectorizer.calculateDocumentVector(getTestBagOfWords(), true,false);
//		 EmotionVector documentVector = result.getEmotionVector();
//		 assertTrue(documentVector.equals(testVector));
//		//tests mode with normalization
//		result = testVectorizer.calculateDocumentVector(getTestBagOfWords(), true, true);
//		documentVector = result.getEmotionVector();
//		assertTrue(documentVector.equals(testVectorNormalized));
//		System.out.println("Normalization works");	
//	}
	
	@Test
	public void testToken2Vectorizer() throws IOException{
		BagOfWords2Vector_Processor testVectorizer = new BagOfWords2Vector_Processor(new EmotionLexicon(EmotionAnalyzer.TESTLEXICON)); //initiates an instance of Token2Vectorizer with test lexicon
		VectorizationResult result = testVectorizer.calculateDocumentVector(getTestBagOfWords());
		 EmotionVector documentVector = result.getEmotionVector();
		 assertTrue(documentVector.equals(testVector));
		//tests mode with normalization
		result = testVectorizer.calculateDocumentVector(getTestBagOfWords());
		documentVector = result.getEmotionVector();
		VectorNormalizer normalizer = new VectorNormalizer();
		documentVector = normalizer.calculateNormalizedDocumentVector(documentVector, 4); // 4 is the expected normalization parameter for the testing bag of words.
		assertTrue(documentVector.equals(testVectorNormalized));
		System.out.println("Normalization works");	
	}	
		


	@Test
	public void testEmotionAnalyzer_Lemmatize() throws IOException{
		EmotionAnalyzer analyzer = new EmotionAnalyzer(EmotionAnalyzer.TESTLEXICON_LEMMA);
		DocumentContainer container = analyzer.analyzeEmotions(EmotionAnalyzer.TESTFILE_LEMMA);
		EmotionVector documentVector = container.getNormalizedEmotionVector();
		documentVector.print();
	}
	
	@Test
	public void testEmotionAnalyzer_Tokenize() throws IOException{
		EmotionAnalyzer analyzer = new EmotionAnalyzer(EmotionAnalyzer.TESTLEXICON);
		DocumentContainer documentContainer= analyzer.analyzeEmotions(EmotionAnalyzer.TESTFILE, DocumentContainer.Preprocessing.TOKENIZE);
		EmotionVector documentVector = documentContainer.getSumOfVectors();
		documentVector.print();
		documentContainer.getNormalizedEmotionVector().print();
		testVector.print();
		assertTrue(documentVector.equals(testVector));
		
	}
	
	@Test
	public void testEmotionAnalyzer2_Tokenize() throws IOException{
		EmotionAnalyzer analyzer = new EmotionAnalyzer(EmotionAnalyzer.DEFAULTLEXICON);
		//this is now done by the second parameter of calculateEmotionVector (printing all found dictionary entries	
		//		analyzer.lexicon.lookUp("AIDS").print();
//		analyzer.lexicon.lookUp("calm").print();
//		analyzer.lexicon.lookUp("lobotomy").print();
//		analyzer.lexicon.lookUp("leukemia").print();
//		analyzer.lexicon.lookUp("librarian").print();
//		analyzer.lexicon.lookUp("earthquake").print();
		
		DocumentContainer documentContainer= analyzer.analyzeEmotions(EmotionAnalyzer.TESTFILE2, DocumentContainer.Preprocessing.TOKENIZE);
		EmotionVector documentVector = documentContainer.getSumOfVectors();
		documentContainer.getSumOfVectors().print();
		documentContainer.getNormalizedEmotionVector().print();
		testVector2.print();
		assertTrue(documentVector.equals(testVector2));
		
	}
	
	@Test
	public void testEmotionAnalyzerNormalized_Tokenize() throws IOException{
		EmotionAnalyzer analyzer = new EmotionAnalyzer(EmotionAnalyzer.TESTLEXICON);
		System.out.println("used lexicon:");
		analyzer.showLexicon();
		DocumentContainer documentContainer = analyzer.analyzeEmotions(EmotionAnalyzer.TESTFILE, DocumentContainer.Preprocessing.TOKENIZE);
		EmotionVector documentVector = 	documentContainer.getNormalizedEmotionVector();
		assertTrue(documentVector.equals(testVectorNormalized));
		System.out.println("\ndocument vector:");
		documentVector.print();
	}
	
	@Test
	public void testEmotionAnalyzerNormalized2_Tokenize() throws IOException{
		EmotionAnalyzer analyzer = new EmotionAnalyzer(EmotionAnalyzer.DEFAULTLEXICON);
//		analyzer.lexicon.lookUp("AIDS").print();
//		analyzer.lexicon.lookUp("calm").print();
//		analyzer.lexicon.lookUp("lobotomy").print();
//		analyzer.lexicon.lookUp("leukemia").print();
//		analyzer.lexicon.lookUp("librarian").print();
//		analyzer.lexicon.lookUp("earthquake").print();	
		DocumentContainer documentContainer= analyzer.analyzeEmotions(EmotionAnalyzer.TESTFILE2, DocumentContainer.Preprocessing.TOKENIZE);
		EmotionVector documentVector = documentContainer.getNormalizedEmotionVector();
		documentVector.print();
		testVectorNormalized2.print();
		assertTrue(documentVector.equals(testVectorNormalized2));
		
	}
	
	@Test
	public void testEmotionVector() {
		EmotionVector vector1 = new EmotionVector(1,2,3);
		EmotionVector vector2 = new EmotionVector(3,2,1);
		EmotionVector.printTemplate();
		vector1.print();
		vector2.print();
		vector1.addVector(vector2);
//		assertEquals(new EmotionVector(4,4,4), vector1);  //this does not work. implement equals-method for vector.
		assertEquals(4, vector1.getValence(),0001);
		assertEquals(4, vector1.getArousal(),0.001);
		assertEquals(4, vector1.getDominance(),0.001);
		assertEquals(true, vector1.equals(new EmotionVector(4,4,4)));
//		assertEquals(Math.sqrt(48), vector1.getLength()); //Floats cannot be compared like this. Delta has to be given.
		assertEquals(Math.sqrt(48), vector1.getLength(), 0.001);
		vector1.print();
		vector1.normalize(4);
		assertEquals(1, vector1.getValence(),0001);
		assertEquals(1, vector1.getArousal(),0.001);
		assertEquals(1, vector1.getDominance(),0.001);
		vector1.print();
		assertEquals(Math.sqrt(48)/4, vector1.getLength(), 0.001);
		vector1.multiplyWithConstant(3);
		vector1.print();
		assertEquals(Math.sqrt(48)/4*3, vector1.getLength(), 0.001);
		//chechs if equals-method of EmotionVector-class works (also with double roundoff errors)
		assertTrue(new EmotionVector(1,2,3).equals(new EmotionVector(1,2,3)));
		assertTrue(new EmotionVector(1,2,3).equals(new EmotionVector(1.000000001,1.999999999,3.0000000005)));
		//checks if normalization works
		System.out.println("testing normalization");
		testVector.normalize(4);
		testVector.print();
		testVectorNormalized.print();
		assertTrue(testVector.equals(testVectorNormalized));
		System.out.println("Normalization works");
	}
	
	@Test
	public void testEmotionLexicon() throws IOException{
		System.out.println("anfang");
		EmotionLexicon lexicon = new EmotionLexicon();
		EmotionVector.printTemplate();
		lexicon.lookUp("AIDS").print();	//delete print-statements before end of project.
		lexicon.lookUp("calm").print();
		lexicon.lookUp("lobotomy").print();
		lexicon.lookUp("lovable").print();
//		lexicon.lookUp("loveable").print(); //cannot be printed because EmotionVector lexicon.lookUp("loveable)=null.
		assertTrue(
				(vectorAIDS.equals(lexicon.lookUp("AIDS"))) &&
				(vectorCalm.equals(lexicon.lookUp("calm"))) &&
				(vectorLobotomy.equals(lexicon.lookUp("lobotomy"))) &&
				(vectorLovable.equals(lexicon.lookUp("lovable"))) &&
			//	(lexicon.neutralVector.equals(lexicon.lookUp("this is not in dictionary")))  //deprecated: I now interprete a not identified word as null
				(lexicon.lookUp("this is not in lexikon")==null)
				);
	}
	
	@Test
	public void testFile2String() throws IOException{
		File2BagOfWords_Processor proc = new File2BagOfWords_Processor();
		String str = proc.file2String(EmotionAnalyzer.TESTFILE);
		System.out.println(str);
		assertEquals("File was read incorrectly.", "fish fish fish.\ntest.\nThisIsNotInLexicon.", str);
	}

}
