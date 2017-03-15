# JEmAS – Jena Emotion Analysis System

## Intro
JEmAS is an open source command line tool for measuring the emotional content of a textual document of arbitrary length. It employs a simple bag-of-words and lexicon-based approach. It follows the psychological Valence-Arousal-Dominance model of emotion so that an emotion will be represented as three-dimensional vector of numerical values. The elements of this emotion vector refer to Valence (the degree of pleasentness or unpleasentness of an emotion), Arousal (degree of calmness or excitement), and Dominance (the degree of perceived control ranging from submissive to dominant).

## Citation
Sven Buechel and Udo Hahn: Emotion Analysis as a Regression Problem - Dimensional Models and Their Implications on Emotion Representation and Metrical Evaluation. In: ECAI 2016. 22nd European Conference on Artificial Intelligence. August 29 - September 2, 2016, The Hague, Netherlands, pp. 1114-1122.

## Alternative Versions
The core functionality of JEmAS is also implemented in a [UIMA Analysis Engine](https://github.com/JULIELab/jcore-base/tree/master/jcore-jemas-ae) as part of our component repository [JCoRe](https://github.com/JULIELab/jcore-projects). This newer version should be a lot faster. However, you have to be familiar with the UIMA framework to use it.

## Installation
JEmAS was written for Java 7. You will need Maven to compile. Alternatively, you can use the already compiled JAR (attached with the v0.1 release).

## Usage
JEmAS has two distinct operation mode, a default mode and an advanced mode. Using the advanced mode, you can manually choose the employed  word emotion lexicon, the term weighting function for constructing the BOW representation (absolute frequency or TFIDF) and the preprocessing mode (no lexical normalization or lemmatization). Using the default mode, JEmAS will run with default settings:
- lexical normilazation: lemmatization
- term weighting function: absolute term frequency
- word emotion lexicon: a minor variation of Warriner's lexicon (Warriner, A.B., Kuperman, V., & Brysbaert, M. (2013). Norms of valence, arousal, and dominance for 13,915 English lemmas. Behavior Research Methods, 45, 1191-1207. Available: http://crr.ugent.be/archives/1003 ) where the range of the emotion values is transformed into [-4, 4].

### Default mode:
````
java -jar NAME_OF_JAR INPUT (AUXILIARY_FOLDER)
```` 
Where INPUT is the path to a folder in which all existing files (with .txt suffix or without any suffix) will be processed or the path to a file where each line will then be processed individually (generating emotion scores for each seperate line). AUXILIARY_FOLDER is the path to an existing folder where auxiliary output files (such as the vocabulary) will be saved. When you omit this argument, a new folder will be created in your working directory.

### Advanced mode:
````
java -jar NAME_OF_JAR -advanced
````

You will specify your desired settings in a dialog-like fashion.

### Formatting of custom emotion lexicons:
The lexcion has to be csv-formatted with TAB as delimiter and without column headers. Each entry (consisting of a word and an associated VAD value) must, thus, be formatted like this:
````
WORD TAB VALENCE TAB AROUSAL TAB DOMINANCE
```
Where VALENCE, AROUSAL and DOMINANCE are numerical values.

## Output
The output of the tool is printed on standard output (your terminal window) in tsv format (TAB seperated values). It should look like this where "..." indicates some following lines with numbers (one line per document you analyze). 
```
File Name	Valence	Arousal	Dominance	StdDev Valence	StdDev Arousal	StdDev Dominance	Tokens	Alphabetic Token	Non-Stopword Tokens	Recognized Tokens	NumberCount
test.txt	0,51901	-0,82562	0,7281	1,17961	0,82111	0,85398	612	362	274	121	25
...
````
You can copy+paste this output into excel or calc to get proper formatting (or redirect the output into a file right from the start). 

The columns have the following meanings:

- File Name: The file you analyzed.
- Valence, Arousal, Domiance: The three-dimensional emotion value of the document as determined by JEmAS (this is the most important piece of information you want to get from it).
- StdDev Valence, StdDev Arousal, StdDev Dominance: Standard deviation (SD) of all the _words_ in the document in respect to their individual Valence, Arousal and Dominance ratings.
- Token: The number of tokens (i.e., individual words, numbers, punctuation marks, ...) in your document
- Alphabethic Token: the number of tokens which start with a letter (thus excluding numbers and punctuation).
- Non-Stopword Tokens: the number of tokens left after stopword (mostly non-content words) removal
- Recognized Tokens: the number of tokens which are recognized to be emotional relevant according to the emotion lexicon.
- NumberCount: the number of numeric expressions (numbers, currency,...)

## Contact
I am happy to give additional information or get feedback on this tool via email: sven.buechel@uni-jena.de
