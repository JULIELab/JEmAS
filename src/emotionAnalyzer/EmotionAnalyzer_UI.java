package emotionAnalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Scanner;

public class EmotionAnalyzer_UI {
	

	/**
	 * USAGE: -option- -file-
	 * OPTIONS:-file	Analyzes a single file or a list of files.
				-folder	Analyzes all .txt-files in a folder.
				-help	Show this help message;
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		EmotionAnalyzer analyzer = new EmotionAnalyzer(EmotionAnalyzer.DEFAULTLEXICON);
//		System.out.println("Folgende Argumente wurden erkannt:");
//		for (String arg: args) System.out.println(arg);
		
		if (args.length>0) {
			switch (args[0]) {
			case "-file":
//				System.out.println("reading arguments...");
				printDataTemplate();
				int argCount = 1;
				while (argCount<args.length){
					getVector(args[argCount], analyzer);
					argCount++;
				}
				break;
				
			case "-folder":
				printDataTemplate();
				File folder = new File(args[1]);
				if (!folder.isDirectory()) throw new FileNotFoundException("No such directory!");
				File[] files = folder.listFiles();
				for (File currentFile: files){
					if (currentFile.getName().endsWith(".txt")){
						getVector(currentFile.getPath(), analyzer);
					}
				}
				break;

			case "-help":
				printHelp();
				break;
			
			default:
				System.out.println("No correct option. Printing -help ...");
			
			}
		}
		else printHelp();
		
//		if (args.length==0){
//		System.out.println("Welcome to the EmotionAnalyzer!\nPlease type in the path of the file to be analyzed and press Enter.");
//		Scanner scanner = new Scanner(System.in);
//		String input = scanner.nextLine();
//		getVector(input);
//		scanner.close();
//		}
		
//		else if (args[0].equals("-run")){
//			System.out.println("reading arguments...");
//			EmotionVector.printTemplate();
//			int argCount = 1;
//			while (argCount<args.length){
//				getVector(args[argCount]);
//				argCount++;
//			}
//		}
		
	}
	
	
	
	private static void printDataTemplate() {
		System.out.println("File Name"
				+ "\tReport Category"
				+ "\tOrigin"
				+ "\tOrganization"
				+ "\tYear"
				+ "\tValence"
				+ "\tArousal"
				+ "\tDominance"
				+ "\tLength"
				+ "\tTokens"
				+ "\tWord Tokens"
				+ "\tIdentified Tokens"); //Documentname, Valence, Arousal, Dominace, vector lenght
		
	}



	private static void printHelp() {
		System.out.println("USAGE: <option> <file>\n\n"
				+ "OPTIONS:\t-file\tAnalyzes a single file or a list of files.\n"
				+ "\t\t-folder\tAnalyzes all .txt-files in a folder.\n"
				+ "\t\t-help\tShow this help message");
				
		
	}

	private static void getVector(String documentPath, EmotionAnalyzer emotionAnalyzer) throws IOException{
//		EmotionAnalyzer currentEmotionAnalyzer = new EmotionAnalyzer(EmotionAnalyzer.DEFAULTLEXICON);
		DocumentContainer container = null;
//			container = emotionAnalyzer.analyzeEmotions(documentPath);
		container = emotionAnalyzer.analyzeEmotions(documentPath, Util.defaultSettings);
		if (container!=null) {
			container.printData();
		}
		
	}

}
