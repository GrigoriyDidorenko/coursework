package net.didorenko;

import net.didorenko.general.Rule;
import net.didorenko.lexer.Lexical;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * package: net.didorenko.exception
 * project: coursework
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 16.12.2015
 */

public abstract class LineFounder {

    public static String findExceptionPosition(int inTermsPosition, ArrayList<Integer> lineIndexes) {
        int lineLumber = -1;
        for (int i = 0; i < lineIndexes.size(); i++)
            if (lineIndexes.get(i) <= inTermsPosition)
                lineLumber = i;
        return String.valueOf(++lineLumber);
    }

    public static String findExceptionPosition(Rule.Term[] terms, Rule.Term problemTerm) {
        int inTermsPosition = Arrays.asList(terms).indexOf(problemTerm);
        return findExceptionPosition(inTermsPosition, Lexical.getLineIndexes());
    }
}
