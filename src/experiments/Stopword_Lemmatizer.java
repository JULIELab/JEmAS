package experiments;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

import stanford_lemmatizer.StanfordLemmatizer;
import emotionAnalyzer.Util;

public class Stopword_Lemmatizer {
	public static void main (String[] args) throws IOException{
		List<String> stopwords = Files.readAllLines(new File (Util.STOPWORDLIST).toPath());
		StanfordLemmatizer lemmatizer = new StanfordLemmatizer();
		List<String> unique = new LinkedList<String>();
		List<String> duplicate = new LinkedList<String>();
		
		for (String str: stopwords){
			str = lemmatizer.lemmatize(str).get(0);
			if (!unique.contains(str)) unique.add(str);
			else duplicate.add(str);
		}
		System.out.println("UNIQUE:");
		for (String str: unique) System.out.println(str);
		System.out.println();
		System.out.println("DUPLICATE");
		for (String str: duplicate) System.out.println(str);
			
	}

}
