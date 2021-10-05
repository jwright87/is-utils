package uk.intenso.utils;

import lombok.AccessLevel;
import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IsFileUtils {

    private static final Logger log = LoggerFactory.getLogger(IsFileUtils.class);

    private static final ClassLoader classLoader = IsFileUtils.class.getClassLoader();

    public static Path getPath(String location) {
        return Paths.get(location);
    }

    public static Path getPath(URI uri) {
        return Paths.get(uri);
    }

    public static ClassLoader getClassLoader(Class clazz) {
        return clazz.getClassLoader();
    }

    public static String readFile(String location) throws IOException {
        return joinLines(Files.newBufferedReader(Paths.get(location)).lines());
    }

    @SneakyThrows
    public static String readClasspathFile(ClassLoader classLoader, String location) {

        InputStream in = classLoader.getResourceAsStream(location);
        return joinLines(new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))
                .lines());


    }

    private static String joinLines(Stream<String> lines) {
        return lines.collect(Collectors.joining("\n"));
    }

    public static String cwd() {
        return System.getProperty("user.dir");
    }

    public static File newFile(String location) {
        return new File(location);
    }

    public static void deleteFile(File file) {
        file.delete();
    }

    public static void writeToFile(File file, String data, boolean append) throws IOException {
        String path = file.getCanonicalPath().toString();
        @Cleanup BufferedWriter fileWriter  = new BufferedWriter( new FileWriter(path));

        if (append) {
            fileWriter.append(data);
        } else {
            fileWriter.write(data);
        }
        fileWriter.flush();

    }
}
