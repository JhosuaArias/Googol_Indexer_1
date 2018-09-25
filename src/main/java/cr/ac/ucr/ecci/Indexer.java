package cr.ac.ucr.ecci;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Indexer {

    private Map<String, TermData> vocabulary;
    private static final Logger LOG = LogManager.getLogger(Indexer.class.getName());
    private ArrayList<WebDocument> allDocuments;

    public Indexer() {
        this.vocabulary = new HashMap<>();
        this.allDocuments = new ArrayList<>();
    }

    /**
     * Iterates through each document in the collection, storing and calculating the necessary information.
     */
    public void indexFiles() {
        List<String> urls = FileHandler.readFileStringArray("./src/main/resources/URLS.txt");
        for(String string : urls){

            // Get the name and content of each document
            String[] url = string.split("\\s+");
            url[0] = url[0].replaceAll("\\s+", "");
            url[0] = url[0].replaceAll("\\n", "");
            String html = FileHandler.readFileSB("./src/main/resources/htmls/" + url[0]);
            Document doc = Jsoup.parse(html);
            String htmlDocument = FileHandler.readFileSB("./src/main/resources/htmls/" + url[0]);
            String documentName = url[0].replaceAll("\\.html","");

            // Use Jsoup to remove unwanted HTML syntax
            Document doc = Jsoup.parse(htmlDocument);
            String body = doc.body().text();
            LOG.info("Done with " + url[0]);

            body = this.preprocessDocument(body);
            System.out.println(documentName+" : "+body);
            this.processDocumentTerms(documentName,body);
        }
    }

    /**
     * Preprocces all the document to removes useless terms.
     * @param document the document to preprocess.
     * @return a preproccesed document.
     */
    private String preprocessDocument(String document){
        document = Preprocessor.removeTerms30(document);
        document = Preprocessor.toLowerCase(document);
        document = Preprocessor.removeInvalidTerms(document);
        document = Preprocessor.removeTermsStartsNumeric(document);
        document = Preprocessor.removeNumberToHigh(document);
        document = Preprocessor.removeStopWords(document);
        return document;
    }

    /**
     * Iterates through each term in the document, storing metadata like the number of its occurrences in the document
     * and in the collection.
     * @param documentName the name of the document whose terms will be parsed.
     * @param allTerms     the terms in the document.
     */
    private void processDocumentTerms(String documentName, String allTerms){
        WebDocument newDocument = new WebDocument(documentName);
        int maxFrequency = 0;
        TermData termData;

        String[] splitTerms = allTerms.split("\\s+");

        for (String term : splitTerms) {
            termData = this.addToVocabulary(newDocument.getDocumentName(), term);
            newDocument.addTerm(termData);

            if(termData.getFrequencyInDocument(documentName) > maxFrequency){
                maxFrequency = termData.getFrequencyInDocument(documentName);
            }
        }

        newDocument.setMaxFrequency(maxFrequency);
        this.allDocuments.add(newDocument);

        System.out.println("Document: "+documentName+", maxFrequency: "+maxFrequency);
    }

    /**
     * Adds a term to the collection's vocabulary.
     * If a term is already registered in the vocabulary map, its number of occurrences is updated.
     * Otherwise, a new TermData object is created to represent that term.
     * @param documentName
     * @param term
     * @return a TermData object that represents the term
     */
    private TermData addToVocabulary(String documentName, String term){
        TermData newTerm;

        if(this.vocabulary.containsKey(term)) {
            newTerm = this.vocabulary.get(term);
        }else{
            newTerm = new TermData(term);
            this.vocabulary.put(term,newTerm);
        }

        newTerm.addOccurrenceInDocument(documentName);
        return newTerm;
    }

}
