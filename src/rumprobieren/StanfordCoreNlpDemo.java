package rumprobieren;

import java.io.*;
import java.util.*;

import edu.stanford.nlp.io.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.*;

public class StanfordCoreNlpDemo {

  public static void main(String[] args) throws IOException {
    PrintWriter out;
    if (args.length > 1) {
      out = new PrintWriter(args[1]);
    } else {
      out = new PrintWriter(System.out);
    }
    PrintWriter xmlOut = null;
    if (args.length > 2) {
      xmlOut = new PrintWriter(args[2]);
    }

    Properties props = new Properties();
    props.put("annotators", "tokenize, ssplit");

    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    Annotation annotation;
    if (args.length > 0) {
      annotation = new Annotation(IOUtils.slurpFileNoExceptions(args[0]));
    } else {
      annotation = new Annotation("Kosgi Santosh sent an email to Stanford University. He didn't get a reply.");
    }
    
    pipeline.annotate(annotation);
//    pipeline.prettyPrint(annotation, out);
   
//    FileOutputStream os = new FileOutputStream(new File("./target/", "nlp.xml"));
//    Writer os = new BufferedWriter(new OutputStreamWriter(System.out));
//    pipeline.conllPrint(annotation, os);
    pipeline.xmlPrint(annotation, out);
//    if (xmlOut != null) {
//      pipeline.xmlPrint(annotation, xmlOut);
//    }
    
//    TextOutputter.prettyPrint(annotation, out, pipeline);
    
    
    // An Annotation is a Map and you can get and use the various analyses individually.
    // For instance, this gets the parse tree of the first sentence in the text.
//    List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
//    if (sentences != null && sentences.size() > 0) {
//      CoreMap sentence = sentences.get(0);
//      Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
//      out.println();
//      out.println("The first sentence parsed is:");
////      tree.pennPrint(out);
//    }
//    System.out.print("done");
  }

}

/*
 * this acutually DOES work.
 */

//import java.io.*;
//import java.util.*;
//
//import edu.stanford.nlp.io.*;
//import edu.stanford.nlp.ling.*;
//import edu.stanford.nlp.pipeline.*;
//import edu.stanford.nlp.trees.*;
//import edu.stanford.nlp.util.*;
//
//public class StanfordCoreNlpDemo {
//
//  public static void main(String[] args) throws IOException {
//    PrintWriter out;
//    if (args.length > 1) {
//      out = new PrintWriter(args[1]);
//    } else {
//      out = new PrintWriter(System.out);
//    }
//    PrintWriter xmlOut = null;
//    if (args.length > 2) {
//      xmlOut = new PrintWriter(args[2]);
//    }
//
//    Properties props = new Properties();
//    props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
//    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
//    Annotation annotation;
//    if (args.length > 0) {
//      annotation = new Annotation(IOUtils.slurpFileNoExceptions(args[0]));
//    } else {
//      annotation = new Annotation("Kosgi Santosh sent an email to Stanford University. He didn't get a reply.");
//    }
//
//    pipeline.annotate(annotation);
//    pipeline.prettyPrint(annotation, out);
//    if (xmlOut != null) {
//      pipeline.xmlPrint(annotation, xmlOut);
//    }
//    // An Annotation is a Map and you can get and use the various analyses individually.
//    // For instance, this gets the parse tree of the first sentence in the text.
//    List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
//    if (sentences != null && sentences.size() > 0) {
//      CoreMap sentence = sentences.get(0);
//      Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
//      out.println();
//      out.println("The first sentence parsed is:");
//      tree.pennPrint(out);
//    }
//  }
//
//}
