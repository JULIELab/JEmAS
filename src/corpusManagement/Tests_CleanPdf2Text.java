package corpusManagement;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class Tests_CleanPdf2Text {

	@Test
	public void testPdfOptimizer() throws IOException {
		File outputFile = new File(Util.OPTIMIZER_TEST_OUTPUT);
		outputFile.delete();
		PdfOptimizer optimizer = new PdfOptimizer();
		optimizer.optimize(Util.OPTIMIZER_TEST_DOCUMENT, Util.OPTIMIZER_TEST_OUTPUT);
		String outputString = Util.readFile(Util.OPTIMIZER_TEST_OUTPUT);
//		System.out.println(outputString);
		assertEquals("Optimization failed. Wrong output","this is a test.\n", outputString);
		outputFile.delete();
	
		//checks if rewriting the same file works
		//copy the input file and make it writable
		File input2 = new File("resources/tempOutput.txt");
		Files.copy(Paths.get(Util.OPTIMIZER_TEST_DOCUMENT), Paths.get(input2.getAbsolutePath()));
		input2.setWritable(true);
		
		optimizer.optimize(input2.getAbsolutePath(), input2.getAbsolutePath());
		String outputString2 = Util.readFile(input2.getAbsolutePath());
//		System.out.println(outputString);
		assertEquals("Optimization failed. Wrong output","this is a test.\n", outputString2);
		input2.delete();
	}
	
	@Test
	public void testPdfToTextWrapper() throws IOException, InterruptedException{
		PdfToTextWrapper.pdftotext(Util.PDF2TEXT_TEST_INPUT, Util.PDF2TEXT_TEST_OUTPUT);
		String outputString = Util.readFile(Util.PDF2TEXT_TEST_OUTPUT);
		assertEquals("Converting failed. Wrong output.", "This is the sample pdf input.", outputString.trim());
	}
	
	@Test
	public void testCleanConvert() throws Exception{
		CleanPdf2TextConverter.convert(Util.PDF_INPUT_FOR_COMPLETE_TEST, Util.OUTPUT_OF_COMPLETE_TEST);
		String outputString = Util.readFile(Util.OUTPUT_OF_COMPLETE_TEST);
		assertEquals("Converting failed. Wrong output.", "This is the sample for the complete test.\n", outputString);
		
	}
	
//	@Test
//	public void testProcess() throws IOException{
////		new ProcessBuilder("mkdir", "resources/foofoo").start();
////		new ProcessBuilder("mkdir resources/foofoo").start();
//		new ProcessBuilder("./pdftotext", Util.PDF2TEXT_TEST_INPUT, Util.PDF2TEXT_TEST_OUTPUT	).start();
//	}
	
//	@Test
//	public void testListFile(){
//		File file = new File("/Users/sven/Documents/Korpus_Bachelorarbeit/Reports");
//		assertEquals("Das File ist leider kein Verzeichnis.", true, file.isDirectory());
////		assertEquals("Das File ist leider keine normale Datei.", true, file.isFile());
//		File[] list = file.listFiles();
//		for (File x: list){
//			System.out.println(x.getName());
//		}
//	}
	
	@Test
	public void testAdidas2004() throws Exception{
		CleanPdf2TextConverter.convert("/Users/sven/Documents/Korpus_Bachelorarbeit/Reports_sortiert/Annual_Reports.Dax.Adidas.2004.pdf", "/Users/sven/Documents/Korpus_Bachelorarbeit/Reports_sortiert/Annual_Reports.Dax.Adidas.2004.txt");
	}

}
