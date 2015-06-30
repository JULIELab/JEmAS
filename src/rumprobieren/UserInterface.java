package rumprobieren;

import java.io.IOException;
import java.util.Scanner;

public class UserInterface {
	

	public static void main(String[] args) throws IOException {
		System.out.println("Folgende Argumente wurden erkannt:");
		for (String arg: args) System.out.println(arg);
		
		if (args.length==0){
		System.out.println("Welcome to the EmotionAnalyzer!\nPlease type in the path of the file to be analyzed and press Enter.");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		getVector(input);
		scanner.close();
		}
		else if (args[0].equals("run")){
			System.out.println("reading arguments...");
			int argCount = 1;
			while (argCount<args.length){
				getVector(args[argCount]);
				argCount++;
			}
		}
	}
	
	private static void getVector(String documentPath) throws IOException{
		EmotionVector calculatedVector=null;
		EmotionAnalyzer currentEmotionAnalyzer = new EmotionAnalyzer(EmotionAnalyzer.DEFAULTLEXICON);
		try {
			calculatedVector = currentEmotionAnalyzer.calculateEmotionVector(documentPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("No such file!");
		}
		if (calculatedVector!=null) {
			currentEmotionAnalyzer.presentResults(calculatedVector, false);
		}
		
	}

}
