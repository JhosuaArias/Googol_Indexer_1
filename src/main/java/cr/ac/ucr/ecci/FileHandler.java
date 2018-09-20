package cr.ac.ucr.ecci;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class FileHandler {

    private static StringBuilder sb;
    private static List<String> stringArray;

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

        try (Stream<String> stream = Files.lines(Paths.get(path))) {

            stream.forEach(appendFunction);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void appendSB(String line) {
        sb.append(line).append(" ");
    }

    private static void appendStringArray(String line) {
        stringArray.add(line);
    }

}
