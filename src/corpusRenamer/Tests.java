package corpusRenamer;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class Tests {
	
	static final String CORPUS_ROOT= "Korpus_Sample";
	static final String ADIDAS_1996_PDF = CORPUS_ROOT+"/Reports/Annual_Reports/Dax/Adidas/1996.pdf";
	static final String ADIDAS_1996_TXT= CORPUS_ROOT+"/Reports_TxT/Annual_Reports/Dax/Adidas/Adidas-0R.txt";
	

	@Test
	public void is_Sample_Available() {
		File currentFile= new File(CORPUS_ROOT);
		File file2 = new File(CORPUS_ROOT+"/bla");
		assertTrue(!file2.isFile());
		assertTrue(currentFile.isDirectory());
		File file3 = new File (ADIDAS_1996_PDF);
		assertTrue(file3.isFile());
		File file4 = new File (ADIDAS_1996_TXT);
		assertTrue(file4.isFile());
	}
	
	@Test
	public void testGetYear() throws IOException{
		File testFile = new File(ADIDAS_1996_TXT);
		assertEquals("Test file not found", true, testFile.isFile());
		String output = CorpusRenamer.getYear(testFile);
		System.out.println(output);
		assertEquals("message", true, output.equals("1996"));
		File testFile2 = new File (CORPUS_ROOT+"/Reports_TxT/Annual_Reports/Dax/Adidas/Adidas-1R.txt");
		System.out.println (CorpusRenamer.getYear(testFile2));
	}
	
	
	//TODO this test should not use absolute paths...
	@Test
	public void testGetParallelFolder() throws IOException{
		File adidasTestFile = new File(ADIDAS_1996_TXT);
//		System.out.println(adidasTestFile.getCanonicalPath());
		assertEquals("wrong file", "/Users/sven/GitHub/EmotionAnalyzer/Korpus_Sample/Reports_TxT/Annual_Reports/Dax/Adidas/Adidas-0R.txt", adidasTestFile.getCanonicalPath());
		File parent = adidasTestFile.getParentFile();
		File parallelFolder = CorpusRenamer.getParallelFolder(parent);
//		System.out.println(parallelFolder.getCanonicalPath());
		assertEquals("wrong parallel folder", "/Users/sven/GitHub/EmotionAnalyzer/Korpus_Sample/Reports/Annual_Reports/Dax/Adidas", parallelFolder.getCanonicalPath());
	}
	
	

}
