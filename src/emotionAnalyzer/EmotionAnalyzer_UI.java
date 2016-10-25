package emotionAnalyzer;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class EmotionAnalyzer_UI {
	
	public static void main (String[] args) throws Exception{
		EmotionAnalyzer analyzer;
		DocumentContainer[] containers;
		if (args.length > 0){
			switch (args[0]){
			
			case "-help":
				printHelp();
				break;
			case "-test":
				runTests();
				break;
			case "-advanced":
				System.out.print("Indicate lexicon. The Lexicon has to be in csv-Format. ");
				String lexicon = System.console().readLine();
				System.out.print("Indicate weight function (absolute / tfidf). ");
				String weight = System.console().readLine();
				if (!(weight.equals("absolute") || weight.equals("tfidf")))
					throw new Exception("Invalid weight function selected.");
				System.out.print("Indicate the kind of preprocessing for input documents and lexicon (lemmatize/none). ");
				String preprocessing_in = System.console().readLine();
				Preprocessing preprocessing = null;
				if (preprocessing_in.equals("lemmatize")) preprocessing = Preprocessing.LEMMATIZE;
				else if (preprocessing_in.equals("none")) preprocessing = Preprocessing.NONE;
				else throw new Exception("Invalid preprocessing mode chosen. ");
				System.out.print("Indicate the path of the input files. ");
				String input = System.console().readLine();
				System.out.print("Indicate the path where the aux files should be saved. ");
				String aux = System.console().readLine();
				System.out.print("Indicate output-file. ");
				String output = System.console().readLine();
				analyzer = new EmotionAnalyzer();
				containers = analyzer.analyze(new File(input), new File(aux), new Settings(preprocessing, false, weight), lexicon);
				//printDataTemplate();
				//for (DocumentContainer container: containers){
				//	container.printData();
				//}
				List<String> output_list = new ArrayList<String>();
				output_list.add(DATA_TEMPLATE);
				DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
				otherSymbols.setDecimalSeparator('.');
				otherSymbols.setGroupingSeparator(','); 
				DecimalFormat df = new DecimalFormat("#.#####", otherSymbols); // Anzahl der Dezimalstellen festlegen	
				for ( DocumentContainer container: containers){
					output_list.add(container.document.getName() + "\t" 	
							+ df.format(container.documentEmotionVector.getValence()) + "\t"
							+ df.format(container.documentEmotionVector.getArousal()) + "\t"
							+ df.format(container.documentEmotionVector.getDominance()) + "\t"
							+ df.format(container.standardDeviationVector.getValence()) + "\t"
							+ df.format(container.standardDeviationVector.getArousal()) + "\t"
							+ df.format(container.standardDeviationVector.getDominance()) + "\t"
							+ df.format(container.tokenCount) + "\t"  
							+ df.format(container.alphabeticTokenCount) + "\t"
							+ df.format(container.non_stopword_tokenCount) + "\t"
							+ df.format(container.recognizedTokenCount)	+	"\t"
							+ df.format(container.numberCount));
				}
				Util.writeList2File(output_list, output);
				break;
			case "-config":
				String inputfolder = args[1];
				String lexiconpath = args[2];
				String preprocessing_string = args[3];
				Preprocessing preprocessing_type = null;
				if (preprocessing_string.equals("lemmatize")) preprocessing_type = Preprocessing.LEMMATIZE;
				else if (preprocessing_string.equals("none")) preprocessing_type = Preprocessing.NONE;
				String weighting_function = args[4];
				String aux_path = args[5];
				String output_path = args[6];
				analyzer = new EmotionAnalyzer();
				containers = analyzer.analyze(new File(inputfolder), new File(aux_path), 
						new Settings(preprocessing_type, false, weighting_function), lexiconpath);
				List<String> outlist = new ArrayList<String>();
				outlist.add(DATA_TEMPLATE);
				DecimalFormatSymbols symbols = new DecimalFormatSymbols();
				symbols.setDecimalSeparator('.');
				symbols.setGroupingSeparator(','); 
				DecimalFormat df1 = new DecimalFormat("#.#####", symbols); // Anzahl der Dezimalstellen festlegen	
				for ( DocumentContainer container: containers){
					outlist.add(container.document.getName() + "\t" 	
							+ df1.format(container.documentEmotionVector.getValence()) + "\t"
							+ df1.format(container.documentEmotionVector.getArousal()) + "\t"
							+ df1.format(container.documentEmotionVector.getDominance()) + "\t"
							+ df1.format(container.standardDeviationVector.getValence()) + "\t"
							+ df1.format(container.standardDeviationVector.getArousal()) + "\t"
							+ df1.format(container.standardDeviationVector.getDominance()) + "\t"
							+ df1.format(container.tokenCount) + "\t"  
							+ df1.format(container.alphabeticTokenCount) + "\t"
							+ df1.format(container.non_stopword_tokenCount) + "\t"
							+ df1.format(container.recognizedTokenCount)	+	"\t"
							+ df1.format(container.numberCount));
				}
				Util.writeList2File(outlist, output_path);
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
				analyzer = new EmotionAnalyzer();
				containers = analyzer.analyze(srcDir, targetDir, Util.defaultSettings, Util.DEFAULTLEXICON);
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
	
	private static final String DATA_TEMPLATE =	"File Name"
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
			+ "\tNumberCount";
	
	
	private static void printDataTemplate() {
		System.out.println(DATA_TEMPLATE); 
		
	}

	private static void printHelp(){
		System.out.println("\nUsage:\tIndicate a source folder (first argument, all txt-files will be "
				+ "analyzed) and a target folder (second argument, auxilary files and additional output "
				+ "will be saved there). The main output of this tool will be printed in standard output."
				+ " For further Information, please consult the README-file."
				+ "\n\n Options:"
				+ "\n\n\t-help\t\tPrint this message."
//				+ "\n\t-test\t\tCheck functionality of this tool.\n"
				+ "\n\t-advanced\t\tSpecify advanced settings in dialog-fashion.\n"
				+ "\t-config\t\t Parameters: input, lexicon, preprocessing, weighting, auxilary files, output."
				);
	}
}
