package cleanPdf2Text;

import java.io.IOException;
import java.util.ArrayList;


public class PdfToTextWrapper {
	
	/**
	 * Greift auf das Kommandozeilenprogramm PDFtoText zu ( https://en.wikipedia.org/wiki/Pdftotext  ). Vorraussetzung dafür ist, dass dieses installiert ist und über den Befehl (unter Mac/Linux) pdftotext [options] <PDF-file> [<text-file>] angesprochen werden kann. 
	 * @author sven
	 * @throws IOException 
	 * @throws InterruptedException 
	 *
	 */
	public static void pdftotext(final String pdfPath, final String txtPath) throws IOException, InterruptedException{
		new ProcessBuilder("./pdftotext", pdfPath, txtPath).start().waitFor();
	}

}
