package corpusManagement;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

public class UI_Corpus2TxtConverter {
	
	/**
	 * Erstellt zu allem im angegebenen Verzeichnis befindlichen PDF-Dokumenten eine bereinigte TXT-Datei im gleichen Verzeichnis.
	 * @param args Erstes und einziges Argument: Ein Verzeichnis mit PDF-Dokumenten
	 * @throws Exception 
	 */
	public static void main (String[] args) throws Exception{
		File dir = new File(args[0]);
		File[] files = dir.listFiles();
		//Verzeichnis für die Dateien, die nicht konvertiert werden können, wegen Kopierschutz
		File copyProtectionFolder = new File(args[0]+"/CopyProtectionFolder");
		copyProtectionFolder.mkdir();
		//Für jede Datei im angebenen Verzeichnis, die eine pdf-Datei ist...
		for (File currentFile: files){
			if ( (currentFile.isFile() && (currentFile.getName().endsWith(".pdf")) ) ) {
				//Der Name vom Output-Path wird ermittelt, indem an der Pfad txt statt pdf angehängt wird
				String outputPath = currentFile.getPath().substring(0, currentFile.getAbsolutePath().length()-3 ) + "txt";
				//Datei wird in bereinigte txt-Datei umgewandet.
				try {
					CleanPdf2TextConverter.convert(currentFile.getPath(), outputPath);
				} catch (Exception e) {
//					//copy the elements which could not be converted into a seperated folder
//					Files.move(currentFile, new File (copyProtectionFolder.getPath()+"/"+currentFile.getName()));
					
					//file will be decrypted and conversion will be tried again
					PdfDecrypter.decyptPdf(currentFile.getPath());
					CleanPdf2TextConverter.convert(currentFile.getPath(), outputPath);
				}
			}
		}
	}

}
