import java.util.Locale;

/**
 * Removes stop words and other undesirable information from the documents.
 *
 * @authir Jhosua Arias, Roberto Leandro
 */
public final class Preprocessor {
    // Matches
    private static final String SPANISH_WORD_PATTERN = "[^ÁÉÍÓÚÜÑáéíóúñü\\w\\s]";

    // Matches all the invalid characters
    private static final String INVALID_SYMBOLS_PATTERN = SPANISH_WORD_PATTERN +"*";

    // Matches all words larger than
    private static final String LARGER_THAN_30_PATTERN = "[.^"+ SPANISH_WORD_PATTERN +"]{31,}";

    // Spanish articles
    private static final String ARTICLES_PATTERN = "\\b(el|los|la|las|un|unos|una|unas|lo)\\b";

    // Spanish prepositions
    private static final String PREPOSITIONS_PATTERN = "\\b(a|ante|bajo|cabe|con|contra|de|desde|durante|en|entre|hacia|hasta|mediante|para|por|según|sin|so|sobre|tras|versus|vía)\\b";

    //Words with numeric start
    private static final String STREING_WITH_NUMERICAL_START = "\\b[0-9]+[a-z]+\\b";
    /**
     * Removes all words larger than 30 characters long.
     * @param document
     * @return
     */
    public static String removeTerms30(String document) {
        return document.replaceAll(LARGER_THAN_30_PATTERN,"");
    }

    /**
     *
     * @return
     */
    public static String removeInvalidTerms(String document){
       String lowercase = document.toLowerCase(Locale.forLanguageTag("es"));

        return lowercase.replaceAll(INVALID_SYMBOLS_PATTERN,"");
    }

    public static String removeStopWords(String document) {
        String noStopWords = document.replaceAll(ARTICLES_PATTERN,"");
        noStopWords = noStopWords.replaceAll(PREPOSITIONS_PATTERN, "");

        return noStopWords;
    }
}
