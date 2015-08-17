package emotionAnalyzer;

import com.google.common.hash.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.sound.sampled.Line;

import com.google.common.collect.HashMultiset;

public class EmotionLexicon {
	
	String lexiconPath="src/emotionAnalyzer/LexiconWarriner2013_transformed.txt";	//TODO put in config-file?
	HashMap<String, EmotionVector> LexiconMap = new HashMap<String, EmotionVector>(14000,(float)1.0);
	
	public EmotionVector neutralVector = new EmotionVector(0,0,0); //TODO in config-file oder an andere Stelle?
	
	public EmotionLexicon() throws IOException{
//		System.out.println("loading lexicon");
		this.loadLexicon();
	}
	
	public EmotionLexicon(String lexiconPath) throws IOException{
		this.loadLexicon(lexiconPath);
	}
	
	private void loadLexicon() throws IOException{
		this.loadLexicon(lexiconPath);
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
				 this.LexiconMap.put(currentWord, currentVector);}
		 	}
		 return;
	}
	
	public EmotionVector lookUp(String token) throws IOException{
		if (this.LexiconMap.get(token)!=null) return this.LexiconMap.get(token);
		else return neutralVector;
	}
	
	
	private BufferedReader file2BufferedReader(String path){
		BufferedReader bReader = null;
		try {
			bReader= new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bReader;
		
	}
	
	void printLexicon(){
		for (Map.Entry<String, EmotionVector> currentEntry: LexiconMap.entrySet()){
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
	//TODO Not tested yet. Perhaps, not necessary at all, because i can do that in a spreadsheet.
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
	
	
	
}
