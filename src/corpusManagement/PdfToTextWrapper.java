package corpusManagement;

import java.io.IOException;


public class PdfToTextWrapper {
	
	/**
	 * Greift auf das Kommandozeilenprogramm PDFtoText zu ( https://en.wikipedia.org/wiki/Pdftotext  ). Vorraussetzung dafür ist, dass dieses installiert ist und über den Befehl (unter Mac/Linux) pdftotext [options] <PDF-file> [<text-file>] angesprochen werden kann. 
	 * @author sven
	 * @throws IOException 
	 * @throws InterruptedException 
	 *
	 */
	public static void pdftotext(final String pdfPath, final String txtPath) throws IOException, InterruptedException{
		System.out.println("Converting " + pdfPath +" ...");
		new ProcessBuilder("/usr/local/bin/pdftotext", pdfPath, txtPath).start().waitFor(); //Das hier ist Plattformabhängig. So wird vorausgesetzt, dass dort das Programm installiert ist.
	}

}
