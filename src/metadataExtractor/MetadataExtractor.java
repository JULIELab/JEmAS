package metadataExtractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MetadataExtractor {
	
	/**
	 * Extracts Metadata for Bachelor thesis from filename. Using return-class "Metadata" to wrap the different attributes of a document.
	 * @param filename
	 * @return
	 */
	public static Metadata extractMetadata(String filename){
		String[] parts = getDocumentAttributes(filename);	
		return new Metadata(parts[1], parts[2], parts[0], parts[3], filename);
	}
	
	/**
	 * Returns the attributes of the document (e.g. report type, origin, year,...) based on the documents name.
	 * @param document
	 * @return index 0: category type; 1: origin; 2: organization; 3: year; 4: suffix
	 */
	private static String[] getDocumentAttributes(String documentName){
		String[] nameParts = documentName.split("\\.");
		String[] attributes;
		//performing a few corrections to deal with some irregularities in the file namings.
		if (nameParts.length==5){
			attributes = nameParts;
			// wrong format in the year part, e. g. CSR_Reports.Dax.Henkel.Henkel_ustainability-report_2009.txt
			if (!attributes[3].matches("[0-9]{4}")){
				Pattern pattern = Pattern.compile("[0-9]{4}");
				Matcher matcher = pattern.matcher(attributes[3]);
				//If there is a year in the specific part, rewrite this part, else leave it as it is.
				if (matcher.find()){
				attributes[3] = matcher.group();	
				}		
			}
		}
		else if (nameParts.length == 6){
			//in this case  the file name looks like type.origin.enterprise.year.pdf.txt. e. g. : Annual_Reports.DowJones.Johnson_Johnson.2009.PDF.txt
			if ( nameParts[5].toLowerCase().equals("txt") && nameParts[4].toLowerCase().equals("pdf") ){
				//build new string without pdf and call method again.
				String concat = nameParts[0] + "." + nameParts[1] + "." + nameParts[2] + "." + nameParts[3]+ "." + nameParts[5];
 				attributes = getDocumentAttributes(concat);
			}
			//Probelems with the dax firm e.on written as E.ON (leading to a split). E. g.: CSR_Reports.Dax.EON.E.ON_CR_Report_2004.txt
			else if (nameParts[3].equals("E") && nameParts[4].startsWith("ON")){
				//concat 3 and 4 and call method again
				String concat = nameParts[0] + "." + nameParts[1] + "." + nameParts[2] + "." + nameParts[3] + nameParts[4] +  "." + nameParts[5];
				attributes = getDocumentAttributes(concat);
			}
			
			else attributes = new String[]{null, null , null , null, null};
		}
		
		else {
			attributes = new String[]{null , null , null , null, null};
		}
		
		return attributes;
	}

}
