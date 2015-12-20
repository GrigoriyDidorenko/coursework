package net.didorenko.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadWriteFile {

    public static String load(String path) throws ReadWriteException {
        byte[] encoded;
        try {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ReadWriteException("Cant read from file with this path: " + path, e);
        }
        return new String(encoded, Charset.defaultCharset());
    }

    public static void save(String path, String code) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(path)) {
            out.print(code);
        } catch (FileNotFoundException e) {
            System.out.println("File to save not found" + path);
        }
    }

}
