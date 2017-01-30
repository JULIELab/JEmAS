package emotionAnalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;









//import porterStemmer.PorterStemmerWrapper;
import stanford_lemmatizer.StanfordLemmatizer;

public class EmotionAnalyzer {


	private EmotionLexicon lexicon;
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
	private Settings settings;
	public Vocabulary getVocabulary() {
		return vocabulary;
	}


	private DocumentContainer[] containers;
	
	private File normalizedDocumentFolder;
	private File documentTermVectorFolder;
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
	public EmotionAnalyzer() throws IOException{
		this.lemmatizer = new StanfordLemmatizer();
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
	public DocumentContainer[] analyze(File givenCorpusFolder, File givenTargetFolder, Settings givenSettings, String givenLexiconPath) 
			throws Exception{
		//ensure everything is null
		this.vocabulary = null;
		this.settings = givenSettings;
		this.lexicon = new EmotionLexicon(givenLexiconPath, this.lemmatizer, this.settings);
		this.containers = null;
		this.corpus = null;
		this.corpusFolder = null;
		this.targetFolder = null;
		this.settings = null;
		this.documentTermVectorFolder = null;
		
//		this.settings = null;
		//txt-files erfassen, File[] corpus füllen, container initialiseren.
		System.err.println("Registering input files...");
		this.corpusFolder=givenCorpusFolder;
//		if (! (corpusFolder.isDirectory() || corpusFolder.getPath().equals("emotionAnalyzer/testFolder"))) 
//			throw new Exception("Input ( "+ corpusFolder.getPath() +" ) is not a directory!");
		//TODO Continue: if corpusFolder is not directory, read all lines from file, create 1 file per line in a temp directory and set this dir as corpus folder.
		if (!corpusFolder.isDirectory()){
			//file lesen und für jede Zeile eine neue Datei in tmpFolder schreiben
			File tmpFolder = Files.createTempDirectory("tempCorpusFOlder").toFile();
			try (BufferedReader br = new BufferedReader(new FileReader(corpusFolder))){
				String line;
				int count = 0;
				while ((line = br.readLine()) != null){
						count += 1;
						File tmpFile = new File(tmpFolder.getPath() + "/"
								+ count);
						try {
							PrintWriter pw = new PrintWriter(tmpFile);
							pw.println(line);
							pw.close();
						} catch (IOException e) {
							System.err
									.print("Failed to write temporary files. Indicated input file is not a directory.");
							System.err.print(e);
						}
				}
			}
			this.corpusFolder = tmpFolder;
			givenTargetFolder = tmpFolder.getParentFile();
		}
		this.corpus = fillCorpusArray();
		this.targetFolder = givenTargetFolder;
		this.settings = givenSettings;
		
		
			//make folders for normalized files and Document-Term-Vectors
			System.err.println("Making directories...");
			this.normalizedDocumentFolder = new File(
					this.targetFolder.getPath() + "/Normalized_Documents");
			this.normalizedDocumentFolder.mkdir();
			this.documentTermVectorFolder = new File(
					this.targetFolder.getPath() + "/Document_Term_Vectors");
			//container array initialisieren und füllen, Metadaten erheben
			this.containers = new DocumentContainer[this.corpus.length];
			for (int index = 0; index < this.corpus.length; index++) {
				this.containers[index] = new DocumentContainer(
						this.corpus[index], this.normalizedDocumentFolder,
						this.documentTermVectorFolder);
			}
			//jeden text normalisieren: lemmatisieren, non-alphabetics entfernen,
			//stopwörter entfernen (wegen dem verwendeten Lemmatizer geht es nicht andersherum) und
			//die bereinigte lemma-listen in extra ordner speichern
			System.err.println("Normalizing input files...");
			for (DocumentContainer cont : containers) {
				System.err.println("\t" + cont.document.getName());
				List<String> normalizedText = new ArrayList<String>();
				//wenn man Lemmatisierung als Vorverarbeitung ausgewählt hat.
				if (this.settings.usedPreprocessing.equals(Preprocessing.LEMMATIZE)) {
					normalizedText = lemmatizer.lemmatize(Util
							.readfile2String(cont.document.getPath()));
				}
				//wenn man KEINE Vorverarbeitung ausgewählt hat (ie, Lexikon und Dokumente sind schon lemmatisiert)
				else if (this.settings.usedPreprocessing.equals(Preprocessing.NONE)){
					//das hier ist jetzt der eigentliche Unterschied. Zwischen den beiden Vorverarbeitungsmodi:
					// statt den Text zu lemmatisieren, wird er hier einfach nur eingelesen. Alles andere bleibt.
					try (BufferedReader br = new BufferedReader(new FileReader(cont.document.getPath()))) {
					    String line;
					    while ((line = br.readLine()) != null) {
					       normalizedText.add(line);
					    }
					}
//					normalizedText = Arrays.asList(Util.
//							readfile2String(cont.document.getPath()).split("\n"));
				}
				//measure token count
				cont.tokenCount = normalizedText.size();
				//Zahlen entfernen und Anzahl berechnen.
				normalizedText = numberFilter.filter(normalizedText);
				cont.numberCount = cont.tokenCount - normalizedText.size();
				normalizedText = nonAlphabeticFilter.filter(normalizedText);
				cont.alphabeticTokenCount = normalizedText.size();
				//no case-folding/stopword removal wenn keine Vorverarbeitung gewähtl wurde.
				if (this.settings.usedPreprocessing.equals(Preprocessing.LEMMATIZE)){
					normalizedText = stopwordfilter.filter(normalizedText);
					}
				
				cont.non_stopword_tokenCount = normalizedText.size();
				Util.writeList2File(normalizedText,
						cont.normalizedDocument.getPath());
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
		
		
		/**
		 * Dependend on the weight function used:
		 */
		//termFrequency weights...
		if (this.settings.weightFunction.equals("absolute")){
			//Dokument-Term-Vektoren erheheben, dictionary look-ups durchführen, Emotionsvektoren berechnen.
			//für jedes Dokument dictionnnary - look-up durchführen und Emotionsvektoren berechnen 
			//(D-T-Vektor in Liste von Wortemotionsvektoren umwandeln (diese NICHT speichern!), 
			//mittelwert berechnen (ist gleichzeitig Dokumentenemotionsvektor), Standardabweichung berechnen, 
			//diese Kennwerte festhalten.
			System.err.println("Calculating Document-Term-Vectors, Performing "
					+ "dictionary look-ups and calculating document emotion...");
			for (DocumentContainer container: containers){
				int[] documentTermVector = calculateDocumentTermVector(container);
				container.recognizedTokenCount = Util.sumOverVector(lexikonProjection(documentTermVector));
				System.err.println("\t"+container.document.getName());
				calculateEmotionVector(container, documentTermVector);
			}
		}
		//tfidf weights...
		else if (this.settings.weightFunction.equals("tfidf")){
			System.err.println("Calculating Document-Term-Vectors, Performing "
					+ "dictionary look-ups and calculating document emotion...");
			this.documentTermVectorFolder.mkdir();
			//calculate and save document term vectors
			for (DocumentContainer container: containers){
				int[] documentTermVector = calculateDocumentTermVector(container);
				container.writeDocumentTermVector(documentTermVector);
				container.recognizedTokenCount = Util.sumOverVector(lexikonProjection(documentTermVector));
			}
			//calculate document frequencies
			int[] documentFrequencies = this.calculateDocumentFrequencies();
			//calculate tf-idf measures and emotion vectors
			for (DocumentContainer container: containers){
				System.err.println("\t"+container.document.getName());
				float[] tfidfVector = calculateTfidf(container, container.getDocumentTermVector(),
						documentFrequencies);
				calculateEmotionVector(container, tfidfVector);
			}

		}
		else
			throw new Exception("Illegal weight function chosen!");
			
		//Rückgabe
		return containers;
	}
	
	/**
	 * Calculates the number of documents each word of the vocabulary is in.
	 * @return
	 * @throws IOException
	 */
	private int[] calculateDocumentFrequencies() throws IOException{
		int[] docuementFrequencies = new int[this.vocabulary.size];
		//for every document term vector
		for (DocumentContainer container: this.containers){
			int[] termFrequencies = container.getDocumentTermVector();
			//for every entry
			for (int i = 0; i < this.vocabulary.size; i++){
				//if entry is greater than 0 than increment the corresponding entry of the
				//document frequencies
				if (termFrequencies[i] > 0)
					docuementFrequencies[i]++;
			}		
		}
		return docuementFrequencies;
	}
	
	
	private float[] calculateTfidf(DocumentContainer container, int[] documentTermVector,
			int[] documentFrequencies ){
		float[] tfidfWeights = new float[this.vocabulary.size];
		for (int i = 0; i < this.vocabulary.size; i++)
			tfidfWeights[i] = (float) Util.tfidf(documentTermVector[i], this.corpus.length, documentFrequencies[i]);
		return tfidfWeights;
	}
	
	
	private void calculateEmotionVector(DocumentContainer container, int[] documentTermVector) throws IOException {
		float[] fArray = new float[documentTermVector.length];
		for (int i = 0; i < documentTermVector.length; i++)
			fArray[i] = (float) documentTermVector[i];
		calculateEmotionVector(container, fArray);
	}
	
	private void calculateEmotionVector(DocumentContainer container, float[] weightVector) throws IOException {
		float[] projectedWeightVector = lexiconProjection(weightVector);
		double valence = 0;
		double arousal = 0;
		double dominance = 0;
		double sumOfWeights = 0;
		for (int i = 0; i < this.vocabulary.size; i++){
			valence += projectedWeightVector[i]*this.vocabularyEmotionMatrix[i][0];
			arousal += projectedWeightVector[i]*this.vocabularyEmotionMatrix[i][1];
			dominance += projectedWeightVector[i]*this.vocabularyEmotionMatrix[i][2];
			sumOfWeights +=  projectedWeightVector[i];
		}
//		container.recognizedTokenCount = sumOfWeights; // Das musste ich hier leider rausnehmen, weil 
														// das bei tfidf keinen sinn mehr gemacht hätte,
														// stattdessen wird das jetzt woanders berechnet...
		// if the number of recognized tokens is 0, normalisation is not possivle
		if (sumOfWeights==0){
			container.documentEmotionVector = new EmotionVector(0,0,0);
			container.standardDeviationVector = new EmotionVector(0,0,0);
		}
		else{
		EmotionVector emotionVector = new EmotionVector(valence, arousal, dominance);
		emotionVector.normalize(sumOfWeights);
		container.documentEmotionVector = emotionVector;
		
		//TODO Weiß nicht, ob das mit tfidf wirklich sinn macht...
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
				sqDevValence += projectedWeightVector[i] * Math.pow((emotionVector.getValence() - vocabularyEmotionMatrix[i][0]), 2);
				sqDevArousal += projectedWeightVector[i] * Math.pow((emotionVector.getArousal() - vocabularyEmotionMatrix[i][1]), 2);
				sqDevDominance += projectedWeightVector[i] * Math.pow((emotionVector.getDominance() - vocabularyEmotionMatrix[i][2]), 2);
			}
		}
		EmotionVector sdVector = new EmotionVector(Math.sqrt(sqDevValence/sumOfWeights),Math.sqrt(sqDevArousal/sumOfWeights),Math.sqrt(sqDevDominance/sumOfWeights));
		container.standardDeviationVector = sdVector;
		}
	}
		
		

	/**
	 * Sets all compounents of the document-term-vector to 0, if they represent a word not in
	 * the lexicon.
	 * @param givenDocumentTermVector
	 * @return
	 */
	private float[] lexiconProjection(float[] givenDocumentTermVector){
		for (int i=0; i<givenDocumentTermVector.length; i++){
			givenDocumentTermVector[i] = givenDocumentTermVector[i]*(float)this.vocabularyLexiconVector[i];			
		}
		return givenDocumentTermVector;
	}
	
	private float[] lexikonProjection(int[] givenDoucemntTermVector){
		float[] fArray = new float[this.vocabulary.size];
		for (int i = 0; i < this.vocabulary.size; i++)
			fArray[i] = (float) givenDoucemntTermVector[i];
		return lexiconProjection(fArray);
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
		// no filtering anymore, just analyze every file
		 // create new filename filter
//        FilenameFilter filter = new FilenameFilter() {
//  
//           @Override
//           public boolean accept(File dir, String name) {
//              if (name.endsWith(".txt")) return true;
//              else return false;
//           }
//        };
//		corpus = corpusFolder.listFiles(filter);
		List<File> tmpList = new ArrayList<File>();
		for (File f: corpusFolder.listFiles()){
			if (f.isFile()) tmpList.add(f);
		}
		return tmpList.toArray(new File[tmpList.size()]);
	}

	
	void showLexicon(){
		this.lexicon.printLexicon();
	}
	
	void showStemmedLexicon(){
		this.stemmedLexicon.printLexicon();
	}
	
	
	private Vocabulary collectVocabulary() throws IOException{
		//***NEW*** map <String, VocabularyEntry> vocMap
		this.VocabularyFolder=new File(this.targetFolder.getAbsolutePath()+"/Vocabulary");
		this.VocabularyFolder.mkdir();
		Set<String> vocabularySet= new HashSet<String>();
		//for every normalized document
		for (DocumentContainer container: this.containers){
			System.err.println("\t"+container.document.getName());
			List<String> normalizedDocument = Files.readAllLines(container.normalizedDocument.toPath());
			//for every word in normalized document
			for (String line : normalizedDocument){
				vocabularySet.add(line);
				/*** NEW VERSION ***
				 * increment
				 * 
				 * 
				 * Ich glaube das funzt so wirklich alles nicht und man muss ganz standard
				 * eine document-term-matrix aufbauen....dann könnte man die Modifikationen tatsächlich
				 * an einer anderen Stelle machen, nämlich wenn die Dokuemtn Term vektoren
				 * schon da sind..
				 * 
				 * 
				 */
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
