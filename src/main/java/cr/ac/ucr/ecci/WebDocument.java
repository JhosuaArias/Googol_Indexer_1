package cr.ac.ucr.ecci;

import java.util.HashMap;
import java.util.Map;

public class WebDocument {

    private int max_frec;
    private Map<String, TermData> documentTerms;
    private String documentName;

    public WebDocument(String documentName) {
        this.setDocumentName(documentName);
        this.setMax_frec(0);
        this.setDocumentTerms(new HashMap<>());
    }


    /**
     * Verifies if a term is in the vocabulary, otherwise, this adds that term to de map
     * @param term the term to verify
     */
    public void addToMap(String term){
        if(this.documentTerms.containsKey(term)) {
            this.documentTerms.get(term).sumFrecuencyDocument();
        }else{
            TermData newTerm = new TermData(term);
            this.documentTerms.put(term,newTerm);
        }
    }

    public void computeMax_Frec(){
        int higher = 0;
        for (TermData termData : this.documentTerms.values()) {
            higher =(termData.getFrecuencyinDocument() > higher)? termData.getFrecuencyinDocument():higher;
        }
    }




    /**
     * Setters and Getters
     **/
    public int getMax_frec() {
        return max_frec;
    }

    public void setMax_frec(int max_frec) {
        this.max_frec = max_frec;
    }

    public Map<String, TermData> getDocumentTerms() {
        return documentTerms;
    }

    public void setDocumentTerms(Map<String, TermData> documentTerms) {
        this.documentTerms = documentTerms;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
}
    
