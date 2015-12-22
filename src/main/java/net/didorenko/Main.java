package net.didorenko;

import net.didorenko.assembler.TransformToAssembler;
import net.didorenko.io.ReadWriteException;
import net.didorenko.io.ReadWriteFile;
import net.didorenko.lexer.Lexical;
import net.didorenko.lexer.UnexpectedTokenException;
import net.didorenko.semantic.LogicException;
import net.didorenko.semantic.Semantic;
import net.didorenko.semantic.WrongTypeException;
import net.didorenko.syntaxical.Syntactical;
import net.didorenko.syntaxical.UndeclaredVariableException;
import net.didorenko.syntaxical.UnexpectedSymbolException;

import java.io.FileNotFoundException;

/**
 * package: net.didorenko.exception
 * project: coursework
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 16.12.2015
 */

public class Main {

    public static void main(String[] args) {

        String path = "src\\main\\resources\\example.txt";
        try {
            compile(path, path.replace(".txt", ".asm"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void compile(String input, String output) throws
            ReadWriteException, UndeclaredVariableException, UnexpectedSymbolException,
            FileNotFoundException, LogicException, WrongTypeException, UnexpectedTokenException {

        System.out.println("Lexical processing:\n" +
                "--------------------------");
        Lexical.splitWholeConstruction(ReadWriteFile.loadFromFile(input));

        System.out.println("Syntaxical processing:\n" +
                "--------------------------");
        Syntactical.inspect();

        System.out.println("Semantic processing:\n" +
                "--------------------------");
        Semantic.check();

        TransformToAssembler codeGenerator = new TransformToAssembler(Syntactical.getTreeRoot(), Syntactical.getIdArray());
        System.out.println("Generating code input MASM processing:\n" +
                "--------------------------");
        codeGenerator.genOutText();

        ReadWriteFile.saveToFile(output, codeGenerator.getOutText());
        System.out.println("DONE !");
    }
}
