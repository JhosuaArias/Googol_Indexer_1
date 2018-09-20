package cr.ac.ucr.ecci;

import java.lang.annotation.Documented;
import java.util.ArrayList;

public class TermData {
    private String term;
    private ArrayList<String> documents;

    private int frecuencyinDocument;

    //Times appeared in all documents (n_i)
    private int timesAppeared;

    public TermData(String term) {
        this.setTerm(term);
        this.setDocuments(new ArrayList<>());
        this.setTimesAppeared(1);
        this.setFrecuencyinDocument(1);
    }



    public double normalizeFrecuency(double max_frec){
        return ((double)this.getFrecuencyinDocument()) /max_frec;
    }

    public void sumTimesAppeared(){
        this.timesAppeared++;
    }

    public void sumFrecuencyDocument() {
        this.frecuencyinDocument++;
    }

    public void addDocument(String document){
        if (!this.documents.contains(document)) {
            this.documents.add(document);
        }
    }

    /**Getters and Setters**/
    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public ArrayList<String> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<String> documents) {
        this.documents = documents;
    }

    public int getTimesAppeared() {
        return timesAppeared;
    }

    public void setTimesAppeared(int timesAppeared) {
        this.timesAppeared = timesAppeared;
    }

    public int getFrecuencyinDocument() {
        return frecuencyinDocument;
    }

    public void setFrecuencyinDocument(int frecuencyinDocument) {
        this.frecuencyinDocument = frecuencyinDocument;
    }
}
