package cr.ac.ucr.ecci;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Indexer {

    private Map<String, TermData> vocabulary;
    private List<WebDocument> allDocuments;

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
            String htmlDocument = FileHandler.readFileSB("./src/main/resources/htmls/" + url[0]);
            String documentName = url[0].replaceAll("\\.html","");

            // Use Jsoup to remove unwanted HTML syntax
            Document doc = Jsoup.parse(htmlDocument);
            String body = doc.body().text();

            body = this.preprocessDocument(body);
            //System.out.println(documentName+" : "+body);
            this.processDocumentTerms(documentName,body);
        }

        FileHandler.writeVocabularyFile(this.vocabulary.values(), this.allDocuments.size());
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
        WebDocument document = new WebDocument(documentName);
        int maxFrequency = 0;
        TermData termData;

        String[] splitTerms = allTerms.split("\\s+");

        for (String term : splitTerms) {
            termData = this.addToVocabulary(document.getDocumentName(), term);
            document.addTerm(termData);

            if(termData.getFrequencyInDocument(documentName) > maxFrequency){
                maxFrequency = termData.getFrequencyInDocument(documentName);
            }
        }

        document.setMaxFrequency(maxFrequency);
        this.allDocuments.add(document);

        FileHandler.writeDocumentTok(document);

        //System.out.println("Document: "+documentName+", maxFrequency: "+maxFrequency);
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
