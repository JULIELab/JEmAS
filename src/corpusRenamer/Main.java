package corpusRenamer;

import java.io.File;

public class Main {

	public static void main (String[] args) throws Exception{
		CorpusRenamer.renameRecursively(new File(args[0]));
	}
}
