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
    private static Grammar GRAMMAR = new Grammar();

    public static Grammar getInstance() {
        return GRAMMAR;
    }

    private Grammar() {
    }

    /*ADDING RULES TO LIST*/

        public ArrayList<ThreeElement> rules = new ArrayList<>(Arrays.asList(

/*        new ThreeElement(new ThreeElement.Term(false, EXPRESSION),
                new ThreeElement.Term[]{new ThreeElement.Term(false, CONDITION)}),

                new ThreeElement(new ThreeElement.Term(false, EXPRESSION),
                        new ThreeElement.Term[]{new ThreeElement.Term(false, CYCLE)}),

                new ThreeElement(new ThreeElement.Term(false, EXPRESSION),
                        new ThreeElement.Term[]{new ThreeElement.Term(false, METHOD)})

                new ThreeElement.Term[]{
                }*/

        new ThreeElement(new ThreeElement.Term(false, PROGRAM),
                new ThreeElement.Term[]{
                        new ThreeElement.Term(true,TokenType.INITIALIZING.stringValue),
                        new ThreeElement.Term(true,TokenType.BEGIN.stringValue),
                        new ThreeElement.Term(true,TokenType.END.stringValue)})
        ));

    public static final String
            E = "",
            ID = "ID",
            ONLY_ID = "ONLY_ID",
            TYPE = "TYPE",
            NUMBER = "NUMBER",
            PROGRAM = "PROGRAM",
            DEFINES = "DEFINES",
            DEFINE = "DEFINE",
            DEFINE_END = "DEFINE_END",
            EXPRESSIONS = "EXPRESSIONS",
            EXPRESSIONS_END = "EXPRESSIONS_END",
            EXPRESSION = "EXPRESSION",
            ASSIGNMENT = "ASSIGNMENT",
            HP_SIGN = "HP_SIGN",
            LP_SIGN = "LP_SIGN",
            VALUE = "VALUE",
            VALUE_END = "VALUE_END",
            HP_MATH_EXPR = "HP_MATH_EXPR",
            HP_MATH_EXPR_END = "HP_MATH_EXPR_END",
            ARGUMENT = "ARGUMENT",
            CONDITION = "CONDITION",
            COND_END = "COND_END",
            ELSE_PART = "ELSE_PART",
            COND_EXPR = "COND_EXPR",
            BOOL_SIGN = "BOOL_SIGN",
            CYCLE = "CYCLE",
            METHOD = "METHOD",
            MET_NAME = "MET_NAME",
            MET_PARS = "MET_PARS",
            MET_PARS_END = "MET_PARS_END",
            FUNCTION = "FUNCTION",
            FUNC_NAME = "FUNC_NAME",
            FUNC_PARS = "FUNC_PARS",
            FUNC_PARS_END = "FUNC_PARS_END",
            STRING = "STRING";
}
