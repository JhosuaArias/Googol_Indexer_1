package cr.ac.ucr.ecci;

import java.util.TreeSet;

/**
 * Holds all the data relevant to a document in the collection.
 */
public class WebDocument {

    // A string representation of the document's title
    private String documentName;

    // Holds each term in the collection.
    private TreeSet<TermData> terms;

    // The frequency of the term with the highest frequency in the collection.
    private int maxFrequency;

    public WebDocument(String documentName) {
        this.setDocumentName(documentName);
        this.setMaxFrequency(0);
        this.setTerms(new TreeSet<>());
    }

    /**
     * Adds a term to this document's term list.
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

    public TreeSet<TermData> getTerms() {
        return terms;
    }

    public void setTerms(TreeSet<TermData> terms) {
        this.terms = terms;
    }
}
    
