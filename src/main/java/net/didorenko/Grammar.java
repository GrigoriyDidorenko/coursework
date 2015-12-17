package net.didorenko;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * package: net.didorenko
 * project: coursework
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 17.12.2015
 */
public class Grammar {

    private Grammar() {
    }

    /*ADDING RULES TO LIST*/

    public static ArrayList<Rule> rules = new ArrayList<>(Arrays.asList(

/*        new Rule(new Rule.Term(false, EXPRESSION),
                new Rule.Term[]{new Rule.Term(false, CONDITION)}),

                new Rule(new Rule.Term(false, EXPRESSION),
                        new Rule.Term[]{new Rule.Term(false, CYCLE)}),

                new Rule(new Rule.Term(false, EXPRESSION),
                        new Rule.Term[]{new Rule.Term(false, METHOD)})

                new Rule.Term[]{
                }*/

            new Rule(new Rule.Term(false, "PROGRAM"),
                    new Rule.Term[]{
                            new Rule.Term(true, TokenType.INITIALIZING.stringValue),
                            new Rule.Term(true, TokenType.BEGIN.stringValue),
                            new Rule.Term(true, TokenType.END.stringValue)}),

            new Rule(new Rule.Term(false, "PROGRAM"),
                    new Rule.Term[]{
                            new Rule.Term(true, TokenType.BEGIN.stringValue),
                            new Rule.Term(true, TokenType.END.stringValue)}),

            new Rule(new Rule.Term(false, "PROGRAM"),
                    new Rule.Term[]{
                            new Rule.Term(true, TokenType.BEGIN.stringValue),
                            new Rule.Term(true, TokenType.END.stringValue)}),

            new Rule(new Rule.Term(false, "EXPRESSION"),
                    new Rule.Term[]{
                            new Rule.Term(true, TokenType.BEGIN.stringValue),
                            new Rule.Term(true, TokenType.END.stringValue)}),

            new Rule(new Rule.Term(false, "ASSIGNMENT_INT"),
                    new Rule.Term[]{
                            new Rule.Term(true, TokenType.INITIALIZING.stringValue),
                            new Rule.Term(true, TokenType.ASSIGNMENT.stringValue),
                            new Rule.Term(true, TokenType.INT_NUMBER.stringValue)}),

            new Rule(new Rule.Term(false, "ASSIGMENT_STRING"),
                    new Rule.Term[]{
                            new Rule.Term(true, TokenType.INITIALIZING.stringValue),
                            new Rule.Term(true, TokenType.ASSIGNMENT.stringValue),
                            new Rule.Term(true, TokenType.ASSIGNMENT.stringValue)})


    ));
}


