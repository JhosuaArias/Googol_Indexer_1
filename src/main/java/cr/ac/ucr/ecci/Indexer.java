package cr.ac.ucr.ecci;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Indexer {

    private int documentCount;
    private int termCount;
    private Map<String, TermData> vocabulary;
    private ArrayList<String> allDocuments;

    public Indexer() {
        this.documentCount = 0;
        this.vocabulary = new HashMap<>();
        this.allDocuments = new ArrayList<>();
    }

    public void indexFiles() {

        List<String> urls = FileHandler.readFileStringArray("./src/main/resources/URLS.txt");
        for(String string : urls){
            this.documentCount++;

            // Get the name and content of each document
            String[] url = string.split("\\s+");
            String htmlDocument = FileHandler.readFileSB("./src/main/resources/htmls/" + url[0]);
            String documentName = url[0].replaceAll("\\.html","");

            // Use Jsoup to remove unwanted HTML syntax
            Document doc = Jsoup.parse(htmlDocument);
            String body = doc.body().text();

            body = this.preprocessDocument(body);
            // System.out.println(documentName+" : "+body);
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
        this.allDocuments.add(newDocument.getDocumentName());

        System.out.println("Documents: "+documentName+", maxFrequency: "+maxFrequency);

        //TODO write a method in FileHandler to make the .tok
    }

    /**
     * Verifies if a term is in the vocabulary, otherwise, this adds that term to the map
     * @param term the term to verify
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
