package cleanPdf2Text;

import java.io.IOException;

public class SimpleOptimizerUI {
	
	/**
	 * UI um eine txt-Datei zu optimieren. Das erste Argument ist der Pfad der Eingabedatei, das zweite Argument ist der Pfad der Ausgabedatei.
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{
		PdfOptimizer optimizer = new PdfOptimizer();
		optimizer.simpleOptimize(args[0], args[1]);
	}
}
