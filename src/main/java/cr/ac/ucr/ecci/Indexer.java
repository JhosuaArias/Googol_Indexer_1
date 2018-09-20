package cr.ac.ucr.ecci;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Indexer {

    private int termCount;
    private Map<String, TermData> vocabulary;

    public Indexer() {
        this.vocabulary = new HashMap<>();
    }

    public void indexFiles() {
        List<String> urls = FileHandler.readFileStringArray("./src/main/resources/URLS.txt");
        for(String string : urls){
            String[] url = string.split("\\s+");
            String html = FileHandler.readFileSB("./src/main/resources/htmls/" + url[0]);
            String documentName = url[0].replaceAll("\\.html","");

            Document doc = Jsoup.parse(html);
            String body = doc.body().text();

            this.proccessDocumentTerms(documentName,this.preproccesDocument(body));


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
        String[] splitedTerms = allTerms.split("\\s+");
        Map<String,TermData> documentVocabulary;
        for (String term : splitedTerms) {
            this.addToMap(term);
            //TODO Make a FileHandler method to insert terms in the respective .tok file.
        }
    }

    /**
     * Verifies if a term is in the vocabulary, otherwise, this adds that term to de map
     * @param term the term to verify
     */
    private void addToMap(String term){
        if(this.vocabulary.containsKey(term)) {

        }else{

        }
    }

}
