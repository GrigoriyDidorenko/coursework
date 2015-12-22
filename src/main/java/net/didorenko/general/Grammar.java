package net.didorenko.general;

/**
 * package: net.didorenko.exception
 * project: coursework
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 17.12.2015
 */

public class Grammar {

        private static final Grammar GRAMMAR = new Grammar();

        public static Grammar getInstance(){
                return GRAMMAR;
        }

    public final String
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

    public boolean isNonTerminal(String s) {
        return s.equals(ID) ||
                s.equals(ONLY_ID) ||
                s.equals(TYPE) ||
                s.equals(NUMBER) ||
                s.equals(PROGRAM) ||
                s.equals(DEFINES) ||
                s.equals(DEFINE) ||
                s.equals(DEFINE_END) ||
                s.equals(EXPRESSIONS) ||
                s.equals(EXPRESSIONS_END) ||
                s.equals(EXPRESSION) ||
                s.equals(ASSIGNMENT) ||
                s.equals(HP_SIGN) ||
                s.equals(LP_SIGN) ||
                s.equals(VALUE) ||
                s.equals(VALUE_END) ||
                s.equals(HP_MATH_EXPR) ||
                s.equals(HP_MATH_EXPR_END) ||
                s.equals(ARGUMENT) ||
                s.equals(CONDITION) ||
                s.equals(COND_END) ||
                s.equals(ELSE_PART) ||
                s.equals(COND_EXPR) ||
                s.equals(BOOL_SIGN) ||
                s.equals(CYCLE) ||
                s.equals(METHOD) ||
                s.equals(MET_NAME) ||
                s.equals(MET_PARS) ||
                s.equals(MET_PARS_END) ||
                s.equals(FUNCTION) ||
                s.equals(FUNC_NAME) ||
                s.equals(FUNC_PARS) ||
                s.equals(STRING) ||
                s.equals(FUNC_PARS_END);
    }

    public final Rule[] RULES = {
            new Rule(new Rule.Term(false, PROGRAM),
                    new Rule.Term[]{new Rule.Term(true, Keyword.RESERVED_WORDS[0]), new Rule.Term(false, DEFINES), new Rule.Term(true, Keyword.RESERVED_WORDS[1]), new Rule.Term(false, EXPRESSIONS), new Rule.Term(true, Keyword.RESERVED_WORDS[5])}),
            new Rule(new Rule.Term(false, PROGRAM),
                    new Rule.Term[]{new Rule.Term(true, Keyword.RESERVED_WORDS[1]), new Rule.Term(false, EXPRESSIONS), new Rule.Term(true, Keyword.RESERVED_WORDS[5])}),
            new Rule(new Rule.Term(false, PROGRAM),
                    new Rule.Term[]{new Rule.Term(true, Keyword.RESERVED_WORDS[1]), new Rule.Term(true, Keyword.RESERVED_WORDS[5])}),
            new Rule(new Rule.Term(false, DEFINES),
                    new Rule.Term[]{new Rule.Term(false, DEFINE), new Rule.Term(false, DEFINES)}),
            new Rule(new Rule.Term(false, DEFINES),
                    new Rule.Term[]{new Rule.Term(false, DEFINE)}),
            new Rule(new Rule.Term(false, DEFINE),
                    new Rule.Term[]{new Rule.Term(false, TYPE), new Rule.Term(true, Keyword.SYNTAX_SYMBOLS[3]), new Rule.Term(false, ONLY_ID), new Rule.Term(false, DEFINE_END)}),
            new Rule(new Rule.Term(false, DEFINE_END),
                    new Rule.Term[]{new Rule.Term(true, Keyword.SYNTAX_SYMBOLS[2]), new Rule.Term(false, ONLY_ID), new Rule.Term(false, DEFINE_END)}),
            new Rule(new Rule.Term(false, DEFINE_END),
                    new Rule.Term[]{new Rule.Term(true, Keyword.SYNTAX_SYMBOLS[1])}),
            new Rule(new Rule.Term(false, EXPRESSIONS),
                    new Rule.Term[]{new Rule.Term(false, EXPRESSION), new Rule.Term(false, EXPRESSIONS_END)}),
            new Rule(new Rule.Term(false, EXPRESSIONS_END),
                    new Rule.Term[]{new Rule.Term(false, EXPRESSIONS)}),
            new Rule(new Rule.Term(false, EXPRESSIONS_END),
                    new Rule.Term[]{new Rule.Term(false, E)}),
            new Rule(new Rule.Term(false, EXPRESSION),
                    new Rule.Term[]{new Rule.Term(false, ASSIGNMENT)}),
            new Rule(new Rule.Term(false, EXPRESSION),
                    new Rule.Term[]{new Rule.Term(false, CONDITION)}),
            new Rule(new Rule.Term(false, EXPRESSION),
                    new Rule.Term[]{new Rule.Term(false, CYCLE)}),
            new Rule(new Rule.Term(false, EXPRESSION),
                    new Rule.Term[]{new Rule.Term(false, METHOD)}),
            new Rule(new Rule.Term(false, ASSIGNMENT),
                    new Rule.Term[]{new Rule.Term(false, ONLY_ID), new Rule.Term(true, Keyword.SYNTAX_SYMBOLS[0]),
                            new Rule.Term(false, VALUE), new Rule.Term(true, Keyword.SYNTAX_SYMBOLS[1])}),
            new Rule(new Rule.Term(false, VALUE),
                    new Rule.Term[]{new Rule.Term(false, HP_MATH_EXPR), new Rule.Term(false, VALUE_END)}),
            new Rule(new Rule.Term(false, VALUE_END),
                    new Rule.Term[]{new Rule.Term(false, LP_SIGN), new Rule.Term(false, VALUE)}),
            new Rule(new Rule.Term(false, VALUE_END),
                    new Rule.Term[]{new Rule.Term(false, E)}),
            new Rule(new Rule.Term(false, HP_MATH_EXPR),
                    new Rule.Term[]{new Rule.Term(false, ARGUMENT), new Rule.Term(false, HP_MATH_EXPR_END)}),
            new Rule(new Rule.Term(false, HP_MATH_EXPR_END),
                    new Rule.Term[]{new Rule.Term(false, HP_SIGN), new Rule.Term(false, HP_MATH_EXPR)}),
            new Rule(new Rule.Term(false, HP_MATH_EXPR_END),
                    new Rule.Term[]{new Rule.Term(false, E)}),
            new Rule(new Rule.Term(false, ARGUMENT),
                    new Rule.Term[]{new Rule.Term(false, ID)}),
            new Rule(new Rule.Term(false, ARGUMENT),
                    new Rule.Term[]{new Rule.Term(false, NUMBER)}),
            new Rule(new Rule.Term(false, ARGUMENT),
                    new Rule.Term[]{new Rule.Term(false, FUNCTION)}),
            new Rule(new Rule.Term(false, ARGUMENT),
                    new Rule.Term[]{new Rule.Term(true, Keyword.BRACKETS[0]), new Rule.Term(false, VALUE), new Rule.Term(true, Keyword.BRACKETS[1])}),
            new Rule(new Rule.Term(false, FUNCTION),
                    new Rule.Term[]{
                            new Rule.Term(false, FUNC_NAME), new Rule.Term(true, Keyword.BRACKETS[0]),
                            new Rule.Term(false, FUNC_PARS), new Rule.Term(true, Keyword.BRACKETS[1])}),
            new Rule(new Rule.Term(false, FUNC_PARS),
                    new Rule.Term[]{new Rule.Term(false, VALUE), new Rule.Term(false, FUNC_PARS_END)}),
            new Rule(new Rule.Term(false, FUNC_PARS_END),
                    new Rule.Term[]{new Rule.Term(true, Keyword.SYNTAX_SYMBOLS[2]), new Rule.Term(false, VALUE), new Rule.Term(false, FUNC_PARS_END)}),
            new Rule(new Rule.Term(false, FUNC_PARS_END),
                    new Rule.Term[]{new Rule.Term(false, E)}),
            new Rule(new Rule.Term(false, CONDITION),
                    new Rule.Term[]{new Rule.Term(true, Keyword.RESERVED_WORDS[2]), new Rule.Term(true, Keyword.BRACKETS[0]),
                            new Rule.Term(false, COND_EXPR), new Rule.Term(true, Keyword.BRACKETS[1]),
                            new Rule.Term(false, COND_END), new Rule.Term(false, ELSE_PART)}),
            new Rule(new Rule.Term(false, COND_END),
                    new Rule.Term[]{new Rule.Term(true, Keyword.RESERVED_WORDS[1]), new Rule.Term(false, EXPRESSIONS), new Rule.Term(true, Keyword.RESERVED_WORDS[5])}),
            new Rule(new Rule.Term(false, COND_END),
                    new Rule.Term[]{new Rule.Term(false, EXPRESSION)}),
            new Rule(new Rule.Term(false, ELSE_PART),
                    new Rule.Term[]{new Rule.Term(true, Keyword.RESERVED_WORDS[3]), new Rule.Term(false, COND_END)}),
            new Rule(new Rule.Term(false, ELSE_PART),
                    new Rule.Term[]{new Rule.Term(false, E)}),
            new Rule(new Rule.Term(false, COND_EXPR),
                    new Rule.Term[]{new Rule.Term(false, VALUE), new Rule.Term(false, BOOL_SIGN), new Rule.Term(false, VALUE)}),
            new Rule(new Rule.Term(false, CYCLE),
                    new Rule.Term[]{new Rule.Term(true, Keyword.RESERVED_WORDS[4]), new Rule.Term(true, Keyword.BRACKETS[0]), new Rule.Term(false, COND_EXPR), new Rule.Term(true, Keyword.BRACKETS[1]), new Rule.Term(false, COND_END)}),
            new Rule(new Rule.Term(false, METHOD),
                    new Rule.Term[]{new Rule.Term(false, MET_NAME), new Rule.Term(true, Keyword.BRACKETS[0]), new Rule.Term(false, MET_PARS), new Rule.Term(true, Keyword.SYNTAX_SYMBOLS[1])}),
            new Rule(new Rule.Term(false, MET_PARS),
                    new Rule.Term[]{new Rule.Term(true, Keyword.BRACKETS[1])}),
            new Rule(new Rule.Term(false, MET_PARS),
                    new Rule.Term[]{new Rule.Term(false, VALUE), new Rule.Term(false, MET_PARS_END), new Rule.Term(true, Keyword.BRACKETS[1])}),
            new Rule(new Rule.Term(false, MET_PARS_END),
                    new Rule.Term[]{new Rule.Term(true, Keyword.SYNTAX_SYMBOLS[2]), new Rule.Term(false, VALUE), new Rule.Term(false, MET_PARS_END)}),
            new Rule(new Rule.Term(false, MET_PARS_END),
                    new Rule.Term[]{new Rule.Term(false, E)}),
            new Rule(new Rule.Term(false, ARGUMENT),
                    new Rule.Term[]{new Rule.Term(true, Keyword.BRACKETS[2]), new Rule.Term(false, STRING), new Rule.Term(true, Keyword.BRACKETS[2])})
    };

    public final RuleMatcher[] HELPER = {
            new RuleMatcher(0, new String[]{Keyword.RESERVED_WORDS[0]}),
            new RuleMatcher(2, new String[]{Keyword.RESERVED_WORDS[1], Keyword.RESERVED_WORDS[5]}),
            new RuleMatcher(4, new String[]{Keyword.SYNTAX_SYMBOLS[1], Keyword.RESERVED_WORDS[1]}),
            new RuleMatcher(6, new String[]{Keyword.SYNTAX_SYMBOLS[2]}),
            new RuleMatcher(7, new String[]{Keyword.SYNTAX_SYMBOLS[1]}),
            new RuleMatcher(9, new String[]{}),
            new RuleMatcher(11, new String[]{ONLY_ID}),
            new RuleMatcher(12, new String[]{Keyword.RESERVED_WORDS[2]}),
            new RuleMatcher(13, new String[]{Keyword.RESERVED_WORDS[4]}),
            new RuleMatcher(14, new String[]{MET_NAME}),
            new RuleMatcher(17, new String[]{LP_SIGN}),
            new RuleMatcher(20, new String[]{HP_SIGN}),
            new RuleMatcher(22, new String[]{ID}),
            new RuleMatcher(23, new String[]{NUMBER}),
            new RuleMatcher(24, new String[]{FUNC_NAME}),
            new RuleMatcher(25, new String[]{Keyword.BRACKETS[0]}),
            new RuleMatcher(42, new String[]{Keyword.BRACKETS[2]}),
            new RuleMatcher(28, new String[]{Keyword.SYNTAX_SYMBOLS[2]}),
            new RuleMatcher(31, new String[]{Keyword.RESERVED_WORDS[1]}),
            new RuleMatcher(33, new String[]{Keyword.RESERVED_WORDS[3]}),
            new RuleMatcher(38, new String[]{Keyword.BRACKETS[1]}),
            new RuleMatcher(40, new String[]{Keyword.SYNTAX_SYMBOLS[2]})
    };

    public class RuleMatcher {
        Rule rule;
        String[] firsts;

        public RuleMatcher(int inRulesPosition, String[] firsts) {
            rule = RULES[inRulesPosition];
            this.firsts = firsts;
        }

        public Rule getRule() {
            return rule;
        }

        public String[] getFirsts() {
            return firsts;
        }
    }
}
