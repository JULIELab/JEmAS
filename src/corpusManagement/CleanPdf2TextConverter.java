package corpusManagement;

import java.io.File;

public class CleanPdf2TextConverter {
	
	public static void convert (String inputPath, String outputPath) throws Exception{
		//generating objects
		PdfOptimizer optimizer = new PdfOptimizer();
		//executing
		PdfToTextWrapper.pdftotext(inputPath, outputPath);
		//überprüft, ob die pdf wegen passwort nicht konvertiert werden konnte
		if (new File(outputPath).exists()) {		
			//		optimizer.simpleOptimize(outputPath, outputPath.substring(0, outputPath.length()-4)+"2.txt");
			optimizer.optimize(outputPath, outputPath);
		}
		else {
			throw new Exception("PDF could not be converted probably because of copy protection.");
		}
	}

}
