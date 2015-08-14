package emotionAnalyzer;

public class VectorNormalizer {
	
double calculateNormalizationParameter(int vectorCount){
//	System.out.print(tokenCount);
	return vectorCount;
}

EmotionVector calculateNormalizedDocumentVector(EmotionVector givenVector, double normalizationParameter){
//	givenVector.normalize( (int) normalizationParameter); //this does not work. Scheint so, als ob hier die reingegebene Klasse koreferenziert wird. Deshalb wird Sum of Vectors gleich mit verändert.
	EmotionVector normalizedVector = new EmotionVector(givenVector.getValence()/normalizationParameter, givenVector.getArousal()/normalizationParameter, givenVector.getDominance()/normalizationParameter);
	return normalizedVector;

}

}
