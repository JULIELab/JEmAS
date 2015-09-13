package emotionAnalyzer;

import java.io.IOException;

import com.google.common.collect.HashMultiset;

public class BagOfWords2Vector_Processor {

	/**
	 * Transforms bag-of-words representation of the document into an emotion vector representation.
	 * @param givenSet
	 * @return Instance of class VectorizationResult. This class contains both, the documentVector and the number of successfully attributed vectors (the number of words found in the lexicon).
	 * @throws IOException
	 */	 
	 VectorizationResult calculateDocumentVector(HashMultiset<String> givenSet, EmotionLexicon givenLexicon, Settings givenSettings) throws IOException{
		 if (givenSettings.printIdentifiedTokens) System.out.println("\n\nIdentified tokens:\n");
		 EmotionVector documentVector = new EmotionVector(0, 0, 0);
		 int count=0;
		 	//Iterates over all entries of the Multiset. N interations for an entry of count N.
			for (String currentToken : givenSet) { 
				EmotionVector currentVector = givenLexicon.lookUp(currentToken);
				//if Stemmer is used, the look-up should also check for small letter word beginnings
				if (givenSettings.usedPreprocessing == Preprocessing.STEM && currentVector==null){
					currentToken = currentToken.substring(0, 1).toLowerCase() +currentToken.substring(1);
					currentVector=givenLexicon.lookUp(currentToken);
				}
				if (currentVector!=null){
				documentVector.addVector(currentVector);
				count++;
				if (givenSettings.printIdentifiedTokens) System.out.println(currentToken);
				}
			}
			return new VectorizationResult(documentVector, count);
	 }
}