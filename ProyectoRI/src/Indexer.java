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
        List<String> urls = FileHandler.readFileStringArray("./ProyectoRI/resources/URLS.txt");
        for(String string : urls){
            string.split("\\s+");
        }
    }

}
