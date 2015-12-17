package net.didorenko.file;

/**
 * package: net.didorenko.file
 * project: coursework
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 17.12.2015
 */
public class FileWriterReaderException extends Exception {

    protected int lineNumber;

    public FileWriterReaderException(String s, int lineNumber){
        super(s);
        this.lineNumber = lineNumber;
    }

    public FileWriterReaderException(String s){
        super(s);
        this.lineNumber = 0;
    }

    @Override
    public String getMessage(){
        return new StringBuilder().append("Line number: ").append(lineNumber).append("\n").append(super.getMessage()).toString();
    }
}
