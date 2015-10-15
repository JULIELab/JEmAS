package emotionAnalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class EmotionAnalyzer_UI {
	

	/**
	 * @param args
	 * @throws Exception 
	 */
	
	public static void main (String[] args) throws Exception{
		if (args.length==1){
			switch (args[0]){
			
			case "-help":
				printHelp();
				break;
			case "-test":
				runTests();
				break;
			default:
				File dir = new File(args[0]);
				EmotionAnalyzer analyzer = new EmotionAnalyzer(Util.DEFAULTLEXICON);
				DocumentContainer[] containers = analyzer.analyze(dir, Util.defaultSettings);
				printDataTemplate();
				for (DocumentContainer container: containers){
					container.printData();
				}
				break;
			}
		}
		else printHelp();
	}
	
	
	private static void runTests(){
		JUnitCore junit = new JUnitCore();
		Result result = junit.run(Tests.class);
		System.err.println("Ran " + result.getRunCount() + " tests in "+ result.getRunTime() +"ms.");
		if (result.wasSuccessful()) System.out.println("All tests were successfull!");
		else {
			System.err.println(result.getFailureCount() + "Failures:");
			for (Failure fail: result.getFailures()){
				System.err.println("Failure in: "+ fail.getTestHeader());
				System.err.println(fail.getMessage());
				System.err.println(fail.getTrace());
				System.err.println();
			}
		}
		
	}
	
//	public static void main(String[] args) throws IOException {
//		EmotionAnalyzer analyzer = new EmotionAnalyzer(Util.DEFAULTLEXICON);
////		System.out.println("Folgende Argumente wurden erkannt:");
////		for (String arg: args) System.out.println(arg);
//		
//		if (args.length>0) {
//			switch (args[0]) {
//			case "-file":
////				System.out.println("reading arguments...");
//				printDataTemplate();
//				int argCount = 1;
//				while (argCount<args.length){
//					getVector(args[argCount], analyzer);
//					argCount++;
//				}
//				break;
//				
//			case "-folder":
//				printDataTemplate();
//				File folder = new File(args[1]);
//				if (!folder.isDirectory()) throw new FileNotFoundException("No such directory!");
//				File[] files = folder.listFiles();
//				for (File currentFile: files){
//					if (currentFile.getName().endsWith(".txt")){
//						getVector(currentFile.getPath(), analyzer);
//					}
//				}
//				break;
//
//			case "-help":
//				printHelp();
//				break;
//			
//			default:
//				System.out.println("No correct option. Printing -help ...");
//			
//			}
//		}
//		else printHelp();
//		
////		if (args.length==0){
////		System.out.println("Welcome to the EmotionAnalyzer!\nPlease type in the path of the file to be analyzed and press Enter.");
////		Scanner scanner = new Scanner(System.in);
////		String input = scanner.nextLine();
////		getVector(input);
////		scanner.close();
////		}
//		
////		else if (args[0].equals("-run")){
////			System.out.println("reading arguments...");
////			EmotionVector.printTemplate();
////			int argCount = 1;
////			while (argCount<args.length){
////				getVector(args[argCount]);
////				argCount++;
////			}
////		}
//		
//	}
	
	
	
	private static void printDataTemplate() {
		System.out.println("File Name"
				+ "\tReport Category"
				+ "\tOrigin"
				+ "\tOrganization"
				+ "\tYear"
				+ "\tValence"
				+ "\tArousal"
				+ "\tDominance"
				+ "\tStdDev Valenence"
				+ "\tStdDev Arousal"
				+ "\tStdDev Dominance"
				+ "\tTokens"
				+ "\tAlphabetic Token"
				+ "\tNon-Stopword Tokens"
				+ "\tRecognized Tokens"); 
		
	}



//	private static void printHelp() {
//		System.out.println("USAGE: <option> <file>\n\n"
//				+ "OPTIONS:\t-file\tAnalyzes a single file or a list of files.\n"
//				+ "\t\t-folder\tAnalyzes all .txt-files in a folder.\n"
//				+ "\t\t-help\tShow this help message");
//	}
	
	private static void printHelp(){
		System.out.println("\tUsage:\tIndicate a folder. All txt-files will be analyzed.\n\n -help\t\tPrint this message.\n-test\t\tRun tests. To do this, please place‘testFolder‘ in the working directory");
	}

//	private static void getVector(String documentPath, EmotionAnalyzer emotionAnalyzer) throws IOException{
////		EmotionAnalyzer currentEmotionAnalyzer = new EmotionAnalyzer(EmotionAnalyzer.DEFAULTLEXICON);
//		DocumentContainer container = null;
////			container = emotionAnalyzer.analyzeEmotions(documentPath);
//		container = emotionAnalyzer.analyzeEmotions(documentPath, Util.defaultSettings);
//		if (container!=null) {
//			container.printData();
//		}
//		
//	}

}
