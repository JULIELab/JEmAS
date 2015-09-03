package cleanPdf2Text;

import java.io.File;
import java.io.IOException;

public class CleanPdf2TextConverter {
	
	public static void convert (String inputPath, String outputPath) throws IOException, InterruptedException{
		//generating objects
		PdfOptimizer optimizer = new PdfOptimizer();
		//executing
		PdfToTextWrapper.pdftotext(inputPath, outputPath);
//		optimizer.simpleOptimize(outputPath, outputPath.substring(0, outputPath.length()-4)+"2.txt");
		optimizer.simpleOptimize(outputPath, outputPath);
	}

}
