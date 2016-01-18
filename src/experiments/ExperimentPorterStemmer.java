package experiments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import stanford_lemmatizer.StanfordLemmatizer;
import emotionAnalyzer.EmotionLexicon;
import emotionAnalyzer.Util;

public class ExperimentPorterStemmer {

	public static void main(String[] arg) throws IOException {
		EmotionLexicon currentLexicon = new EmotionLexicon(Util.DEFAULTLEXICON,
				new StanfordLemmatizer());
		Set<String> unprocessedSet = currentLexicon.getKeySet();
		// System.out.println("before processing: "+unprocessedSet.size());
		Set<String> processedSet = new HashSet<String>();
		// stem elements of unprocessedSet one by one and add to processedSet
		StanfordLemmatizer lemmatizer = new StanfordLemmatizer();
		List<String> lostEntries = new ArrayList<String>();
		for (String unprocessedString : unprocessedSet) {
			String processedString = "";
			for (String str : lemmatizer.lemmatize(unprocessedString)) {
				if (!processedString.equals(""))
					processedString = processedString.concat(" ");
				processedString = processedString.concat(str);
			}
			// if the processed String is already contained in the processed set
			// the unprocessed string will be saved so that the lexicon entries
			// lost can be tracked.
			if (processedSet.contains(processedString)) {
				lostEntries.add(unprocessedString);
			} else
				processedSet.add(processedString);
		}
		System.out.println("before processing: " + unprocessedSet.size());
		System.out.println("after processing " + processedSet.size());
		// print lost entries
		for (String str : lostEntries) {
			System.out.println(str);
		}
	}

}
