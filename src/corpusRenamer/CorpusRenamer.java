package corpusRenamer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CorpusRenamer {
	
	public static void renameRecursively(File folderWithTxts) throws Exception{
		for (File file: getFolderContents(folderWithTxts)){
			if (file.isFile()) renameFile(file);
			if (file.isDirectory()) renameRecursively(file);
		}
		//	renameFile(file, getTypIndexEnterprise, getYear)
		//for folders in current folder
		//	renamer(folder)
	}
	
	/**
	 * returns type of the report, the index the enterprise is listed in (i.e. the location of the enterprise) and the enterprise's name.
	 */
	private static String getTypIndexEnterprise(File givenFile){
		String enterprise = givenFile.getParentFile().getName();
		String index = givenFile.getParentFile().getParentFile().getName();
		String type =  givenFile.getParentFile().getParentFile().getParentFile().getName();
		return type+"_"+index+"_"+"_"+enterprise;
	}
	
	/**
	 * renames a file using getTypIndexEnterprise and getYear
	 */
	private static void renameFile(File givenFile) throws Exception{
		  // File (or directory) with new name
	    File file2 = new File(getNewFileName(givenFile));
	    if(file2.exists()) throw new java.io.IOException("file exists");
	    // Rename file (or directory)
	   givenFile.renameTo(file2);
//	    boolean success = givenFile.renameTo(file2);
//	    if (!success) {
//	        // File was not successfully renamed
//	    }
	}
	
	static String getNewFileName(File givenFile) throws IOException{
		return getTypIndexEnterprise(givenFile)+"_"+getYear(givenFile);
	}
	
	/** 
	 * return the year of a report based on the naming of the file parallel directory of pdf-files. 
	 * It will just append the name of the parallel pdf-file, although this
	 * may cause a bunch of errors, but I will correct them afterwards
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	static String getYear (File givenFile) throws IOException{
		//fileIndex <- index of the file in its folder
		File folderOfGivenFile = givenFile.getParentFile();
		ArrayList<File> folderContent = new ArrayList<File>(getFolderContents(folderOfGivenFile));
		int indexOfGivenFile = folderContent.indexOf(givenFile);	
		//get files in parallel folder
		ArrayList<File> parallelFoldersContent = new ArrayList<File>(getFolderContents(getParallelFolder(folderOfGivenFile)));
		//pick the parallel file
		File parallelFile = parallelFoldersContent.get(indexOfGivenFile);
		//extract year from filename and return it
		return extractYear(parallelFile);
		
	}
	
	private static List<File> getFolderContents(File givenFile){
		ArrayList<File> content = new ArrayList<File>(Arrays.asList(givenFile.listFiles()));
		return content;
	}
	
	private static String extractYear (File givenFile){
		String filename = givenFile.getName();
		filename = filename.replaceAll("\\^[\\^\\d]*", ""); //cuts off every non-digit preface
		filename = filename.replaceAll("^3M-", ""); //for the enterprise 3M, because the format is different in the corpus...
		filename = filename.replaceAll("\\.pdf$", ""); //cuts off suffix
		return filename;
		
	}
	
/**
 * returns a string describing the path of the folder in the pdf-folder which is parallel to the given
 * one in the txt-folder.
 * @param folder
 * @return
 * @throws IOException 
 */
	static File getParallelFolder(File folder) throws IOException{
		if (!folder.isDirectory()) throw new java.io.IOException("file is not a folder");
		String folderPath = folder.getAbsolutePath();
		return new File(folderPath.replaceFirst("/Reports_TxT/", "/Reports/"));
	}

}
