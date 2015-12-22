package net.didorenko.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * package: net.didorenko.exception
 * project: coursework
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 21.12.2015
 */

public class ReadWriteFile {

    public static String loadFromFile(String path) throws ReadWriteException {
        byte[] encoded;
        try {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ReadWriteException("Cant read from file with this path: " + path, e);
        }
        return new String(encoded, Charset.defaultCharset());
    }

    public static void saveToFile(String path, String code) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(path)) {
            out.print(code);
        } catch (FileNotFoundException e) {
            System.out.println("File to saveToFile not found" + path);
        }
    }

}
