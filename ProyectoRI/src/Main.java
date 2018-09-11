import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String html = "<p>An <a href='http://example.com/'><b>example asdf 1234567891234567891234567891234</b></a> link.</p>";
        Document doc = Jsoup.connect("https://es.wikipedia.org/wiki/Nintendo_Switch").get();
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        outputSettings.prettyPrint(true);
        doc.outputSettings(outputSettings);
        String body = doc.text();
        //System.out.println(body);
        System.out.println(Preprocessor.removeInvalidTerms(Preprocessor.removeTerms30("ÁÚÍÑ")));
    }
}
