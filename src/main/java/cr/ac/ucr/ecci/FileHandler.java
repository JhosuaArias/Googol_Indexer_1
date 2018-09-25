package cr.ac.ucr.ecci;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.spi.CharsetProvider;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

}
