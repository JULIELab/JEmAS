package emotionAnalyzer;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;
import com.google.common.collect.HashMultiset;

public class Util {
	
	public static double tfidf (int termFrequency, int collectionSize, int documentFrequency){
		// watch out for implicit type cast!
		double tfidf = (double)termFrequency * Math.log10((double)collectionSize/(double)documentFrequency);
		return tfidf;
	}
	
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
		
		if (path.startsWith("src")) {
			path = path.substring(4);
		}
		else if (path.startsWith("resources")) {
			path = path.substring(10);
			path = "emotionAnalyzer/"+path;
		}
		return path;
	}
	
	public static boolean compareFiles(File file1, File file2) throws IOException{
		List<String> list1 = Util.readFile2List(file1.getPath());
		List<String> list2 = Util.readFile2List(file2.getPath());
		if (list1.equals(list2)) return true;
		else return false;	
	}
	
	public static String readfile2String(String path) throws IOException{
		List<String> lines = Util.readFile2List(path);
		String output = "";
		for (String line: lines){
			if (!output.isEmpty()) output=output+"\n";
			output=output+line;
		}
		return output;
	}
	
	public static int sumOverVector(float[] a){
		int sum = 0;
		for (int i = 0; i < a.length; i++)
			sum += a[i];
		return sum;
	}
	
	/**
	 * Reads a given File und returns it listwise. Also works when packed in jar-File.
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static List<String> readFile2List(String path) throws IOException{
		List<String> lines = new ArrayList<String>();
		// Works in IDE
		try{
			lines = Files.readAllLines(Paths.get(path));
		}
		//Works in Jar
		catch (Exception e){
			String line;
			BufferedReader bReader= Util.file2BufferedReader(getJarPath(path));
			while ((line = bReader.readLine())!=null){
				lines.add(line);
			}
		}
		return lines;
	}
	
	/**
	 * Returns a Buffered reader of the indicated file/resource. The path which worked in IDE should also work when packed into jar.
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 */
	public static BufferedReader file2BufferedReader(String path) throws FileNotFoundException{
		BufferedReader bReader = null;
		//if not packed into jar	
		try {
				bReader= new BufferedReader(new FileReader(path));
				//if packed into jar
			} catch (Exception e) {
				try{
					bReader = new BufferedReader(new InputStreamReader(Util.class.getClassLoader().getResourceAsStream(Util.getJarPath(path))));
				} catch (Exception f){
					f.printStackTrace();
					System.out.println("\nFailed to find file in jar. Looked at path " + Util.getJarPath(path) );
				}
			}
		
		return bReader;
		
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
		return result/sample.length;
	}
	
	public static double var(double[]sample){
		double result = 0.0;
		double mean = Util.average(sample);
		for (int i=0; i<sample.length; i++){
			result = result + Math.pow(sample[i]-mean, 2);
		}
		
		return result/sample.length;
	}
	
	
	public final  static Settings defaultSettings = new Settings(Preprocessing.LEMMATIZE, false, "absolute");
	public static final Settings settings_tokenize = new Settings(Preprocessing.TOKENIZE, false, "absolute");
	public static final Settings settings_stem = new Settings(Preprocessing.STEM, false, "absolute");
	public static final String TESTFILE ="src/emotionAnalyzer/testFile.txt";
	public static final String TESTFILE2 ="src/emotionAnalyzer/testFile2.txt"; //(not normalized) Document vector should be (-8.43, -3.75, -7.04) using warriners (default) lexicon
	public static final  String TESTFILE5 ="src/emotionAnalyzer/testFile5.txt";
//	public static final String TESTFILE_STEM = "src/emotionAnalyzer/test.test.test.testFile_Stem.txt";
	public static final  String DEFAULTLEXICON ="src/emotionAnalyzer/LexiconWarriner2013_transformed.txt";
	public static final String DEFAULTLEXICON_JAR ="src/emotionAnalyzer/LexiconWarriner2013_transformed.txt";
	public static final String TESTLEXICON="src/emotionAnalyzer/testLexicon.txt";
	public static final String TESTFILE_LEMMA = "src/emotionAnalyzer/test.test.test.testFile_Lemma.txt";
	public static final String TESTLEXICON_LEMMA = "src/emotionAnalyzer/testLexicon_Lemma.txt";
//	public static final String TESTLEXICON_STEMMER = "src/emotionAnalyzer/testLexicon_Stemmer.txt";
//	public static final String STOPWORDLIST_NLTK = "resources/NLTK_stopwords_English.txt";
//	public static final String TESTFOLDER2 ="src/emotionAnalyzer/testFolder2";
//	public static final String TESTFOLDER ="resources/testFolder";
	public static final String STOPWORDLIST = "src/emotionAnalyzer/stopwords.txt";
	public static final String TESTFILE4 = "src/emotionAnalyzer/testFile4.txt";
	public static final String ACTUALOUTPUT ="target/actual_output.txt";
	public static final String EXPECTEDOUTPUT ="src/emotionAnalyzer/expectedTestOutput.txt";
	public static final String TARGETFOLDER = "target/";
	public static final String TESTFILE3 = "src/emotionAnalyzer/testFile3.txt";
	



}
