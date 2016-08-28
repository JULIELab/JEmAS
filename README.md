# JEmAS – Jena Emotion Analysis System

##Intro
JEmAS is an open source command line tool for measuring the emotional content of a textual document of arbitrary length. It employs a simple bag-of-words and lexicon-based approach. It follows the psychological Valence-Arousal-Dominance model of emotion so that an emotion will be represented as three-dimensional vector of numerical values. The elements of this emotion vector refer to Valence (the degree of pleasentness or unpleasentness of an emotion), Arousal (degree of calmness or excitement), and Dominance (the degree of perceived control ranging from submissive to dominant).

##Citation
Sven Buechel and Udo Hahn: Emotion Analysis as a Regression Problem - Dimensional Models and Their Implications on Emotion Representation and Metrical Evaluation. In: ECAI 2016. 22nd European Conference on Artificial Intelligence. August 29 - September 2, 2016, The Hague, Netherlands, pp. 1114-1122.

##Usage
JEmAS has two distinct operation mode, a default mode and an advanced mode. Using the advanced mode, you can manually choose the employed  word emotion lexicon, the term weighting function for constructing the BOW representation (absolute frequency or TFIDF) and the preprocessing mode (no lexical normalization or lemmatization). Using the default mode, JEmAS will run with default settings:
- lexical normilazation: lemmatization
- term weighting function: absolute term frequency
- word emotion lexicon: a minor variation of Warriner's lexicon (Warriner, A.B., Kuperman, V., & Brysbaert, M. (2013). Norms of valence, arousal, and dominance for 13,915 English lemmas. Behavior Research Methods, 45, 1191-1207. Available: http://crr.ugent.be/archives/1003 ) where the range of the emotion values is transformed into [-4, 4].

###Default mode:
````
java -jar NAME_OF_JAR INPUT_FOLDER AUXILIARY_FOLDER
````
Where INPUT_FOLDER is the path to a folder in which all existing files (with .txt suffix or without any suffix) will be processed and AUXILIARY_FOLDER is the path to an existing folder where auxiliary output files (such as the vocabulary) will be saved. 

###Advanced mode:
````
java -jar NAME_OF_JAR -advanced
```
You will specify your desired settings in a dialog-like fashion.

###Formatting of custom emotion lexicons:
The lexcion has to be csv-formatted with TAB as delimiter and without column headers. Each entry (consisting of a word and an associated VAD value) must, thus, be formatted like this:
````
WORD TAB VALENCE TAB AROUSAL TAB DOMINANCE
```
Where VALENCE, AROUSAL and DOMINANCE are numerical values.

##Output
The output of the tool is printed on standard output in CSV format, indicating the following information:
- the name of the respective file
- valence, arousal and domiance of the file as calculated by the tool
- the standard deviation of valence arousal and dominance of the words in the file
- the number of tokens
- the number of "alphabetic" tokens, i.e., tokens which start with a letter (no numbers and punctuation).
- the number of numeric expressions
- the number of tokens left after stopword removal
- the number of tokens which are recognized to be emotional relevant according to the emotion lexicon.

## Contact
I am happy to give additional information or get feedback on this tool via email: sven-eric.buechel@uni-jena.de
