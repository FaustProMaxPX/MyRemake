package com.example.streams;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Words {
    
    /**
     * find all files in the filesystem
     * @param folder the root of the filesystem
     * @return stream of all ordinary files
     */
    static Stream<File> allFilesin(File folder) {

        File[] files = folder.listFiles();
        // recursively get the files in the folder
        Stream<File> descendants = Arrays.stream(files).filter(File::isDirectory).flatMap(Words::allFilesin);
        return Stream.concat(descendants, Arrays.stream(files).filter(File::isFile));
    }

    /**
     * make a filename suffix testing predicate
     * @param suffix suffix string to test
     * @return  true if the filename ends with suffix
     */
    static Predicate<File> endsWith(String suffix) {
        return f -> f.getPath().endsWith(suffix);
    }

    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter("out.txt");
        Stream<File> files = allFilesin(new File(".")).filter(endsWith(".java"));
        Stream<Path> paths = files.map(File::toPath);
        Stream<List<String>> filecontents = paths.map((path) -> {
            try {
                return Files.readAllLines(path);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
        Stream<String> lines = filecontents.flatMap(List::stream);
        Stream<String> words = lines.flatMap(line -> Arrays.stream(line.split("\\W+"))
                                                     .filter(s -> s.length() > 0));
        List<String> t = words.toList();
        for (String s : t) {
            out.write(s + "\n");
        }
        out.flush();
        out.close();
    }
}
