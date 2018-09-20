package cr.ac.ucr.ecci;

import java.util.ArrayList;

public class TermData {
    private String term;
    private ArrayList<String> documents;
    private double inverseFrequency;
    private double frecuency;
    private int timesAppeared;

    public TermData(String term) {
        this.setTerm(term);
        this.setDocuments(new ArrayList<>());
        this.setInverseFrequency(0);
        this.setTimesAppeared(1);
    }



    public void computeInversedFrecuency(){

    }


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

    public double getInverseFrequency() {
        return inverseFrequency;
    }

    public void setInverseFrequency(double inverseFrequency) {
        this.inverseFrequency = inverseFrequency;
    }

    public int getTimesAppeared() {
        return timesAppeared;
    }

    public void setTimesAppeared(int timesAppeared) {
        this.timesAppeared = timesAppeared;
    }
}
