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
	private static String getYear (String filePath){
		//fileIndex <- index of the file in its folder
		//get files in parallel folder
		//pick the parallel file
		//extract year from filename
		//return
		
		return null;
	}
	
	private static List<String> getFolderContents(String folderPath){
		File folder = new File (folderPath);
		ArrayList<String> content = new ArrayList<String>(Arrays.asList(folder.list()));
		return content;
	
	}
	
/**
 * returns a string describing the path of the folder in the pdf-folder which is parallel to the given
 * one in the txt-folder.
 * @param folder
 * @return
 */
	static String getParallelFolder(String folder){
		return folder.replaceFirst("/Reports_TxT/", "Reports/");
	}

}
