package emotionAnalyzer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import porterStemmer.PorterStemmerWrapper;
import stanford_lemmatizer.StanfordLemmatizer;

public class EmotionLexicon {
	
	//StanfordLemmatizer lemmatizer;
	String lexiconPath="src/emotionAnalyzer/LexiconWarriner2013_transformed.txt";
	HashMap<String, EmotionVector> LexiconMap = new HashMap<String, EmotionVector>(14000,(float)1.0);
		
//	public EmotionLexicon() throws IOException{
////		System.out.println("loading lexicon");
//		this.loadLexicon();
//	}
	
	public EmotionLexicon(String lexiconPath, StanfordLemmatizer lemmatizer) throws IOException{
		this.loadLexicon(lexiconPath, lemmatizer);
	}
	
	/**
	 * Constructor used when producing a stemmed lexicon out of an existing one. 
	 * @param givenMap Use getStemmedLexicon method of an existing lexicon to construct a stemmed one out of it.
	 * @param givenLexiconPath Acutally not needed, but in case some other method will ask for the lexicon path it is better to hold it up to date
	 */
	public EmotionLexicon (HashMap<String, EmotionVector> givenMap, String givenLexiconPath){
		this.lexiconPath = null;
		this.lexiconPath = givenLexiconPath;
		this.LexiconMap = givenMap;
	}
	
	
	/**
	 * Returns the a stemmed version of the lexicon's map.
	 * Only the first occurence will be put in the map, if multiple entries are made the same by stemming.
	 */
	public HashMap<String, EmotionVector> getStemmedLexicon(){
		PorterStemmerWrapper stemmer = new PorterStemmerWrapper();
		HashMap<String, EmotionVector> stemmedMap = new HashMap<String, EmotionVector>();
		for (Entry<String, EmotionVector> currentEntry: this.LexiconMap.entrySet()){
			//for every entry of the orignial map, put an entry in the new map in which the key is stemmed and the value is the same.
			stemmedMap.putIfAbsent(stemmer.stem(currentEntry.getKey()), currentEntry.getValue());
		}
		return stemmedMap;
	}
	
	/**
	 * 
	 * @return Returns the key set of the internal representation of the lexicon, e.g., the the key set
	 * of the internal hash map.
	 */
	public Set<String> getKeySet(){
		return LexiconMap.keySet();
	}
	

	//deprecated?
//		private void loadLexicon() throws IOException{
//		try {
//			//if unpacked
//			this.loadLexicon(lexiconPath);
//		} catch (Exception e) {
//			//if packed in jar
//			this.loadLexicon(Util.DEFAULTLEXICON_JAR);
//		}
//	}
//	
//	public Set<String> getKeySet(){
//		return LexiconMap.keySet();
//	}
	
	
	private void loadLexicon(String path, StanfordLemmatizer lemmatizer ) throws IOException{
		BufferedReader bReader = null;
		String line = null;
		List<Token> tokenList = new LinkedList<Token>();
		List<Lemma> lemmaList = new LinkedList<Lemma>();
		
		bReader = Util.file2BufferedReader(path);
		 while ((line = bReader.readLine())!=null){
			 //the lines at the beginning of the file starting with // are comments. Therefore, should not be read to EmotionVector.
			 while (line.startsWith("//")){
				 line =bReader.readLine();
			 }
			 String[] line2Array = line.split("\t");
			 String currentWord = line2Array[0];
			 if (line2Array.length==4){ //checks if csv-entry is well-formed. Otherwise, OutOfArray-Exepction may occur.
				 //transforms the last 3 coloums of the csv-file into an emotion Vector. String values have to be transformed to double using Double.parseDouble(string).#
				 //reads EmotionVector of token currentWord
				 EmotionVector currentVector = new EmotionVector(Double.parseDouble(line2Array[1]), Double.parseDouble(line2Array[2]), Double.parseDouble(line2Array[3]));
				// filing tokenList with all the entries from the external Lexicon
				 tokenList.add(new Token(currentWord, currentVector));
			 }
		 }
		 // mapping tokens to lemmas filling lemmaList
		for (Token currentToken: tokenList){
			boolean exists = false;
			String lemmatizedToken = lemmatizer.lemmatizeToken(currentToken.tokenName);
			for (Lemma currentLemma : lemmaList){
				if (currentLemma.lemmaName.equals(lemmatizedToken)){
					currentLemma.tokenList.add(currentToken);
					exists = true;
				}
			}
			if (exists == false){
				Lemma newLemma = new Lemma();
				newLemma.lemmaName = lemmatizedToken;
				newLemma.tokenList.add(currentToken);
				lemmaList.add(newLemma);
			}
		}
		//calculate lemma emotion and write into lexicon map.
		for (Lemma currentLemma: lemmaList){
			currentLemma.calculateEmotion();
			this.LexiconMap.put(currentLemma.lemmaName, currentLemma.emotionVector);
		}
		


	}
	
	
	/**
	 * Performs the dicitonary look-up. Returns the emotion vector of the given "word". By (to some extend aribitrary definition) the vector of an unindidentified "word" is null (not 0,0,0 or any other neutral vector). Therefore, in normalization process, the number of identified "words" is important.
	 * @param token
	 * @return
	 * @throws IOException
	 */
	public EmotionVector lookUp(String token) throws IOException{
		if (this.LexiconMap.get(token)!=null) return this.LexiconMap.get(token);
		else return null;
		//else return neutralVector
	}
	
	
	
	
	
	public void printLexicon(){
		for (Entry<String, EmotionVector> currentEntry: LexiconMap.entrySet()){
			System.out.print(currentEntry.getKey()+"\t");
			currentEntry.getValue().print();
		}
	}
	
	
	
	
	//dont really need this, transformation can be done beforehand.
	
//	/**
//	 * converts a lexicon csc file by loading it to internal map representation, adding a given vector to every emotion vector in the lexicon.
//	 * This can be done a adjust the "neutral emotion vector" of a lexicon: if the lexicon was build using likert-scale the neutral vector will
//	 * usually be (5,5,5). However, for computation (0,0,0) would be more suitable.
//	 * @param additionalVector
//	 * @param outputPath
//	 * @throws IOException
//	 */
//	//not tested yet
//	public void convertLexicon(EmotionVector additionalVector, double multiplicationConstant) throws IOException{
//		String line = null;
//		this.loadLexicon();
//		for (Map.Entry<String, EmotionVector> currentEntry: LexiconMap.entrySet()){
//			
//			EmotionVector currentVector = currentEntry.getValue();
//			currentVector.addVector(additionalVector);
//			currentVector.multiplyWithConstant(multiplicationConstant);
//			//transforms a map entry into a string
//			line = currentEntry.getKey()+"/t"+currentVector.getValence()+"/t"+currentVector.getArousal()+"/t"+
//					currentVector.getDominance();
//			//
//			try (Writer writer = new BufferedWriter(new OutputStreamWriter(
//		              new FileOutputStream("convertedLexicon.txt"), "utf-8"))) {
//		   writer.write(line+"\n");
//			}
//		}
//	}
	
	
	// Currently not in use.
	/**
	 * Returns a new EmotionLexicon with stemmed entries.
	 * @return
	 * @throws IOException
	 */
	public EmotionLexicon stemLexicon () throws IOException{
		EmotionLexicon stemmedLexicon = new EmotionLexicon(this.getStemmedLexicon(), this.lexiconPath);
		return stemmedLexicon;
	}
	
	
	
}
