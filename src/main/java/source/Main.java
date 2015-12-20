package source;

import net.didorenko.assembler.TransformToAssembler;
import net.didorenko.io.ReadWriteException;
import net.didorenko.io.ReadWriteFile;
import net.didorenko.lexer.Lexical;
import net.didorenko.semantic.LogicException;
import net.didorenko.semantic.Semantic;
import net.didorenko.semantic.WrongTypeException;
import net.didorenko.syntaxical.Syntactical;
import net.didorenko.syntaxical.UndeclaredVariableException;
import net.didorenko.syntaxical.UnexpectedSymbolException;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws URISyntaxException {

        String path = "D:\\coursework\\Example.txt";
        try {
            execute(path, path.replace(".txt", ".asm"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void execute(String in, String out) throws
            ReadWriteException, UndeclaredVariableException, UnexpectedSymbolException,
            FileNotFoundException, LogicException, WrongTypeException {

        System.out.println("Lexical processing:\n" +
                "--------------------------");
        Lexical.splitWholeConstruction(ReadWriteFile.load(in));

        System.out.println("Syntaxical processing:\n" +
                "--------------------------");
        Syntactical.inspect(Lexical.getTokens(), Lexical.getLineIndexes());

        System.out.println("Semantic processing:\n" +
                "--------------------------");
        Semantic.check(Syntactical.getTreeRoot(), Syntactical.getTerms(), Lexical.getLineIndexes());

        TransformToAssembler codeGenerator = new TransformToAssembler(Syntactical.getTreeRoot(), Syntactical.getIdArray());
        System.out.println("Generating code in MASM processing:\n" +
                "--------------------------");
        codeGenerator.genOutText();

        ReadWriteFile.save(out, codeGenerator.getOutText());
        System.out.println("DONE !");
    }
}
