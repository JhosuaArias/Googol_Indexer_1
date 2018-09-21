package cr.ac.ucr.ecci;

import java.util.ArrayList;
import java.util.HashMap;

public class TermData {
    private String term;
    private HashMap<String, Integer> frequencyPerDocument;

    // Frequency in all documentNames (n_i)
    private int frequencyInCollection;

    public TermData(String term) {
        this.setTerm(term);
        this.frequencyPerDocument = new HashMap<>();
        this.setFrequencyInCollection(1);
    }

    public double normalizeFrequency(String documentName, double maxFrequency){
        return ((double)this.frequencyPerDocument.get(documentName)) / maxFrequency;
    }

    public void sumTimesAppeared(){
        this.frequencyInCollection++;
    }

    /**
     * Registers the occurrence of a term in a document, to calculate statistics later.
     * @param documentName the title of the document.
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

    public int getFrequencyInDocument(String documentName) {
        return frequencyPerDocument.get(documentName);
    }
}
