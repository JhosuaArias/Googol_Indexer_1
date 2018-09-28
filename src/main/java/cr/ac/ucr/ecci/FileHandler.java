package cr.ac.ucr.ecci;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class FileHandler {

    private static StringBuilder sb;
    private static List<String> stringArray;
    private static final Logger LOG = LogManager.getLogger(FileHandler.class.getName());

    public static String readFileSB(String path) {
        sb = new StringBuilder();
        FileHandler.abstractReadFile(path, FileHandler::appendSB);

        return sb.toString();
    }

    public static List<String> readFileStringArray(String path) {
        stringArray = new ArrayList<>();
        FileHandler.abstractReadFile(path, FileHandler::appendStringArray);

        return stringArray;
    }

    private static void abstractReadFile(String path, Consumer<? super String> appendFunction) {

        Charset[] supportedCharsets = {StandardCharsets.UTF_8, StandardCharsets.ISO_8859_1, Charset.forName("Windows-1252")};
        boolean fileDecoded = false;
        for (Charset charset : supportedCharsets) {
            try (Stream<String> stream = Files.lines(Paths.get(path), charset)) {

                stream.forEach(appendFunction);
                fileDecoded = true;
                break;

            } catch (IOException e) {
                e.printStackTrace();
                LOG.error("Unexpected IO Exception, proceeding to exit the program");
                System.exit(99);
            } catch (UncheckedIOException e) {
                LOG.error("Incorrect encoding " + charset.toString() + " for file " + path);
            }
        }

        if (!fileDecoded) {
            LOG.error("None of the supported encodings were appropriate, exiting program");
            System.exit(99);
        }

    }

    private static void appendSB(String line) {
        sb.append(line).append(" ");
    }

    private static void appendStringArray(String line) {
        stringArray.add(line);
    }

    /**
     * Writes a .tok file to represent a document, using the following format:
     *  - 30 chars for the term
     *  - 12 chars for the frequency of the term in the document
     *  - 20 normalized frequency in the document
     * And a blank space between each column, with a line skip and the end.
     *
     * @param document the document to be represented in the format above.
     */
    public static void writeDocumentTok(WebDocument document) {
        List<String> lines = new ArrayList<>();

        for(TermData termData : document.getTerms()) {

            // Write the term and pad with enough blank spaces
            StringBuilder termColumn = new StringBuilder(termData.getTerm());
            termColumn = FileHandler.padString(termColumn, 31); // 31 to account for blank space

            // Write the frequency and pad, same as above
            StringBuilder frequencyColumn = new StringBuilder(termData.getFrequencyInDocument(document.getDocumentName()).toString());
            frequencyColumn = FileHandler.padString(frequencyColumn, 13); // 13 to account for blank space

            // Calculate max frequency
            Double maxFrequency = termData.getNormalizedFrequency(document.getDocumentName(),document.getMaxFrequency());

            // Write the normalized frequency
            StringBuilder normalizedFrequencyColumn = new StringBuilder(maxFrequency.toString());
            normalizedFrequencyColumn = FileHandler.padString(normalizedFrequencyColumn, 20); // this line should not have an extra space

            // Concatenate and add the line skip
            String line = termColumn.toString() + frequencyColumn.toString() + normalizedFrequencyColumn.toString();
            lines.add(line);
        }

        // Add the line skip at the end
        lines.add("\n");

        try {
            Files.write(Paths.get("src","main","resources","toks",document.getDocumentName()+".tok").toAbsolutePath(), lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a .tok file to represent a each term in all the collection, using the following format:
     *  - 30 chars for the term
     *  - 12 chars for amount of documents where the term appears
     *  - 20 for the inverse frequency
     *
     * And a blank space between each column, with a line skip and the end.
     * @param vocabulary
     */
    public static void writeVocabularyFile(Collection<TermData> vocabulary, int documentCount) {
        List<String> lines = new ArrayList<>();

        for(TermData termData : vocabulary) {
            // Write the term and pad with enough blank spaces
            StringBuilder termColumn = new StringBuilder(termData.getTerm());
            termColumn = FileHandler.padString(termColumn, 31); // 31 to account for blank space

            // Write the document count
            StringBuilder frequencyColumn = new StringBuilder(termData.getDocumentCount().toString());
            frequencyColumn = FileHandler.padString(frequencyColumn, 13); // 13 to account for blank space

            // Calculate the inverse frequency
            Double inverseFrequency = Math.log10((double) documentCount/termData.getFrequencyInCollection());

            // Write the normalized frequency
            StringBuilder normalizedFrequencyColumn = new StringBuilder(inverseFrequency.toString());
            normalizedFrequencyColumn = FileHandler.padString(normalizedFrequencyColumn, 20); // this line should not have an extra space

            // Concatenate and add the line skip
            String line = termColumn.toString() + frequencyColumn.toString() + normalizedFrequencyColumn.toString();
            lines.add(line);
        }

        try {
            Files.write(Paths.get("src","main","resources","Vocabulario.tok").toAbsolutePath(), lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds enough blank spaces to make stringBuilder have a length equal to padding.
     * @param stringBuilder
     * @param padding
     * @return
     */
    private static StringBuilder padString(StringBuilder stringBuilder, int padding) {
        int initialLength = stringBuilder.length();
        for(int i = 0; i < padding-initialLength; i++) {
            stringBuilder.append(" ");
        }
        return stringBuilder;
    }

}
