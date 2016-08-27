package emotionAnalyzer;


/**
 * ATM unused!
 * @author sven
 *
 */
public class VocabularyEntry {
	final String lemma;
	private int collectionFrequency;
	private int documentFrequency;
	
	public int getCollectionFrequency() {
		return collectionFrequency;
	}

	public int getDocumentFrequency() {
		return documentFrequency;
	}
	
	/**
	 * Increments collection frequency by 1.
	 */
	public void incremCf(){
		this.collectionFrequency =this.collectionFrequency+1;
	}
	
	/**
	 * Increments document frequency by 1.
	 */
	public void incremDf(){
		this.documentFrequency = this.documentFrequency+1;
	}

	
	
	public VocabularyEntry(String lemma, int collectionFrequency,
			int documentFrequency) {
		super();
		this.lemma = lemma;
		this.collectionFrequency = collectionFrequency;
		this.documentFrequency = documentFrequency;
	}
	

}
