package net.didorenko.lexer;

import net.didorenko.general.Keyword;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexical {

    private static String incomeText;
    private static boolean breakable;

    private static ArrayList<String> tokens = new ArrayList<>();
    private static ArrayList<Integer> lineIndexes = new ArrayList<>();

    public static ArrayList<String> getTokens() {
        return tokens;
    }

    public static ArrayList<Integer> getLineIndexes() {
        return lineIndexes;
    }

    public static void splitWholeConstruction(String fromFile) throws UnexpectedTokenException {
        incomeText = fromFile;
        String[] lines = Lexical.incomeText.split("[\\r\\n\\t?]+");
        lineIndexes.add(0);
        for (String line : lines) {
            splitEachLine(line.trim(), lineIndexes.indexOf(tokens.size())+1);
            lineIndexes.add(tokens.size());
        }
    }

    private static void splitEachLine(String line, int lineNumber) throws UnexpectedTokenException {
        String[] words = line.split("\\s+");
        for (String word : words) {

            boolean containsKeywords = false;
            boolean isString = false;

            for (int i = 0; i < word.length(); i++) {
                for (int j = 0; j < Keyword.ALL_KEYWORDS.length; j++) {
                    containsKeywords = false;


                    if (word.indexOf(Keyword.ALL_KEYWORDS[j]) == i) {

                        if (Keyword.ALL_KEYWORDS[j].equals(Keyword.BRACKETS[2]/* " */))
                            isString = !isString;
                        else if (isString) continue;

                        if(!tokens.isEmpty()) {
                            Pattern p = Pattern.compile("^" + Keyword.SYMBOLS_TO_CONTINUE + "$");
                            Matcher m = p.matcher(tokens.get(tokens.size() - 1));
                            if (Keyword.ALL_KEYWORDS[j].equals(Keyword.MATH_OPERATIONS[1])
                                    && word.charAt(0) == '-' && m.matches())
                                continue;
                        }

//                        if (Keyword.ALL_KEYWORDS[j].equals(Keyword.MATH_OPERATIONS[1]/* - */)
//                                && word.charAt(0) == '-'
//                                && (tokens.get(tokens.size() - 1).equals(Keyword.BRACKETS[0]/* ( */)
//                                || tokens.get(tokens.size() - 1).equals(Keyword.SYNTAX_SYMBOLS[0]/* := */)
//                                || tokens.get(tokens.size() - 1).equals(Keyword.MATH_OPERATIONS[0]/* + */)
//                                || tokens.get(tokens.size() - 1).equals(Keyword.MATH_OPERATIONS[1]/* - */)
//                                || tokens.get(tokens.size() - 1).equals(Keyword.MATH_OPERATIONS[2]/* * */)
//                                || tokens.get(tokens.size() - 1).equals(Keyword.MATH_OPERATIONS[3]/* / */)
//                                || tokens.get(tokens.size() - 1).equals(Keyword.BOOL_OPERATIONS[0]/* < */)
//                                || tokens.get(tokens.size() - 1).equals(Keyword.BOOL_OPERATIONS[1]/* > */)
//                                || tokens.get(tokens.size() - 1).equals(Keyword.BOOL_OPERATIONS[2]/* = */)
//                                || tokens.get(tokens.size() - 1).equals(Keyword.BOOL_OPERATIONS[3]/* != */)))
//                            continue;

                        if (Arrays.asList(Keyword.ALL_WORDS).contains(Keyword.ALL_KEYWORDS[j]) &&
                                word.length() > Keyword.ALL_KEYWORDS[j].length())
                            break;
                        containsKeywords = true;

                        String rightPart = word.substring(i + Keyword.ALL_KEYWORDS[j].length(), word.length());
                        String leftPart = word.substring(0, i);

                        Pattern findUnexpectedToken = Pattern.compile("\\d+[a-zA-z]+");
                        Matcher leftPartMatcher = findUnexpectedToken.matcher(leftPart);
                        Matcher rightPartMatcher = findUnexpectedToken.matcher(leftPart);

                        if(leftPartMatcher.matches())
                            throw new UnexpectedTokenException("Unexpected token found \"" + leftPart+"\" at line ["+lineNumber+"]");
                        if(rightPartMatcher.matches())
                            throw new UnexpectedTokenException("Unexpected token found \"" + leftPart+"\" at line ["+lineNumber+"]");

                        if (!leftPart.equals("")) tokens.add(leftPart);
                        tokens.add(Keyword.ALL_KEYWORDS[j]);
                        word = rightPart;
                        if (word.equals("")) {
                            breakable = true;
                            break;
                        }
                        i = 0;
                        j = 0;
                    }
                }
                if (breakable) {
                    breakable = false;
                    break;
                }
            }
            if (!containsKeywords) tokens.add(word);
        }
    }

}
