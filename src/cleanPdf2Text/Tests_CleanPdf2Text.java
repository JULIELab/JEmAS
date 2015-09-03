package cleanPdf2Text;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import edu.stanford.nlp.io.EncodingPrintWriter.out;

public class Tests_CleanPdf2Text {

	@Test
	public void testPdfOptimizer() throws IOException {
		File outputFile = new File(Util.OPTIMIZER_TEST_OUTPUT);
		outputFile.delete();
		PdfOptimizer optimizer = new PdfOptimizer();
		optimizer.simpleOptimize(Util.OPTIMIZER_TEST_DOCUMENT, Util.OPTIMIZER_TEST_OUTPUT);
		String outputString = optimizer.readFile(Util.OPTIMIZER_TEST_OUTPUT);
//		System.out.println(outputString);
		assertEquals("Optimization failed. Wrong output","this is a test. \n", outputString);
		outputFile.delete();
	
		//checks if rewriting the same file works
		//copy the input file and make it writable
		File input2 = new File("resources/tempOutput.txt");
		Files.copy(Paths.get(Util.OPTIMIZER_TEST_DOCUMENT), Paths.get(input2.getAbsolutePath()));
		input2.setWritable(true);
		
		optimizer.simpleOptimize(input2.getAbsolutePath(), input2.getAbsolutePath());
		String outputString2 = optimizer.readFile(input2.getAbsolutePath());
//		System.out.println(outputString);
		assertEquals("Optimization failed. Wrong output","this is a test. \n", outputString2);
		input2.delete();
	}
	
	@Test
	public void testPdfToTextWrapper() throws IOException, InterruptedException{
		PdfToTextWrapper.pdftotext(Util.PDF2TEXT_TEST_INPUT, Util.PDF2TEXT_TEST_OUTPUT);
	}
	
	@Test
	public void testCleanConvert() throws IOException, InterruptedException{
		CleanPdf2TextConverter.convert(Util.PDF_INPUT_FOR_COMPLETE_TEST, Util.OUTPUT_OF_COMPLETE_TEST);
	}
	
//	@Test
//	public void testProcess() throws IOException{
////		new ProcessBuilder("mkdir", "resources/foofoo").start();
////		new ProcessBuilder("mkdir resources/foofoo").start();
//		new ProcessBuilder("./pdftotext", Util.PDF2TEXT_TEST_INPUT, Util.PDF2TEXT_TEST_OUTPUT	).start();
//	}

}
