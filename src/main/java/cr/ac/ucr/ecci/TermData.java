package cr.ac.ucr.ecci;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds all the data relevant to one term in the collection.
 */
public class TermData implements Comparable {
    // A String representation of the term
    private String term;

    // The occurrences of the term in each document in the collection
    private Map<String, Integer> frequencyPerDocument;

    // Frequency in all documentNames (n_i)
    private Integer frequencyInCollection;

    public TermData(String term) {
        this.setTerm(term);
        this.frequencyPerDocument = new HashMap<>();
        this.setFrequencyInCollection(1);
    }

    public double getNormalizedFrequency(String documentName, double maxFrequency){
        return ((double)this.frequencyPerDocument.get(documentName)) / maxFrequency;
    }

    /**
     * Registers the occurrence of a term in a document, to calculate statistics later.
     * Adds 1 to the frequency in all the collection and to the entry in the map of a specific document.
     *
     * @param documentName the title of the document in which the term occurred.
     */
    public void addOccurrenceInDocument(String documentName){
        // Increase the global frequency
        frequencyInCollection++;

        // Increase the frequency in the specific document
        if (!this.frequencyPerDocument.containsKey(documentName)) {
            this.frequencyPerDocument.put(documentName, 1);
        } else {
            this.frequencyPerDocument.put(documentName, this.frequencyPerDocument.get(documentName)+1);
        }
    }

    /**Getters and Setters**/
    public Integer getDocumentCount() {
        return this.frequencyPerDocument.size();
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getFrequencyInCollection() {
        return frequencyInCollection;
    }

    public void setFrequencyInCollection(int frequencyInCollection) {
        this.frequencyInCollection = frequencyInCollection;
    }

    public Integer getFrequencyInDocument(String documentName) {
        return frequencyPerDocument.get(documentName);
    }

    @Override
    public int compareTo(Object o) {
        return this.term.compareTo(((TermData)o).getTerm());
    }
}
