package net.didorenko;

import net.didorenko.lexer.LexerAnalyzer;
import net.didorenko.lexer.LexerException;
import net.didorenko.parser.SyntaxicalAnalyzer;
import net.didorenko.parser.SyntaxicalException;

/**
 * package: net.didorenko
 * project: coursework
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 17.12.2015
 */
public class Main {

    public static void main(String[] args) throws LexerException, SyntaxicalException {
        String input = "begin end";
        SyntaxicalAnalyzer.inspect(LexerAnalyzer.lex(input));
    }
}
