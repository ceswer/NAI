import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Service {
    public static ArrayList<Observation> parse(String dir) {
        ArrayList<Observation> observations = new ArrayList<>();

        try (DirectoryStream<Path> directories = Files.newDirectoryStream(Paths.get(dir))) {
            String language = null;
            for (Path directory : directories) {
                StringBuilder stringBuilder = new StringBuilder();

                if (Files.isDirectory(directory)) {
                    language = directory.getFileName().toString();

                    Stream<Path> files = Files.walk(directory);
                    for (Path file : files.filter(Files::isRegularFile).collect(Collectors.toList())) {
                        BufferedReader br = new BufferedReader(new FileReader(file.toFile()));
                        String line;
                        while (null != (line = br.readLine()))
                            stringBuilder.append(line.toLowerCase().replaceAll("[^a-zA-Z]", ""));
                    }
                }
                observations.add(new Observation(language, stringBuilder.toString()));
            }
        } catch (Exception ignored) {}
        return observations;
    }
}
