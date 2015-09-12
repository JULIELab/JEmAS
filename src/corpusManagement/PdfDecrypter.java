package corpusManagement;

import java.io.File;
import java.io.IOException;

public class PdfDecrypter {
	
	/**
	 * Benutzt das Programm qpdf (muss installiert sein und am Pfad /usr/local/bin/qpdf) um den Kopierschutz einer Pdf zu entfernen. Der Pfad der Datei wird angegeben. Nach der Anwendung sollte diese entschlüsselt sein.
	 * @param filePath Der Pfad der Datei die entschlüsselt werden soll.
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void decyptPdf(String filePath) throws InterruptedException, IOException{
		//constructs file object of encrypted and decrypted pdf file
		File file = new File (filePath);
		File decryptedFile = new File(file.getParentFile().getPath()+"/"+"decrypted_"+file.getName());
		//decrypt pdf using qpdf
		System.out.println("Decrypting " + file.getName() +" ...");
		new ProcessBuilder("/usr/local/bin/qpdf", "--decrypt", file.getPath(), decryptedFile.getPath() ).start().waitFor();
		//delete encrypted file
		file.delete();
		//rename decrypted file
		decryptedFile.renameTo(file);
	}

}
