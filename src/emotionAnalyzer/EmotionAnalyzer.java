package emotionAnalyzer;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.Box.Filler;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import porterStemmer.PorterStemmerWrapper;
import stanford_lemmatizer.StanfordLemmatizer;
import stanford_lemmatizer.StanfordLemmatizerInterface;

public class EmotionAnalyzer {


	final private EmotionLexicon lexicon;
	final private StanfordLemmatizer lemmatizer;
	final private PorterStemmerWrapper stemmer;
	final private StopwordFilter stopwordfilter;
	final private NonAlphabeticFilter nonAlphabeticFilter;
	
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

	private Settings settings;
	private DocumentContainer[] containers;
	
	private File normalizedDocumentFolder;
	private File documentTermVectorFolder;
	private File VocabularyFolder;
	
	
//	final private File2BagOfWords_Processor f2tReader;
//	final private BagOfWords2Vector_Processor t2Vectorizer;
	//	final private VectorNormalizer vectorNormalizer;
	
	
	
	
	
	/**
	 * Constructor for EmotionAnalyzer loads EmotionLexicon (for recycling purposes) and the compounts File2TokenReader and Token2Vectorizer.
	 * @param givenLexiconPath
	 * @throws IOException
	 */
	public EmotionAnalyzer(String givenLexiconPath) throws IOException{
		this.lexicon = new EmotionLexicon(givenLexiconPath);
		this.lemmatizer = new StanfordLemmatizer();
		this.stemmer = new PorterStemmerWrapper();
		this.nonAlphabeticFilter = new NonAlphabeticFilter();
//		this.stopwordfilter = new StopwordFilter(Files.readAllLines(new File(Util.STOPWORDLIST).toPath()));
		this.stopwordfilter = new StopwordFilter(Util.readFile2List(Util.STOPWORDLIST)); 

	}
		
	

	DocumentContainer[] analyze(File givenCorpusFolder, Settings givenSettings) throws Exception{
		//ensure everything is null
		this.vocabulary = null;
		this.containers = null;
		this.corpus = null;
		this.corpusFolder = null;
		//txt-files erfassen, File[] corpus füllen, container initialiseren.
		System.err.println("Registering input files...");
		this.corpusFolder=givenCorpusFolder;
		//TODO Does this work?
		if (! (corpusFolder.isDirectory() || corpusFolder.getPath().equals("emotionAnalyzer/testFolder"))) throw new Exception("Input ( "+ corpusFolder.getPath() +" ) is not a directory!");
		this.corpus = fillCorpusArray();
		
		//make folders for normalized files and Document-Term-Vectors
		System.err.println("Making directories...");
		this.normalizedDocumentFolder = new File(this.corpusFolder.getPath()+"/Normalized_Documents");
		this.normalizedDocumentFolder.mkdir();
		this.documentTermVectorFolder = new File(this.corpusFolder.getPath()+"/Document-Term-Vectors");
		this.documentTermVectorFolder.mkdir();
		//container array initialisieren und füllen
		this.containers = new DocumentContainer[this.corpus.length];
		for (int index =0; index<this.corpus.length; index++){
			this.containers[index] = new DocumentContainer(this.corpus[index], this.normalizedDocumentFolder, this.documentTermVectorFolder);
		}
		//jeden text normalisieren: lemmatisieren, non-alphabetics entfernen, stopwörter entfernen (wegen dem verwendeten Lemmatizer geht es nicht andersherum) und die bereinigte lemma-listen in extra ordner speichern
		System.err.println("Normalizing input files...");
		for (DocumentContainer cont: containers){
			System.err.println("\t"+cont.document.getName());
			List<String> normalizedText = lemmatizer.lemmatize(Util.readfile2String(cont.document.getPath()));
			cont.tokenCount = normalizedText.size();
			normalizedText = nonAlphabeticFilter.filter(normalizedText);
			cont.alphabeticTokenCount = normalizedText.size();
			normalizedText = stopwordfilter.filter(normalizedText);
			cont.non_stopword_tokenCount = normalizedText.size();
			Util.writeList2File(normalizedText, cont.normalizedDocument.getPath());	
		}
		//vokabular erheben, Feld vokabular initialisieren.
		System.err.println("Building vocabulary...");
		this.vocabulary = collectVocabulary();
		//für jedes Dokument den Dokument-Term-Vektor berechnen und in gesondertem Ordner abspeichern. Referenz in Container schreiben.
		System.err.println("Calculating document-term-vectors...");
		for (DocumentContainer container: containers){
			System.err.println("\t"+container.document.getName());
			calculateDocumentTermVector(container);
		}
		//für jedes Dokument Dokument-Term-Vektor dicionary look-up durchführen (D-T-Vektor in Liste von Wortemotionsvektoren umwandeln (diese NICHT speichern!), mittelwert berechnen (ist gleichzeitig Dokumentenemotionsvektor), Standardabweichung berechnen, diese Kennwerte festhalten.
		System.err.println("Performing dictionary look-ups and calculating document emotion...");
		for (DocumentContainer container: containers){
			System.err.println("\t"+container.document.getName());
			calculateEmotionVector(container);
		}
		//Rückgabe
		return containers;
	}
	
	//	DocumentContainer analyzeEmotions(String givenDocumentPath, Settings givenSettings) throws IOException{
//		DocumentContainer documentContainer = new DocumentContainer(givenDocumentPath, givenSettings);
//		//calculates BagOfWords in documentContainer using f2tReader
//		documentContainer.calculateBagOfWords(this.f2tReader);
//		documentContainer.calculateLetterTokenCount();
//		//if the selected preprocessor is stemming, another lexicon (the stemmed one) must be used.
//		if (documentContainer.settings.usedPreprocessing == Preprocessing.STEM){
//			//checks if the lexicon has already been stemmed and does so if necessary
//			if (this.stemmedLexicon == null){this.stemmedLexicon = this.lexicon.stemLexicon();
//			//calculate the sum of vectors with the stemmed lexicon
//			documentContainer.calculateSumOfVectors(t2Vectorizer, stemmedLexicon);
//			}
//		}
//		//calculates the emotion vectors if preprocessor is different than stemmer
//		else documentContainer.calculateSumOfVectors(t2Vectorizer, lexicon);
//		documentContainer.normalizeDocumentVector(vectorNormalizer);
//		return documentContainer; //return 
		
	
	
	
	private void calculateEmotionVector(DocumentContainer container) throws IOException {
		int[] documentTermVector = container.getDocumentTermVector(this.vocabulary.size);
		String token;
		List<EmotionVector> emotionVectors = new LinkedList<EmotionVector>();
		//adding emotionVectors to emotion vector list
		for (int component=0 ; component<documentTermVector.length; component++){
			if (documentTermVector[component] > 0){
				token = vocabulary.indexMap.inverse().get(component);
				EmotionVector currentEmotionVector = this.lexicon.lookUp(token);
				if (currentEmotionVector!=null) {
					//den Emotionsvektor mal den Wert des Index im Dokumenten-Term-Vektor hinzufügen, wenn der Emotionsvektor nicht null ist (weil er nicht im Lexikon auftaucht)
					for (int i = documentTermVector[component]; i > 0; i--) {
						emotionVectors.add(currentEmotionVector);
					}
				}
			}
		}
		container.recognizedTokenCount = emotionVectors.size();
		EmotionVector meanVector = EmotionVector.calculateMean(emotionVectors);
		EmotionVector standardDeviationVector = EmotionVector.calculateStandardDeviation(emotionVectors, meanVector);
		container.documentEmotionVector = meanVector;
		container.standardDeviationVector = standardDeviationVector;
		
		}
		
		
	


	private void calculateDocumentTermVector(DocumentContainer container) throws IOException {
		int index;
		FileWriter writer = new FileWriter(container.documentTermVector);
		int[] documentTermVector = new int[this.vocabulary.size];
		List<String> normalizedDocument = Files.readAllLines(container.normalizedDocument.toPath());
		for (String str: normalizedDocument){
			index = this.vocabulary.indexMap.get(str);
			documentTermVector[index]++;
		}
		for (int i = 0; i<documentTermVector.length; i++){
			String str = Integer.toString(documentTermVector[i]); 
			writer.write(str+'\n');
		}
		writer.close();
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
		this.VocabularyFolder=new File(this.corpusFolder.getAbsolutePath()+"/Vocabulary");
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
