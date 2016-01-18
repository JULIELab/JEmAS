# JenEmo
JenEmo (Jena Emotion Analyzer) is an open source tool for textual emotion detection 
written in 2015/16 at Jena University, Jena, Germany. 
It inferes the emotion expressed by a text document on the basis of the used words. 
For that, it employs an emotion lexicon developed 
by pschologist which consists of 14k entries. Emotions are represented as 
a point in a three dimensional space. The dimensions are called "valence", 
"arousal" and "dominance". 
They take on numeric values ranging from -4 to 4, respecively.

The application is packed in a jar JAR file 
and will be run from the command line interface. 
The Usage is straightforward:
	a) run the JAR file indicating a directory
	b) run the JAR file indicating two directories

In the case a), the application will analyze all txt-files in the indicated directory. 
It produces some auxilary files which will be saved in subdirectories. 
The results will be printed in the standard output. Case b) differs from the first case, 
insofar that the second argument indicates the, where the auxilary files will be saved. 
The results will still be printed in the standard output.
Appart from that, the argument "-help" will show a brief help-text 
whereas the argument "-test" will run a short number of tests 
checking the functionality of the tool. If no argument is given, the help text also
appears.

The output of the tool is presented in CSV format, indicating the following information:
	a) the name of the respective file
	b) – d) valence, arousal and domiance of the file as calculated by the tool
	e) – g) the standard deviation of valence arousal and dominance 
		of the words in the file
	h) the number of tokens
	i) the number of "alphabetic" tokens, i.e., tokens which start with a letter 
		(no numbers and punctuation).
	j) the number of numeric expressions
	k) the number of tokens left after stopword removal
	l) the number of tokens which are recognized to be emotional relevant 
		according to the emotion lexicon.
