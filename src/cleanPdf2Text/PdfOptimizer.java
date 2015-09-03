package cleanPdf2Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PdfOptimizer {
	
	
//	//buechel: ?
//	public static void main(String[] args){
//		PdfOptimizer opt = new PdfOptimizer();
//		
//		File dir = new File("/home/jago/PdfToTxt/output/");
//		File[] subdirs = dir.listFiles();
//		
//		for(int i=0; i<subdirs.length; i++){
//		}
//		
//	}
	
	/**
	 * Führt die Optimierung eines txt-konvertierten Pdfs für eine Datei aus.
	 * @param filePath
	 * @throws IOException
	 */
	public void simpleOptimize(String inputPath, String outputPath) throws IOException{
		String text = readFile(inputPath);
		System.out.println("OPTIMIZE "+inputPath+" ...");
		text = repairTxt(text);
		write(text, outputPath);
	}
	
	private void write (String content, String filePath) throws FileNotFoundException{
		PrintWriter writer = new PrintWriter(filePath);
		writer.write(content);
		writer.close();
	}
	
	/**
	 * "Repariert" die in ein txt umgewandelte PDF-Datei durch 
	 * @param givenString
	 * @return
	 */
	private String repairTxt (String givenString){
		String all = givenString;
		all = all.replace("-\n", "");
		all = all.replace(",\n", "");	
		all = all.replace("_", "");	
		all = all.replace(" -", " ");
		all = all.replace("\n-", "\n");
		all = all.replace("ﬁ", "fi");
		all = all.replace("\n.\n", "");
		all = all.replaceAll("(?<=[\\d])(,)(?=[\\d])","");
		all = all.replaceAll("(?<=[\\d])(\\.)(?=[\\d])","");
		all = all.replace(",,", "'");
		all = all.replace("“", "'");
		all = all.replace("„", "'");
		all = all.replace("”", "\"");
		all = all.replace("'", "'");
		all = all.replace("’’", "'");
		all = all.replace("))", ") )");
		all = all.replace("((", "( (");
		all = all.replace("u¨", "ü");
		all = all.replace("a¨", "ä");
		all = all.replace("o¨", "ö");
		all = all.replace("U¨ ", "ö");
		all = all.replace("==", "");
		all = all.replace("=", "");
		all = all.replace(" ==.", "");
		all = all.replace("=.", "");
		all = all.replace(".", ". ");
		all = all.replace(",", ", ");
		all = all.replace("E. ON", "E.ON");
		all = all.replace("U. S.", "U.S.");
		all = all.replace("U. S. A.", "U.S.A.");
		all = all.replace("U. K.", "U.K.");
		all = all.replace("•", "");
		all = all.replace(" .", ".");
		all = all.replace(" ,", ",");
		all = all.replace("’", "'");
		all = all.replace("’", "'");
		all = all.replace("", "€");
		all = all.replace("·", "");
		all = all.replace(" . ", ". ");
		all = all.replace(" , ", ", ");
		all = all.replace(" – ", " ");
		all = all.replace("-", "-");
		all = all.replace("-", "-");
		all = all.replace("", "ft");
		all = all.replace("", "");
		all = all.replace("⏐", "");
		all = all.replace("", "");
		all = all.replace("", "");
		all = all.replace("|", "");
		all = all.replace("+/-", "+-");
		all = all.replace("@", "[at]");
		all = all.replace("i. a.", "i.a.");
		all = all.replace("e. g.", "e.g.");
		all = all.replace("e,  g.", "e.g.");
		all = all.replace("�", "");
		all = all.replace("ë", "e");
		all = all.replace("", "");
		all = all.replace("‘", "'");
		all = all.replace("Ƭ", "fi");
		all = all.replace("'n", " n");
		all = all.replace("ﬂ", "fl");
		all = all.replace("", " ");
		all = all.replace("", "");
		all = all.replace("•", "");
		all = all.replace("«", "\"");
		all = all.replace("", "ff");
		all = all.replace("", "fi");
		all = all.replace("", "");
		all = all.replace("", "");
		all = all.replace("■", "");
		all = all.replace("", "");
		all = all.replace("", "");
		all = all.replace("", "");
		all = all.replace("", "");
		all = all.replace("", "");
		all = all.replace("", "");
		all = all.replace("", "");
		all = all.replace("", "");
		all = all.replace("", "");
		all = all.replace("", "");
		all = all.replace("", "");
		all = all.replace("", "");
		all = all.replace("", "");
		// all = all.replace("``", "\"");
		// all = all.replace("''", "\"");

		int zeichenBeginn = 0;
		Pattern pattern = Pattern.compile("[a-z]\n");
		Matcher matcher = pattern.matcher(all);
		String ersetzt = "";
		
		while(matcher.find()){
			zeichenBeginn = matcher.start();
			all = all.substring(0, zeichenBeginn+1) + all.substring(zeichenBeginn+1, all.length());
		}
		
		if(zeichenBeginn+2 < all.length()){
			zeichenBeginn = 0;
			pattern = Pattern.compile("[a-z]\n\n");
			matcher = pattern.matcher(all);
			
			while(matcher.find()){
				zeichenBeginn = matcher.start();
				all = all.substring(0, zeichenBeginn+1) + " " + all.substring(zeichenBeginn+2, all.length());
			}
		}
		
		zeichenBeginn = 0;
		pattern = Pattern.compile("\\.");
		matcher = pattern.matcher(all);

		
		while(matcher.find()){
			zeichenBeginn = matcher.start();
			if(zeichenBeginn+2 < all.length() && zeichenBeginn+1 < all.length()){
				String leer = String.valueOf(all.charAt(zeichenBeginn+1));
				String buchstabe = String.valueOf(all.charAt(zeichenBeginn+2));
				if((leer.equals(" ") && buchstabe.matches("[a-z]")) || (leer.equals("[a-z]")) || (leer.equals("\n") && buchstabe.matches("[a-z]"))){
					all = all.substring(0, zeichenBeginn) + "," + all.substring(zeichenBeginn+1, all.length());
				}
			}
		}
		
		zeichenBeginn = 0;
		pattern = Pattern.compile("\n[A-Za-z0-9%?]\\s");
		matcher = pattern.matcher(all);

		
		while(matcher.find()){
			zeichenBeginn = matcher.start();
			String raute = String.valueOf(all.charAt(zeichenBeginn+2));
			if(!raute.equals("I") && !raute.equals("a")){
				all = all.substring(0, zeichenBeginn+1) +"#"+ all.substring(zeichenBeginn+2, all.length());
			}
		}
		
		all = all.replace("#", "");
		return all;
	}
	
	/**
	 * Liest die angebene Datei und gibt sie als String zurück.
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	 String readFile(String filePath) throws IOException{
		InputStreamReader in = new InputStreamReader(new FileInputStream(filePath), "UTF-8");
		BufferedReader br = new BufferedReader(in);
		String line = "";
		String all = "";
		
//		System.out.println("OPTIMIZE "+file+" ...");
		
		while((line = br.readLine()) != null){
			all = all+line+"\n";
		}
		return all;
	}
		


//	private String replaceAll(String string, String string2) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
