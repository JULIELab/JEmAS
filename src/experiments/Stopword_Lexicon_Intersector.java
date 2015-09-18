package experiments;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

import emotionAnalyzer.EmotionLexicon;
import emotionAnalyzer.Util;

public class Stopword_Lexicon_Intersector {
	/**
	 * Finds the intersection of the lemmatized stopword list and the emotin dictionary
	 * @throws IOException 
	 */
	public static void main (String[] args) throws IOException{
		EmotionLexicon lexicon = new EmotionLexicon(Util.DEFAULTLEXICON);
		List<String> stopwords = Files.readAllLines(new File("resources/unique_lemmatized_stopwords.txt").toPath());
		List<String> intersection = new LinkedList<String>();
		for (String str : stopwords){
			if (lexicon.lookUp(str)!= null) intersection.add(str);
		}
		System.out.println("INTERSECTION:");
		for (String str: intersection){
			System.out.println(str);
			stopwords.remove(str);
		}
		System.out.println();
		System.out.println("STOPWORDS NOT IN LEXICON:");
		for (String str: stopwords) System.out.println(str);
	}

}
