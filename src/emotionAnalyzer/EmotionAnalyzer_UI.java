package emotionAnalyzer;

import java.io.File;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class EmotionAnalyzer_UI {
	

	/**
	 * @param args
	 * @throws Exception 
	 */
	
	public static void main (String[] args) throws Exception{
		if (args.length > 0){
			switch (args[0]){
			
			case "-help":
				printHelp();
				break;
			case "-test":
				runTests();
				break;
			default:
				File srcDir = new File(args[0]);
				File targetDir;
				if (args.length >1){
					targetDir = new File(args[1]);
				}
				else{
					targetDir = srcDir;
				}
				EmotionAnalyzer analyzer = new EmotionAnalyzer(Util.DEFAULTLEXICON);
				DocumentContainer[] containers = analyzer.analyze(srcDir, targetDir, Util.defaultSettings);
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
	
	
	private static void printDataTemplate() {
		System.out.println("File Name"
//				+ "\tReport Category"
//				+ "\tOrigin"
//				+ "\tOrganization"
//				+ "\tYear"
				+ "\tValence"
				+ "\tArousal"
				+ "\tDominance"
				+ "\tStdDev Valenence"
				+ "\tStdDev Arousal"
				+ "\tStdDev Dominance"
				+ "\tTokens"
				+ "\tAlphabetic Token"
				+ "\tNon-Stopword Tokens"
				+ "\tRecognized Tokens"
				+ "\tNumberCount"); 
		
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
