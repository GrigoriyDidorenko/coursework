package net.didorenko.syntaxical;

import net.didorenko.exception.LineFounder;
import net.didorenko.general.Grammar;
import net.didorenko.general.Keyword;
import net.didorenko.general.Rule;
import net.didorenko.lexer.Lexical;
import net.didorenko.tree.Node;
import net.didorenko.tree.node.parts.Variable;

import java.util.ArrayList;
import java.util.Arrays;

public class Syntactical extends LineFounder {


    private static Rule.Term[] terms;
    private static Node<Rule.Term> treeRoot;
    private static int currentPosition = 0;
    private static ArrayList<Variable> idArray = new ArrayList<>();

    private static String lastType = "";
    private static boolean inBody = false;

    public static void inspect() throws UnexpectedSymbolException, UndeclaredVariableException {
        postConstruct(Lexical.getTokens());
        treeRoot = new Node<>(new Rule.Term(false, Grammar.getInstance().PROGRAM));
        inspectNode(treeRoot);
    }

    public static void postConstruct(ArrayList<String> tokens) {
        terms = new Rule.Term[tokens.size()];
        for (int i = 0; i < tokens.size(); i++) terms[i] = new Rule.Term(true, tokens.get(i));
    }

    public static Rule.Term[] getTerms() {
        return terms;
    }

    public static Node<Rule.Term> getTreeRoot() {
        return treeRoot;
    }

    public static ArrayList<Variable> getIdArray() {
        return idArray;
    }

    private static void inspectNode(Node<Rule.Term> inspectingNode) throws UnexpectedSymbolException, UndeclaredVariableException {
        Rule rightRule = smartFindRule(inspectingNode.getData().getString());
        if (rightRule.getChildren()[0].getString().equals(Grammar.getInstance().E)) {
            clean(inspectingNode);
            return;
        }
        for (int i = 0; i < rightRule.getChildren().length; i++) {
            Rule.Term inToInspectingTerm = rightRule.getChildren()[i];
            if (inToInspectingTerm.isTerminalOrReserved()) {
                if (!isCorrect(inToInspectingTerm.getString(), currentPosition)) error();
                else {
                    Node<Rule.Term> newChildren = new Node<>(terms[currentPosition]/*new Rule.Term(true, inToInspectingTerm.getString())*/);
                    newChildren.setParent(inspectingNode);
                    inspectingNode.addChild(newChildren);
                    currentPosition++;
                }
            } else {
                Node<Rule.Term> newChildren = new Node<>(new Rule.Term(false, inToInspectingTerm.getString()));
                newChildren.setParent(inspectingNode);
                inspectingNode.addChild(newChildren);
                inspectNode(newChildren);
            }
        }
    }

    private static boolean isCorrect(String termString, int inTermsPosition) throws UndeclaredVariableException, UnexpectedSymbolException {
        analyzeToken(inTermsPosition);
        return terms[inTermsPosition].getString().equals(termString);
    }

    private static Rule smartFindRule(String string) throws UnexpectedSymbolException, UndeclaredVariableException {
        ArrayList<Rule> fondRules = new ArrayList<>();
        for (Rule rule : Grammar.getInstance().RULES) if (rule.getParent().getString().equals(string)) fondRules.add(rule);
        if (fondRules.size() == 1) return fondRules.get(0);

        Grammar.RuleMatcher[] helper = Grammar.getInstance().HELPER;
        for (int i = 0; i < fondRules.size(); i++) {
            Rule rule = fondRules.get(i);
            for (Grammar.RuleMatcher matcher : helper) {
                if (rule == matcher.getRule()) {
                    String[] firsts = matcher.getFirsts();
                    switch (firsts.length) {
                        case 0: {
                            if (doDifferentScan(matcher.getRule()))
                                return rule;
                            else {
                                fondRules.remove(rule);
                                i--;
                            }
                            break;
                        }
                        case 1: {
                            if (isCorrect(firsts[0], currentPosition)) return rule;
                            fondRules.remove(rule);
                            i--;
                            break;
                        }
                        default: {
                            int firstElIndex = findStringPositionFromIndex(currentPosition, firsts[0]);
                            boolean isEquals = false;
                            if (!(firstElIndex == -1 || firstElIndex == terms.length - 1)) {
                                for (int j = 1; j < firsts.length; j++)
                                    if (isCorrect(firsts[j], firstElIndex + 1)) isEquals = true;
                                if (isEquals) return rule;
                            }
                            fondRules.remove(rule);
                            i--;
                        }
                    }
                }
            }
        }

        if (fondRules.isEmpty()) return error();
        return fondRules.get(0);
    }

    private static void analyzeToken(int inTermsPosition) throws UndeclaredVariableException, UnexpectedSymbolException {
        String termString = terms[inTermsPosition].getString();
        if (termString.equals(Keyword.RESERVED_WORDS[1]/* begin */)) inBody = true;

        //������������� �� ������ � ������. ������: -4 ��� -name
        boolean isMinus = (termString.charAt(0) == '-') && (termString.length() != 1);
        if (isMinus) termString = termString.substring(1);

        //�������� �� �����
        boolean isNumber = true;
        for (int i = 0; i < termString.length(); i++) {
            if (!Character.isDigit(termString.charAt(i)) && termString.charAt(i) != '.') {
                isNumber = false;
                break;
            }
        }
        if (isNumber) {
            Rule.Term newTerm = new Rule.Term(false, Grammar.getInstance().NUMBER);
            if (isMinus) termString = "-" + termString;
            newTerm.setValue(termString);
            terms[inTermsPosition] = newTerm;
            return;
        }

        //�������� �� ���
        if (Keyword.isType(termString)) {
            Rule.Term newTerm = new Rule.Term(false, Grammar.getInstance().TYPE);
            newTerm.setValue(termString);
            terms[inTermsPosition] = newTerm;
            lastType = termString;
            return;
        }

        //�������� �� ��� ������
        if (Keyword.isMethod(termString)) {
            Rule.Term newTerm = new Rule.Term(false, Grammar.getInstance().MET_NAME);
            newTerm.setValue(termString);
            terms[inTermsPosition] = newTerm;
            return;
        }

        //�������� �� ��� ����������
        if (Keyword.isMathFunction(termString)) {
            Rule.Term newTerm = new Rule.Term(false, Grammar.getInstance().FUNC_NAME);
            newTerm.setValue(termString);
            terms[inTermsPosition] = newTerm;
            return;
        }

        //�������� �� BOOL_SIGN
        if (termString.equals(Keyword.BOOL_OPERATIONS[0]/* < */) ||
                termString.equals(Keyword.BOOL_OPERATIONS[1]/* > */) ||
                termString.equals(Keyword.BOOL_OPERATIONS[2]/* = */) ||
                termString.equals(Keyword.BOOL_OPERATIONS[3]/* != */)) {
            Rule.Term newTerm = new Rule.Term(false, Grammar.getInstance().BOOL_SIGN);
            newTerm.setValue(termString);
            terms[inTermsPosition] = newTerm;
            return;
        }

        //�������� �� HP_SIGN
        if (termString.equals(Keyword.MATH_OPERATIONS[2]/* * */) ||
                termString.equals(Keyword.MATH_OPERATIONS[3]/* / */)) {
            Rule.Term newTerm = new Rule.Term(false, Grammar.getInstance().HP_SIGN);
            newTerm.setValue(termString);
            terms[inTermsPosition] = newTerm;
            return;
        }

        //�������� �� LP_SIGN
        if (termString.equals(Keyword.MATH_OPERATIONS[0]/* + */) ||
                termString.equals(Keyword.MATH_OPERATIONS[1]/* - */)) {
            Rule.Term newTerm = new Rule.Term(false, Grammar.getInstance().LP_SIGN);
            newTerm.setValue(termString);
            terms[inTermsPosition] = newTerm;
            return;
        }

        //�������� �� STRING
        if (inTermsPosition != 0
                && terms[inTermsPosition - 1].getString().equals("\"")
                && terms[inTermsPosition + 1].getString().equals("\"")
                && !termString.equals(",")) {
            Rule.Term newTerm = new Rule.Term(false, Grammar.getInstance().STRING);
            termString = termString.replace("\\w", " ");
            newTerm.setValue(termString);
            terms[inTermsPosition] = newTerm;
            return;
        }

        if (Character.isAlphabetic(termString.charAt(0))
                && !Keyword.isRegisteredWord(termString)
                && !Grammar.getInstance().isNonTerminal(termString)
                && !terms[inTermsPosition - 1].getString().equals("\"")) {
            Rule.Term newTerm = new Rule.Term(false, Grammar.getInstance().ID);
            if (!inBody || terms[inTermsPosition + 1].getString().equals(":=")) newTerm.setString(Grammar.getInstance().ONLY_ID);

            if (!inBody) {
                if (findIdDataByVariableName(termString) != null)
                    throw new UnexpectedSymbolException("Such variable \"" +
                            termString + "\" is already defined. Change this at [" + findExceptionPosition(inTermsPosition, Lexical.getLineIndexes()) + "]");
                Variable newNodeVariable = new Variable(termString, lastType);
                newTerm.setVariable(newNodeVariable);
                idArray.add(newNodeVariable);
            } else {
                Variable variable = findIdDataByVariableName(termString);
                if (variable == null) {
                    throw new UndeclaredVariableException("Don't know variable \"" + termString + "\" at [" +
                            findExceptionPosition(inTermsPosition, Lexical.getLineIndexes()) + "]" + ". Define it at VAR section. \t");
                }
                newTerm.setVariable(variable);
            }
            if (isMinus) newTerm.setVariable(new Variable("-" + termString, newTerm.getVariable().getVariableType()));
            terms[inTermsPosition] = newTerm;
        }
    }

    private static boolean doDifferentScan(Rule rule) throws UndeclaredVariableException, UnexpectedSymbolException {
        analyzeToken(currentPosition);
        int ruleIndex = Arrays.asList(Grammar.getInstance().RULES).indexOf(rule);
        switch (ruleIndex) {
            case 9: {
                String s = terms[currentPosition].getString();
                return s.equals(Grammar.getInstance().ONLY_ID)
                        || s.equals(Keyword.RESERVED_WORDS[2]/* if */)
                        || s.equals(Keyword.RESERVED_WORDS[4]/* while */)
                        || s.equals(Grammar.getInstance().MET_NAME);
            }
        }
        return false;
    }

    private static int findStringPositionFromIndex(int start, String string) throws UndeclaredVariableException, UnexpectedSymbolException {
        for (int i = start; i < terms.length; i++) {
            analyzeToken(i);
            if (terms[i].getString().equals(string)) return i;
        }
        return -1;
    }

    private static Rule error() throws UnexpectedSymbolException {
        throw new UnexpectedSymbolException("Check corrective near symbol \"" +
                terms[currentPosition].smartGetString() + "\" at line [" + findExceptionPosition(currentPosition, Lexical.getLineIndexes()) + "]");
    }

    private static Variable findIdDataByVariableName(String variableName) {
        for (Variable variable : idArray) if (variable.getVariableName().equals(variableName)) return variable;
        return null;
    }

    private static void clean(Node<Rule.Term> inspectingNode) {
        Node<Rule.Term> parent = inspectingNode.getParent();
        parent.getChildren().remove(inspectingNode);
        if (parent.getChildren().isEmpty()) clean(parent);
    }

}
