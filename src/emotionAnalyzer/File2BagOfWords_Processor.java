package emotionAnalyzer;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import porterStemmer.PorterStemmerInterface;
import stanford_lemmatizer.StanfordLemmatizer;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

public  class File2BagOfWords_Processor {
	StanfordLemmatizer lemmatizer = null;
	PorterStemmerInterface stemmer = null;


	
	public File2BagOfWords_Processor(){
	}
	
	/**
	 * Loads document from file and transform it in a token multi-set using stanford PTBTokenizer.
	 * @param documentPath
	 * @return
	 * @throws IOException
	 */
	public HashMultiset<String> produceBagOfWords_Token(String documentPath) throws IOException{ 
		HashMultiset<String>tokenMultiset = HashMultiset.create();
		PTBTokenizer<CoreLabel> ptbt = new PTBTokenizer<>(new FileReader(documentPath),
	              new CoreLabelTokenFactory(), "");
	      while (ptbt.hasNext()) {
	        CoreLabel label = ptbt.next();
	        tokenMultiset.add(label.toString());
//	        System.out.println(label);
	      }
//	      System.out.println("\n\nMULTISET:\n\n");
//	      for (String token: tokenMultiset) System.out.println(token +"	"+ tokenMultiset.count(token));
	      return tokenMultiset;
	}
	
	//TODO test!
	/**
	 * Loads file a given path into string representation. Process it with Stanford-Lemmatizer and returns it as Mulitset.
	 * @param documentPath
	 * @return
	 * @throws IOException
	 */
	public HashMultiset<String> produceBagOfWords_Lemma(String documentPath) throws IOException{
		HashMultiset<String> lemmaMultiset = HashMultiset.create();
		String doc = file2String(documentPath);
		if (this.lemmatizer==null) this.lemmatizer = new StanfordLemmatizer();
		List<String> lemmas = this.lemmatizer.lemmatize(doc);
		for (String lemma: lemmas) lemmaMultiset.add(lemma);
		return lemmaMultiset;
	}
	
	/**
	 * 
	 * @param documentPath
	 * @return Tokenize document with method above. Then stems the tokens using porter stemmer and returns the stemmed tokens using a multiset.
	 * @throws IOException
	 */
	public HashMultiset<String> produceBagOfWords_Stems(String documentPath) throws IOException{
		if (this.stemmer==null) this.stemmer = new PorterStemmerInterface();
		HashMultiset<String> tokens = produceBagOfWords_Token(documentPath);
		HashMultiset<String> stems = HashMultiset.create();
		for (String str: tokens){
			stems.add(stemmer.stem(str));
		}
		return stems;
	}
		
		
	
	
	//TODO test! Muss ich da noch Absätze dazwischen setzen (zwischen die einzelnen Zeilen???
	public String file2String(String path) throws IOException{
		List<String> lines = Files.readAllLines(Paths.get(path));
		String output = "";
		for (String line: lines){
			if (!output.isEmpty()) output=output+"\n";
			output=output+line;
		}
		return output;
	}
		
}


