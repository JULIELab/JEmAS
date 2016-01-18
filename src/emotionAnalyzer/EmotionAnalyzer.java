package emotionAnalyzer;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

//import porterStemmer.PorterStemmerWrapper;
import stanford_lemmatizer.StanfordLemmatizer;

public class EmotionAnalyzer {


	final private EmotionLexicon lexicon;
	final private StanfordLemmatizer lemmatizer;
	final private StopwordFilter stopwordfilter;
	final private NonAlphabeticFilter nonAlphabeticFilter;
	final private NumberFilter numberFilter;
//	final private PorterStemmerWrapper stemmer;
	
	/**
	 * Will only be assingned if a passed DocumentContainer requires stemming as preprocessing.
	 */
	private EmotionLexicon stemmedLexicon;
	
	/**
	 * Collection of files which should be processed
	 */
	private File[] corpus;
	/**
	 * Folder in which the corpus (every txt-document in it) is lokated.
	 */
	private File corpusFolder;
	private Vocabulary vocabulary;
	public Vocabulary getVocabulary() {
		return vocabulary;
	}


	private DocumentContainer[] containers;
	
	private File normalizedDocumentFolder;
	private File VocabularyFolder;
	/**
	 * The Folder where the additional output is saved.
	 */
	private File targetFolder;

	private int[] vocabularyLexiconVector; // used for lexiconProjection. Components are 0 if the word
											// represented by this index is not in the lexicon
	
	private double[][] vocabularyEmotionMatrix; //represents the emotion values of the words in
												// the lexicon. A row is (0	0 0) if the word is not in the lexicon.
	

	
		
	
	/**
	 * Constructor for EmotionAnalyzer loads EmotionLexicon (for recycling purposes) and the compounts File2TokenReader and Token2Vectorizer.
	 * @param givenLexiconPath
	 * @throws IOException
	 */
	public EmotionAnalyzer(String givenLexiconPath) throws IOException{
		this.lemmatizer = new StanfordLemmatizer();
		this.lexicon = new EmotionLexicon(givenLexiconPath, this.lemmatizer);
//		this.stemmer = new PorterStemmerWrapper();
		this.nonAlphabeticFilter = new NonAlphabeticFilter();
		this.stopwordfilter = new StopwordFilter(Util.readFile2List(Util.STOPWORDLIST)); 
		this.numberFilter = new NumberFilter();
	}
		
	

	/**
	 * 
	 * @param givenCorpusFolder The Folder where the txt-files which will be processed are in.
	 * @param givenTargetFolder The Folder where the additional output (vocabulary, normalized document and 
	 * eventually the document term vectors) will be saved.
	 * @param givenSettings
	 * @return
	 * @throws Exception
	 */
	public DocumentContainer[] analyze(File givenCorpusFolder, File givenTargetFolder, Settings givenSettings) throws Exception{
		//ensure everything is null
		this.vocabulary = null;
		this.containers = null;
		this.corpus = null;
		this.corpusFolder = null;
		this.targetFolder = null;
//		this.settings = null;
		//txt-files erfassen, File[] corpus füllen, container initialiseren.
		System.err.println("Registering input files...");
		this.corpusFolder=givenCorpusFolder;
		if (! (corpusFolder.isDirectory() || corpusFolder.getPath().equals("emotionAnalyzer/testFolder"))) throw new Exception("Input ( "+ corpusFolder.getPath() +" ) is not a directory!");
		this.corpus = fillCorpusArray();
		this.targetFolder = givenTargetFolder;
//		this.settings = givenSettings;
		
		//make folders for normalized files and Document-Term-Vectors
		System.err.println("Making directories...");
		this.normalizedDocumentFolder = new File(this.targetFolder.getPath()+"/Normalized_Documents");
		this.normalizedDocumentFolder.mkdir();
		//container array initialisieren und füllen, Metadaten erheben
		this.containers = new DocumentContainer[this.corpus.length];
		for (int index =0; index<this.corpus.length; index++){
			this.containers[index] = new DocumentContainer(this.corpus[index], this.normalizedDocumentFolder);
		}
		//jeden text normalisieren: lemmatisieren, non-alphabetics entfernen, stopwörter entfernen (wegen dem verwendeten Lemmatizer geht es nicht andersherum) und die bereinigte lemma-listen in extra ordner speichern
		System.err.println("Normalizing input files...");
		for (DocumentContainer cont: containers){
			System.err.println("\t"+cont.document.getName());
			List<String> normalizedText = lemmatizer.lemmatize(Util.readfile2String(cont.document.getPath()));
			//measure token count
			cont.tokenCount = normalizedText.size();
			//Zahlen entfernen und Anzahl berechnen.
			normalizedText = numberFilter.filter(normalizedText);
			cont.numberCount = cont.tokenCount - normalizedText.size();
			normalizedText = nonAlphabeticFilter.filter(normalizedText);
			cont.alphabeticTokenCount = normalizedText.size();
			//remove stopwords and map to lowe case (case-folding)
			normalizedText = stopwordfilter.filter(normalizedText);
			cont.non_stopword_tokenCount = normalizedText.size();
			Util.writeList2File(normalizedText, cont.normalizedDocument.getPath());	
		}
		//vokabular erheben, Feld vokabular initialisieren.
		System.err.println("Building vocabulary...");
		this.vocabulary = collectVocabulary();
		
		//calculate Vocabulary-Emotion-Matrix and vocabulary-lexicon-vector
		this.vocabularyEmotionMatrix = new double[this.vocabulary.size][3];
		this.vocabularyLexiconVector = new int[this.vocabulary.size];
		for (int i=0; i< this.vocabulary.size; i++){
			EmotionVector currentEmotionVector = this.lexicon.lookUp(this.vocabulary.getStringByIndex(i));
			if (currentEmotionVector!=null){
				this.vocabularyEmotionMatrix[i][0] = currentEmotionVector.getValence();
				this.vocabularyEmotionMatrix[i][1] = currentEmotionVector.getArousal();
				this.vocabularyEmotionMatrix[i][2] = currentEmotionVector.getDominance();
				this.vocabularyLexiconVector[i] = 1;
			}
			else{
				this.vocabularyEmotionMatrix[i][0] = 0;
				this.vocabularyEmotionMatrix[i][1] = 0;
				this.vocabularyEmotionMatrix[i][2] = 0;
				this.vocabularyLexiconVector[i] = 0;
			}
		}
		
		
		
		//Dokument-Term-Vektoren erheheben, dictionary look-ups durchführen, Emotionsvektoren berechnen.
		//für jedes Dokument dictionnnary - look-up durchführen und Emotionsvektoren berechnen (D-T-Vektor in Liste von Wortemotionsvektoren umwandeln (diese NICHT speichern!), mittelwert berechnen (ist gleichzeitig Dokumentenemotionsvektor), Standardabweichung berechnen, diese Kennwerte festhalten.
				System.err.println("Calculating Document-Term-Vectors, Performing dictionary look-ups and calculating document emotion...");
				for (DocumentContainer container: containers){
					int[] documentTermVector = calculateDocumentTermVector(container);
					System.err.println("\t"+container.document.getName());
					calculateEmotionVector(container, documentTermVector);
				}
		
		//Rückgabe
		return containers;
	}
	
	
	private void calculateEmotionVector(DocumentContainer container, int[] documentTermVector) throws IOException {
		int[] projectedDocumentTermVector = lexiconProjection(documentTermVector);
		double valence = 0;
		double arousal = 0;
		double dominance = 0;
		int recognizedTokens = 0;
		for (int i = 0; i < this.vocabulary.size; i++){
			valence += projectedDocumentTermVector[i]*this.vocabularyEmotionMatrix[i][0];
			arousal += projectedDocumentTermVector[i]*this.vocabularyEmotionMatrix[i][1];
			dominance += projectedDocumentTermVector[i]*this.vocabularyEmotionMatrix[i][2];
			recognizedTokens +=  projectedDocumentTermVector[i];
		}
		container.recognizedTokenCount = recognizedTokens;
		// if the number of recognized tokens is 0, normalisation is not possivle
		if (recognizedTokens==0){
			container.documentEmotionVector = new EmotionVector(0,0,0);
			container.standardDeviationVector = new EmotionVector(0,0,0);
		}
		else{
		EmotionVector emotionVector = new EmotionVector(valence, arousal, dominance);
		emotionVector.normalize(recognizedTokens);
		container.documentEmotionVector = emotionVector;
		//calcualte standarddev vector
		//squared Deviation Valence
		double sqDevValence = 0;
		//squared Deviation Arousl
		double sqDevArousal = 0;
		//squared Deviation Dominance
		double sqDevDominance = 0;
		//calculate summed squared deviation from mean for VAD (which is the standard dev of all recognized emotion vectors)
		for (int i = 0; i < this.vocabulary.size; i++){
			if(vocabularyLexiconVector[i] == 1){
				sqDevValence += projectedDocumentTermVector[i] * Math.pow((emotionVector.getValence() - vocabularyEmotionMatrix[i][0]), 2);
				sqDevArousal += projectedDocumentTermVector[i] * Math.pow((emotionVector.getArousal() - vocabularyEmotionMatrix[i][1]), 2);
				sqDevDominance += projectedDocumentTermVector[i] * Math.pow((emotionVector.getDominance() - vocabularyEmotionMatrix[i][2]), 2);
			}
		}
		EmotionVector sdVector = new EmotionVector(Math.sqrt(sqDevValence/recognizedTokens),Math.sqrt(sqDevArousal/recognizedTokens),Math.sqrt(sqDevDominance/recognizedTokens));
		container.standardDeviationVector = sdVector;
		}
	}
		
		

	/**
	 * Sets all compounents of the document-term-vector to 0, if they represent a word not in
	 * the lexicon.
	 * @param givenDocumentTermVector
	 * @return
	 */
	private int[] lexiconProjection(int[] givenDocumentTermVector){
		for (int i=0; i<givenDocumentTermVector.length; i++){
			givenDocumentTermVector[i] = givenDocumentTermVector[i]*this.vocabularyLexiconVector[i];			
		}
		return givenDocumentTermVector;
	}

	private int[] calculateDocumentTermVector(DocumentContainer container) throws IOException {
		int index;
		int[] documentTermVector = new int[this.vocabulary.size];
		List<String> normalizedDocument = Files.readAllLines(container.normalizedDocument.toPath());
		for (String str: normalizedDocument){
			index = this.vocabulary.getIndexByString(str);
			documentTermVector[index]++;
		}
		return documentTermVector;
	}



	private File[] fillCorpusArray() throws Exception {
		if (this.corpusFolder==null) throw new Exception("No folder is indicated yet!");
		 // create new filename filter
        FilenameFilter filter = new FilenameFilter() {
  
           @Override
           public boolean accept(File dir, String name) {
              if (name.endsWith(".txt")) return true;
              else return false;
           }
        };
		corpus = corpusFolder.listFiles(filter);
		return corpus;
	}

	
	void showLexicon(){
		this.lexicon.printLexicon();
	}
	
	void showStemmedLexicon(){
		this.stemmedLexicon.printLexicon();
	}
	
	private Vocabulary collectVocabulary() throws IOException{
		this.VocabularyFolder=new File(this.targetFolder.getAbsolutePath()+"/Vocabulary");
		this.VocabularyFolder.mkdir();
		Set<String> vocabularySet= new HashSet<String>();
		for (DocumentContainer container: this.containers){
			System.err.println("\t"+container.document.getName());
			List<String> normalizedDocument = Files.readAllLines(container.normalizedDocument.toPath());
			for (String line : normalizedDocument){
				vocabularySet.add(line);
			}
		}
		int vocabularySize =  0;
		BiMap<String, Integer> indexMap = HashBiMap.create();
		File vocabularyFile = new File(this.VocabularyFolder.getPath()+"/vocabulary.txt");
		FileWriter writer = new FileWriter(vocabularyFile);
		for (String str: vocabularySet){
			indexMap.put(str, vocabularySize);
			writer.write(str+'\n');
			vocabularySize++;	
		}
		writer.close();
		Vocabulary voc = new Vocabulary(vocabularySize, indexMap, vocabularyFile);
		return voc;
	}

}
