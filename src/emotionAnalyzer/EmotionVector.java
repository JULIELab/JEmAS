package emotionAnalyzer;

/*
 * Vector representation of an emotion according to dimensional emotion model (also named VAD-model (valence, arousal, dominance).
 */
public class EmotionVector {
	
	private double valence;
	private double arousal;
	private double dominance;
	
	double getValence(){
	return this.valence;
	}
	
	double getArousal(){
		return this.arousal;
	}
	
	double getDominance(){
		return this.dominance;
	}
	
	void addVector(EmotionVector givenVector){
		this.valence+=givenVector.getValence();
		this.dominance+=givenVector.getDominance();
		this.arousal+=givenVector.getArousal();
	}
	
	void multiplyWithConstant(double constant){
		this.valence*=constant;
		this.arousal*=constant;
		this.dominance*=constant;
	}
	
	double getLength(){
		double length = Math.sqrt(Math.pow(this.valence,2) + Math.pow(this.dominance,2)+ Math.pow(this.arousal, 2));
		return length;
	}
	
	boolean equals (EmotionVector givenVector){
		double delta = 0.0001; //rechnen mit doubles kann seltsame Rundungsfehler geben.
		if ( (Math.abs(this.valence-givenVector.getValence()) < delta) && (Math.abs(this.arousal-givenVector.getArousal())< delta) && (Math.abs(this.dominance-givenVector.getDominance()) < delta)) return true;
		else return false;
	}
	
	void print(){
		System.out.println(this.valence + "\t" + this.arousal + "\t" + this.dominance + "\t" + this.getLength());
	}
	
	static void printTemplate(){
		printTemplate(false);
	}
	
	static void printTemplate(boolean printDocumentName){
		System.out.println("V = Valence\nA = Arousal\nD = Dominance\nL = Length\n");
		if (printDocumentName) System.out.print("Document\t");
		System.out.println("V\tA\tD\tL");
	}
	
	private void multiply(double constant){
		this.valence*=constant;
		this.arousal*=constant;
		this.dominance*=constant;
	}
	
	/**
	 * Divides vector by given integer, e.g. to normalize the emotion vector of a document with the length of the document (number of tokens
	 * or other measure).
	 * @param length
	 */
	void normalize(int length){
		this.multiply(1.0/(double)length);
	}
	
	
	public EmotionVector(double givenValence, double givenArousal, double givenDomincance) {
		this.valence=givenValence;
		this.arousal=givenArousal;
		this.dominance=givenDomincance;
	}
	public EmotionVector(){
		this.valence=0.0;
		this.arousal=0.0;
		this.dominance=0.0;
	}

}
