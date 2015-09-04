package cleanPdf2Text;

import java.io.File;
import java.io.IOException;

public class UI_PdfDecrypter {
	
	
	/**
	 * Removes encryption of PDF files. Indicate path of the pdf file(s)
	 * @param args If indicated path denotes a file, it will remove encryption from this file. If it denotes a directory, it will decrypt every pdf-file in the directory.
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main (String[] args) throws InterruptedException, IOException{
		File input = new File(args[0]);
		
		if (input.isFile()) {
			PdfDecrypter.decyptPdf(input.getPath());
		}
		
		if (input.isDirectory()){
			for (File currentFile: input.listFiles()){
				if ( currentFile.getName().endsWith(".pdf")){
					PdfDecrypter.decyptPdf(currentFile.getPath());
				}
			}
		}
		
		
	}

}
