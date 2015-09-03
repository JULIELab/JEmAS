package cleanPdf2Text;


import java.io.File;
import java.io.IOException;



public class PdfOptimizerUI {
	
	/**
	 * Wendet die Methode PdfOptimizer.optimize für jede Datei in jedem Unterordner von /home/jago/PdfToTxt/output/ an.
	 * @param args
	 */
	public static void main(String[] args){
		PdfOptimizer optimizer = new PdfOptimizer();
		
		File directory = new File("/home/jago/PdfToTxt/output/");
		String[] directories = directory.list();
	
		if(directories == null){
			System.out.println("ERROR: There are no elements in directory 'output'");
		}
		
		else{
			for(int i=0; i<directories.length; i++){
			File file = new File("/home/jago/PdfToTxt/output/"+directories[i]);
			String[] files = file.list();
					
			if(files != null){
				for(int j=0; j<files.length; j++){
					try {
						optimizer.optimize(directories[i], files[j]);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			}
		}
	}

}


