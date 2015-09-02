package start;

import java.io.File;
import java.io.IOException;

import optimize.Optimizer;

public class OptimizerStart {
	
	public static void main(String[] args){
		Optimizer optimizer = new Optimizer();
		
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

