package corpusManagement;

import java.io.File;
import java.nio.file.Files;

public class CorpusOrganizer {

	
	/**
	 * Rekursive Methode um alle PDFs in den verschiedenen Unterordnern in einen Ordner zu schreiben und sie eindeutig zu benennen. Die Bennungskonvention ist dabei Berichtart.Aktienindex.Unternehmen.Jahreszahl.pdf .
	 * @param currentFile Die Datei, das gegenwärtig bearbeitet wird. Beim Start der Rekursion ist diese mit root identisch bzw. muss diese mit root identisch sein.
	 * @param root Das Verzeichnis von dem alle weitere Verzeichnisse ausgehen, die die PDFs mit berichten enthalten.
	 * @param outputPath Das Verzeichnis, in das die umbenannten PDFs geschrieben werden sollen.
	 * @throws Exception
	 */
	public static void reorganizeCorpus(String currentFile, String root, String outputPath) throws Exception{	
		File wurzel = new File(currentFile);
		String name = wurzel.getName();
		
		if (!name.equals(".DS_Store")) {	//betriebssystemspezifisch für Mac OS X
			if (wurzel.isFile()) {
				File copiedFile = new File(outputPath + "/" + getCorrectName(wurzel));
				if (copiedFile.exists()) {
					copiedFile.delete();
				}
				Files.copy(wurzel.toPath(), copiedFile.toPath());
			} else {
				File[] toechter = wurzel.listFiles();
				for (File file : toechter) {
					reorganizeCorpus(file.getPath(), root, outputPath);
				}
			}
		}
		
	}
	
	/**
	 * Ordnet den Pdfs einen eindeutigen Namen zu. Dieser hat das Format BERICHTART.AKTIENINDEX.UNTERNEHMEN.JAHRESZAHL.pdf
	 * @param inputFile
	 * @return
	 * @throws Exception
	 */
	private static String getCorrectName (File inputFile) throws Exception{
		if (!inputFile.isFile()) throw new Exception("Failure! File cannot be renamed, because it is a directory.");
		String newName = inputFile.getName();
		File currentFile = inputFile.getParentFile();
		while (!currentFile.getName().equals("Reports")){
			newName = currentFile.getName() + "." + newName;
			currentFile = currentFile.getParentFile();
		}
		//Adds correct suffix in case it misses.
		if(!newName.endsWith(".pdf")){
			newName = newName+".pdf";
		}
		return newName;
		
		
	}
}
