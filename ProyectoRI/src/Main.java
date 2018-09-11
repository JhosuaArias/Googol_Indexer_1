import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String html = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>";
        Document doc = Jsoup.parse(html);
        String body = doc.body().text();
        System.out.println(body);

        Indexer i = new Indexer();
        i.indexFiles();
    }
}
