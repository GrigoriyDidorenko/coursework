package net.didorenko.semantic;

import net.didorenko.exception.LineFounder;
import net.didorenko.general.Grammar;
import net.didorenko.general.Rule;
import net.didorenko.syntaxical.Syntactical;
import net.didorenko.tree.Node;

import java.util.ArrayList;

public class Semantic extends LineFounder {

    private static final int UNDEFINED = -1;
    private static final int INT = 1;
    private static final int DOUBLE = 2;
    private static final int STRING = 3;

    private static Rule.Term[] terms;



    private static void checkIsCorrectType(Node<Rule.Term> valueNode, int expectedType) throws WrongTypeException, LogicException {
        checkIsCorrectHpMExp(valueNode.getChildAt(0), expectedType);
        if (valueNode.getChildren().size() == 2) {
            if (expectedType == STRING)
                throw new WrongTypeException("Not allowed math opetarions with string ["
                        + findExceptionPosition(terms, valueNode.getChildAt(1).getChildAt(0).getData()) + "]");
            checkIsCorrectType(valueNode.getChildAt(1).getChildAt(1), expectedType);
        }
    }

    public static void check() throws LogicException, WrongTypeException {
        terms = Syntactical.getTerms();
        inspectNode(Syntactical.getTreeRoot());
    }


    private static void inspectNode(Node<Rule.Term> inspectingNode) throws WrongTypeException, LogicException {
        for (int i = 0; i < inspectingNode.getChildren().size(); i++) {
            Node<Rule.Term> child = inspectingNode.getChildAt(i);
            switch (child.getData().getString()) {
                case "ASSIGNMENT": {
                    inspectWrongTypes(child);
                    continue;
                }
                case "CONDITION": {
                    inspectWrongTypes(child.getChildAt(2));
                    inspectNoVarInCondExp(child.getChildAt(2));
                    inspectNode(child.getChildAt(4));
                    if (child.getChildren().size() == 6) inspectNode(child.getChildAt(5).getChildAt(1));
                    continue;
                }
                case "CYCLE": {
                    inspectWrongTypes(child.getChildAt(2));
                    inspectNoVarInCondExp(child.getChildAt(2));
                    inspectNoModifications(child);
                    inspectNode(child.getChildAt(4));
                    continue;
                }
                case "METHOD": {
                    checkIsCorrectMethodPars(child.getChildAt(2));
                    continue;
                }
            }
            if (!child.getData().isTerminal()) inspectNode(child);
        }
    }

    private static void inspectWrongTypes(Node<Rule.Term> inspectingNode) throws WrongTypeException, LogicException {
        int expectedType = UNDEFINED;
        switch (inspectingNode.getData().getString()) {
            case "ASSIGNMENT": {
                switch (inspectingNode.getChildAt(0).getData().getVariable().getVariableType()) {
                    case "int": {
                        expectedType = INT;
                        break;
                    }
                    case "double": {
                        expectedType = DOUBLE;
                        break;
                    }
                    case "string": {
                        expectedType = STRING;
                        break;
                    }
                }
                checkIsCorrectType(inspectingNode.getChildAt(2), expectedType);
                break;
            }
            case "COND_EXPR": {
                Node<Rule.Term> firstValueArgumentNode = inspectingNode.getChildAt(0).getChildAt(0).getChildAt(0);
                expectedType = defineTypeId(firstValueArgumentNode);
                if (expectedType == STRING)
                    throw new WrongTypeException("Can't compare strings AT["
                            + findExceptionPosition(terms, firstValueArgumentNode.getChildAt(0).getData()) + "]");
                checkIsCorrectType(inspectingNode.getChildAt(0), expectedType);
                checkIsCorrectType(inspectingNode.getChildAt(2), expectedType);
                break;
            }
        }
    }


    private static void checkIsCorrectWritelnMethodParsEnd(Node<Rule.Term> metParsEndNode) throws WrongTypeException, LogicException {
        int realType = defineTypeId(metParsEndNode.getChildAt(1).getChildAt(0).getChildAt(0));
        checkIsCorrectType(metParsEndNode.getChildAt(1), realType);
        if (metParsEndNode.getChildren().size() == 3) checkIsCorrectWritelnMethodParsEnd(metParsEndNode.getChildAt(2));
    }

    private static void checkIsCorrectHpMExp(Node<Rule.Term> hpMExpNode, int expectedType) throws WrongTypeException, LogicException {
        int realType = defineTypeId(hpMExpNode.getChildAt(0));
        if (realType != expectedType && (!(realType == INT && expectedType == DOUBLE)
                || hpMExpNode.getChildAt(0).getChildAt(0).getData().getString().equals(Grammar.getInstance().ID)))
            throw new WrongTypeException("Wrong type AT["
                    + findExceptionPosition(terms, hpMExpNode.getChildAt(0).getChildAt(0).getData()) + "]");
        if (hpMExpNode.getChildren().size() == 2) {
            if (expectedType == STRING)
                throw new WrongTypeException("Can't co any math operations with stringt NEAR["
                        + findExceptionPosition(terms, hpMExpNode.getChildAt(1).getChildAt(0).getData()) + "]");
            checkIsCorrectHpMExp(hpMExpNode.getChildAt(1).getChildAt(1), expectedType);
        }
    }

    private static void checkIsCorrectMethodPars(Node<Rule.Term> metParsNode) throws WrongTypeException, LogicException {
        switch (metParsNode.getParent().getChildAt(0).getData().smartGetString()) {
            case "writeln": {
                int realType = defineTypeId(metParsNode.getChildAt(0).getChildAt(0).getChildAt(0));
                checkIsCorrectType(metParsNode.getChildAt(0), realType);
                if (metParsNode.getChildren().size() == 3)
                    checkIsCorrectWritelnMethodParsEnd(metParsNode.getChildAt(1));
                break;
            }
            case "read": {
                break;
            }
        }
    }

    private static int defineTypeId(Node<Rule.Term> argumentNode) throws WrongTypeException, LogicException {
        if (argumentNode.getChildren().size() == 1) {
            Rule.Term inspectingTerm = argumentNode.getChildAt(0).getData();
            if (inspectingTerm.getString().equals(Grammar.getInstance().FUNCTION)) {
                switch (argumentNode.getChildAt(0).getChildAt(0).getData().smartGetString()) {
                    case "sqrt": {
                        signatureQuadraticFunction(argumentNode.getChildAt(0), "sqrt");
                        break;
                    }
                    case "sqr": {
                        signatureQuadraticFunction(argumentNode.getChildAt(0), "sqr");
                        break;
                    }
                    case "exp": {
                        signatureExpFunction(argumentNode.getChildAt(0), "exp");
                        break;
                    }
                }
                return DOUBLE;
            }
            if (inspectingTerm.getVariable() == null) {
                if (inspectingTerm.getValue().contains(".")) return DOUBLE;
                return INT;
            } else {
                switch (inspectingTerm.getVariable().getVariableType()) {
                    case "int": {
                        return INT;
                    }
                    case "double": {
                        return DOUBLE;
                    }
                    case "string": {
                        return STRING;
                    }
                }
            }
        } else {
            Node<Rule.Term> middleChild = argumentNode.getChildAt(1);
            if (middleChild.getData().getString().equals(Grammar.getInstance().STRING)) return STRING;
            return defineTypeId(middleChild.getChildAt(0).getChildAt(0));
        }
        return UNDEFINED;
    }


    private static void inspectNoVarInCondExp(Node<Rule.Term> condExpNode) throws LogicException {
        if (!(isVariableInValue(condExpNode.getChildAt(0), null) || isVariableInValue(condExpNode.getChildAt(2), null)))
            throw new LogicException("Senseless block NEAR["
                    + findExceptionPosition(terms, condExpNode.getChildAt(1).getData()) + "]");
    }

    private static boolean isVariableInValue(Node<Rule.Term> node, Rule.Term varTerm) {
        for (Node<Rule.Term> child : node.getChildren()) {
            if (child.getData().getVariable() != null) {
                if (varTerm != null) varTerm.stole(child.getData());
                return true;
            }
            if (isVariableInValue(child, varTerm)) return true;
        }
        return false;
    }

    private static void getVariablesFromValue(Node<Rule.Term> node, ArrayList<String> inValuesVars) {
        for (Node<Rule.Term> child : node.getChildren()) {
            if (child.getData().getVariable() != null) {
                inValuesVars.add(child.getData().getVariable().getVariableName());
            }
            getVariablesFromValue(child, inValuesVars);
        }
    }


    private static void signatureExpFunction(Node<Rule.Term> node, String funtionName) throws WrongTypeException, LogicException {
        Node<Rule.Term> expFunction = node.getChildAt(2);
        if (expFunction.getChildren().size() != 2)
            throw new LogicException(funtionName + " function require only one parameter AT["
                    + findExceptionPosition(terms, node.getChildAt(0).getData()) + "]");
        Node<Rule.Term> argumentNode = expFunction.getChildAt(0).getChildAt(0).getChildAt(0);
        int realType = defineTypeId(argumentNode);
        if (realType != DOUBLE)
            throw new WrongTypeException(funtionName + " function require only double value AT["
                    + findExceptionPosition(terms, argumentNode.getChildAt(0).getData()) + "]");
    }

    private static void signatureQuadraticFunction(Node<Rule.Term> node, String funtionName) throws WrongTypeException, LogicException {
        Node<Rule.Term> quadraticFunction = node.getChildAt(2);
        if (quadraticFunction.getChildren().size() != 1)
            throw new LogicException(funtionName + " function require only one parameter AT["
                    + findExceptionPosition(terms, node.getChildAt(0).getData()) + "]");
        Node<Rule.Term> argumentNode = quadraticFunction.getChildAt(0).getChildAt(0).getChildAt(0);
        int realType = defineTypeId(argumentNode);
        if (realType != DOUBLE)
            throw new WrongTypeException(funtionName + " function require only double value AT["
                    + findExceptionPosition(terms, argumentNode.getChildAt(0).getData()) + "]");
        if (funtionName.equals("sqrt"))
            if (argumentNode.getChildAt(0).getData().smartGetString().charAt(0) == '-')
                throw new LogicException(funtionName + " function require only POSITIVE double value AT["
                        + findExceptionPosition(terms, argumentNode.getChildAt(0).getData()) + "]");
        checkIsCorrectType(quadraticFunction.getChildAt(0), realType);
    }

    private static void inspectNoModifications(Node<Rule.Term> cycleNode) throws LogicException {
        ArrayList<String> inExpressionVars = new ArrayList<>();
        findAssignmentVars(cycleNode.getChildAt(4), inExpressionVars);

        ArrayList<String> inValuesVars = new ArrayList<>();
        getVariablesFromValue(cycleNode.getChildAt(2).getChildAt(0), inValuesVars);
        getVariablesFromValue(cycleNode.getChildAt(2).getChildAt(2), inValuesVars);
        boolean isHave = false;
        for (String s : inValuesVars)
            if (inExpressionVars.contains(s)) {
                isHave = true;
                break;
            }
        if (!isHave)
            throw new LogicException("No modification of cycle variable FOUND ["
                    + findExceptionPosition(terms, cycleNode.getChildAt(2).getChildAt(1).getData()) + "]");
    }

    private static void findAssignmentVars(Node<Rule.Term> node, ArrayList<String> vars) {

        for (int i = 0; i < node.getChildren().size(); i++) {
            Node<Rule.Term> child = node.getChildAt(i);
            if (child.getData().getString().equals(Grammar.getInstance().EXPRESSIONS_END))
                child = child.getChildAt(0);
            if (child.getData().getString().equals(Grammar.getInstance().EXPRESSIONS))
                findAssignmentVars(child, vars);
            else if (child.getData().getString().equals(Grammar.getInstance().EXPRESSION))
                child = child.getChildAt(0);
            switch (child.getData().getString()) {
                case "ASSIGNMENT": {
                    vars.add(child.getChildAt(0).getData().smartGetString());
                    continue;
                }
                case "CYCLE": {
                    int pos = 0;
                    if (child.getChildAt(4).getChildren().size() == 3) pos = 1;
                    findAssignmentVars(child.getChildAt(4).getChildAt(pos), vars);
                    break;
                }
                case "CONDITION": {
                    int pos = 0;
                    if (child.getChildAt(4).getChildren().size() == 3) pos = 1;
                    findAssignmentVars(child.getChildAt(4).getChildAt(pos), vars);
                    if (child.getChildren().size() == 6) {
                        pos = 0;
                        if (child.getChildAt(5).getChildAt(1).getChildren().size() == 3) pos = 1;
                        findAssignmentVars(child.getChildAt(5).getChildAt(1).getChildAt(pos), vars);
                    }
                    break;
                }
            }

        }
    }

}
