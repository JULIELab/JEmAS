package corpusManagement;

import java.io.File;


public class UI_CorpusOrganizer {

	/**Sortiert das Korpus um, sodass alle Pdfs in einem Ordner stehen und eindeutig benannt sind, wobei der Name die Art des Reports, den Aktienindex, das Unternehmen und die Jahreszahl enthalten jeweils abgegrenzt durch ".". 
	 * @param args Als erstes Argument muss der Pfad zum Ordner angegeben werden, vom dem alle weiteren Ordner mit Pdfs abzweigen. Das zweite Argument ist der Ordner, in den die umbenannten Pdfs reinkopiert werden sollen. Bsp: "/Users/sven/Documents/Korpus_Bachelorarbeit/Reports" "/Users/sven/Documents/Korpus_Bachelorarbeit/Reports_sortiert"
	 * @throws Exception
	 */
	public static void main (String[] args) throws Exception{
		File newDir = new File(args[1]);
		if (!newDir.exists()) {
			newDir.mkdir();
		}
		CorpusOrganizer.reorganizeCorpus(args[0], args[0], args[1]);
	}
}
