package corpusRenamer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CorpusRenamer {
	public static void renameCorpus(String path){
		//for file in folder
		//	renameFile(file, getTypIndexEnterprise, getYear)
		//for folders in current folder
		//	renamer(folder)
	}
	
	/**
	 * returns type of the report, the index the enterprise is listed in (i.e. the location of the enterprise) and the enterprise's name.
	 */
	private static String getTypIndexEnterprise(String filePath){
		return null;
	}
	
	/** 
	 * return the year of a report based on the naming of the file parallel directory of pdf-files. 
	 * It will just append the name of the parallel pdf-file, although this
	 * may cause a bunch of errors, but I will correct them afterwards
	 * @param filePath
	 * @return
	 */
	private static String getYear (File givenFile){
		//fileIndex <- index of the file in its folder
		File folderOfGivenFile = givenFile.getParentFile();
		ArrayList<File> folderContent = new ArrayList<File>(getFolderContents(folderOfGivenFile));
		int indexOfGivenFile = folderContent.indexOf(givenFile);	
		//get files in parallel folder
		ArrayList<File> parallelFoldersContent = new ArrayList<File>(getFolderContents(new File(getParallelFolder(folderOfGivenFile))));
		//pick the parallel file
		File parallelFile = parallelFoldersContent.get(indexOfGivenFile);
		//extract year from filename and return it
		return extractYear(parallelFile);
		
	}
	
	private static List<File> getFolderContents(File givenFile){
		ArrayList<File> content = new ArrayList<File>(Arrays.asList(givenFile.listFiles()));
		return content;
	}
	
	static String extractYear (File givenFile){
		String filename = givenFile.getName();
		filename = filename.replaceAll("\\^[^\\d\\]*", ""); //cuts off every non-digit preface
		filename = filename.replaceAll("^3M-", ""); //for the enterprise 3M, because the format is different in the corpus...
		filename = filename.replaceAll("\\.pdf", ""); //cuts off suffix
		return null;
		
	}
	
/**
 * returns a string describing the path of the folder in the pdf-folder which is parallel to the given
 * one in the txt-folder.
 * @param folder
 * @return
 */
	static String getParallelFolder(File folder){
		String folderPath = folder.getAbsolutePath();
		return folderPath.replaceFirst("/Reports_TxT/", "Reports/");
	}

}
