package metadataExtractor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


public class MetadataExtractor_UI {

	/**
	 * Reads a file, containing a list of filenames. Extracts the metadata from the filenames according
	 * to the conventions of the ba-thesis. Prints the metadata in csv-format. Delimiter will be TAB.
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		File input = new File(args[0]);
		if (!input.isFile()) System.out.println("Error. Input must indicate a file!");
		else{
			System.out.println("Filename\tReport.Category\tOrigin\tOrganization\tYear");
			List<String> lines = Files.readAllLines(input.toPath());
			for (String line: lines){
				Metadata data = MetadataExtractor.extractMetadata(line);
				System.out.println(data.filename +"\t"+data.reportCategory+"\t"+data.origin
						+"\t" +data.organization + "\t" + data.year);
			}
		}
		

	}

}
