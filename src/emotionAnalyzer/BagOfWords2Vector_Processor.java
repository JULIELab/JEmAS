package emotionAnalyzer;

import java.io.IOException;

import com.google.common.collect.HashMultiset;

public class BagOfWords2Vector_Processor {
//	private HashMultiset<String> bagOfWords = null;
//	private EmotionVector documentVector = null;
	private EmotionLexicon lexicon = null;
	
	public BagOfWords2Vector_Processor(EmotionLexicon givenLexicon) throws IOException{
//	this.bagOfWords=givenSet;
	this.lexicon = givenLexicon; //TODO besser in der hauptklasse laden und recyceln.
//	this.calculateDocumentVector();
	
	}
	
	/**
	 * Transforms bag-of-words representation of the document into an emotion vector representation.
	 * @param givenSet
	 * @return
	 * @throws IOException
	 */
//	VectorizationResult calculateDocumentVector(HashMultiset<String> givenSet) throws IOException{
//		return calculateDocumentVector(givenSet, false);
//	}
	
	
	//that's where the magic happens
	/**
	 * 
	 * @param givenSet
	 * @param printVectors Prints each token together with associated vector.
	 * @return
	 * @throws IOException
	 */
//	 VectorizationResult calculateDocumentVector(HashMultiset<String> givenSet, boolean printVectors) throws IOException {
//		return calculateDocumentVector(givenSet, printVectors, false);
//	}
	 
//	 EmotionVector calculateDocumentVector(HashMultiset<String> givenSet, boolean printVectors, boolean normalize) throws IOException{
//		 EmotionVector documentVector = new EmotionVector(0, 0, 0);
//		 int count=0;
//			for (String currentToken : givenSet) { //Iterates over all entries of the Multiset. N interations for an entry of count N.
//				EmotionVector currentVector = lexicon.lookUp(currentToken);
//				if (printVectors) 	{
//					System.out.print(currentToken+"\t");
//					currentVector.print();}
//				documentVector.addVector(currentVector);
//				//TODO Das sollte ich noch ändern. Zum einen ist das Verfahren fragwürdig, dass ich am Ende durch die Anzahl der emotionalen Wörter teile, zum anderen sollte ich eine Methode einbauen, die explizit danach fragt, ob das wort im Lexikon ist (es kann ja auch drinstehen und trotzdem neutral sein.
//				if (!currentVector.equals(EmotionAnalyzer.neutralVector)){ //if the token is in the dictionanary (most probably different that neutral vector) count will be increment. Therefore, only emotional words are counted.
//					count++;
//				}
////				this is wrong. Iteration over multiple entries is yet done by outer loop.
////				do {
////					documentVector.addVector(currentVector);
////					count--;
////				} while (count > 0);
//			}
//			if (normalize) documentVector.normalize(count);
//			return documentVector; 
//	 }
	 
//	 VectorizationResult calculateDocumentVector(HashMultiset<String> givenSet, boolean printVectors, boolean normalize) throws IOException{
//		 EmotionVector documentVector = new EmotionVector(0, 0, 0);
//		 int count=0;
//			for (String currentToken : givenSet) { //Iterates over all entries of the Multiset. N interations for an entry of count N.
//				EmotionVector currentVector = lexicon.lookUp(currentToken);
//				if (printVectors) 	{
//					System.out.print(currentToken+"\t");
//					currentVector.print();}
//				documentVector.addVector(currentVector);
//				//TODO Das sollte ich noch ändern. Zum einen ist das Verfahren fragwürdig, dass ich am Ende durch die Anzahl der emotionalen Wörter teile, zum anderen sollte ich eine Methode einbauen, die explizit danach fragt, ob das wort im Lexikon ist (es kann ja auch drinstehen und trotzdem neutral sein.
//				if (!currentVector.equals(EmotionAnalyzer.neutralVector)){ //if the token is in the dictionanary (most probably different that neutral vector) count will be increment. Therefore, only emotional words are counted.
//					count++;
//				}
////				this is wrong. Iteration over multiple entries is yet done by outer loop.
////				do {
////					documentVector.addVector(currentVector);
////					count--;
////				} while (count > 0);
//			}
//			if (normalize) documentVector.normalize(count);
//			return new VectorizationResult(documentVector, count);
//	 }
	 
	 VectorizationResult calculateDocumentVector(HashMultiset<String> givenSet) throws IOException{
		 EmotionVector documentVector = new EmotionVector(0, 0, 0);
		 int count=0;
			for (String currentToken : givenSet) { //Iterates over all entries of the Multiset. N interations for an entry of count N.
				EmotionVector currentVector = lexicon.lookUp(currentToken);
				documentVector.addVector(currentVector);
				//TODO Das sollte ich noch ändern. Zum einen ist das Verfahren fragwürdig, dass ich am Ende durch die Anzahl der emotionalen Wörter teile, zum anderen sollte ich eine Methode einbauen, die explizit danach fragt, ob das wort im Lexikon ist (es kann ja auch drinstehen und trotzdem neutral sein.
				if (!currentVector.equals(EmotionAnalyzer.neutralVector)){ //if the token is in the dictionanary (most probably different that neutral vector) count will be increment. Therefore, only emotional words are counted.
					count++;
				}
//				this is wrong. Iteration over multiple entries is yet done by outer loop.
//				do {
//					documentVector.addVector(currentVector);
//					count--;
//				} while (count > 0);
			}
			return new VectorizationResult(documentVector, count);
	 }
	

//	public EmotionVector getDocumentVector(){
//		return this.documentVector;
//	}


}