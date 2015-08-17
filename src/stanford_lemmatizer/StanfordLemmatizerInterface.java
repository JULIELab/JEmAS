package stanford_lemmatizer;

import java.util.List;

public class StanfordLemmatizerInterface {

	public static void main(String[] args) {
			StanfordLemmatizer lemmatizer = new StanfordLemmatizer();
			List<String> lemmas = lemmatizer.lemmatize(args[0]);
			for (String str: lemmas){
				System.out.println(str);
			}
			

	}

}
