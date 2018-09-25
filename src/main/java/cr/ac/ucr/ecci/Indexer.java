package cr.ac.ucr.ecci;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Indexer {

    private int termCount;
    private Map<String, TermData> vocabulary;
    private static final Logger LOG = LogManager.getLogger(Indexer.class.getName());

    public Indexer() {
        this.vocabulary = new HashMap<>();
    }

    public void indexFiles() {
        List<String> urls = FileHandler.readFileStringArray("./src/main/resources/URLS.txt");
        for(String string : urls){
            String[] url = string.split("\\s+");
            url[0] = url[0].replaceAll("\\s+", "");
            url[0] = url[0].replaceAll("\\n", "");
            String html = FileHandler.readFileSB("./src/main/resources/htmls/" + url[0]);
            Document doc = Jsoup.parse(html);
            String body = doc.body().text();
            LOG.info("Done with " + url[0]);
        }
    }
}
