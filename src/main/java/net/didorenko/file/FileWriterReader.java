package net.didorenko.file;

/**
 * package: net.didorenko.file
 * project: coursework
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 17.12.2015
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


public class FileWriterReader {

    public static String load(String path) throws FileWriterReaderException {
        byte[] encodedFile;
        try {
            encodedFile = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileWriterReaderException("Cant read from file with this path: " + path);
        }
        return new String(encodedFile, Charset.defaultCharset());
    }

    public static void save(String path, String code) throws FileWriterReaderException {
        try(PrintWriter out = new PrintWriter(path)){
            out.print(code);
        }catch (Exception e){
            throw new FileWriterReaderException("Cant save to file with this path: " + path);
        }
    }

}

