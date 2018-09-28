package cr.ac.ucr.ecci;

import java.util.Locale;

/**
 * Removes stop words and other undesirable information from the documents.
 *
 * @authir Jhosua Arias, Roberto Leandro
 */
public final class Preprocessor {
    // Matches
    private static final String SPANISH_WORD_PATTERN = "áéíóúñü\\w";

    // Matches all the invalid characters
    private static final String INVALID_SYMBOLS_PATTERN = "[^" + SPANISH_WORD_PATTERN + "\\s]+";

    // Matches all words larger than
    private static final String LARGER_THAN_30_PATTERN = "\\b\\S{31,}\\b";

    // Spanish articles
    private static final String ARTICLES_PATTERN = "\\b(el|los|la|las|un|unos|una|unas|lo|al|del)\\b";

    // Spanish prepositions
    private static final String PREPOSITIONS_PATTERN = "\\b(a|ante|bajo|cabe|con|contra|de|desde|durante|en|entre|hacia|hasta|mediante|para|por|según|sin|so|sobre|tras|versus|vía)\\b";

    //Spanish conjunctions
    private static final String CONJUNCTIONS = "\\b(y|e|ni|o|u|bien|ora|ya|pero|mas|sino|luego|conque|así que|porque|puesto que|ya que|pues|si|que|como|para que|a fin de que|aunque|aun cuando|si bien)\\b";

    //Words with numeric start
    private static final String STRING_WITH_NUMERICAL_START = "\\b[0-9]+["+ SPANISH_WORD_PATTERN +"]+\\b";

    //Numbers range 0-10000
    private static final String  NUMBER_RANGE_0_999999999 = "[0-9]{9,}";

    /**
     * Removes all words larger than 30 characters long.
     *
     * @param document the document to process.
     * @return the document without terms larger than 30 chars long.
     */
    public static String removeTermsLongerThan30(String document) {
        return document.replaceAll(LARGER_THAN_30_PATTERN, "");
    }

    /**
     * Converts all uppercase chars into lowercase.
     * @param document the document to process.
     * @return the document without uppercase chars.
     */
    public static String toLowerCase(String document) {
        return document.toLowerCase(Locale.forLanguageTag("es"));
    }

    /**
     * Remove all special terms with special symbols.
     * @param document the document to process.
     * @return the document without invalid terms.
     */
    public static String removeInvalidTerms(String document) {
        return document.replaceAll(INVALID_SYMBOLS_PATTERN, "");
    }

    /**
     * Remove all terms that start with numbers.
     * @param document the document to process.
     * @return the document without invalid terms.
     */
    public static  String removeTermsStartsNumeric(String document){
        return document.replaceAll(STRING_WITH_NUMERICAL_START,"");
    }

    /**
     * Remove all numbers higher than 999999999.
     * @param document the document to process.
     * @return the document without invalid numbers.
     */
    public static  String removeNumbersGreaterThan999999999(String document){
        return document.replaceAll(NUMBER_RANGE_0_999999999,"");
    }

    /**
     * Remove all stopwords from a document.
     * @param document the document to process.
     * @return the document without articles, prepositions and conjunctions.
     */
    public static String removeStopWords(String document) {
        String noStopWords = document.replaceAll(ARTICLES_PATTERN, "");
        noStopWords = noStopWords.replaceAll(PREPOSITIONS_PATTERN, "");
        noStopWords = noStopWords.replaceAll(CONJUNCTIONS,"");
        return noStopWords;
    }
}
