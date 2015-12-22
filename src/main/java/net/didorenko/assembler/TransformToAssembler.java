package net.didorenko.assembler;

import net.didorenko.general.Rule;
import net.didorenko.tree.Node;
import net.didorenko.tree.node.parts.Variable;

import java.util.ArrayList;



public class TransformToAssembler {

    private static final int UNDEFINED = -1;
    private static final int INT = 1;
    private static final int DOUBLE = 2;
    private static final int STRING = 3;

    private int currentAvailableLabel = 1;
    private int currentAvailableText = 1;

    private Node<Rule.Term> root;
    private ArrayList<Variable> ids;
    private StringBuilder outText = new StringBuilder("%include \"io.inc\"\n"
            + "extern printf\n\n");

    public TransformToAssembler(Node<Rule.Term> root, ArrayList<Variable> ids) {
        this.root = root;
        this.ids = ids;
    }

    public String getOutText() {
        return outText.toString();
    }

    public void genOutText() {
        outText.append("section .data\n");
        genVariables();

        outText.append("section .text\n");
        outText.append("global CMAIN\n");
        outText.append("CMAIN:\n");
        outText.append("mov ebp, esp\n\n");
        genProgramm();
        outText.append("xor eax, eax\n");
        outText.append("mov esp, ebp\n");
        outText.append("ret\n");
    }

    private void genProgramm() {
        for (int i = 0; i < root.getChildren().size(); i++)
            if (root.getChildAt(i).getData().getString().equals("begin")) {
                root = root.getChildAt(i + 1);
                break;
            }
        genPartOfTree(root, outText);
    }

    private void genVariables() {
        if (!ids.isEmpty()) {
            for (Variable data : ids) {
                StringBuilder define = new StringBuilder(data.getVariableName());
                if (data.getVariableType().equals("string")) define.append(" db '', ");
                else define.append(" dd ");
                define.append("0");
                outText.append(define).append("\n");
            }
        }
    }


    private void genPartOfTree(Node<Rule.Term> node, StringBuilder appendingDestination) {
        for (int i = 0; i < node.getChildren().size(); i++) {
            Node<Rule.Term> child = node.getChildAt(i);
            switch (child.getData().getString()) {
                case "EXPRESSIONS_END": {
                    genPartOfTree(child.getChildAt(0), appendingDestination);
                    break;
                }
                case "EXPRESSIONS": {
                    genPartOfTree(child, appendingDestination);
                    break;
                }
                case "EXPRESSION": {
                    Node<Rule.Term> expression = child.getChildAt(0);
                    String expressionType = expression.getData().getString();
                    switch (expressionType) {
                        case "ASSIGNMENT": {
                            genAssignment(expression, appendingDestination);
                            break;
                        }
                        case "CONDITION": {
                            genCondition(expression, appendingDestination);
                            break;
                        }
                        case "CYCLE": {
                            genCycle(expression, appendingDestination);
                            break;
                        }
                        case "METHOD": {
                            genMethod(expression, appendingDestination);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void assignValue(String varName, String value) {
        int varPos = outText.indexOf(varName + " db");
        int left = outText.indexOf("'", varPos);
        int right = outText.indexOf("'", left + 1);
        outText.replace(left + 1, right, value);
    }

    private void genAssignment(Node<Rule.Term> node, StringBuilder appendingDestination) {
        StringBuilder assignment = new StringBuilder();
        int varType = UNDEFINED;
        varType = defineTypeId(node, varType);
        if (varType != STRING) {
            genValue(node.getChildAt(2), assignment, varType);
            assignment.append("mov dword[").append(node.getChildAt(0).getData().smartGetString()).append("], eax\n");
        } else assignValue(node.getChildAt(0).getData().smartGetString(),
                node.getChildAt(2).getChildAt(0).getChildAt(0).getChildAt(1).getData().smartGetString());
        appendingDestination.append(assignment).append("\n");
    }

    private void genHpMExp(Node<Rule.Term> node, StringBuilder value, int varType) {
        StringBuilder hpMExp = new StringBuilder();
        ArrayList<Node<Rule.Term>> children = (ArrayList<Node<Rule.Term>>) node.getChildren();
        genArgument(children.get(0), hpMExp, varType);
        if (children.size() == 2) {
            switch (varType) {
                case DOUBLE:
                case INT: {
                    hpMExp.append("push eax\n");
                    genHpMExp(children.get(1).getChildAt(1), hpMExp, varType);
                    hpMExp.append("mov ebx, eax\n");
                    hpMExp.append("pop eax\n");
                    genBinOperation(children.get(1).getChildAt(0).getData().smartGetString(), hpMExp, varType);
                    break;
                }
                
            }
        }
        value.append(hpMExp);
    }

    private void genValue(Node<Rule.Term> node, StringBuilder assignment, int varType) {
        StringBuilder value = new StringBuilder();
        ArrayList<Node<Rule.Term>> children = (ArrayList<Node<Rule.Term>>) node.getChildren();
        genHpMExp(children.get(0), value, varType);
        if (children.size() == 2) {
            switch (varType) {
                case DOUBLE:
                case INT: {
                    value.append("push eax\n");
                    genValue(children.get(1).getChildAt(1), value, varType);
                    value.append("mov ebx, eax\n");
                    value.append("pop eax\n");
                    genBinOperation(children.get(1).getChildAt(0).getData().smartGetString(), value, varType);
                    break;
                }
            }
        }
        assignment.append(value).append("\n");
    }

    private void genBinOperation(String sign, StringBuilder source, int varType) {
        StringBuilder binOperation = new StringBuilder();
        switch (varType) {
            case INT: {
                switch (sign) {
                    case "+": {
                        binOperation.append("add ");
                        binOperation.append("eax, ebx\n");
                        break;
                    }
                    case "-": {
                        binOperation.append("sub ");
                        binOperation.append("eax, ebx\n");
                        break;
                    }
                    case "*": {
                        binOperation.append("xor edx, edx\n");
                        binOperation.append("imul ebx\n");
                        break;
                    }
                    case "/": {
                        binOperation.append("xor edx, edx\n");
                        binOperation.append("idiv ebx\n");
                        break;
                    }
                }
                break;
            }
            case DOUBLE: {
                binOperation.append("push eax\n");
                binOperation.append("fld dword[esp]\n");
                binOperation.append("push ebx\n");
                binOperation.append("fld dword[esp]\n");
                switch (sign) {
                    case "+": {
                        binOperation.append("faddp\n");
                        break;
                    }
                    case "-": {
                        binOperation.append("fsubp\n");
                        break;
                    }
                    case "*": {
                        binOperation.append("fmulp\n");
                        break;
                    }
                    case "/": {
                        binOperation.append("fdivp\n");
                        break;
                    }
                }
                binOperation.append("fstp dword[esp+4]\n");
                binOperation.append("pop eax\n");
                binOperation.append("pop eax\n");
                break;
            }
        }
        source.append(binOperation);
    }

    private void genArgument(Node<Rule.Term> node, StringBuilder hpMExp, int varType) {
        StringBuilder argument = new StringBuilder();
        ArrayList<Node<Rule.Term>> children = (ArrayList<Node<Rule.Term>>) node.getChildren();
        switch (children.size()) {
            case 1: {
                if (varType == UNDEFINED) defineTypeId(children.get(0), varType);
                Node<Rule.Term> child = children.get(0);
                String childStringDate = child.getData().smartGetString();
                boolean isMinus = childStringDate.charAt(0) == '-';
                if (isMinus) childStringDate = childStringDate.substring(1);
                switch (varType) {
                    case INT: {
                        if (child.getData().getVariable() != null) childStringDate = "dword [" + childStringDate + "]";
                        argument.append("mov eax, ").append(childStringDate).append("\n");
                        if (isMinus) argument.append("neg eax\n");
                        break;
                    }
                    case DOUBLE: {
                        if (childStringDate.equals("FUNCTION")) {
                            genFunction(child, argument);
                            break;
                        }
                        if (child.getData().getVariable() != null) childStringDate = "dword [" + childStringDate + "]";
                        else {
                            float f = Float.parseFloat(childStringDate);
                            int i = Float.floatToIntBits(f);
                            childStringDate = Integer.toHexString(i);
                            if (!Character.isDigit(childStringDate.charAt(0))) childStringDate = '0' + childStringDate;
                            childStringDate += 'h';
                        }
                        if (!isMinus) {
                            argument.append("mov eax, ").append(childStringDate).append("\n");
                        } else {
                            argument.append("push ").append(childStringDate).append("\n");
                            argument.append("fld dword[esp]\n");
                            argument.append("fchs\n");
                            argument.append("fstp dword[esp]\n");
                            argument.append("pop eax\n");
                        }
                        break;
                    }
                    case STRING: {
                        addStringVar(childStringDate);
                        break;
                    }
                }
                break;
            }
            case 3: {
                genValue(children.get(1), argument, varType);
                break;
            }
        }
        hpMExp.append(argument);
    }


    private int defineTypeId(Node<Rule.Term> node, int varType) {
        Rule.Term inspectingTerm = node.getChildAt(0).getData();
        if (inspectingTerm.smartGetString().equals("("))
            return defineTypeId(node.getChildAt(1).getChildAt(0).getChildAt(0), varType);

        if (inspectingTerm.smartGetString().equals("\"")) {
            inspectingTerm = node.getChildAt(1).getData();
            return STRING;
        }

        if (inspectingTerm.getVariable() == null) {
            if (inspectingTerm.getString().equals("FUNCTION") || inspectingTerm.getValue().contains("."))
                return DOUBLE;
            return INT;
        } else {
            switch (inspectingTerm.getVariable().getVariableType()) {
                case "int": {
                    varType = INT;
                    break;
                }
                case "double": {
                    varType = DOUBLE;
                    break;
                }
                case "string": {
                    varType = STRING;
                    break;
                }
            }
        }
        return varType;
    }

    private void genCondExpression(Node<Rule.Term> node, StringBuilder condition) {
        StringBuilder condExpression = new StringBuilder();
        int varType = UNDEFINED;
        varType = defineTypeId(node.getChildAt(0).getChildAt(0).getChildAt(0), varType);
        genValue(node.getChildAt(0), condExpression, varType);
        condExpression.append("push eax\n");
        genValue(node.getChildAt(2), condExpression, varType);
        condExpression.append("mov ebx, eax\n");
        condExpression.append("pop eax\n");
        switch (varType) {
            case INT: {
                condExpression.append("cmp eax, ebx\n");
                break;
            }
            case DOUBLE: {
                condExpression.append("push ebx\n");
                condExpression.append("fld dword[esp]\n");
                condExpression.append("pop ebx\n");
                condExpression.append("push eax\n");
                condExpression.append("fld dword[esp]\n");
                condExpression.append("pop eax\n");
                condExpression.append("fcomip\n");
                break;
            }
        }
        switch (node.getChildAt(1).getData().smartGetString()) {
            case "=": {
                condExpression.append("jne ");
                break;
            }
            case "<>": {
                condExpression.append("je ");
                break;
            }
            case ">": {
                condExpression.append("jb ");
                break;
            }
            case "<": {
                condExpression.append("ja ");
                break;
            }
        }
        condExpression.append("Lbl").append(currentAvailableLabel - 1).append("\n");
        condition.append(condExpression);
    }

    private void genCondition(Node<Rule.Term> node, StringBuilder appendingDestination) {
        StringBuilder condition = new StringBuilder();
        int justReservedLabelIndex = currentAvailableLabel;
        currentAvailableLabel++;
        genCondExpression(node.getChildAt(2), condition);
        genCondEnd(node.getChildAt(4), condition);
        if (node.getChildren().size() == 6) condition.append("jmp Lbl").append(currentAvailableLabel).append("\n");
        condition.append("Lbl").append(justReservedLabelIndex).append(":\n");
        justReservedLabelIndex = currentAvailableLabel;
        currentAvailableLabel++;
        if (node.getChildren().size() == 6) {
            genElsePart(node.getChildAt(5), condition);
            condition.append("Lbl").append(justReservedLabelIndex).append(":\n");
        }
        appendingDestination.append(condition).append("\n");
    }

    private void genCondEnd(Node<Rule.Term> node, StringBuilder condition) {
        StringBuilder condEnd = new StringBuilder();
        genPartOfTree(node, condEnd);
        condition.append(condEnd);
    }


    private void genElsePart(Node<Rule.Term> node, StringBuilder condition) {
        StringBuilder elsePart = new StringBuilder();
        genCondEnd(node.getChildAt(1), elsePart);
        condition.append(elsePart);
    }


    private void genCycle(Node<Rule.Term> node, StringBuilder appendingDestination) {
        StringBuilder cycle = new StringBuilder();
        int justReservedLabelIndex = currentAvailableLabel;
        currentAvailableLabel++;
        cycle.append("Lbl").append(currentAvailableLabel).append(":\n");
        genCondExpression(node.getChildAt(2), cycle);
        genCondEnd(node.getChildAt(4), cycle);
        currentAvailableLabel++;
        cycle.append("jmp Lbl").append(currentAvailableLabel - 1).append("\n");
        cycle.append("Lbl").append(justReservedLabelIndex).append(":\n");
        appendingDestination.append(cycle).append("\n");
    }


    private void genMethod(Node<Rule.Term> node, StringBuilder appendingDestination) {
        StringBuilder method = new StringBuilder();
        String methodName = node.getChildAt(0).getData().smartGetString();

        switch (methodName) {
            case "writeln": {
                genPrintln(node, method);
                break;
            }
        }

        appendingDestination.append(method).append("\n");
    }

    private boolean genSinglePrintParsValue(Node<Rule.Term> valueNode, StringBuilder printPars, StringBuilder outputFormat) {
        Node<Rule.Term> argumentTerm = valueNode.getChildAt(0).getChildAt(0);
        int valueType = UNDEFINED;
        valueType = defineTypeId(argumentTerm, valueType);
        boolean isStringVar = false;

        switch (valueType) {
            case STRING: {
                if (argumentTerm.getChildAt(0).getData().getVariable() != null) {
                    isStringVar = true;
                    outputFormat.append(argumentTerm.getChildAt(0).getData().smartGetString()).append("\n");
                    break;
                }
                outputFormat.append(argumentTerm.getChildAt(1).getData().smartGetString());
                break;
            }
            case INT: {
                outputFormat.append("%d");
                genValue(valueNode, printPars, valueType);
                printPars.append("push eax\n");
                break;
            }
            case DOUBLE: {
                outputFormat.append("%f");
                genValue(valueNode, printPars, valueType);
                printPars.append("push eax\n");
                printPars.append("fld dword[esp]\n");
                printPars.append("pop eax\n");
                printPars.append("sub esp, 8\n");
                printPars.append("fstp qword[esp]\n");
                break;
            }
        }
        return isStringVar;
    }

    private void genPrintPars(Node<Rule.Term> node, StringBuilder print) {
        StringBuilder printPars = new StringBuilder();
        StringBuilder outputFormat = new StringBuilder("");

        if (node.getChildren().size() == 1) {
            addStringVar(outputFormat.toString()); 
            printPars.append("push dword printText").append(currentAvailableText - 1).append("\n");
        } else {
            if (node.getChildren().size() == 2) {
                boolean isStringVar = genSinglePrintParsValue(node.getChildAt(0), printPars, outputFormat);
                if (!isStringVar) {
                    addStringVar(outputFormat.toString()); 
                    printPars.append("push dword printText").append(currentAvailableText - 1).append("\n");
                } else printPars.append("push dword ").append(outputFormat.toString());
            } else {
                genPrintParsEnd(node.getChildAt(1), printPars, outputFormat);
                StringBuilder innerOutputFormat = new StringBuilder();
                genSinglePrintParsValue(node.getChildAt(0), printPars, innerOutputFormat);
                if (innerOutputFormat.charAt(innerOutputFormat.length() - 1) == '\n') {
                    innerOutputFormat = innerOutputFormat.deleteCharAt(innerOutputFormat.length() - 1);
                    printPars.append("push dword ").append(innerOutputFormat).append("\n");
                    innerOutputFormat.insert(0, "%");
                }
                outputFormat.insert(0, innerOutputFormat);
                addStringVar(outputFormat.toString()); 
                printPars.append("push dword printText").append(currentAvailableText - 1).append("\n");
            }
        }

        print.append(printPars);
    }

    private void genPrintParsEnd(Node<Rule.Term> node, StringBuilder printPars, StringBuilder outputFormat) {
        if (node.getChildren().size() == 3) genPrintParsEnd(node.getChildAt(2), printPars, outputFormat);
        StringBuilder innerOutputFormat = new StringBuilder();
        boolean isStringVar = genSinglePrintParsValue(node.getChildAt(1), printPars, innerOutputFormat);
        if (isStringVar) {
            innerOutputFormat = innerOutputFormat.deleteCharAt(innerOutputFormat.length() - 1);
            printPars.append("push dword ").append(innerOutputFormat).append("\n");
            innerOutputFormat.insert(0, "%");
        }
        outputFormat.insert(0, innerOutputFormat);
    }

    private void genFunction(Node<Rule.Term> node, StringBuilder argument) {
        StringBuilder function = new StringBuilder();
        String functionName = node.getChildAt(0).getData().smartGetString();

        switch (functionName) {
            case "sqrt": {
                genValue(node.getChildAt(2).getChildAt(0), function, DOUBLE);
                function.append("push eax\n");
                function.append("fld dword[esp]\n");
                function.append("fsqrt\n");
                function.append("fstp dword[esp]\n");
                function.append("pop eax\n");
                break;
            }

            case "exp": {
                genValue(node.getChildAt(2).getChildAt(0), function, DOUBLE);
                function.append("push eax\n");
                genValue(node.getChildAt(2).getChildAt(1).getChildAt(1), function, INT);
                function.append("mov ebx, eax\n");
                function.append("pop eax\n");
                function.append("push eax\n");
                function.append("cmp ebx, 0\n" +
                        " je ifzero" + currentAvailableLabel + "\n" +
                        " pow" + currentAvailableLabel + ":\n" +
                        " dec ebx\n" +
                        " cmp ebx, 0\n" +
                        " je endpow" + currentAvailableLabel + "\n" +
                        " \n" +
                        " fld dword[esp]\n" +
                        " push eax\n" +
                        " fld dword[esp]\n" +
                        " fmulp\n" +
                        " fstp dword[esp]\n" +
                        " pop eax\n" +
                        " jmp pow" + currentAvailableLabel + "\n" +
                        " ifzero" + currentAvailableLabel + ":\n" +
                        " mov eax, 1\n" +
                        " endpow" + currentAvailableLabel + ":\n" +
                        " mov ebx, eax\n" +
                        " pop eax\n" +
                        " mov eax, ebx\n");
                currentAvailableLabel++;
                break;
            }
        }

        argument.append(function).append("\n");
    }


    private void genPrintln(Node<Rule.Term> node, StringBuilder method) {
        StringBuilder print = new StringBuilder();
        genPrintPars(node.getChildAt(2), print);
        print.append("call printf\n");
        method.append(print);
    }


    private void addStringVar(String format) {
        int textStartPosition = outText.indexOf("section .text"); 
        outText.insert(textStartPosition, "printText" + currentAvailableText + " db '" + format + "', 10, 0\n");
        currentAvailableText++;
    }


    
}
