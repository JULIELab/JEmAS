package emotionAnalyzer;

import java.io.FileReader;
import java.io.IOException;
import com.google.common.collect.HashMultiset;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

public  class File2TokenReader {
//	private String documentPath=null;
//	private HashMultiset<String>tokenMultiset = null;

	
	public File2TokenReader(){
//		this.documentPath=givenDocumentPath;
//		this.tokenMultiset = HashMultiset.create();
//		this.produceBagOfWords();
	}
	
	//TODO hier muss was geladen werden (insbesondere, wenn es komplizierter wird. Deshalb sollte ich das recyceln.
	public HashMultiset<String> produceBagOfWords(String documentPath) throws IOException{ 
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
	
//	public HashMultiset<String> getBagOfWords(){
//		return tokenMultiset;
//	}
	
		
	}


