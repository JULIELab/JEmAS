package experiments;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

import emotionAnalyzer.EmotionLexicon;
import emotionAnalyzer.EmotionVector;
import emotionAnalyzer.Lemma;
import emotionAnalyzer.Token;
import emotionAnalyzer.Util;
import stanford_lemmatizer.StanfordLemmatizer;

public class TokenLemmaMappingAnalyzer {
	
	/**
	 * This class is used to find out how simliar the emotions of tokens are whiche will be mapped 
	 * on the same lemma. Therefore, it provides data on the number of tokens per lemma and univariant
	 * statistics for them.
	 * @param args
	 */
	static public void main(String[]args) throws Exception{
		StanfordLemmatizer lemmatizer = new StanfordLemmatizer();
		BufferedReader bReader = null;
		String line = null;
		List<Token> tokenList = new LinkedList<Token>();
		List<Lemma> lemmaList = new LinkedList<Lemma>();	
		
		bReader = Util.file2BufferedReader(Util.DEFAULTLEXICON);
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
		//Print lemma-List
		System.setOut(new PrintStream(new File("Token_Lemma_Mapping_2015-12-05.csv")));
		Lemma.printLemmaStatsHeader(); 
		for (Lemma lemma: lemmaList){
			lemma.printLemmaStats();
		}
		
	}

}
