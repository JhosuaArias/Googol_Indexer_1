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
            Document doc = Jsoup.parse(html);
            String body = doc.body().text();
            System.out.println(body);
        }
    }
}
