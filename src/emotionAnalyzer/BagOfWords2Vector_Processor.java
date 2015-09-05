package emotionAnalyzer;

import java.io.IOException;

import com.google.common.collect.HashMultiset;

public class BagOfWords2Vector_Processor {
	final private EmotionLexicon lexicon;
	
	public BagOfWords2Vector_Processor(EmotionLexicon givenLexicon) throws IOException{
	this.lexicon = givenLexicon; 
	}
	
	/**
	 * Transforms bag-of-words representation of the document into an emotion vector representation.
	 * @param givenSet
	 * @return Instance of class VectorizationResult. This class contains both, the documentVector and the number of successfully attributed vectors (the number of words found in the lexicon).
	 * @throws IOException
	 */	 
	 VectorizationResult calculateDocumentVector(HashMultiset<String> givenSet) throws IOException{
		 EmotionVector documentVector = new EmotionVector(0, 0, 0);
		 int count=0;
		 	//Iterates over all entries of the Multiset. N interations for an entry of count N.
			for (String currentToken : givenSet) { 
				EmotionVector currentVector = lexicon.lookUp(currentToken);
				if (currentVector!=null){
				documentVector.addVector(currentVector);
				count++;
				}
			}
			return new VectorizationResult(documentVector, count);
	 }
	

}