package experiments;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import stanford_lemmatizer.StanfordLemmatizer;
import emotionAnalyzer.EmotionLexicon;
import emotionAnalyzer.Util;

public class PrintLemmatizedLexicon {
	public static void main(String[] args) throws IOException{
		EmotionLexicon lexicon = new EmotionLexicon(Util.DEFAULTLEXICON, new StanfordLemmatizer());
		System.setOut(new PrintStream(new File("Lemmatisertes_Lexikon_2015-12-05.csv")));
		lexicon.printLexicon();
	}

}
