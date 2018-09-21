package cr.ac.ucr.ecci;

import java.util.ArrayList;

public class WebDocument {

    private int maxFrequency;
    private String documentName;
    private ArrayList<TermData> terms;

    public WebDocument(String documentName) {
        this.setDocumentName(documentName);
        this.setMaxFrequency(0);
        this.setTerms(new ArrayList<>());
    }

    /**
     * Verifies if a term is in the vocabulary, otherwise, this adds that term to de map
     * @param term the term to verify
     */
    public void addTerm(TermData term){
        terms.add(term);
    }

    /**
     * Setters and Getters
     **/
    public int getMaxFrequency() {
        return maxFrequency;
    }

    public void setMaxFrequency(int maxFrequency) {
        this.maxFrequency = maxFrequency;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public ArrayList<TermData> getTerms() {
        return terms;
    }

    public void setTerms(ArrayList<TermData> terms) {
        this.terms = terms;
    }
}
    
