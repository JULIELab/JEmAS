package emotionAnalyzer;

import java.io.File;
import com.google.common.collect.BiMap;


public class Vocabulary {
	public Vocabulary(int size, BiMap<String, Integer> indexMap,
			File vocabularyFile) {
		super();
		this.size = size;
		this.indexMap = indexMap;
		this.vocabularyFile = vocabularyFile;
	}
	final int size;
	/**
	 * Maps a element of the vocabulary to the index of the corresponding component of the document term vector. And the other way round (uniqueness in both directions).
	 */
	final BiMap<String, Integer> indexMap;
	final File vocabularyFile;
}