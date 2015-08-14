package emotionAnalyzer;

public class VectorizationResult {
	
	public VectorizationResult(EmotionVector emotionVector,
			int numberOfAddedVectors) {
		super();
		this.emotionVector = emotionVector;
		this.numberOfAddedVectors = numberOfAddedVectors;
	}
	private final EmotionVector emotionVector;
	private final int numberOfAddedVectors;
	public EmotionVector getEmotionVector() {
		return emotionVector;
	}
	public int getNumberOfAddedVectors() {
		return numberOfAddedVectors;
	}

}
