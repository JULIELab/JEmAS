package emotionAnalyzer;

import java.util.List;
import java.io.File;
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
			put(EmotionAnalyzer.DEFAULTLEXICON, "/emotionAnalyzer/LexiconWarriner2013_transformed.txt");
		}};
		return map.get(formerLocation);
		
	}
	
	/**
	 * Decides if the given token is a letter word (meaning it consists purely of letters).
	 * @param currentToken
	 * @return
	 */
	public static boolean isLetterToken(String givenToken) {
		return givenToken.matches("\\p{L}+");
	}
	
	public static String file2String(String path) throws IOException{
		List<String> lines = Files.readAllLines(Paths.get(path));
		String output = "";
		for (String line: lines){
			if (!output.isEmpty()) output=output+"\n";
			output=output+line;
		}
		return output;
	}
	
	public final  static Settings defaultSettings = new Settings(Preprocessing.LEMMATIZE, false);
	public static final Settings settings_tokenize = new Settings(Preprocessing.TOKENIZE, false);
	public static final Settings settings_stem = new Settings(Preprocessing.STEM, false);


}
