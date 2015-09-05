package emotionAnalyzer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;

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
	
//	public static final String 


}
