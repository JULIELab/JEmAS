package emotionAnalyzer;

import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.HashMultiset;

public class Util {
	
	static void printBagOfWords(HashMultiset<String> bagOfWords){
		Set<String> set = bagOfWords.elementSet();
		for (String str: set){
			System.out.println(str+"\t"+bagOfWords.count(str));
		}
	}

	/**
	 * rewrites a path so it will be correct in a Jar-file.
	 * @param path
	 * @return
	 */
	public static String getJarPath(String path) {
		path = path.substring(4);
		return path;
	}
	
	public static boolean compareFiles(File file1, File file2) throws IOException{
		List<String> list1 = Files.readAllLines(file1.toPath());
		List<String> list2 = Files.readAllLines(file2.toPath());
		if (list1.equals(list2)) return true;
		else return false;	
	}
	
	public static String getJarLocation (String formerLocation){
		final Map<String, String> map = new HashMap<String, String>(){/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
			put(Util.DEFAULTLEXICON, "/emotionAnalyzer/LexiconWarriner2013_transformed.txt");
		}};
		return map.get(formerLocation);
		
	}
	
//	/**
//	 * Decides if the given token is a letter word (meaning it consists purely of letters).
//	 * @param currentToken
//	 * @return
//	 */
//	public static boolean isLetterToken(String givenToken) {
//		return givenToken.matches("\\p{L}+");
//	}
	
	public static String readfile2String(String path) throws IOException{
		List<String> lines = Files.readAllLines(Paths.get(path));
		String output = "";
		for (String line: lines){
			if (!output.isEmpty()) output=output+"\n";
			output=output+line;
		}
		return output;
	}
	
	public static void writeList2File(List<String> list, String path) throws IOException{
		FileWriter writer = new FileWriter(path); 
		for (String line : list){
			writer.write(line+'\n');
		}
		writer.close();
	}
	
	public static double stdev (double[] sample){
		return Math.sqrt(Util.var(sample));
	}
	
	public static double average (double[] sample){
		double result = 0.0;
		for (int i = 0; i<sample.length; i++){
			result = result+sample[i];
		}
		return result/(double)sample.length;
	}
	
	public static double var(double[]sample){
		double result = 0.0;
		double mean = Util.average(sample);
		for (int i=0; i<sample.length; i++){
			result = result + Math.pow(sample[i]-mean, 2);
		}
		
		return result/sample.length;
	}
	
	
	public final  static Settings defaultSettings = new Settings(Preprocessing.LEMMATIZE, false);
	public static final Settings settings_tokenize = new Settings(Preprocessing.TOKENIZE, false);
	public static final Settings settings_stem = new Settings(Preprocessing.STEM, false);
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
	public static final String STOPWORDLIST_NLTK = "resources/NLTK_stopwords_English.txt";
	public static final String TESTFOLDER2 ="src/emotionAnalyzer/testFolder2";
	public static final String TESTFOLDER ="src/emotionAnalyzer/testFolder";
	public static final String STOPWORDLIST = "resources/Stopwords_lemmatisiert_nicht_im_Lexikon.txt";


}
