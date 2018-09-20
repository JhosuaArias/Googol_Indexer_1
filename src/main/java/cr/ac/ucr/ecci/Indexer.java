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

            String[] url = string.split("\\s+");
            String html = FileHandler.readFileSB("./src/main/resources/htmls/" + url[0]);
            String documentName = url[0].replaceAll("\\.html","");

            Document doc = Jsoup.parse(html);
            String body = doc.body().text();

            body = this.preproccesDocument(body);
            System.out.println(documentName+":"+body);
            this.proccessDocumentTerms(documentName,body);

        }
    }

    /**
     * Preprocces all the document to removes useless terms.
     * @param document the document to preprocess.
     * @return a preproccesed document.
     */
    private String preproccesDocument(String document){
        document = Preprocessor.removeTerms30(document);
        document = Preprocessor.toLowerCase(document);
        document = Preprocessor.removeInvalidTerms(document);
        document = Preprocessor.removeTermsStartsNumeric(document);
        document = Preprocessor.removeNumberToHigh(document);
        document = Preprocessor.removeStopWords(document);
        return document;
    }

    private void proccessDocumentTerms(String documentsName, String allTerms){

        WebDocument newDocument = new WebDocument(documentsName);

        String[] splitedTerms = allTerms.split("\\s+");
        Map<String,TermData> documentVocabulary;
        for (String term : splitedTerms) {
            this.addToMap(newDocument.getDocumentName(), term);
            newDocument.addToMap(term);
        }

        this.allDocuments.add(newDocument.getDocumentName());

        //TODO write a method in FileHandler to make the .tok
    }

    /**
     * Verifies if a term is in the vocabulary, otherwise, this adds that term to de map
     * @param term the term to verify
     */
    private void addToMap(String document, String term){
        if(this.vocabulary.containsKey(term)) {
            this.vocabulary.get(term).sumTimesAppeared();
            this.vocabulary.get(term).addDocument(document);
        }else{
            TermData newTerm = new TermData(term);
            newTerm.addDocument(document);
            this.vocabulary.put(term,newTerm);
        }

    }

}
