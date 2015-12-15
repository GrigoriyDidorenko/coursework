package net.didorenko.lexer;

import net.didorenko.Token;
import net.didorenko.TokenType;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * package: PACKAGE_NAME
 * project: coursework
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 14.12.2015
 */
public class Lexer {

    private String incomeText;

/*    public static void main(String[] args) {
        String input = "for 1 to 5 do";
        ArrayList<net.didorenko.Token> tokens = lex(input);
        for (net.didorenko.Token token : tokens)
            System.out.println(token);
    }*/

    public static ArrayList<Token> lex(String input) {

        // The tokens to return

        ArrayList<Token> tokens = new ArrayList<Token>();

        // Lexer logic begins here

        StringBuffer tokenPatternsBuffer = new StringBuffer();
        for (TokenType tokenType : TokenType.values())
            tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
        Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));

        // Begin matching tokens
        Matcher matcher = tokenPatterns.matcher(input);
        while (matcher.find()) {

            if (matcher.group(TokenType.NUMBER.name()) != null) {
                tokens.add(new Token(TokenType.NUMBER, matcher.group(TokenType.NUMBER.name())));
                continue;
            } else if (matcher.group(TokenType.MATH.name()) != null) {
                tokens.add(new Token(TokenType.MATH, matcher.group(TokenType.MATH.name())));
                continue;
            } else if(matcher.group(TokenType.RESERVED.name())!= null){
                tokens.add(new Token(TokenType.RESERVED, matcher.group(TokenType.RESERVED.name())));
                continue;
            }
            else if (matcher.group(TokenType.WHITESPACE.name()) != null)
                continue;
        }


        return tokens;
    }

    public String getIncomeText() {
        return incomeText;
    }

    public void setIncomeText(String incomeText) {
        this.incomeText = incomeText;
    }
}
