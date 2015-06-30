package rumprobieren;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.HashMultiset;

public class TestsRumprobieren {
	final EmotionVector vectorAIDS = new EmotionVector(-3.67, 0.0, -1.45);
	final EmotionVector vectorCalm = new EmotionVector(1.89, -3.33, 2.44);
	final EmotionVector vectorLobotomy = new EmotionVector(-2.55, 0.32, -3.0);
	final EmotionVector vectorLovable = new EmotionVector(3.26, 0.41, 1.83);
	final EmotionVector testVector = new EmotionVector(4,5,6); //to test the calculated emotion vector of testFile.txt when using testLexicon.txt
	final EmotionVector testVectorNormalized = new EmotionVector(1.0, 5.0/4.0, 6.0/4.0);
	final EmotionVector[] testVectors = {vectorAIDS, vectorCalm, vectorLobotomy, vectorLovable, EmotionAnalyzer.neutralVector};
	final EmotionVector testVector2 = new EmotionVector(-8.43, -3.75, -7.04);
	final EmotionVector testVectorNormalized2 = new EmotionVector(-8.43/6.0, -3.75/6.0, -7.04/6.0); //this vector should be the normalized (divided by number of found lexicon entries) document vector of testFile2.txt using defaultLexicon.
	
	public HashMultiset<String> getTestBagOfWords(){
		HashMultiset<String> testBagOfWords = HashMultiset.create();
		String[] strArray = {"test", "fish", "fish", "fish", ".",".",".", "ThisIsNotInLexicon"};
		for (String str: strArray){
			testBagOfWords.add(str);}
		return testBagOfWords;
	}
	
	@Test
	public void testFile2Tokens() throws IOException  {
		File2TokenReader reader = new File2TokenReader();
		HashMultiset<String> bagOfWords = reader.produceBagOfWords("src/rumprobieren/testFile.txt");
		Util.printBagOfWords(bagOfWords);
		Util.printBagOfWords(getTestBagOfWords());
		assertTrue(bagOfWords.equals(getTestBagOfWords()));
	}
	
	@Test
	public void testToken2Vectorizer() throws IOException{
		Token2Vectorizer testVectorizer = new Token2Vectorizer(new EmotionLexicon(EmotionAnalyzer.TESTLEXICON)); //initiates an instance of Token2Vectorizer with test lexicon
		EmotionVector documentVector = testVectorizer.calculateDocumentVector(getTestBagOfWords(), true,false);
		assertTrue(documentVector.equals(testVector));
		//tests mode with normalization
		documentVector = testVectorizer.calculateDocumentVector(getTestBagOfWords(), true, true);
		assertTrue(documentVector.equals(testVectorNormalized));
		System.out.println("Normalization works");
		
		
		
	}
		


		@Test
	public void testEmotionAnalyzer() throws IOException{
		EmotionAnalyzer analyzer = new EmotionAnalyzer(EmotionAnalyzer.TESTLEXICON);
		EmotionVector documentVector= analyzer.calculateEmotionVector(EmotionAnalyzer.TESTFILE,true,false);
		assertTrue(documentVector.equals(testVector));
		documentVector.print();
	}
	
	@Test
	public void testEmotionAnalyzer2() throws IOException{
		EmotionAnalyzer analyzer = new EmotionAnalyzer(EmotionAnalyzer.DEFAULTLEXICON);
		//this is now done by the second parameter of calculateEmotionVector (printing all found dictionary entries	
		//		analyzer.lexicon.lookUp("AIDS").print();
//		analyzer.lexicon.lookUp("calm").print();
//		analyzer.lexicon.lookUp("lobotomy").print();
//		analyzer.lexicon.lookUp("leukemia").print();
//		analyzer.lexicon.lookUp("librarian").print();
//		analyzer.lexicon.lookUp("earthquake").print();
		
		EmotionVector documentVector= analyzer.calculateEmotionVector(EmotionAnalyzer.TESTFILE2,true,false);
		documentVector.print();
		assertTrue(documentVector.equals(testVector2));
		
	}
	
	@Test
	public void testEmotionAnalyzerNormalized() throws IOException{
		EmotionAnalyzer analyzer = new EmotionAnalyzer(EmotionAnalyzer.TESTLEXICON);
		System.out.println("used lexicon:");
		analyzer.showLexicon();
		EmotionVector documentVector = analyzer.calculateEmotionVector(EmotionAnalyzer.TESTFILE);
		assertTrue(documentVector.equals(testVectorNormalized));
		System.out.println("\ndocument vector:");
		documentVector.print();
	}
	
	@Test
	public void testEmotionAnalyzerNormalized2() throws IOException{
		EmotionAnalyzer analyzer = new EmotionAnalyzer(EmotionAnalyzer.DEFAULTLEXICON);
//		analyzer.lexicon.lookUp("AIDS").print();
//		analyzer.lexicon.lookUp("calm").print();
//		analyzer.lexicon.lookUp("lobotomy").print();
//		analyzer.lexicon.lookUp("leukemia").print();
//		analyzer.lexicon.lookUp("librarian").print();
//		analyzer.lexicon.lookUp("earthquake").print();	
		EmotionVector documentVector= analyzer.calculateEmotionVector(EmotionAnalyzer.TESTFILE2);
		documentVector.print();
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
		lexicon.lookUp("loveable").print();
		assertTrue(
				(vectorAIDS.equals(lexicon.lookUp("AIDS"))) &&
				(vectorCalm.equals(lexicon.lookUp("calm"))) &&
				(vectorLobotomy.equals(lexicon.lookUp("lobotomy"))) &&
				(vectorLovable.equals(lexicon.lookUp("lovable"))) &&
				(lexicon.neutralVector.equals(lexicon.lookUp("this is not in dictionary"))) 
				);
	}

}
