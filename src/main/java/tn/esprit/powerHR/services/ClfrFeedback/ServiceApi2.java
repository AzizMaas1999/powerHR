package tn.esprit.powerHR.services.ClfrFeedback;
import edu.stanford.nlp.pipeline.*;
import java.util.Properties;

public class ServiceApi2 {

     public static void main(String[] args) {
            String text = "I am very happy today!";

            Properties props = new Properties();
            props.setProperty("annotators", "tokenize,ssplit,pos,lemma,parse,sentiment");
            StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

            CoreDocument document = new CoreDocument(text);
            pipeline.annotate(document);

            document.sentences().forEach(sentence ->
                    System.out.println("Sentiment: " + sentence.sentiment()));
        }
    }

