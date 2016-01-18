package emotionAnalyzer;

import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import porterStemmer.PorterStemmerWrapper;
import stanford_lemmatizer.StanfordLemmatizer;

import com.google.common.collect.HashMultiset;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;



/**
 * Currently not in use.
 */
public  class File2BagOfWords_Processor {
	StanfordLemmatizer lemmatizer = null;
	PorterStemmerWrapper stemmer = null;

	
	public File2BagOfWords_Processor(){
		// this is your print stream, store the reference
		PrintStream err = System.err;

		// now make all writes to the System.err stream silent 
		System.setErr(new PrintStream(new OutputStream() {
		    @Override
			public void write(int b) {
		    }
		}));
		this.lemmatizer = new StanfordLemmatizer();
		this.stemmer = new PorterStemmerWrapper();
		// set everything back to its original state afterwards
		System.setErr(err); 
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
	
	/**
	 * Loads file at given path into string representation. Process it with Stanford-Lemmatizer and returns it as Mulitset.
	 * @param documentPath
	 * @return
	 * @throws IOException
	 */
	public HashMultiset<String> produceBagOfWords_Lemma(String documentPath) throws IOException{
		HashMultiset<String> lemmaMultiset = HashMultiset.create();
		String doc = Util.readfile2String(documentPath);
//		if (this.lemmatizer==null) this.lemmatizer = new StanfordLemmatizer(); //deprecated. will be constructed in class constructor
		List<String> lemmas = this.lemmatizer.lemmatize(doc);
		for (String lemma: lemmas){
			lemmaMultiset.add(lemma);
//			System.out.println(lemma);
		}
	
		return lemmaMultiset;
	}
	
	/**
	 * 
	 * @param documentPath
	 * @return Tokenize document with method above. Then stems the tokens using porter stemmer and returns the stemmed tokens using a multiset.
	 * @throws IOException
	 */
	public HashMultiset<String> produceBagOfWords_Stems(String documentPath) throws IOException{
		HashMultiset<String> tokens = produceBagOfWords_Token(documentPath);
		HashMultiset<String> stems = HashMultiset.create();
		for (String str: tokens){
			stems.add(stemmer.stem(str));
		}
		return stems;
	}
		
}


