import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {


        Indexer i = new Indexer();
        i.indexFiles();
    }
}
