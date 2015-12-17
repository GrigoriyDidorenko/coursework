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
public class LexerAnalyzer {

    private String incomeText;
    private static ArrayList<Token> tokens = new ArrayList<>();


/*    public static ArrayList<Token> lex(String[] lines) {

        ArrayList<Token> tokens = new ArrayList<>();

        StringBuffer tokenPatternsBuffer = new StringBuffer();
        for (TokenType tokenType : TokenType.values())
            tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.stringValue));
        Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));

        for (int i = 0; i < lines.length; i++) {
            Matcher matcher = tokenPatterns.matcher(lines[i]);
            while (matcher.find()) {

                if (matcher.group(TokenType.INT_NUMBER.name()) != null) {
                    tokens.add(new Token(TokenType.INT_NUMBER, matcher.group(TokenType.INT_NUMBER.name()), i));
                    continue;
                } else if (matcher.group(TokenType.MATH.name()) != null) {
                    tokens.add(new Token(TokenType.MATH, matcher.group(TokenType.MATH.name()), i));
                    continue;
                } else if (matcher.group(TokenType.VARIABLE.name()) != null) {
                    tokens.add(new Token(TokenType.VARIABLE, matcher.group(TokenType.VARIABLE.name()), i));
                    continue;
                } else if (matcher.group(TokenType.RESERVED.name()) != null) {
                    tokens.add(new Token(TokenType.RESERVED, matcher.group(TokenType.RESERVED.name()), i));
                    continue;
                } else if (matcher.group(TokenType.WHITESPACE.name()) != null)
                    continue;
            }
        }
        return tokens;
    }*/

    public static String[] splitByLines(String incomeText) {
        return incomeText.split("[\\r\\n\\t?]+");
    }

    public static String[] splitEachLine(String line) {
        return line.trim().split("\\s+");
    }

    public static ArrayList<Token> lex(String incomeText) throws LexerException {
        String[] lines = splitByLines(incomeText);
        for (int i = 0; i < lines.length; i++) {
            String[] words = splitEachLine(lines[i]);
            for (String word : words) {
                boolean wasEnter = false;
                for (TokenType token : TokenType.values()) {
                    Pattern p = Pattern.compile("^" + token.stringValue + "$");
                    Matcher m = p.matcher(word);
                    if (m.matches()) {
                        wasEnter = true;
                        tokens.add(new Token(token, word, i));
                        break;
                    }
                }
                if (!wasEnter) throw new LexerException("Unexpected element found: " + word, i + 1);
            }
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
