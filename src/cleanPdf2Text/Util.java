package cleanPdf2Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Util {
	
	public static final String OPTIMIZER_TEST_DOCUMENT = "resources/optimizerTestDocument.txt";
	public static final String OPTIMIZER_TEST_OUTPUT = "resources/optimizerOutput.txt";
	public static final String PDF2TEXT_TEST_INPUT = "resources/SamplePdfInput.pdf";
	public static final String PDF2TEXT_TEST_OUTPUT = "resources/SamplePdfOutput.txt";
	public static final String PDF_INPUT_FOR_COMPLETE_TEST = "resources/SampleInputComplete.pdf";
	public static final String OUTPUT_OF_COMPLETE_TEST = "resources/OutputOfCompleteTest.txt";
	
	static void write (String content, String filePath) throws FileNotFoundException{
		PrintWriter writer = new PrintWriter(filePath);
		writer.write(content);
		writer.close();
	}
	
	/**
	 * Liest die angebene Datei und gibt sie als String zurück.
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	 static String readFile(String filePath) throws IOException{
		InputStreamReader in = new InputStreamReader(new FileInputStream(filePath), "UTF-8");
		BufferedReader br = new BufferedReader(in);
		String line = "";
		String all = "";
		
//		System.out.println("OPTIMIZE "+file+" ...");
		
		while((line = br.readLine()) != null){
			all = all+line+"\n";
		}
		return all;
	}
}
