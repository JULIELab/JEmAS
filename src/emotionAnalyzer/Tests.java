package emotionAnalyzer;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import porterStemmer.PorterStemmerWrapper;
import stanford_lemmatizer.StanfordLemmatizer;


public class Tests {
	final EmotionVector vectorAIDS = new EmotionVector(-3.67, 0.0, -1.45);
	final EmotionVector vectorCalm = new EmotionVector(1.89, -3.33, 2.44);
	final EmotionVector vectorLobotomy = new EmotionVector(-2.55, 0.32, -3.0);
	final EmotionVector vectorLovable = new EmotionVector(3.26, 0.41, 1.83);
	final EmotionVector testVector = new EmotionVector(4,5,6); //to test the calculated emotion vector of testFile.txt when using testLexicon.txt
	final EmotionVector testVectorNormalized = new EmotionVector(1.0, 5.0/4.0, 6.0/4.0);
	final EmotionVector testVector2 = new EmotionVector(-8.43, -3.75, -7.04);
	final EmotionVector testVectorNormalized2 = new EmotionVector(-8.43/6.0, -3.75/6.0, -7.04/6.0); //this vector should be the normalized (divided by number of found lexicon entries) document vector of testFile2.txt using defaultLexicon.
	final StanfordLemmatizer lemmatizer = new StanfordLemmatizer();
	
//	/**
//	 * 
//	 * @return Returns A HashMultiSet containing the expected tokens from testFile.txt
//	 */
//	public HashMultiset<String> getTestBagOfWords(){
//		HashMultiset<String> testBagOfWords = HashMultiset.create();
//		//number of identified tokens (normalization parameter
//		String[] strArray = {"test", "fish", "fish", "fish", ".",".",".", "ThisIsNotInLexicon"};
//		for (String str: strArray){
//			testBagOfWords.add(str);}
//		return testBagOfWords;
//	}
	
//	/**
//	 * 
//	 * @return Returns A HashMultiSet containing the stemmed forms of testFile3.txt
//	 */
//	public HashMultiset<String> getTestBagOfWords_Stem(){
//		HashMultiset<String> testBagOfWords = HashMultiset.create();
//		String[] strArray = {"veri", "outsid", "happi", "go", "I", "to", "am", "."};
//		for (String str: strArray){
//			testBagOfWords.add(str);}
//		return testBagOfWords;
//	}
	
//	/**
//	 * 
//	 * @return Returns A HashMultiSet containing the lemmatized forms of testFile3.txt
//	 */
//	public HashMultiset<String> getTestBagOfWords_Lemma(){
//		HashMultiset<String> testBagOfWords = HashMultiset.create();
//		String[] strArray = {"very", "be", "outside", "happy", "go", "I", "to", "."};
//		for (String str: strArray){
//			testBagOfWords.add(str);}
//		return testBagOfWords;
//	}
	
	
//	/**
//	 * Tests if production of a token-based bag of words from a file works. (method: File2BagOfWords_Processor.produceBagOfWords_Token).
//	 * @throws IOException
//	 */
//	@Test
//	public void testFile2Tokens() throws IOException  {
//		File2BagOfWords_Processor reader = new File2BagOfWords_Processor();
//		HashMultiset<String> bagOfWords = reader.produceBagOfWords_Token(Util.TESTFILE);
////		Util.printBagOfWords(bagOfWords);
////		Util.printBagOfWords(getTestBagOfWords());
////		assertTrue(bagOfWords.equals(getTestBagOfWords()));
//		assertEquals("Error in tokenization! Produced bag of words differs from TestBagOfWords.", true, bagOfWords.equals(getTestBagOfWords()));
//	}
	
//	
//	/**
//	 * Tests if the production of a lemma-based bag of words from a file works. (method: File2BagOfWords_Processor.produceBagOfWords_Lemma )
//	 * @throws IOException
//	 */
//	@Test
//	public void testFile2Lemma() throws IOException{
//		File2BagOfWords_Processor processor = new File2BagOfWords_Processor();
//		HashMultiset<String> bagOfWords = processor.produceBagOfWords_Lemma(Util.TESTFILE3);
////		Util.printBagOfWords(bagOfWords);
//		assertEquals("Error in Lemmatization", getTestBagOfWords_Lemma(), bagOfWords);
//	}
//	
//	/**
//	 * Tests if the production of a stem-based bag of words from a file works. (method: File2BagOfWords_Processor.produceBagOfWords_Stem )
//	 * @throws IOException
//	 */
//	@Test
//	public void testFile2Stem() throws IOException{
//		File2BagOfWords_Processor processor = new File2BagOfWords_Processor();
//		HashMultiset<String> bagOfWords = processor.produceBagOfWords_Stems(Util.TESTFILE3);
////		Util.printBagOfWords(bagOfWords); 
//		assertEquals("Error in stemming", getTestBagOfWords_Stem(), bagOfWords);
//	}
	
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
//
//	/**
//	 * Tests if calculation of document emotion vector works when a bag of words is already given a) without normalizaton and b) with normalization
//	 * @throws IOException
//	 */
//	@Test
//	public void testToken2Vectorizer() throws IOException {
//		EmotionLexicon lexicon = new EmotionLexicon(Util.TESTLEXICON);
//		// initiates an instance of Token2Vectorizer with test lexicon
//		BagOfWords2Vector_Processor testVectorizer = new BagOfWords2Vector_Processor();
//		VectorizationResult result = testVectorizer.calculateDocumentVector(
//				getTestBagOfWords(), lexicon, Util.defaultSettings);
//		EmotionVector documentVector = result.getEmotionVector();
//		//tests result before normalization
//		assertEquals("Fehler bei der Vektorberechnung ohne Normalisierung.", true , documentVector.equals(testVector));
//		
//		// tests mode with normalization
//		result = testVectorizer.calculateDocumentVector(getTestBagOfWords(),
//				lexicon, Util.defaultSettings);
//		documentVector = result.getEmotionVector();
//		VectorNormalizer normalizer = new VectorNormalizer();
//		documentVector = normalizer.calculateNormalizedDocumentVector(
//				documentVector, 4); // 4 is the expected normalization parameter
//									// for the testing bag of words.
//		assertEquals("Failure at normalization of calculated bag of words. Acutal vector differs from expected.", true, documentVector.equals(testVectorNormalized));
//	}
		


//	/**
//	 * Tests whole analysis with lemmatized bag of words.
//	 * @throws IOException
//	 */
//	@Test
//	public void testEmotionAnalyzer_Lemmatize() throws IOException{
//		EmotionAnalyzer analyzer = new EmotionAnalyzer(Util.TESTLEXICON_LEMMA);
//		DocumentContainer container = analyzer.analyze(Util.TESTFILE_LEMMA, new Settings(Preprocessing.LEMMATIZE,false));
//		EmotionVector documentVector = container.getNormalizedEmotionVector();
////		documentVector.print();
//		EmotionVector testVector =new EmotionVector(1.0/3, 1.0/3, 1.0/3);
////		testVector.print();
//		assertEquals("Wrong result of analysis with lemmatizer.", true, testVector.equals(documentVector));
//	}
//	
	
//	/**
//	 * Tests whole analysis with token-based bag of words without normalization of vector.
//	 * @throws IOException
//	 */
//	@Test
//	public void testEmotionAnalyzer_Tokenize() throws IOException{
//		EmotionAnalyzer analyzer = new EmotionAnalyzer(Util.TESTLEXICON);
//		DocumentContainer documentContainer= analyzer.analyzeEmotions(Util.TESTFILE, Util.settings_tokenize);
//		EmotionVector documentVector = documentContainer.getSumOfVectors();
////		documentVector.print();
////		documentContainer.getNormalizedEmotionVector().print();
////		testVector.print();
//		assertTrue(documentVector.equals(testVector));
//		
//	}
	
//	/**
//	 * Another testing of the whole analysis (still without normalization) but with the use of the default lexicon.
//	 * @throws IOException
//	 */
//	@Test
//	public void testEmotionAnalyzer2_Tokenize() throws IOException{
//		EmotionAnalyzer analyzer = new EmotionAnalyzer(Util.DEFAULTLEXICON);
//		DocumentContainer documentContainer= analyzer.analyzeEmotions(Util.TESTFILE2, Util.settings_tokenize);
//		EmotionVector documentVector = documentContainer.getSumOfVectors();
//		assertTrue(documentVector.equals(testVector2));
//		
//	}
//	
//	/**
//	 * Tests the whole analysis with a token-based bag of words. Normalization is also checked this time.
//	 * @throws IOException
//	 */
//	@Test
//	public void testEmotionAnalyzerNormalized_Tokenize() throws IOException{
//		EmotionAnalyzer analyzer = new EmotionAnalyzer(Util.TESTLEXICON);
//		DocumentContainer documentContainer = analyzer.analyzeEmotions(Util.TESTFILE, Util.settings_tokenize);
//		EmotionVector documentVector = 	documentContainer.getNormalizedEmotionVector();
//		assertTrue(documentVector.equals(testVectorNormalized));
//	}
	
//	
//	/**
//	 * Another testing of the whole analysis using a token-based bag-of words. Normalization is performed.
//	 * @throws IOException
//	 */
//	@Test
//	public void testEmotionAnalyzerNormalized2_Tokenize() throws IOException{
//		EmotionAnalyzer analyzer = new EmotionAnalyzer(Util.DEFAULTLEXICON);
//		DocumentContainer documentContainer= analyzer.analyzeEmotions(Util.TESTFILE2, Util.settings_tokenize);
//		EmotionVector documentVector = documentContainer.getNormalizedEmotionVector();
//		assertTrue(documentVector.equals(testVectorNormalized2));
//		
//	}
	
	@Test
	public void testLemmatizeToken(){
		String input = "pancake";
		System.out.println(lemmatizer.lemmatizeToken(input));
		input = "pancakes";
		System.out.println(lemmatizer.lemmatizeToken(input));
		
	}
	
	
	/** 
	 * Tests functions of EmotionVector class: addition, equals, getters,
	 */
	@Test
	public void testEmotionVector() {
		EmotionVector vector1 = new EmotionVector(1,2,3);
		EmotionVector vector2 = new EmotionVector(3,2,1);
		//adding vector1 and 2
		vector1.addVector(vector2);
		//checking addition
		assertEquals(4, vector1.getValence(),0001);
		assertEquals(4, vector1.getArousal(),0.001);
		assertEquals(4, vector1.getDominance(),0.001);
		//checking .equals-method
		assertEquals(true, vector1.equals(new EmotionVector(4,4,4)));
		//checking getLength (sqrt(48) = length of vector 4,4,4)
		assertEquals(Math.sqrt(48), vector1.getLength(), 0.001);
		//tests normalization
		vector1.normalize(4);
		assertEquals(1, vector1.getValence(),0001);
		assertEquals(1, vector1.getArousal(),0.001);
		assertEquals(1, vector1.getDominance(),0.001);
		assertEquals(Math.sqrt(48)/4, vector1.getLength(), 0.001);
		//test multiplication
		vector1.multiplyWithConstant(3);
		assertEquals(Math.sqrt(48)/4*3, vector1.getLength(), 0.001);
		//checks if equals-method of EmotionVector-class works (also with double roundoff errors)
		assertTrue(new EmotionVector(1,2,3).equals(new EmotionVector(1,2,3)));
		assertTrue(new EmotionVector(1,2,3).equals(new EmotionVector(1.000000001,1.999999999,3.0000000005)));
		//again testing normalization
		testVector.normalize(4);
		assertTrue(testVector.equals(testVectorNormalized));
		vector1 = new EmotionVector(1,2,3);
		vector2 = new EmotionVector(3,2,1);
		List<EmotionVector> vectorList = Arrays.asList(new EmotionVector[]{vector1,vector2});
		for (EmotionVector vec: vectorList){
			vec.print();
		}
		//mean
		EmotionVector.calculateMean(vectorList).print();
		assertEquals(true, new EmotionVector(2,2,2).equals(EmotionVector.calculateMean(vectorList)));
		//sd
		assertEquals(true, new EmotionVector(1,0,1).equals( 
				EmotionVector.calculateStandardDeviation(vectorList, EmotionVector.calculateMean(vectorList))));
		assertEquals(true, new EmotionVector(1,0,1).equals(
				EmotionVector.calculateStandardDeviation(vectorList)));
		//min
		assertEquals(true, new EmotionVector(1,2,1).
				equals(EmotionVector.calculateMinimumVector(vectorList)));
		//max
		assertEquals(true, new EmotionVector(3,2,3).
				equals(EmotionVector.calculateMaximumVector(vectorList)));
		
	}
	
	/**
	 * Test the functionality of the EmotionLexicon class, especially the correct look-up.
	 * @throws IOException
	 */
	@Test
	public void testEmotionLexicon() throws IOException{
		EmotionLexicon lexicon = new EmotionLexicon(Util.DEFAULTLEXICON, lemmatizer);
		assertTrue(
				//(vectorAIDS.equals(lexicon.lookUp("AIDS"))) && //to to caps-folding and lemma-folding, "AIDS" is no longer in lexicon
				(vectorCalm.equals(lexicon.lookUp("calm"))) &&
				(vectorLobotomy.equals(lexicon.lookUp("lobotomy"))) &&
				(vectorLovable.equals(lexicon.lookUp("lovable"))) &&
				(lexicon.lookUp("this is not in lexikon")==null)
				);
	}
	
	
	
	/**
	 * Tests the method .file2String of File2BagOfWords_Processor.
	 * @throws IOException
	 */
	@Test
	public void testFile2String() throws IOException{
		String str;
		str = Util.readfile2String(Util.TESTFILE);
	
		assertEquals("File was read incorrectly.", "fish fish fish.\ntest.\nThisIsNotInLexicon.", str);
	}
	
	
	/**
	 *Die hier getestete Funktion wird scheinbar gar nicht benötigt.
	 */
//	/**
//	 * Tests the static function Util.isLetterToken.
//	 */
//	@Test
//	public void testIsLetterToken (){
//		boolean boo;
//		boo = Util.isLetterToken("314345");
//		assertEquals("Wrong result.", false, boo);
//		boo = Util.isLetterToken("look-up");
//		assertEquals("Wrong result.", false, boo);
//		boo = Util.isLetterToken("house");
//		assertEquals("Wrong result.", true, boo);
//		boo = Util.isLetterToken("Allé");
//		assertEquals("Wrong result.", true, boo);
//		boo = Util.isLetterToken(".");
//		assertEquals("Wrong result.", false, boo);
//	}
	
	
	
	//Stemming is currently not in use.
//	/**
//	 * Tests the method stemLexicon of EmotionLexicon class.
//	 * @throws IOException
//	 */
//	@Test
//	public void testStemLexikon () throws IOException{
//		EmotionLexicon oldLexicon = new EmotionLexicon(Util.TESTLEXICON_STEMMER);
//		EmotionLexicon newLexicon = oldLexicon.stemLexicon();
//		//testing
//		PorterStemmerWrapper stemmer = new PorterStemmerWrapper();
//		//for every key in the old lexicon...
//		for (String currentKey: oldLexicon.getKeySet()){
//			//... check if the value of the key in the old lexicon and the value of the stemmed key in the new lexicon are the same
//			assertEquals("Different values", oldLexicon.lookUp(currentKey) , newLexicon.lookUp(stemmer.stem(currentKey)));
//		}
//		
//	}
	
	
//	/**
//	 * Tests the whole analysis with a stemming-based bag of words.
//	 * @throws IOException
//	 */
//	@Test
//	public void testEmotionAnalyzer_Stem () throws IOException{
//		EmotionAnalyzer analyzer = new EmotionAnalyzer(Util.TESTLEXICON_STEMMER);
//		DocumentContainer output = analyzer.analyzeEmotions(Util.TESTFILE_STEM, new Settings(Preprocessing.STEM, false));		
//		assertEquals("Wrong result of emotion analysis with stemmer.",
//				true,
//				output.getNormalizedEmotionVector().equals(new EmotionVector(2, 2, 2)));
//	}
	
//	/**
//	 * Tests the option printIdentifiedTokens of EmotionAnalyzer.analyze by redirecting the standard output into temp file and compare it to another file.
//	 * @throws IOException
//	 */
//	@Test
//	public void testPrintIdentifiedTokens() throws IOException{
//		EmotionAnalyzer analyzer = new EmotionAnalyzer(Util.TESTLEXICON);
//		PrintStream originalOut = System.out;
//		File outputFile = File.createTempFile("temp", "txt");
//		PrintStream newOut = new PrintStream(outputFile);		
//		//redirect output
//		System.setOut(newOut);
//		analyzer.analyzeEmotions(Util.TESTFILE, new Settings(Preprocessing.LEMMATIZE, true));		
//		//back to normal
//		System.setOut(originalOut);
//		assertEquals("Output differs from expected one.",true , Util.compareFiles(outputFile, new File("src/emotionAnalyzer/testOutput_testPrintIdentifiedTokens.txt")));		
//	}
	
	
	/**
	 * Tests if the printed output of the tool is correct. 
	 * @throws IOException
	 */
//	@Test
//	public void testPrintedOutput() throws IOException{
//		PrintStream originalStream = System.out;
//		File output = File.createTempFile("temp", "txt");
//		File testOutput = new File("src/emotionAnalyzer/testOutput_testPrintedOutput.txt");
//		PrintStream newOut = new PrintStream(output);
//		//redirect output
//		System.setOut(newOut);
//		EmotionAnalyzer_UI.main(new String[]{"-file", Util.TESTFILE_LEMMA});
//		//switch output back to normal
//		System.setOut(originalStream);
//		assertEquals("Wrong printed output! Output differs from predefined test output.", true, Util.compareFiles(output, testOutput));
//	}
//	
//	@Test
//	public void testEmotionAnaylzer2() throws Exception{
//		EmotionAnalyzer_UI.main(new String[]{Util.TESTFOLDER2});
//	}
	
	
	/**
	 * Tests the output, this tool should produce.
	 * @throws Exception
	 */
	@Test
	public void testEmotionAnaylzer_UI() throws Exception{
		PrintStream originalStream = System.out;
		File acutalOutput = new File(Util.ACTUALOUTPUT);
		File expectedOutput =new File(Util.EXPECTEDOUTPUT);
		// if this file does not exist, the test will (hopefully) be run from inside a jar. In this case, create a new 
		if (!expectedOutput.exists()){
			expectedOutput = new File("testOutput_testPrintedOutput.txt");
			Util.writeList2File(Util.readFile2List(Util.getJarPath("src/emotionAnalyzer/testOutput_testPrintedOutput.txt")), expectedOutput.getPath());
			}
		PrintStream newOut = new PrintStream(acutalOutput);
		//redirect output
		System.setOut(newOut);
		 //this works in IDE
		File testfolder = new File(Util.TESTFOLDER);
		File targetFolder = new File(Util.TARGETFOLDER);
		// if not in IDE, checks for the folder in the filesystem (in working directory)
//		if (!testfolder.exists()) testfolder = new File("testFolder");
		// if the folder does not exists in the filesystem, create and fill it.
		if (!testfolder.exists()){
//			System.err.println("********** NO TESTFOLDER. CREATING IT.");
			testfolder = new File("testFolder");
			testfolder.mkdir();
			Util.writeList2File(Util.readFile2List(Util.getJarPath(Util.TESTFILE)), "testFolder/test.test.test.testFile.txt");
			Util.writeList2File(Util.readFile2List(Util.getJarPath(Util.TESTFILE2)), "testFolder/test.test.test.testFile2.txt");
			Util.writeList2File(Util.readFile2List(Util.getJarPath(Util.TESTFILE3)), "testFolder/type.origin.enterprise.year2000.txt");
			Util.writeList2File(Util.readFile2List(Util.getJarPath(Util.TESTFILE4)), "testFolder/type.origin.enterprise.year1999.txt");
		}
		EmotionAnalyzer_UI.main(new String[]{testfolder.getPath(), targetFolder.getPath()});
		//switch output back to normal
		System.setOut(originalStream);
		//test
		assertEquals("Wrong printed output! Output differs from predefined test output.", true, Util.compareFiles(acutalOutput, expectedOutput));
	}
		
	
	@Test
	public void testUtilStdev(){
		double[] sample ={1,2,3,4,5,6,7,8,9,};
		double expected = 2.5819888975;
		assertEquals(expected, Util.stdev(sample), 0.00000001);
	}
	
	@Test
	public void testUtilAverage(){
		double[] sample = {1,2,3,4,5,6,7,8,9,};
		double expected = 5;
		assertEquals(expected, Util.average(sample), 0.000000001);
	}
	
	@Test
	public void testUtilVariance(){
		double[] sample = {1,2,3,4,5,6,7,8,9,};
		double expected = 6.666666666666666;
		assertEquals(expected, Util.var(sample), 0.000000001);
	}
	
	
	/**
	 * Tests the whole tool.
	 * @throws Exception
	 */
	@Test
	public void testEmotionAnalyzer() throws Exception{
		EmotionAnalyzer analyzer;
		try {
			analyzer = new EmotionAnalyzer(Util.DEFAULTLEXICON);
		} catch (Exception e1) {
			analyzer = new EmotionAnalyzer(Util.getJarPath(Util.DEFAULTLEXICON));

		}
		DocumentContainer[] containers;
		File testfolder = new File(Util.TESTFOLDER);
		File targetFolder = new File(Util.TARGETFOLDER);
		if (!testfolder.exists()){
			testfolder = new File("testFolder");
			testfolder.mkdir();
			Util.writeList2File(Util.readFile2List(Util.getJarPath(Util.TESTFILE)), "testFolder/test.test.test.testFile.txt");
			Util.writeList2File(Util.readFile2List(Util.getJarPath(Util.TESTFILE2)), "testFolder/test.test.test.testFile2.txt");
			Util.writeList2File(Util.readFile2List(Util.getJarPath(Util.TESTFILE3)), "testFolder/type.origin.enterprise.year2000.txt");
			Util.writeList2File(Util.readFile2List(Util.getJarPath(Util.TESTFILE4)), "testFolder/type.origin.enterprise.year1999.txt");

		}
		
		containers = analyzer.analyze(testfolder, targetFolder, Util.defaultSettings);
		
		DocumentContainer container;
		
		
		/**
		 * Checking testfile3 ("NothingInLexikon") 
		 * this is for checking if EmotionAnalyzer produces 0,0,0 instead of NaN if no token
		 * has been recognized.
		 */
		container = containers[2];
//		System.out.println(container.)
		//check emotion vector
		assertEquals(true, container.documentEmotionVector.equals(new EmotionVector(0.0, 0.0, 0.0)));
		//check standard deviation vector
//		container.standardDeviationVector.print();
		assertEquals(true, container.standardDeviationVector.equals(new EmotionVector(0.0,0.0,0.0)));
		//ckeck token count
		assertEquals(1, container.tokenCount);
		//check alphabetic tokens
		assertEquals(1, container.alphabeticTokenCount);
		//check non-stopword tokens
		assertEquals(1, container.non_stopword_tokenCount);
		//check recognized tokens
		assertEquals(0, container.recognizedTokenCount);
		
		
		/**
		 * Checking testfile4 ("I love to eat cake and icecream")
		 */
		container = containers[3];
//		System.out.println(container.)
		//check emotion vector
//		container.documentEmotionVector.print();
		assertEquals(true, container.documentEmotionVector.equals(new EmotionVector(2.5016, 0.0616666, 1.4799999)));
		//check standard deviation vector
//		container.standardDeviationVector.print();
		assertEquals(true, container.standardDeviationVector.equals(new EmotionVector(0.301118, 0.485632, 0.577581)));
		//ckeck token count
		assertEquals(13, container.tokenCount);
		//check alphabetic tokens
		assertEquals(7, container.alphabeticTokenCount);
		assertEquals(3, container.numberCount);
		//check non-stopword tokens
		assertEquals(4, container.non_stopword_tokenCount);
		//check recognized tokens
		assertEquals(3, container.recognizedTokenCount);
		//check report's attributes
//		assertEquals("type", container.reportCategory);
//		assertEquals("origin", container.origin);
//		assertEquals("enterprise", container.organization);
//		assertEquals("1999", container.year);
		
		/**
		 * checking testfile3 ("I am very happy to go outside.")
		 */
		container = containers[4];
		//check emotion vector
		assertEquals(false, container.documentEmotionVector.equals(new EmotionVector(1.99, -0.22, 1.012)));
		assertEquals(true, container.documentEmotionVector.equals(new EmotionVector(1.99, -0.22, 1.01333)));
		//check standard deviation vector
		assertEquals(true, container.standardDeviationVector.equals(new EmotionVector(1.0480776053, 1.071105348, 0.8490124983)));
		//ckeck token count
		assertEquals(12, container.tokenCount);
		//check alphabetic tokens
		assertEquals(7, container.alphabeticTokenCount);
		//check non-stopword tokens
		assertEquals(4, container.non_stopword_tokenCount);
		//check recognized tokens
		assertEquals(3, container.recognizedTokenCount);
		//check report's attributes
//		assertEquals("type", container.reportCategory);
//		assertEquals("origin", container.origin);
//		assertEquals("enterprise", container.organization);
//		assertEquals("2000", container.year);
		/**
		 * checking testfile ("fish fish fish...")
		 */
		container = containers[0];
		//check emotion vector
		assertEquals(true, container.documentEmotionVector.equals(new EmotionVector(0.925,-1.4275, 0.575)));
		//check standard deviation vector
		assertEquals(true, containers[0].standardDeviationVector.equals(new EmotionVector(0.8573651497,0.4200223208, 0.8746856578)));
		//ckeck token count
		assertEquals(8, container.tokenCount);
		//check alphabetic tokens
		assertEquals(5, container.alphabeticTokenCount);
		//check non-stopword tokens
		assertEquals(5, container.non_stopword_tokenCount);
		//check recognized tokens
		assertEquals(4, container.recognizedTokenCount);
		//check report's attributes
//		assertEquals("test", container.reportCategory);
//		assertEquals("test", container.origin);
//		assertEquals("test", container.organization);
//		assertEquals("testFile", container.year);
		/**
		 * checking testfile2 (AIDS, calm, lobotomy,...)
		 */
		container=containers[1];
		//check emotion vector
		assertEquals(false, container.documentEmotionVector.equals(new EmotionVector(1.99, -0.22, 1.012)));
//		container.documentEmotionVector.print();
		assertEquals(true, container.documentEmotionVector.equals(new EmotionVector(-0.952, -0.75,-1.118)));
		//check standard deviation vector
//		container.standardDeviationVector.print();
		assertEquals(true, container.standardDeviationVector.equals(new EmotionVector(2.1776537833181835, 2.1260950119879403 ,2.0780991314179404)));
		//ckeck token count
		assertEquals(7, container.tokenCount);
		//check alphabetic tokens
		assertEquals(6, container.alphabeticTokenCount);
		//check non-stopword tokens
		assertEquals(6, container.non_stopword_tokenCount);
		//check recognized tokens
		/**
		 * Hier zeigt das System einen kleinen Fehler. 
		 * Das Wort "aids" im Dokument wird dort scheinbar anders lemmatisiert als im Lexikon,
		 * mit dem Effekt, dass es nicht mehr erkannt wird.
		 */
		assertEquals(5, container.recognizedTokenCount);
		//check report's attributes
//		assertEquals("test", container.reportCategory);
//		assertEquals("test", container.origin);
//		assertEquals("test", container.organization);
//		assertEquals("testFile2", container.year);
		/**
		 * checking vocabulary
		 */
		String[] expected = new String[]{"love", "leukemia", "be", "test", "happy", "go", "librarian", "earthquake", "calm", "icecream", "cake", "outside", "fish", "lobotomy", "eat", "aids","thisisnotinlexicon", "nothinginlexikon", };
		String[] actual = analyzer.getVocabulary().asArray();
		assertArrayEquals(expected, actual);
	}
	
	
	/**
	 * Tests method which removes tokens which do not include a letter.
	 */
	@Test
	public void testFilterNonAlphabetics(){
		NonAlphabeticFilter filter = new NonAlphabeticFilter();
		String[] input = {"123", "123a", "a-b", ".", "djfkdjf", "apple", "tree", "mushroom", "Mr. President"};
		String[] expected = {"a-b","djfkdjf", "apple", "tree", "mushroom", "Mr. President"};
		List<String> inputList = Arrays.asList(input);
		List<String> actualList = filter.filter(inputList);
		String[] actual = actualList.toArray(new String[actualList.size()]);
		assertArrayEquals(expected, actual);
	}
	
	//TODO remove, not in use anymore
//	/**
//	 * Test the method in DocumentContainer which gets the attributes of a report out of the files name.
//	 */
//	@Test
//	public void testDocumentContainerGetDocumentAttributes(){
//		String input;
//		String[] expected;
//		String[] actual;
//		//normal case, everything is fine here.
//		input = "Annual_Reports.Dax.Adidas.1996.txt";
//		expected = new String[]{"Annual_Reports", "Dax", "Adidas", "1996", "txt"};
//		actual = DocumentContainer.getDocumentAttributes(input);
//		assertArrayEquals(expected, actual);
//		//everything is normal here, too
//		input = "Annual_Reports.FTSE.WPP.2006.txt";
//		expected = new String[]{"Annual_Reports", "FTSE", "WPP","2006", "txt" };
//		actual = DocumentContainer.getDocumentAttributes(input);
//		assertArrayEquals(expected, actual);
//		//too many parts caused by additional suffix
//		input = "Annual_Reports.DowJones.Johnson_Johnson.2009.PDF.txt";
//		expected = new String[]{"Annual_Reports", "DowJones","Johnson_Johnson","2009", "txt"}; 
//		actual = DocumentContainer.getDocumentAttributes(input);
//		assertArrayEquals(expected, actual);
//		//too many parts caused by E.ON
//		input = "CSR_Reports.Dax.EON.E.ON_CR_Report_2004.txt";
//		expected = new String[] {"CSR_Reports","Dax","EON", "2004", "txt" };	
//		actual = DocumentContainer.getDocumentAttributes(input);
//		assertArrayEquals(expected, actual);
//		//wrong year format
//		input = "CSR_Reports.Dax.Muenchener_Rueck.MunichRe_CSR_2011.txt";
//		expected = new String[]{"CSR_Reports", "Dax", "Muenchener_Rueck","2011", "txt"};
//		actual = DocumentContainer.getDocumentAttributes(input);
//		assertArrayEquals(expected, actual);
//		//another wrong year format
//		input= "CSR_Reports.FTSE.WPP.WPP_201314.txt";
//		expected = new String[]{"CSR_Reports","FTSE","WPP", "2013", "txt"};
//		actual = DocumentContainer.getDocumentAttributes(input);
//		assertArrayEquals(expected, actual);
//
//	}
	
	@Test
	public void testNumberFilter(){
		List<String> testList = Arrays.asList(new String[]{"a", "b", "test", "133", "1", "foo", "bar", "000", "$12", "$12.50", "£12", 
				"£12.50", "€12.50","€12foo.50", "123.9382", "145.246345%", "23/45/2424"});
		List<String> actualList = new NumberFilter().filter(testList);
		List<String> expectedList = Arrays.asList(new String[]{"a", "b", "test", "foo", "bar", "€12foo.50"});
		assertArrayEquals(expectedList.toArray(new String[expectedList.size()]), actualList.toArray(new String[actualList.size()]));
//		for (String line: actualList) System.out.println(line);
	}
	
	
}
