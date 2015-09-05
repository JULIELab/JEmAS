package emotionAnalyzer;

public class VectorizationResult {
	private final EmotionVector emotionVector;
	private final int numberOfAddedVectors;
	
	public VectorizationResult(EmotionVector emotionVector, int numberOfAddedVectors) {
		this.emotionVector = emotionVector;
		this.numberOfAddedVectors = numberOfAddedVectors;
	}

	public EmotionVector getEmotionVector() {
		return emotionVector;
	}
	
	public int getNumberOfAddedVectors() {
		return numberOfAddedVectors;
	}

}
