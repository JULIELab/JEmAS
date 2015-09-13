package emotionAnalyzer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import porterStemmer.PorterStemmerWrapper;

public class EmotionLexicon {
	
	String lexiconPath="src/emotionAnalyzer/LexiconWarriner2013_transformed.txt";
	HashMap<String, EmotionVector> LexiconMap = new HashMap<String, EmotionVector>(14000,(float)1.0);
		
	public EmotionLexicon() throws IOException{
//		System.out.println("loading lexicon");
		this.loadLexicon();
	}
	
	public EmotionLexicon(String lexiconPath) throws IOException{
		this.loadLexicon(lexiconPath);
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
	

	//deprecated?
		private void loadLexicon() throws IOException{
		try {
			//if unpacked
			this.loadLexicon(lexiconPath);
		} catch (Exception e) {
			//if packed in jar
			this.loadLexicon(EmotionAnalyzer.DEFAULTLEXICON_JAR);
		}
	}
	
	public Set<String> getKeySet(){
		return LexiconMap.keySet();
	}
	
	
	private void loadLexicon(String path) throws IOException{
		BufferedReader bReader = null;
		String line = null;
		
		bReader = this.file2BufferedReader(path);
		 while ((line = bReader.readLine())!=null){
			 //the lines at the beginning of the file starting with // are comments. Therefore, should not be read to EmotionVector.
			 while (line.startsWith("//")){
				 line =bReader.readLine();
			 }
			 String[] line2Array = line.split("\t");
			 String currentWord = line2Array[0];
			 if (line2Array.length==4){ //checks if csv-entry is well-formed. Otherwise, OutOfArray-Exepction may occur.
				 //transforms the last 3 coloums of the csv-file into an emotion Vector. String values have to be transformed to double using Double.parseDouble(string).
				 EmotionVector currentVector = new EmotionVector(Double.parseDouble(line2Array[1]), Double.parseDouble(line2Array[2]), Double.parseDouble(line2Array[3]));
				 this.LexiconMap.put(currentWord, currentVector);
			 }
		 }
		
//		 try {
//			bReader = this.file2BufferedReader(path);
//			 while ((line = bReader.readLine())!=null){
//				 //the lines at the beginning of the file starting with // are comments. Therefore, should not be read to EmotionVector.
//				 while (line.startsWith("//")){
//					 line =bReader.readLine();
//				 }
//				 String[] line2Array = line.split("\t");
//				 String currentWord = line2Array[0];
//				 if (line2Array.length==4){ //checks if csv-entry is well-formed. Otherwise, OutOfArray-Exepction may occur.
//					 //transforms the last 3 coloums of the csv-file into an emotion Vector. String values have to be transformed to double using Double.parseDouble(string).
//					 EmotionVector currentVector = new EmotionVector(Double.parseDouble(line2Array[1]), Double.parseDouble(line2Array[2]), Double.parseDouble(line2Array[3]));
//					 this.LexiconMap.put(currentWord, currentVector);}
//			 }
//		} catch (Exception e) {
//			//if packed in jar
////			bReader = this.file2BufferedReader(Util.getJarPath(path));
//			bReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/emotionAnalyzer/LexiconWarriner2013_transformed.txt")));
//			 while ((line = bReader.readLine())!=null){
//				 //the lines at the beginning of the file starting with // are comments. Therefore, should not be read to EmotionVector.
//				 while (line.startsWith("//")){
//					 line =bReader.readLine();
//				 }
//				 String[] line2Array = line.split("\t");
//				 String currentWord = line2Array[0];
//				 if (line2Array.length==4){ //checks if csv-entry is well-formed. Otherwise, OutOfArray-Exepction may occur.
//					 //transforms the last 3 coloums of the csv-file into an emotion Vector. String values have to be transformed to double using Double.parseDouble(string).
//					 EmotionVector currentVector = new EmotionVector(Double.parseDouble(line2Array[1]), Double.parseDouble(line2Array[2]), Double.parseDouble(line2Array[3]));
//					 this.LexiconMap.put(currentWord, currentVector);}
//			 }
//			
//		}
//		 return;
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
	
	
	
	/**
	 * Returns a Buffered reader of the indicated file/resource. The path which worked in IDE should also work when packed into jar.
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 */
	private BufferedReader file2BufferedReader(String path) throws FileNotFoundException{
		BufferedReader bReader = null;
			try {
				//if not packed into jar
				bReader= new BufferedReader(new FileReader(path));
			} catch (Exception e) {
				//if packed into jar
				bReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(Util.getJarLocation(path))));
			}
		return bReader;
		
	}
	
	void printLexicon(){
		for (Entry<String, EmotionVector> currentEntry: LexiconMap.entrySet()){
			System.out.print(currentEntry.getKey()+"\t");
			currentEntry.getValue().print();
		}
	}
	
	
	
	
	
	/**
	 * converts a lexicon csc file by loading it to internal map representation, adding a given vector to every emotion vector in the lexicon.
	 * This can be done a adjust the "neutral emotion vector" of a lexicon: if the lexicon was build using likert-scale the neutral vector will
	 * usually be (5,5,5). However, for computation (0,0,0) would be more suitable.
	 * @param additionalVector
	 * @param outputPath
	 * @throws IOException
	 */
	//not tested yet
	public void convertLexicon(EmotionVector additionalVector, double multiplicationConstant) throws IOException{
		String line = null;
		this.loadLexicon();
		for (Map.Entry<String, EmotionVector> currentEntry: LexiconMap.entrySet()){
			
			EmotionVector currentVector = currentEntry.getValue();
			currentVector.addVector(additionalVector);
			currentVector.multiplyWithConstant(multiplicationConstant);
			//transforms a map entry into a string
			line = currentEntry.getKey()+"/t"+currentVector.getValence()+"/t"+currentVector.getArousal()+"/t"+
					currentVector.getDominance();
			//
			try (Writer writer = new BufferedWriter(new OutputStreamWriter(
		              new FileOutputStream("convertedLexicon.txt"), "utf-8"))) {
		   writer.write(line+"\n");
			}
		}
	}
	
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
